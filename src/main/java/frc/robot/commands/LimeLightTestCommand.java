// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionTargetTracker;
import frc.robot.VisionTargetTracker.LedMode;

public class LimeLightTestCommand extends CommandBase {
  private final VisionTargetTracker visionTargetTracker;

  /** Creates a new SpinShooterHighCommand. */
  public LimeLightTestCommand(VisionTargetTracker visionTargetTracker) {
    this.visionTargetTracker = visionTargetTracker;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    visionTargetTracker.setLedMode(LedMode.FORCE_ON);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double targetDistance = visionTargetTracker.computeTargetDistance();

    Boolean isTargetValid = visionTargetTracker.isValid();

    SmartDashboard.putBoolean("TargetValid", isTargetValid);
    SmartDashboard.putNumber("TargetDistance", targetDistance);
    System.out.println("TargetValue " + isTargetValid);
    System.out.println("TargetValue " + targetDistance);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    visionTargetTracker.setLedMode(LedMode.FORCE_OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
