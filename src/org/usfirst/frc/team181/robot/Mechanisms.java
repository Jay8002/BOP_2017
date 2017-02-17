package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Servo;


public class Mechanisms{
	static VictorSP shooterMotor = new VictorSP(2);
	//create DoubleSolenoids for gear collector
	static DoubleSolenoid collectorSolenoid = new DoubleSolenoid(0,2,3);
	static boolean collector_closed = true;
	static Servo climberServo = new Servo(3);
	
	public static void servoOpen(){
		climberServo.setAngle(22);
	}
	public ststic void servoClosed(){
		climberServo.setAngle(50);
	}
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
