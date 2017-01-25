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
		if(joystick.getRawButton(1) == true && highGear == false){
			System.out.println("Engageing High Gear!");
			DriveTrain.highGear();
			highGear = true;
		}
		if(joystick.getRawButton(1) == false && highGear == true){
			System.out.println("Going to Low Gear!");
			DriveTrain.lowGear();
			highGear = false;
		}
	}
	
	
	public static double getY(){
		return joystick.getY();
	}
	
	public static double getZ(){
		return joystick.getZ();
	}
	
	
}
