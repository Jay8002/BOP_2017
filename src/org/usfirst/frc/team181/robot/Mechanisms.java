//Created by Matthew Shelto and Laila Yost in 2017

//This Class stores all the methods for operating mechanisms on the robot.


package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Servo;


public class Mechanisms{
	//Create shooter motor on port 2
	static VictorSP shooterMotor = new VictorSP(2);
	//create DoubleSolenoids for gear collector
	static DoubleSolenoid gearSolenoid = new DoubleSolenoid(0,2,3);
	
	//Create the servo that locks the climber. Port 7.
	static Servo climberServo = new Servo(7);
	
	//The agitator is not on the final robot.
	//static VictorSP fuelAgitator = new VictorSP(3);
	
	//For allowing balls into the shooter.
	static Servo shootDoor = new Servo(4);
	
	//variables for state of mechanisms
	static boolean gear_closed = true;
	static boolean lock_closed = false;

	public static void openShooter (){
		shootDoor.setAngle(0);
	}
	
	public static void closeShooter (){
		shootDoor.setAngle(45);
	}
	
	public static void servoClosed(){
		climberServo.setAngle(38);
		lock_closed = true;
	}
	public static void servoOpen(){
		climberServo.setAngle(90);
		lock_closed = false;
	}
	
	//A pnumatic Solenoid works on air pressure. Forward pushes air through.
	public static void gearOpen (){
		gearSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void gearClosed (){
		gearSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	//Set the shooter speed to slider on Operator Stick. Max value is .8
	public static void OpShooterOn() {
		shooterMotor.set(convertThrottle(joyStick.getOpSlider()));
	}
	
	//Used for autonomous
	public static void shooterOn(double speed){
		shooterMotor.set(speed);
	}
	public static void shooterOff() {
		shooterMotor.set(0);
	}
	public static double getServoAngle(){
		return climberServo.getAngle();
	}
	//The agitator is no longer on the robot.
	/*
	public static void agitateFuel(){
		fuelAgitator.set(1);
	}
	public static void unagitateFuel(){
		fuelAgitator.set(0);
	}
	public static void reverseAgitate(){
		fuelAgitator.set(-1);
	}
	*/
	
	//converts slider value to a scale of 0 to .8
	public static double convertThrottle(double slider){
		return .8*(1-(slider+1)/2)+1*((slider+1)/2);
	}
}
