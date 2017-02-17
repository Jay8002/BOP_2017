package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class Mechanisms{
	static VictorSP shooterMotor = new VictorSP(2);
	//create DoubleSolenoids for gear collector
	static DoubleSolenoid gearSolenoid = new DoubleSolenoid(0,2,3);
	static boolean gear_closed = true;
	
	public static void gearOpen (){
		gearSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void gearClosed (){
		gearSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public static void shooterOn() {
		shooterMotor.set(joyStick.getSlider());
	}
	public static void shooterOff() {
		shooterMotor.set(0);
	}
}
