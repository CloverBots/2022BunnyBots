package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.SequentialCommandGroupExtended;
import frc.robot.VisionTargetTracker;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.commands.LiftToPositionCommand;

public class AutoCommand extends SequentialCommandGroupExtended {
  private final static double DRIVE_SPEED = 0.2;
  private final static double DRIVE_DISTANCE = 1;
  private final static double DRIVE_ROTATE = 0;
  private final static String SMART_DASHBOARD_AUTO_WAIT_TIME = "AutoWaitTime";
  private final static int LIFT_UP_POSITION = -400;
  private final static int LIFT_DOWN_POSITION = 0;
  private final static double INTAKE_SPEED = .5;
  private final static double INTAKE_RUN_TIME = 2;
  private final static double AUTO_ALIGN_TIMEOUT_SECONDS = 3;

  /** Creates a new AutonomousLM. */
  public AutoCommand(
      DriveSubsystem driveSubsystem,
      IntakeSubsystem intakeSubsystem,
      LiftSubsystem liftSubsystem,
      VisionTargetTracker visionTargetTracker) {

    // Autonomous commands in running order
    //addCommands(new SmartDashboardWaitCommand(SMART_DASHBOARD_AUTO_WAIT_TIME));

    //addCommands(new AutoAlignCommand(driveSubsystem, visionTargetTracker, AUTO_ALIGN_TIMEOUT_SECONDS));

    //10.0 distance from target to stop at, 0.2 tolerance, 0.5 max power
    //addCommands(new DriveToLimeTargetCommand(driveSubsystem, visionTargetTracker, 82, 0.2, 0.1));

    //addCommands(new LiftToPositionCommand(liftSubsystem, LIFT_UP_POSITION));

    //addInstant(() -> intakeSubsystem.startIntake(INTAKE_SPEED));

    //addCommands(new WaitCommand(INTAKE_RUN_TIME));

    //addInstant(() -> intakeSubsystem.stop());

    //addCommands(new LiftToPositionCommand(liftSubsystem, LIFT_DOWN_POSITION));
    
    addCommands(new DriveToDistanceCommand(driveSubsystem, DRIVE_DISTANCE, DRIVE_SPEED, DRIVE_ROTATE, 0.03));
  }
}
