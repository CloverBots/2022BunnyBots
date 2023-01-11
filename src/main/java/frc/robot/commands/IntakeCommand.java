package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;
    private DoubleSupplier intakeSpeed;
    private BooleanSupplier reversed;
    
    public IntakeCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier intakeSpeed) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeSpeed = intakeSpeed;
        addRequirements(intakeSubsystem);
    }

    public IntakeCommand(IntakeSubsystem intakeSubsystem, BooleanSupplier reversed) {
        this.intakeSubsystem = intakeSubsystem;
        this.reversed = reversed;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void execute() {
        if (reversed != null && reversed.getAsBoolean())
        intakeSubsystem.startIntake(-1);
        else if (intakeSpeed != null)
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
