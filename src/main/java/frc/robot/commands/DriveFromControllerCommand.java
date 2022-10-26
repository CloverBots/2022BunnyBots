package frc.robot.commands;

import java.util.function.DoubleSupplier;

import org.ejml.sparse.ComputePermutation;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveFromControllerCommand extends CommandBase {
  private static final double SLOW_FORWARD_RATIO = .2;
  private static final double SLOW_FORWARD_CURVE = 1.5;
  private static final double SLOW_ROTATION_RATIO = .3;
  private static final double SLOW_ROTATION_CURVE = 2;

  private static final double DEFAULT_FOWARD_RATIO = .7;
  private static final double DEFAULT_FORWARD_CURVE = 1.5;
  private static final double DEFAULT_ROTATION_RATIO = .6;
  private static final double DEFAULT_ROTATION_CURVE = 2;

  private final DriveSubsystem driveSubsystem;
  private final DoubleSupplier forward;
  private final DoubleSupplier rotation;
  private final DoubleSupplier slowModeTrigger;

  /**
   * Constructs a new {@link DriveFromControllerCommand} instance.
   * 
   * @param driveSubsystem The drive subsystem to control.
   * @param forward        Used to read the forward input.
   * @param rotation       Used to read the rotation input.
   */
  public DriveFromControllerCommand(
      DriveSubsystem driveSubsystem,
      DoubleSupplier forward,
      DoubleSupplier rotation,
      DoubleSupplier slowModeTrigger) {
    this.driveSubsystem = driveSubsystem;
    this.forward = forward;
    this.rotation = rotation;
    this.slowModeTrigger = slowModeTrigger;

    addRequirements(driveSubsystem);
  }

  @Override
  public void execute() {

    // set dummy values if something fails
    double forwardRatio = .6;
    double forwardCurve = 1.5;
    double rotationRatio = .6;
    double rotationCurve = 2;

    if (slowModeTrigger.getAsDouble() > .3) {
      forwardRatio = SLOW_FORWARD_RATIO;
      forwardCurve = SLOW_FORWARD_CURVE;
      rotationRatio = SLOW_ROTATION_RATIO;
      rotationCurve = SLOW_ROTATION_CURVE;
    } else {
      forwardRatio = DEFAULT_FOWARD_RATIO;
      forwardCurve = DEFAULT_FORWARD_CURVE;
      rotationRatio = DEFAULT_ROTATION_RATIO;
      rotationCurve = DEFAULT_ROTATION_CURVE;
    }

    driveSubsystem.arcadeDrive(
        -computeInputCurve(forwardRatio * forward.getAsDouble(), forwardCurve),
        computeInputCurve(rotationRatio * rotation.getAsDouble(), rotationCurve));
  }

  // math calculation
  private double computeInputCurve(double rawInput, double power) {
    var sign = rawInput < 0 ? 1 : -1;
    return Math.pow(Math.abs(rawInput), power) * sign;
  }
  
}