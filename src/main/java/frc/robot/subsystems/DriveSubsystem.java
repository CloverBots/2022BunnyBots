// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.SparkMaxPIDController;
import frc.robot.RobotLifecycleCallbacks;

import frc.robot.IDs;
import frc.robot.NavXGyro;

public class DriveSubsystem extends SubsystemBase implements RobotLifecycleCallbacks {
  public static final double WHEEL_DIAMETER_METERS = 0.1524; // = 6 inches
  public static final double ENCODER_METERS_PER_ROTATION = WHEEL_DIAMETER_METERS * Math.PI; 
  public static final double ENCODER_TICKS_PER_ROTATION = 8; // Determined by running robot and counting wheel rotations
  
  private final CANSparkMax leftLeadMotor = new CANSparkMax(IDs.DRIVE_LEFT_LEAD_DEVICE, MotorType.kBrushless);
  private final CANSparkMax rightLeadMotor = new CANSparkMax(IDs.DRIVE_RIGHT_LEAD_DEVICE, MotorType.kBrushless);
  private final CANSparkMax leftFollowMotor = new CANSparkMax(IDs.DRIVE_LEFT_FOLLOW_DEVICE, MotorType.kBrushless);
  private final CANSparkMax rightFollowMotor = new CANSparkMax(IDs.DRIVE_RIGHT_FOLLOW_DEVICE, MotorType.kBrushless);

  private static final double LIME_DISTANCE_PID_P = 0.02;
  private static final double LIME_DISTANCE_PID_I = 0.0;
  private static final double LIME_DISTANCE_PID_D = 0.0;

  private static final double LIME_ROTATE_PID_P = 0.01;
  private static final double LIME_ROTATE_PID_I = 0.0;
  private static final double LIME_ROTATE_PID_D = 0.0;
  private static final double LIME_ROTATE_PID_DEFAULT_SETPOINT = 0;

  private static final double DRIVE_DISTANCE_PID_P = 0.8;
  private static final double DRIVE_DISTANCE_PID_I = 0.0;
  private static final double DRIVE_DISTANCE_PID_D = 0.06;

  private static final double DRIVE_ROTATE_PID_P = 0.0005;
  private static final double DRIVE_ROTATE_PID_I = 0.00;
  private static final double DRIVE_ROTATE_PID_D = 0.0;

  public final PIDController limeDistancePidController = new PIDController(
      LIME_DISTANCE_PID_P,
      LIME_DISTANCE_PID_I,
      LIME_DISTANCE_PID_D);

  public final PIDController limeRotationPidController = new PIDController(
      LIME_ROTATE_PID_P,
      LIME_ROTATE_PID_I,
      LIME_ROTATE_PID_D);

  public final PIDController driveDistancePidController = new PIDController(
      DRIVE_DISTANCE_PID_P,
      DRIVE_DISTANCE_PID_I,
      DRIVE_DISTANCE_PID_D);

  public final PIDController driveRotatePidController = new PIDController(
      DRIVE_ROTATE_PID_P,
      DRIVE_ROTATE_PID_I,
      DRIVE_ROTATE_PID_D);

  public final NavXGyro navXGyro = new NavXGyro();

  public DriveSubsystem() {
    leftFollowMotor.follow(leftLeadMotor);
    rightFollowMotor.follow(rightLeadMotor);

    rightLeadMotor.setInverted(true);
    rightFollowMotor.setInverted(true);

    limeRotationPidController.setSetpoint(LIME_ROTATE_PID_DEFAULT_SETPOINT);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("heading", navXGyro.getHeading());
  }

  public void arcadeDrive(double forward, double rotate) {
    leftLeadMotor.set(forward + rotate);
    rightLeadMotor.set(forward - rotate);
    SmartDashboard.putNumber("encoderTicks", leftLeadMotor.getEncoder().getPosition());
  }

  // differentialDrive requires constant updates, so we are manually setting the
  // motor speeds to test autonomous
  public void autoDrive(double forward, double rotate) {
    leftLeadMotor.set(forward + rotate);
    rightLeadMotor.set(forward - rotate);
    SmartDashboard.putNumber("motor forward", forward);
    SmartDashboard.putNumber("motor rotate", rotate);
  }

  public void setOpenLoopRamp(double secondsFromNeutralToFull) {
    leftLeadMotor.setOpenLoopRampRate(secondsFromNeutralToFull);
    rightLeadMotor.setOpenLoopRampRate(secondsFromNeutralToFull);
    leftFollowMotor.setOpenLoopRampRate(secondsFromNeutralToFull);
    rightFollowMotor.setOpenLoopRampRate(secondsFromNeutralToFull);
  }

  // Get the current position of the left encoder.
  // Currently used to get the left encoder for driving by distance, but may be
  // changed to include right
  public double getLeftEncoderDistance() {
    return leftLeadMotor.getEncoder().getPosition() / ENCODER_TICKS_PER_ROTATION * ENCODER_METERS_PER_ROTATION;
  }

  // Get the current position of the right encoder.
  // Currently used to get the left encoder for driving by distance, but may be
  // changed to include right
  public double getRightEncoderDistance() {
    return rightLeadMotor.getEncoder().getPosition() / ENCODER_TICKS_PER_ROTATION * ENCODER_METERS_PER_ROTATION;
  }

  // Get the average position of left and right encoders.
  public double getAverageEncoderDistance() {
    return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2;
  }

  public double calculateLimeRotatePidOutput(double measurement) {
    return limeRotationPidController.calculate(measurement);
  }

  public double calculateLimeDistancePidOutput(double measurement) {
    return limeDistancePidController.calculate(measurement);
  }

  public void resetEncoders() {
    leftLeadMotor.getEncoder().setPosition(0);
    rightLeadMotor.getEncoder().setPosition(0);
  }

  @Override
  public void autonomousInit() {
    setOpenLoopRamp(0);
    // Disable the differentialDrive safety during autonomous
    // differentialDrive requires constant feeding of motor inputs when safety is
    // enabled.
    // differentialDrive.setSafetyEnabled(false);
  }

  @Override
  public void teleopInit() {
    setOpenLoopRamp(0.3); // .1 is how much time it takes to get desired value, values of 0.5+ make it
                          // really drifty
    // Re-enable safety for teleop
    // differentialDrive.setSafetyEnabled(true);



  }

  @Override
  public void disabledInit() {
  }
}
