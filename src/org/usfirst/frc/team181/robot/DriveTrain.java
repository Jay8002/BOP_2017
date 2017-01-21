package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//import needed libraries
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveTrain {
	//create two speed controllers for right and left.
	static SpeedController driveTrainLeft = new VictorSP(0);
	static SpeedController driveTrainRight = new VictorSP(1);
	
	//create drivesystem for robot, using speed controllers.
	public static RobotDrive robotDrive = new RobotDrive(driveTrainLeft, driveTrainRight);
	
	//create DoubleSolenoids for gears.
	static DoubleSolenoid doubleSolenoid = new DoubleSolenoid(0,0,1);
	
	public static void setup(){
		//set parameters for drivesystem
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setSensitivity(0.5);
        robotDrive.setMaxOutput(1.0);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
	}
	
	public static void move (double y, double z){
		robotDrive.arcadeDrive(-y, -z);
	}

	public static void joyMove(){
		robotDrive.arcadeDrive(-joyStick.getY(), -joyStick.getZ());
	}
	
	public static void stop(){
		robotDrive.stopMotor();
	}
	
	public static void highGear(){
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static void lowGear(){
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}
}
