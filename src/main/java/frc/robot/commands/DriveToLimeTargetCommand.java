package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionTargetTracker;
import frc.robot.VisionTargetTracker.LedMode;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToLimeTargetCommand extends CommandBase {
    

    private final DriveSubsystem driveSubsystem;
    private final VisionTargetTracker tracker;

    public DriveToLimeTargetCommand(DriveSubsystem driveSubsystem, VisionTargetTracker tracker, double distanceRequired) {
        
        this.driveSubsystem = driveSubsystem;
        this.tracker = tracker;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        tracker.setLedMode(LedMode.FORCE_ON);
    }

    @Override
    public void execute() {
        double distance = tracker.computeTargetDistance();

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
