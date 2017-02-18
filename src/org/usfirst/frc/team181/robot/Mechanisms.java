package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Servo;


public class Mechanisms{
	static VictorSP shooterMotor = new VictorSP(2);
	//create DoubleSolenoids for gear collector
	static DoubleSolenoid gearSolenoid = new DoubleSolenoid(0,2,3);
	static boolean gear_closed = true;
	static Servo climberServo = new Servo(7);
	
	public static void servoOpen(){
		climberServo.setAngle(50);
	}
	public static void servoClosed(){
		climberServo.setAngle(22);
	}
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
	public static double getServoAngle(){
		return climberServo.getAngle();
	}
}
