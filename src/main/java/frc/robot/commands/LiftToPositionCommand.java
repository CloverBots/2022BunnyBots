// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LiftSubsystem;

public class LiftToPositionCommand extends CommandBase {
  private final LiftSubsystem liftSubsystem;
  private final double LIFT_SPEED = 0.5;
  private int position;
  private int direction;

  /** Creates a new LiftCommand. */
  public LiftToPositionCommand(LiftSubsystem liftSubsystem, int position) {
    this.liftSubsystem = liftSubsystem;
    // TODO: put safe guards on position values
    this.position = position;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(liftSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    direction = 1;
    if (liftSubsystem.getLiftEncoderPosition() > position) {
      direction = -1;
    }

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
    if (direction == 1 && liftSubsystem.getLiftEncoderPosition() < position) {
      return true;
    } else if (direction == -1 && liftSubsystem.getLiftEncoderPosition() > position) {
      return true;
    } else {
      return false;
    }
  }
}