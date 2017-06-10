//Created by Matthew Shelto and Laila Yost in 2017

//This class controls all input from both joysticks.

package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

public class joyStick {

	//create joysticks
	public static Joystick joystick = new Joystick(0);
	public static Joystick opStick = new Joystick(1);
	
	
	//create state variables
	static boolean highGear = false;
	static boolean gear_open = false;
	static boolean climbing = false;
	static boolean processing = false;

	///Method checks buttons on Driver stick
	public static void doJoyButtons(){
		//If button 1 is pressed, and high gear is not yet enabled, run high gear method in DriveTrain class. Change State variable.
		if(joystick.getRawButton(1) == true && highGear == false){
			System.out.println("Engaging High Gear!");
			DriveTrain.highGear();
			highGear = true;
				
		}
		if(joystick.getRawButton(1) == false && highGear == true){
			System.out.println("Going to Low Gear!");
			DriveTrain.lowGear();
			highGear = false;
		}
		//reset encoders driver stick button 3
		if (joystick.getRawButton(3) == true) {
			DriveTrain.resetEncoders();
		}
		//push gear out. Driverstick button 2
		if(joystick.getRawButton(2) == true && gear_open == false){
			Mechanisms.gearOpen();
			gear_open = true;
		}
		//retract plunger Driverstick button 2 (when let go)
		if(joystick.getRawButton(2) == false && gear_open == true){
			Mechanisms.gearClosed();
			gear_open = false;
		}
		
		//if operator is not being pressed, if driverstick 9 is being pressed turn on shooter
		if(opStick.getRawButton(1) == false){
			if(joystick.getRawButton(9) == true) {
				Mechanisms.shooterOn(getDriverSlider());
			}
			//if driver9 is not pressed turn shooter off.
			if(joystick.getRawButton(9) == false) {
				Mechanisms.shooterOff();
			}
		}
		// if driver 8 is being pressed and we are ready to move it, close the climbing lock
		if (joystick.getRawButton(8) == true && climbing == false && processing == false){ 
			processing = true;
			Mechanisms.servoClosed();
			climbing = true;
			processing = false;
		}
		//same for driver7 but close it
		else if(joystick.getRawButton(7) == true && climbing == true && processing == false){
			processing = true;
			Mechanisms.servoOpen();
			climbing = false;
			processing = false;
		}
		//if driver 11 pressed close shooter door
		if(joystick.getRawButton(11) == true){
			Mechanisms.closeShooter();
		}
		//if driver 12 pressed open shooter door
		if(joystick.getRawButton(12) == true){
			Mechanisms.openShooter();
		}
	}
	
	//Checks and executes all buttons for Operator Stick.
	public static void doOpButtons(){
		//if op2 pressed and is not already open, push the gear out
		if(opStick.getRawButton(2) == true && gear_open == false){
			Mechanisms.gearOpen();
			gear_open = true;
		}
		//if op 2 bring back plunger
		if(opStick.getRawButton(2) == false && gear_open == true){
			Mechanisms.gearClosed();
			gear_open = false;
		}	
		//if driver 9 is not being pressed and operator 1 is pressed turn on shooter 
		if(joystick.getRawButton(9) == false){
			if(opStick.getRawButton(1) == true) {
				Mechanisms.shooterOn(getOpSlider());
			}
			//same thing but if op 1 is not pressed turn off shooter
			if(opStick.getRawButton(1) == false) {
				Mechanisms.shooterOff();
			}
		}
		//if operator 11 is pressed close shooter door
		if(opStick.getRawButton(11) == true){
			Mechanisms.closeShooter();
		}
		//if operator 12 pressed open shooter door.
		if(opStick.getRawButton(12) == true){
			Mechanisms.openShooter();
		}
	}

	//Get value of Y axis for the Drive Stick
	public static double getY(){
		return joystick.getY();
	}
	//get value of Z axis
	public static double getZ(){	
		return joystick.getZ();
	}
	//return operator stick slider value. This value has been converted to a scale of 0 to .8
	public static double getOpSlider() {
		return Mechanisms.convertThrottle(opStick.getThrottle());
	}
	//return driver stick slider value. This value has been converted to a scale of 0 to .8 
	public static double getDriverSlider() {
		return Mechanisms.convertThrottle(joystick.getThrottle());
	}
}
