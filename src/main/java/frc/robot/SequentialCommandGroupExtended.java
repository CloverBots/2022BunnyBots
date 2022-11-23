package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Similar to a {@link SequentialCommandGroup}, but with some additional
 * helpers.
 */
public class SequentialCommandGroupExtended extends SequentialCommandGroup {
  /**
   * Adds an {@link InstantCommand} from the given procedure and subsystem
   * requirements.
   * 
   * @param toRun        The procedure to run.
   * @param requirements The subsystem dependencies of the procedure.
   */
  protected void addInstant(Runnable toRun, Subsystem... requirements) {
    addCommands(new InstantCommand(toRun, requirements));
  }
}
