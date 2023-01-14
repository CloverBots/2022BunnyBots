package frc.robot;

import edu.wpi.first.wpilibj.I2C.Port;

public final class IDs {
  // ControllerPorts
  public static final int CONTROLLER_DRIVE_PORT = 0;
  public static final int CONTROLLER_OPERATOR_PORT = 1;

  // Drive
  public static final int DRIVE_LEFT_LEAD_DEVICE = 1;
  public static final int DRIVE_LEFT_FOLLOW_DEVICE = 2;
  public static final int DRIVE_RIGHT_LEAD_DEVICE = 3;
  public static final int DRIVE_RIGHT_FOLLOW_DEVICE = 4;

  // Intake
  public static final int INTAKE_LEAD_DEVICE = 5;

  // Lift
  public static final int LIFT_UPPER_SWITCH = 9;
  public static final int LIFT_LOWER_SWITCH = 8;
  public static final int LIFT_WINCH_DEVICE0 = 8;
  public static final int LIFT_WINCH_DEVICE1 = 9;

  // NavX Gyro
  public static final Port AHRS_PORT_ID = Port.kMXP;

  // Beam
  public static final double BEAM_BALANACED_DRIVE_KP = 0.005; //.015, lower = slower, as weight goes up number needs to go up
  public static final double BEAM_BALANCED_GOAL_DEGREES = 0;
  public static final double BEAM_BALANCED_ANGLE_THRESHOLD_DEGREES = 1;
  public static final double BACKWARDS_BALANCING_EXTRA_POWER_MULTIPLIER = 1.35;

}
