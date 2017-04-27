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
	
	public static void pidForward (double speed){
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
	
	public static void pidBackward (double speed){
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
	
	public static void move (double y, double z){ 		
		robotDrive.arcadeDrive(-y, z);
	}
	public static void moveLR (double l, double r){
		robotDrive.tankDrive(l, r);
	}

	
	public static void straightButton(){
		
	}
	
	public static void visionTurn(){

		for(double center = Vision.getCenter(); center < ((Robot.IMG_WIDTH/2)-10) || center > ((Robot.IMG_WIDTH/2)+10); ){
	
			if(center > (Robot.IMG_WIDTH/2)){
				move(0, -.25);
			}
			if(center < (Robot.IMG_WIDTH/2)){
				move(0, .25);
			}
		}
	
	
	}
	/*public static void move (){
		// (DriveTrain.readEncoderL() <= clicks && DriveTrain.readEncoderR() <= clicks){
			DriveTrain.move(-0.7, 0);
		//}
	}
	*/
	
	public static void teleopCorrect(double speed){
		if(joyStick.getY() < 0 && joyStick.getY() < 0){
			pidForward(speed);
		}
		if(joyStick.getY() > 0 && joyStick.getY() > 0){
			pidBackward(speed);
		}
	}
	public static void joyMove() {
		robotDrive.arcadeDrive(joyStick.getY(), -joyStick.getZ());
	}
	
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
	
	
	public static double toInches(int clicks){
		return (clicks * 0.0552);
	}
	public static void highGear() {
		resetEncoders();
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
		highGear = true;
		resetEncoders();
	}
	
	public static void lowGear() {
		resetEncoders();
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
		highGear = false;
		resetEncoders();
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
	
	