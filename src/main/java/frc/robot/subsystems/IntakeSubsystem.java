package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IDs;

public class IntakeSubsystem extends SubsystemBase {

    private static final double DEFAULT_INTAKE_SPEED = 1.0;
    private final TalonSRX intakeMotor = new TalonSRX(IDs.INTAKE_LEAD_DEVICE);
    private double speed;

    public IntakeSubsystem() {
        intakeMotor.setInverted(true);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        intakeMotor.set(TalonSRXControlMode.PercentOutput, speed);
    }


    public void startIntake(double speed) {
        this.speed = speed;
    }

    public void startIntake() {
        startIntake(DEFAULT_INTAKE_SPEED);
    }

    public void stop() {
        speed = 0;
        intakeMotor.set(TalonSRXControlMode.PercentOutput, 0);
    }
}
