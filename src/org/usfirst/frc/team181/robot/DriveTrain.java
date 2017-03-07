package org.usfirst.frc.team181.robot;

//import needed libraries
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
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
       // robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //set parameters for left encoder
        leftEncoder.setDistancePerPulse(0.05);
        //set parameters for right encoder
        rightEncoder.setDistancePerPulse(0.05);
        
	}
	
	public static void move (double y, double z){ 		
			robotDrive.arcadeDrive(-y, z);	
	}

	/*public static void move (){
		// (DriveTrain.readEncoderL() <= clicks && DriveTrain.readEncoderR() <= clicks){
			DriveTrain.move(-0.7, 0);
		//}
	}
	*/
	public static void joyMove() {
		robotDrive.arcadeDrive(joyStick.getY(), -joyStick.getZ());
	}
	
	public static void turn(int angle){
		//Zero out and get the Yaw of the robot from the Gyro
		for (double i = getYaw();  i >= angle+5 || i <= angle-5 ; i = getYaw()){
			
			if (i < angle){
				move(0, -.5);
				stop();
			}
			if (i > angle){
				move(0, .5);
				stop();
			}
			
		}
		System.out.println("Stopped turning");
		
	}
	
	public static void stop() {
		robotDrive.stopMotor();
	}
	
	
	public static double toInches(int clicks){
		return (clicks * 0.0552);
	}
	public static void highGear() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void lowGear() {
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static double readEncoderL() {
		return -leftEncoder.getDistance();
	}
	public static double readEncoderR() {
		return -rightEncoder.getDistance();
	}
	public static double rateEncoderR(){
		return rightEncoder.getRate();
	}
	public static double rateEncoderL(){
		return leftEncoder.getRate();
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
	
	