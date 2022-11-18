package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.SequentialCommandGroupExtended;
import frc.robot.VisionTargetTracker;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.commands.LiftToPositionCommand;

public class AutoCommand extends SequentialCommandGroupExtended {
  private final static double DRIVE_SPEED = 0.5;
  private final static double DRIVE_DISTANCE = -2;
  private final static double DRIVE_ROTATE = 0;
  private final static String SMART_DASHBOARD_AUTO_WAIT_TIME = "AutoWaitTime";
  private final static int LIFT_UP_POSITION = 100;
  private final static int LIFT_DOWN_POSITION = 0;
  private final static double INTAKE_SPEED = .5;
  private final static double INTAKE_RUN_TIME = 2;

  /** Creates a new AutonomousLM. */
  public AutoCommand(
      DriveSubsystem driveSubsystem,
      IntakeSubsystem intakeSubsystem,
      LiftSubsystem liftSubsystem,
      VisionTargetTracker visionTargetTracker) {

    // Autonomous commands in running order
    addCommands(new SmartDashboardWaitCommand(SMART_DASHBOARD_AUTO_WAIT_TIME));
    // TODO: Drive with limelight
    addCommands(new LiftToPositionCommand(liftSubsystem, LIFT_UP_POSITION));
    addInstant(() -> intakeSubsystem.startIntake(INTAKE_SPEED));
    addCommands(new WaitCommand(INTAKE_RUN_TIME));
    addCommands(new LiftToPositionCommand(liftSubsystem, LIFT_DOWN_POSITION));
    addCommands(new DriveToDistanceCommand(driveSubsystem, DRIVE_DISTANCE, DRIVE_SPEED, DRIVE_ROTATE, 0.03));
  }
}
