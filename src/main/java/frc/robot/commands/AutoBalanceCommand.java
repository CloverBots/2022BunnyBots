package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXGyro;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalanceCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private NavXGyro gyro; // easy way to get gyro values

    
    public AutoBalanceCommand(DriveSubsystem drive) {
        driveSubsystem = drive;
        gyro = driveSubsystem.navXGyro;
        driveSubsystem.balancePidController.setTolerance(3);
        driveSubsystem.balancePidController.calculate(gyro.getRoll());
    }
    @Override
    public void execute() {
        System.out.println("gyro: "+gyro.getRoll());
        System.out.println("pid: "+driveSubsystem.balancePidController.calculate(gyro.getRoll()));
        
    }
    @Override
    public boolean isFinished() {
        return true;
    }

}