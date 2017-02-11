package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class Mechanisms{
	static VictorSP shooterMotor = new VictorSP(2);
	//create DoubleSolenoids for gear collector
	static DoubleSolenoid collectorSolenoid = new DoubleSolenoid(0,2,3);
	static boolean collector_closed = true;
	
	public static void collectorOpen (){
		collectorSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void collectorClosed (){
		collectorSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public static void shooterOn() {
		shooterMotor.set(joyStick.getSlider());
	}
	public static void shooterOff() {
		shooterMotor.set(0);
	}
}
