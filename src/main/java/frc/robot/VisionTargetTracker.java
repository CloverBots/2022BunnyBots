package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * To find the IP address of the vision system, open SmartDashboard
 * from within the Driver station and it will be displayed.
 * Port 5800 gives camera view, port 5801 opens a config view.
 */

/**
 * Represents the vision tracking system for the robot and provides read/write
 * access to the vision configuration.
 */
public class VisionTargetTracker {
  /**
   * Represents the LED mode for the camera.
   */
  public enum LedMode {
    /**
     * Maintain the current LED mode.
     */
    CURRENT(0),

    /**
     * Force the LEDs to be off.
     */
    FORCE_OFF(1),

    /**
     * Force the LEDs to blink.
     */
    FORCE_BLINK(2),

    /**
     * Force the LEDs to be on.
     */
    FORCE_ON(3);

    private final int value;

    private LedMode(int value) {
      this.value = value;
    }

    /**
     * Gets the integer representation of the {@link LedMode}.
     */
    public int getValue() {
      return value;
    }
  }

  private static final String LIMELIGHT_TABLE_NAME = "limelight";
  private static final String LIMELIGHT_TABLE_ENTRY_X = "tx";
  private static final String LIMELIGHT_TABLE_ENTRY_Y = "ty";
  private static final String LIMELIGHT_TABLE_ENTRY_A = "ta";
  // tv = 0 if no valid targets identified, tv = 1 for valid target
  private static final String LIMELIGHT_TABLE_ENTRY_VALID = "tv";
  private static final String LIMELIGHT_TABLE_ENTRY_LED_MODE = "ledMode";

  private final VisionConfiguration configuration;
  private final NetworkTable table;
  private final NetworkTableEntry tx;
  private final NetworkTableEntry ty;
  private final NetworkTableEntry ta;
  private final NetworkTableEntry tv;
  private final NetworkTableEntry ledMode;

  /**
   * Constructs a new {@link VisionTargetTracker} instance.
   */
  public VisionTargetTracker(VisionConfiguration configuration) {
    this.configuration = configuration;

    table = NetworkTableInstance.getDefault().getTable(LIMELIGHT_TABLE_NAME);
    tx = table.getEntry(LIMELIGHT_TABLE_ENTRY_X);
    ty = table.getEntry(LIMELIGHT_TABLE_ENTRY_Y);
    ta = table.getEntry(LIMELIGHT_TABLE_ENTRY_A);
    tv = table.getEntry(LIMELIGHT_TABLE_ENTRY_VALID);
    ledMode = table.getEntry(LIMELIGHT_TABLE_ENTRY_LED_MODE);
  }

  /**
   * Gets the center X offset of the vision target in degrees
   * ranging from -27.0 to 27.0.
   */
  public double getX() {
    return tx.getDouble(0.0);
  }

  /**
   * Gets the center Y offset of the vision target in degrees
   * ranging from -20.5 to 20.5.
   */
  public double getY() {
    return ty.getDouble(0.0);
  }

  /**
   * Gets the area of the vision target ranging from 0.0 to 100.0, where
   * 100.0 is the full area of the camera view.
   */
  public double getArea() {
    return ta.getDouble(0.0);
  }

  /**
   * Get whether there is a valid target visible
   */
  public boolean isValid() {
    if (tv.getNumber(0).intValue() == 0) {
      return false;
    }

    return true;
  }

  /**
   * Gets the {@link LedMode} of the camera.
   */
  public LedMode getLedMode() {
    var intValue = ledMode.getNumber(0).intValue();
    return LedMode.values()[intValue];
  }

  /**
   * Sets the {@link LedMode} of the camera.
   * 
   * @param mode The {@link LedMode} of the camera.
   */
  public void setLedMode(LedMode mode) {
    ledMode.setNumber(mode.getValue());
  }

  /**
   * Computes the distance to the target, usually in inches.
   * 
   * @param config The configuration describing camera and field measurements.
   */
  public double computeTargetDistance() {
    var targetHeight = configuration.getTargetHeight();
    var cameraHeight = configuration.getCameraHeight();
    var cameraPitch = configuration.getCameraPitch();

    return (targetHeight - cameraHeight) / Math.tan(Math.toRadians(cameraPitch + getY()));
  }
}
