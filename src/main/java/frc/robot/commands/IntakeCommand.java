package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;
    private final DoubleSupplier intakeSpeed;
    private final BooleanSupplier reversed;

    public IntakeCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier intakeSpeed, BooleanSupplier reversed) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeSpeed = intakeSpeed;
        this.reversed = reversed;
        addRequirements(intakeSubsystem);
    }
    
    @Override
    public void execute() {
        intakeSubsystem.startIntake(reversed.getAsBoolean() ? -1 : intakeSpeed.getAsDouble());
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
