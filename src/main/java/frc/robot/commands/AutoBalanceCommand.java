package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.IDs;
import frc.robot.NavXGyro;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalanceCommand extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private NavXGyro gyro;

    private double error;
    private double currentAngle;
    private double drivePower;

    /**
     * Command to use Gyro data to resist the tip angle from the beam - to stabalize
     * and balanace
     */
    public AutoBalanceCommand(DriveSubsystem drive) {
        driveSubsystem = drive;
        gyro = driveSubsystem.navXGyro;

        // this.m_DriveSubsystem = Robot.m_driveSubsystem;
        // addRequirements(m_DriveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Uncomment the line below this to simulate the gyroscope axis with a
        // controller joystick
        // Double currentAngle = -1 *
        // Robot.controller.getRawAxis(IDs.LEFT_VERTICAL_JOYSTICK_AXIS) * 45;
        this.currentAngle = gyro.getRoll();

        error = IDs.BEAM_BALANCED_GOAL_DEGREES - currentAngle;
        drivePower = -Math.min(IDs.BEAM_BALANACED_DRIVE_KP * error, 1);

        // Our robot needed an extra push to drive up in reverse, probably due to weight
        // imbalances
        if (drivePower < 0) {
            drivePower *= IDs.BACKWARDS_BALANCING_EXTRA_POWER_MULTIPLIER;
        }

        // Limit the max power
        if (Math.abs(drivePower) > 0.4) {
            drivePower = Math.copySign(0.4, drivePower);
        }

        driveSubsystem.autoDrive(drivePower, 0);

        // Debugging Print Statments
        System.out.println("Current Angle: " + currentAngle);
        System.out.println("Error " + error);
        System.out.println("Drive Power: " + drivePower);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.autoDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(error) < IDs.BEAM_BALANCED_ANGLE_THRESHOLD_DEGREES; // End the command when we are within the
                                                                            // specified threshold of being 'flat'
                                                                            // (gyroscope pitch of 0 degrees)
    }
}