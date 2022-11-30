package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionTargetTracker;
import frc.robot.VisionTargetTracker.LedMode;
import frc.robot.subsystems.DriveSubsystem;

public class AutoAlignCommand extends CommandBase {
    private static final double MAX_OUTPUT = .2;

    private final DriveSubsystem driveSubsystem;
    private final VisionTargetTracker visionTargetTracker;
    private final double timeoutInSeconds;
    private Timer timer = new Timer();

    public AutoAlignCommand(DriveSubsystem driveSubsystem, VisionTargetTracker visionTargetTracker, double timeoutInSeconds) {

        this.driveSubsystem = driveSubsystem;
        this.visionTargetTracker = visionTargetTracker;
        this.timeoutInSeconds = timeoutInSeconds;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("a2");
        visionTargetTracker.setLedMode(LedMode.FORCE_ON);
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double xOffset = visionTargetTracker.getX();
        SmartDashboard.putNumber("AutoAlign X", xOffset);
        
        double rotation = Math.min(MAX_OUTPUT, Math.max(-MAX_OUTPUT, driveSubsystem.calculateLimeRotatePidOutput(xOffset)));
        driveSubsystem.autoDrive(0, rotation);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        visionTargetTracker.setLedMode(LedMode.FORCE_OFF);
        driveSubsystem.autoDrive(0, 0);
        timer.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (!timer.hasElapsed(0.5)) return false;
        return timer.hasElapsed(timeoutInSeconds) || visionTargetTracker.getX() == 0;
    }
}
