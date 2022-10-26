// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.RobotLifecycleCallbacks;


import frc.robot.IDs;

public class DriveSubsystem extends SubsystemBase implements RobotLifecycleCallbacks {
  public static final double WHEEL_DIAMETER_METERS = 0.1524;
  public static final double ENCODER_POSITION_CONVERSION_FACTOR = 0.1 * WHEEL_DIAMETER_METERS * Math.PI;
  public static final double ENCODER_VELOCITY_CONVERSION_FACTOR = ENCODER_POSITION_CONVERSION_FACTOR * 60.0;
  public static final double ENCODER_TICKS_PER_ROTATION = 2048;

  private final TalonFX leftLeadMotor = new TalonFX(IDs.DRIVE_LEFT_LEAD_DEVICE);
  private final TalonFX rightLeadMotor = new TalonFX(IDs.DRIVE_RIGHT_LEAD_DEVICE);
  private final TalonFX leftFollowMotor = new TalonFX(IDs.DRIVE_LEFT_FOLLOW_DEVICE);
  private final TalonFX rightFollowMotor = new TalonFX(IDs.DRIVE_RIGHT_FOLLOW_DEVICE);

  public DriveSubsystem() {
    leftFollowMotor.follow(leftLeadMotor);
    rightFollowMotor.follow(rightLeadMotor);

    rightLeadMotor.setInverted(false);
    rightFollowMotor.setInverted(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void arcadeDrive(double forward, double rotate) {
    leftLeadMotor.set(TalonFXControlMode.PercentOutput, forward + rotate);
    rightLeadMotor.set(TalonFXControlMode.PercentOutput, forward - rotate);
  }

  // differentialDrive requires constant updates, so we are manually setting the
  // motor speeds to test autonomous
  public void autoDrive(double forward, double rotate) { // TODO: Investigate why arcadeDrive isn't working, but
                                                         // autoDrive is.
    leftLeadMotor.set(TalonFXControlMode.PercentOutput, forward + rotate);
    rightLeadMotor.set(TalonFXControlMode.PercentOutput, forward - rotate);
  }

  // documentation at CTRE Pheonix TalonFX documentation
  public void setOpenLoopRamp(double secondsFromNeutralToFull) {
    leftLeadMotor.configOpenloopRamp(secondsFromNeutralToFull, 10);
    rightLeadMotor.configOpenloopRamp(secondsFromNeutralToFull, 10);
    leftFollowMotor.configOpenloopRamp(secondsFromNeutralToFull, 10);
    rightFollowMotor.configOpenloopRamp(secondsFromNeutralToFull, 10);
  }

  // Get the current position of the left encoder.
  // Currently used to get the left encoder for driving by distance, but may be
  // changed to include right
  public double getLeftEncoderPosition() {
    return leftLeadMotor.getSelectedSensorPosition() / ENCODER_TICKS_PER_ROTATION * ENCODER_POSITION_CONVERSION_FACTOR;
  }

  // Get the current position of the right encoder.
  // Currently used to get the left encoder for driving by distance, but may be
  // changed to include right
  public double getRightEncoderPosition() {
    return rightLeadMotor.getSelectedSensorPosition() / ENCODER_TICKS_PER_ROTATION * ENCODER_POSITION_CONVERSION_FACTOR;
  }

  // Get the average position of left and right encoders.
  public double getAverageEncoderPosition() {
    return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
  }

  public void resetEncoders() {
    leftLeadMotor.setSelectedSensorPosition(0);
    rightLeadMotor.setSelectedSensorPosition(0);
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
    setOpenLoopRamp(0.1); // .1 is how much time it takes to get desired value, values of 0.5+ make it
                          // really drifty
    // Re-enable safety for teleop
    // differentialDrive.setSafetyEnabled(true);
  }

  @Override
  public void disabledInit() {
    // TODO Auto-generated method stub
  }
}
