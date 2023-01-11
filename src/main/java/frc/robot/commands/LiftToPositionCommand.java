// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LiftSubsystem;

public class LiftToPositionCommand extends CommandBase {
  private final LiftSubsystem liftSubsystem;
  private final double LIFT_SPEED = 0.4;
  private int position;
  private int direction;

  /** Creates a new LiftCommand. */
  public LiftToPositionCommand(LiftSubsystem liftSubsystem, int position) {
    this.liftSubsystem = liftSubsystem;

    //Guard against too large of a position value
    if (position > LiftCommand.UPPER_ENDPOINT) {
      position = (int) LiftCommand.UPPER_ENDPOINT;
    }
    
    this.position = position;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(liftSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    direction = -1; // going up
    if (liftSubsystem.getLiftEncoderPosition() > position) {
      direction = 1; // going down
    }
    SmartDashboard.putNumber("Lift starting position: ", liftSubsystem.getLiftEncoderPosition());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Lift position: ", liftSubsystem.getLiftEncoderPosition());
    liftSubsystem.setLiftSpeed(LIFT_SPEED * direction);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    liftSubsystem.setLiftSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (direction == 1 && liftSubsystem.getLiftEncoderPosition() <= position) {
      return true;
    } else if (direction == -1 && liftSubsystem.getLiftEncoderPosition() >= position) {
      return true;
    } else {
      return false;
    }
  }
}