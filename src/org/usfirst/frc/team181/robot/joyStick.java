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

			
	//create joystick buttons
	JoystickButton gearButton = new JoystickButton(joystick, 1);
	
	public static void doButtons(){
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
		if(opStick.getRawButton(2) == true && gear_open == false){
			Mechanisms.gearOpen();
			gear_open = true;
		}
		if(opStick.getRawButton(2) == false && gear_open == true){
			Mechanisms.gearClosed();
			gear_open = false;
		}	 		 			
		if(opStick.getRawButton(1) == true) {
			Mechanisms.shooterOn();
		}
		if(opStick.getRawButton(1) == false) {
			Mechanisms.shooterOff();
		}
		if(joystick.getRawButton(6) == false && climbing == false) {
			Mechanisms.servoOpen();
			climbing = true;
		}
		if(joystick.getRawButton(6) == true && climbing == true) {
			Mechanisms.servoClosed();
			climbing = false;
		}
	
	}
	
	


	public static double getY(){
		return joystick.getY();
	}
	
	public static double getZ(){	
		return joystick.getZ();
	}
	public static double getSlider() {
		return opStick.getThrottle();
	}
	
}
