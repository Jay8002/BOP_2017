//Created by Matthew Shelto and Laila Yost in 2017

//This Class contains all methods for controlling Wheels, and sensors related to driving. 

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
	
	//tells when to stop running vision turn
	static boolean targeting = false;
	
	//set up the drive train for use.
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
	//move the robot in a straight line forwards no matter what. Uses rate
	public static void pidForwardRate (double speed){
		if(DriveTrain.rateEncoderL() == DriveTrain.rateEncoderR()){
			DriveTrain.moveLR(-speed, -speed);
		}
		if(DriveTrain.rateEncoderL() > DriveTrain.rateEncoderR()){
			DriveTrain.moveLR(-speed + .05, -speed);
		}
		if(DriveTrain.rateEncoderL() < DriveTrain.rateEncoderR()){
			DriveTrain.moveLR(-speed - 0.05, -speed);
		}
	}
	
	//Move the robot in a straight line backwards no matter what. Uses rate.
	public static void pidBackwardRate (double speed){
		if(DriveTrain.rateEncoderL() == DriveTrain.rateEncoderR()){
			DriveTrain.moveLR(-speed, -speed);
		}
		if(DriveTrain.rateEncoderL() < DriveTrain.rateEncoderR()){
			DriveTrain.moveLR(-speed - 0.05, -speed);
		}
		if(DriveTrain.rateEncoderL() > DriveTrain.rateEncoderR()){
			DriveTrain.moveLR(-speed + 0.05, -speed);
		}
	}
	
	//Move the robot in a straight line forwards. Uses distance.
	public static void pidForwardDistance(double speed){
		if(DriveTrain.readEncoderL() == DriveTrain.readEncoderR()){
			DriveTrain.moveLR(-speed, -speed);
		}
		if(DriveTrain.readEncoderL() > DriveTrain.readEncoderR()){
			DriveTrain.moveLR(-speed + .05, -speed);
		}
		if(DriveTrain.readEncoderL() < DriveTrain.readEncoderR()){
			DriveTrain.moveLR(-speed - 0.05, -speed);
		}
	}
	//Move the robot in a straight line backwards no matter what. Uses distance.	
	public static void pidBackwardDistance(double speed){
		if(DriveTrain.readEncoderL() == DriveTrain.readEncoderR()){
			DriveTrain.moveLR(-speed, -speed);
		}
		if(DriveTrain.readEncoderL() < DriveTrain.readEncoderR()){
			DriveTrain.moveLR(-speed - 0.05, -speed);
		}
		if(DriveTrain.readEncoderL() > DriveTrain.readEncoderR()){
			DriveTrain.moveLR(-speed + 0.05, -speed);
		}
	}
	//move the robot with a speed and turn factor
	public static void move (double y, double z){ 		
		robotDrive.arcadeDrive(-y, z);
	}
	//move the robot with a speed for both left and right motors.
	public static void moveLR (double l, double r){
		robotDrive.tankDrive(l, r);
	}

	// turn using vision targeting from the raspberry pi. THIS HAS NOT BEEN TESTED
	public static void visionTurn(){

		//get the center point between two targets. turn until that point is in the center of the camera.
		for(Double center = Vision.getCenter(); center < ((Robot.IMG_WIDTH/2)-10) || center > ((Robot.IMG_WIDTH/2)+10) || center == null && targeting == true; center=Vision.getCenter()){
	
			if(center.equals(null)){
				targeting = false;
			}
			if(center > (Robot.IMG_WIDTH/2)){
				move(0, -.25);
			}
			if(center < (Robot.IMG_WIDTH/2)){
				move(0, .25);
			}
		}
	
		targeting = false;
	
	}
	/*public static void move (){
		// (DriveTrain.readEncoderL() <= clicks && DriveTrain.readEncoderR() <= clicks){
			DriveTrain.move(-0.7, 0);
		//}
	}
	*/
	
	//use the drive straight program in teleop. uses distance
	public static void teleopCorrectD(double speed){
		if(joyStick.getY() < 0 && joyStick.getY() < 0){
			pidForwardDistance(speed);
		}
		if(joyStick.getY() > 0 && joyStick.getY() > 0){
			pidBackwardDistance(speed);
		}
	}
	//use the drive straight program in teleop. uses rate
	public static void teleopCorrect(double speed){
		if(joyStick.getY() < 0 && joyStick.getY() < 0){
			pidForwardRate(speed);
		}
		if(joyStick.getY() > 0 && joyStick.getY() > 0){
			pidBackwardRate(speed);
		}
	}
	//move using the joystick
	public static void joyMove() {
		robotDrive.arcadeDrive(joyStick.getY(), -joyStick.getZ());
	}
	
	//turn the robot to an angle. Uses gyroscope.
	public static void turn(double angle){
		//Zero out and get the Yaw of the robot from the Gyro
		for (double i = getYaw();  i >= angle+5 || i <= angle-5 ; i = getYaw()){
			
			if (i < angle){
				move(0, -.6);
				stop();
			}
			if (i > angle){
				move(0, .6);
				stop();
			}
			
		}
		System.out.println("Stopped turning");
		
	}
	
	public static void stop() {
		robotDrive.stopMotor();
	}
	
	//convert clicks to inches. DO WE EVEN USE THIS??
	public static double toInches(int clicks){
		return (clicks * 0.0552);
	}
	//Go to high gear
	public static void highGear() {
		resetEncoders();
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
		highGear = true;
		resetEncoders();
	}
	//Go go low gear
	public static void lowGear() {
		resetEncoders();
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
		highGear = false;
		resetEncoders();
	}
	
	//return left encoder distance value
	public static double readEncoderL() {
		return -leftEncoder.getDistance();
	}
	//return right encoder value with distance
	public static double readEncoderR() {
		return -rightEncoder.getDistance();
	}
	// return right encoder rate
	public static double rateEncoderR(){
		return rightEncoder.getRate();
	}
	//return left encoder rate
	public static double rateEncoderL(){
		return leftEncoder.getRate();
	}
	//set all encoder values to 0
	public static void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
		
	}
	//set the current gyroscope angle to 0
	public static void zeroYaw(){
		ahrs.zeroYaw();
	}
	//return the current gyroscope angle
	public static double getYaw(){
		return ahrs.getYaw();
	}
	//return if the gyroscope is calibrating or not.
	public static boolean isCalibrating(){
		return ahrs.isCalibrating();
	}
}
	
	