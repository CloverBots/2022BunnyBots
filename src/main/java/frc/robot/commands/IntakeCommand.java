package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;
    private final DoubleSupplier intakeSpeed;

    public IntakeCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier intakeSpeed) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeSpeed = intakeSpeed;
        addRequirements(intakeSubsystem);
    }
    
    @Override
    public void execute() {
        intakeSubsystem.startIntake(intakeSpeed.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
