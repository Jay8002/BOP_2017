package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class joyStick {

	//create joystick
	public static Joystick joystick = new Joystick(0);
	
	//create joystick buttons
	JoystickButton gearButton = new JoystickButton(joystick, 1);
	
	public static void doButtons(){
		if(joystick.getRawButton(1) == true){
			System.out.println("Engageing High Gear!");
			DriveTrain.highGear();
		}
		if(joystick.getRawButton(1) == false){
			System.out.println("Going to Low Gear!");
			DriveTrain.lowGear();
		}
	}
	
	
	public static double getY(){
		return joystick.getY();
	}
	
	public static double getZ(){
		return joystick.getZ();
	}
	
	
}
