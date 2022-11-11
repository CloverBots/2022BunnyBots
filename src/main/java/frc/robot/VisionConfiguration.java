package frc.robot;

public class VisionConfiguration {
  private final double targetHeight;
  private final double cameraHeight;
  private final double cameraPitch;

  /**
   * Constructs a new {@link VisionConfiguration} instance.
   * 
   * @param targetHeight The height of the target, usually in inches.
   * @param cameraHeight The height of the camera off the ground, usually in
   *                     inches.
   * @param cameraPitch  The pitch of the camera in degrees.
   */
  public VisionConfiguration(double targetHeight, double cameraHeight, double cameraPitch) {
    this.targetHeight = targetHeight;
    this.cameraHeight = cameraHeight;
    this.cameraPitch = cameraPitch;
  }

  /**
   * Gets the height of the target, usually in inches.
   */
  public double getTargetHeight() {
    return targetHeight;
  }

  /**
   * Gets the height of the camera, usually in inches.
   */
  public double getCameraHeight() {
    return cameraHeight;
  }

  /**
   * Gets the pitch of the camera in degrees.
   */
  public double getCameraPitch() {
    return cameraPitch;
  }
}