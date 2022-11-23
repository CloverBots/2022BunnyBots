package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionTargetTracker;
import frc.robot.VisionTargetTracker.LedMode;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToLimeTargetCommand extends CommandBase {
    

    private final DriveSubsystem driveSubsystem;
    private final VisionTargetTracker tracker;
    private static final double MAX_OUTPUT = .2;
    private final double distanceRequired;
    public DriveToLimeTargetCommand(DriveSubsystem driveSubsystem, VisionTargetTracker tracker, double distanceRequired) {
        this.driveSubsystem = driveSubsystem;
        this.tracker = tracker;
        this.distanceRequired = distanceRequired;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        tracker.setLedMode(LedMode.FORCE_ON);
    }

    @Override
    public void execute() {
        double xOffset = tracker.getX();
        double rotation = Math.min(MAX_OUTPUT, Math.max(-MAX_OUTPUT, driveSubsystem.calculateLimePIDOutput(xOffset)));
        //double distance = Math.max(0, tracker.computeTargetDistance() - distanceRequired);
        driveSubsystem.autoDrive(0, rotation);
    }

    

    @Override
    public void end(boolean interrupted) {
        tracker.setLedMode(LedMode.FORCE_OFF);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
