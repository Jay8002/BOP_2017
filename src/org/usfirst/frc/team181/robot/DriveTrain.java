package org.usfirst.frc.team181.robot;

//import needed libraries
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import com.kauailabs.navx.frc.AHRS;

public class DriveTrain {
	//create two speed controllers for right and left.
	static SpeedController driveTrainLeft = new VictorSP(0);
	static SpeedController driveTrainRight = new VictorSP(1);
	static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	
	//create drivesystem for robot, using speed controllers.
	public static RobotDrive robotDrive = new RobotDrive(driveTrainLeft, driveTrainRight);
	
	//Encoder
	static Encoder leftEncoder = new Encoder(2, 1, true, Encoder.EncodingType.k4X);
	static Encoder rightEncoder = new Encoder(0, 3, false, Encoder.EncodingType.k4X);
	
	//create DoubleSolenoids for gears.
	static DoubleSolenoid doubleSolenoid = new DoubleSolenoid(0,0,1);
	static boolean highGear = false;
	
	public static void setup(){
		//set parameters for drive system
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setSensitivity(0.5);
        robotDrive.setMaxOutput(1.0);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //set parameters for left encoder
        leftEncoder.setDistancePerPulse(1);
        //set parameters for right encoder
        rightEncoder.setDistancePerPulse(1);
        
	}
	
	public static void move (double y, double z){ 		
			robotDrive.arcadeDrive(-y, -z);	
	}

	public static void joyMove() {
		robotDrive.arcadeDrive(-joyStick.getY(), -joyStick.getZ());
	}
	
	public static void stop() {
		robotDrive.stopMotor();
	}
	
	public static void highGear() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void lowGear() {
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static double readEncoderL() {
		return leftEncoder.getDistance();
	}
	public static double readEncoderR() {
		return rightEncoder.getDistance();
	}
	public static void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public static void zeroYaw(){
		ahrs.zeroYaw();
	}
	
	public static double getYaw(){
		return ahrs.getYaw();
	}

	public static boolean isCalibrating(){
		return ahrs.isCalibrating();
	}
}
	
	