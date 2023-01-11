// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToCollision extends CommandBase {
  private final DriveSubsystem driveSubsystem;
  private final double speed;
  private Timer timer = new Timer();
  private final double timeoutInSeconds;

  /** Creates a new DriveToCollision. */
  public DriveToCollision(DriveSubsystem driveSubsystem, double speed, double timeoutInSeconds) {
    this.speed = speed;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
    this.timeoutInSeconds = timeoutInSeconds;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.autoDrive(speed, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    driveSubsystem.autoDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println(driveSubsystem.getAverageEncoderDistance());
    return timer.hasElapsed(timeoutInSeconds);
  }
}
