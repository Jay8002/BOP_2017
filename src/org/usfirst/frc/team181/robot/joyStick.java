package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class joyStick {

	//create joystick
	public static Joystick joystick = new Joystick(0);
	static boolean highGear = false;
	
	//create joystick buttons
	JoystickButton gearButton = new JoystickButton(joystick, 1);
	
	public static void doButtons(){
		//if button one on the joystick is pressed, and high gear is not already on
		if(joystick.getRawButton(1) == true && highGear == false){
			//tell the driver high gear is turning on
			System.out.println("Engageing High Gear!");
			//turn on high gear
			DriveTrain.highGear();
			highGear = true;
		}
		//if button one is released and high gear is already on
		if(joystick.getRawButton(1) == false && highGear == true){
			//tell the driver low gear is turning on
			System.out.println("Going to Low Gear!");
			//turn on low gear
			DriveTrain.lowGear();
			highGear = false;
		}
	}
	
	//return a decimal with the current position of the y axis on the joystick
	public static double getY(){
		return joystick.getY();
	}
	
	//return a decimal with the current position of the z azis on the joystick
	public static double getZ(){
		return joystick.getZ();
	}
	
	
}
