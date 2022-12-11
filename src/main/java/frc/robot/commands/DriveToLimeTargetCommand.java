package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionTargetTracker;
import frc.robot.VisionTargetTracker.LedMode;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToLimeTargetCommand extends CommandBase {
    

    private final DriveSubsystem driveSubsystem;
    private final VisionTargetTracker tracker;
    private final double distanceRequired;
    private final double tolerance;
    private final double maxSpeed;

    public DriveToLimeTargetCommand(DriveSubsystem driveSubsystem, VisionTargetTracker tracker, double distanceRequired, double tolerance, double maxSpeed) {
        this.driveSubsystem = driveSubsystem;
        this.tracker = tracker;
        this.distanceRequired = distanceRequired;
        this.tolerance = tolerance;
        this.maxSpeed = maxSpeed;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        tracker.setLedMode(LedMode.FORCE_ON);
        driveSubsystem.limeDistancePidController.setSetpoint(distanceRequired);
        driveSubsystem.limeDistancePidController.setTolerance(tolerance);
        driveSubsystem.limeRotationPidController.setSetpoint(tracker.getX());
    }

    @Override
    public void execute() {
        double xOffset = tracker.getX();
        double rotation = Math.min(maxSpeed, Math.max(-maxSpeed, driveSubsystem.calculateLimeRotatePidOutput(xOffset)));
        double forward = Math.min(maxSpeed, Math.max(0, tracker.computeTargetDistance() - distanceRequired));
        driveSubsystem.autoDrive(-forward, rotation);
        
        SmartDashboard.putNumber("rotation", rotation);
        SmartDashboard.putNumber("forward", forward);
        SmartDashboard.putNumber("distance", tracker.computeTargetDistance());
    }

    

    @Override
    public void end(boolean interrupted) {
        tracker.setLedMode(LedMode.FORCE_OFF);
        driveSubsystem.autoDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return driveSubsystem.limeDistancePidController.atSetpoint();
    }
}
