// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.VisionTargetTracker.LedMode;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.AutoCommand2;
import frc.robot.commands.DriveFromControllerCommand;
import frc.robot.commands.DriveToCollisionCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.LiftCommand;
import frc.robot.commands.LimeLightTestCommand;
import frc.robot.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private static final double VISION_TARGET_HEIGHT = 34; // inches
  private static final double CAMERA_HEIGHT = 21.25; 
  private static final double CAMERA_PITCH = 0; //degrees

  private static final double speed = -.1;
  private static final double timeoutInSeconds = 3;

  private final VisionConfiguration visionConfiguration = new VisionConfiguration(
      VISION_TARGET_HEIGHT,
      CAMERA_HEIGHT,
      CAMERA_PITCH);

  private final VisionTargetTracker visionTargetTracker = new VisionTargetTracker(visionConfiguration);

  private final XboxController driverController = new XboxController(IDs.CONTROLLER_DRIVE_PORT);
  private final XboxController operatorController = new XboxController(IDs.CONTROLLER_OPERATOR_PORT);

  private final DriveSubsystem driveSubsystem = new DriveSubsystem();

  private final LiftSubsystem liftSubsystem = new LiftSubsystem();

  private final LiftCommand liftCommand = new LiftCommand(liftSubsystem, operatorController::getLeftTriggerAxis,
      operatorController::getLeftY);

  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  private final DriveFromControllerCommand driveFromController = new DriveFromControllerCommand(
      driveSubsystem,
      liftSubsystem,
      driverController::getLeftY,
      driverController::getRightX,
      driverController::getLeftTriggerAxis);

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  private final AutoCommand autoCommand = new AutoCommand(driveSubsystem, intakeSubsystem, liftSubsystem, visionTargetTracker);
  private final AutoCommand2 autoCommand2 = new AutoCommand2(driveSubsystem, intakeSubsystem);


  public RobotContainer() {
    driveSubsystem.setDefaultCommand(driveFromController);
    liftSubsystem.setDefaultCommand(liftCommand);
    configureButtonBindings();
    configureChooserModes();
    
    visionTargetTracker.setLedMode(LedMode.FORCE_ON);

    SmartDashboard.putNumber("Auto Distance Inches", 270);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    JoystickTrigger startIntakeTrigger = new JoystickTrigger(operatorController, XboxController.Axis.kRightTrigger.value);
    startIntakeTrigger.whileHeld(new IntakeCommand(intakeSubsystem, operatorController::getRightTriggerAxis));
    JoystickButton reverseIntakeButton = new JoystickButton(operatorController, XboxController.Button.kY.value);
    reverseIntakeButton.whileHeld(new IntakeCommand(intakeSubsystem, operatorController::getYButton));
    //JoystickButton limeLightTestButton = new JoystickButton(operatorController, XboxController.Button.kA.value); 
    //limeLightTestButton.whileHeld(new LimeLightTestCommand(visionTargetTracker));
    
    JoystickButton balance = new JoystickButton(driverController, XboxController.Button.kB.value);
    balance.whileHeld(new AutoBalanceCommand(driveSubsystem));
    
    JoystickButton driveToCollisionButton = new JoystickButton(operatorController, XboxController.Button.kB.value);
    driveToCollisionButton.whenReleased(new DriveToCollisionCommand(driveSubsystem, speed, timeoutInSeconds));
  }

  private void configureChooserModes() {

    SmartDashboard.putData("Autonomous Mode", chooser);
    SmartDashboard.putNumber("Auto wait time", 0);

    chooser.addOption("Autonomous", autoCommand);
    chooser.setDefaultOption("Autonomous", autoCommand);
    chooser.addOption("Autonomous: Bunny", autoCommand2);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}
