package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

public class joyStick {

	//create joystick
	public static Joystick joystick = new Joystick(0);
	public static Joystick opStick = new Joystick(1);
	static boolean highGear = false;
	static boolean gear_open = false;
	static boolean climbing = false;
	static boolean processing = false;
	static JoystickButton seven = new JoystickButton(joystick, 7);
	
	//create joystick buttons
	JoystickButton gearButton = new JoystickButton(joystick, 1);
	JoystickButton openShooter = new JoystickButton(opStick, 12);
	JoystickButton closeShooter = new JoystickButton(opStick, 11);
	
	public static void doJoyButtons(){
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
		if (joystick.getRawButton(3) == true) {
			DriveTrain.resetEncoders();
		}
		
		if(joystick.getRawButton(2) == true && gear_open == false){
			Mechanisms.gearOpen();
			gear_open = true;
		}
		if(joystick.getRawButton(2) == false && gear_open == true){
			Mechanisms.gearClosed();
			gear_open = false;
		}
		
		if(opStick.getRawButton(1) == false){
			if(joystick.getRawButton(9) == true) {
				Mechanisms.shooterOn(getDriverSlider());
			}
			if(joystick.getRawButton(9) == false) {
				Mechanisms.shooterOff();
			}
		}
		
		if (joystick.getRawButton(8) == true && climbing == false && processing == false){ 
			processing = true;
			Mechanisms.servoClosed();
			climbing = true;
			processing = false;
		}
		else if(joystick.getRawButton(7) == true && climbing == true && processing == false){
			processing = true;
			Mechanisms.servoOpen();
			climbing = false;
			processing = false;
		}
		if(joystick.getRawButton(11) == true){
			Mechanisms.closeShooter();
		}
		if(joystick.getRawButton(12) == true){
			Mechanisms.openShooter();
		}
	}
	
	public static void doOpButtons(){
		if(opStick.getRawButton(2) == true && gear_open == false){
			Mechanisms.gearOpen();
			gear_open = true;
		}
		if(opStick.getRawButton(2) == false && gear_open == true){
			Mechanisms.gearClosed();
			gear_open = false;
		}	
		
		if(joystick.getRawButton(9) == false){
			if(opStick.getRawButton(1) == true) {
				Mechanisms.shooterOn(getOpSlider());
			}
			if(opStick.getRawButton(1) == false) {
				Mechanisms.shooterOff();
			}
		}
		
		if(opStick.getRawButton(11) == true){
			Mechanisms.closeShooter();
		}
		if(opStick.getRawButton(12) == true){
			Mechanisms.openShooter();
		}
	}


	public static double getY(){
		return joystick.getY();
	}
	
	public static double getZ(){	
		return joystick.getZ();
	}
	public static double getOpSlider() {
		return Mechanisms.convertThrottle(opStick.getThrottle());
	}
	public static double getDriverSlider() {
		return Mechanisms.convertThrottle(joystick.getThrottle());
	}
}
