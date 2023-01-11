package frc.robot.commands;

import frc.robot.SequentialCommandGroupExtended;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoCommand2 extends SequentialCommandGroupExtended {

  private final static double DRIVE_SPEED = 0.15;
  private final static double DRIVE_ROTATE = 0;
  private final static double INTAKE_SPEED = .5;

  /** Creates a new AutonomousLM. */
  public AutoCommand2(
      DriveSubsystem driveSubsystem,
      IntakeSubsystem intakeSubsystem) {

        addInstant(() -> intakeSubsystem.startIntake(INTAKE_SPEED));
        addCommands(new DriveToDistanceCommand(driveSubsystem, 1, DRIVE_SPEED, DRIVE_ROTATE, 0.03));
        addInstant(() -> intakeSubsystem.stop());
    }
}
