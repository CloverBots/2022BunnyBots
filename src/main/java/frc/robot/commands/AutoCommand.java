package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.SequentialCommandGroupExtended;
import frc.robot.VisionTargetTracker;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LiftSubsystem;

public class AutoCommand extends SequentialCommandGroupExtended {
  private final static double DRIVE_SPEED = 0.3;
  private final static double DRIVE_ROTATE = 0;
  private final static String SMART_DASHBOARD_AUTO_WAIT_TIME = "AutoWaitTime";
  private final static int LIFT_UP_POSITION = (int) LiftCommand.UPPER_ENDPOINT;
  private final static int LIFT_DOWN_POSITION = 0;
  private final static double INTAKE_SPEED = -.5;
  private final static double INTAKE_RUN_TIME = 0.5;
  private final static double AUTO_ALIGN_TIMEOUT_SECONDS = 1;

  /** Creates a new AutonomousLM. */
  public AutoCommand(
      DriveSubsystem driveSubsystem,
      IntakeSubsystem intakeSubsystem,
      LiftSubsystem liftSubsystem,
      VisionTargetTracker visionTargetTracker) {

    // Get distance to drive from SmartDashboard: (entered in inches, converted to
    // meters)
    double distanceInMeters = Math.min(Math.abs(SmartDashboard.getNumber("Auto Distance Inches", 270) / 39.37), 10);
    System.out.println("asdfasdfdasfsad "+ distanceInMeters);
    // Autonomous commands in running order
    // addCommands(new SmartDashboardWaitCommand(SMART_DASHBOARD_AUTO_WAIT_TIME));
    
    if (distanceInMeters > 0) {
      addCommands(new DriveToDistanceCommand(driveSubsystem, distanceInMeters/2, DRIVE_SPEED, DRIVE_ROTATE, 0.03));
    }
    addCommands(new AutoAlignCommand(driveSubsystem, visionTargetTracker, AUTO_ALIGN_TIMEOUT_SECONDS));

    if (distanceInMeters > 0) {
      addCommands(new DriveToDistanceCommand(driveSubsystem, distanceInMeters/2, DRIVE_SPEED, DRIVE_ROTATE, 0.03));
    }

    addCommands(new LiftToPositionCommand(liftSubsystem, 10));

    addInstant(() -> intakeSubsystem.startIntake(INTAKE_SPEED));

    addCommands(new WaitCommand(INTAKE_RUN_TIME));

    addInstant(() -> intakeSubsystem.stop());

    addCommands(new LiftToPositionCommand(liftSubsystem, LIFT_UP_POSITION));

    addInstant(() -> intakeSubsystem.startIntake(-1));

    addCommands(new WaitCommand(INTAKE_RUN_TIME * 2));

    // addInstant(() -> intakeSubsystem.stop());

    // addCommands(new LiftToPositionCommand(liftSubsystem, LIFT_DOWN_POSITION));

  }
}
