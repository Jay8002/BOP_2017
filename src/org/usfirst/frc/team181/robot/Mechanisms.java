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
	static VictorSP fuelAgitator = new VictorSP(3);
	
	public static void servoClosed(){
		climberServo.setAngle(38);
	}
	public static void servoOpen(){
		climberServo.setAngle(90);
	}
	public static void gearOpen (){
		gearSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void gearClosed (){
		gearSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public static void shooterOn() {
		shooterMotor.set(convertThrottle());
	}
	public static void shooterOff() {
		shooterMotor.set(0);
	}
	public static double getServoAngle(){
		return climberServo.getAngle();
	}
	public static void agitateFuel(){
		fuelAgitator.set(1);
	}
	public static void unagitateFuel(){
		fuelAgitator.set(0);
	}
	public static void reverseAgitate(){
		fuelAgitator.set(-1);
	}
	public static double convertThrottle(){
		return .8*(1-(joyStick.getSlider()+1)/2)+1*((joyStick.getSlider()+1)/2);
	}
}
