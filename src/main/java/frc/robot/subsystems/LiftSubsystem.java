package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IDs;
import frc.robot.LiftPosition;

public class LiftSubsystem extends SubsystemBase implements LiftObserver {
  private final int CURRENT_LIMIT = 30; 

  private final CANSparkMax winch0 = new CANSparkMax(IDs.LIFT_WINCH_DEVICE0, MotorType.kBrushless);
  private final CANSparkMax winch1 = new CANSparkMax(IDs.LIFT_WINCH_DEVICE1, MotorType.kBrushless);

  private final DigitalInput lowerSwitch = new DigitalInput(IDs.LIFT_LOWER_SWITCH);
  private final DigitalInput upperSwitch = new DigitalInput(IDs.LIFT_UPPER_SWITCH);

  private LiftPosition liftPosition;

  /**
   * Constructs a new {@link LiftSubsystem} instance.
   */
  public LiftSubsystem() {
    winch0.setSmartCurrentLimit(CURRENT_LIMIT);
    winch1.setSmartCurrentLimit(CURRENT_LIMIT);

    winch0.setIdleMode(IdleMode.kBrake);
    winch1.setIdleMode(IdleMode.kBrake);

    winch0.setInverted(true);
    winch1.setInverted(true);

    winch0.getEncoder().setPosition(0);
    winch1.follow(winch0, true);

    liftPosition = LiftPosition.DOWN;
  }

  public void setLiftSpeed(double speed) {
    winch0.set(speed);
  }

  public double getLiftEncoderPosition() {
    return -winch0.getEncoder().getPosition();
  }

  public void setLiftMaximumPosition(double min, double max) {
    winch0.setSoftLimit(SoftLimitDirection.kForward, (float) max);
    winch0.setSoftLimit(SoftLimitDirection.kForward, (float) min);
  }

  /**
   * Returns the position of the lift.
   */
  public LiftPosition getLiftPosition() {
    return liftPosition;
  }

  public Boolean getLowerSwitch() {
    return lowerSwitch.get();
  }

  public Boolean getUpperSwitch() {
    return upperSwitch.get();
  }
}
