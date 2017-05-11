package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public SendableChooser<String> users = new SendableChooser<>();
	final String twoUsers = "twoUsers";
	final String singleUser = "singleUser";

	
	DriverStation.Alliance color;
	private Camera1 cam;
	//private Camera2 cam2;
	public static final int IMG_WIDTH = 240;
	public static final int IMG_HEIGHT = 480;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double rectX1;
	private double rectX2;
	
	private final Object imgLock = new Object();
	
	double goalDistance = 0;
	double totalDistance = 0;
	
	
	autonomous autonomous = new autonomous();
	String autoSelected;

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		SmartDashboard.putData("Auto choices", autonomous.chooser);
		DriveTrain.setup();
		
		DriveTrain.lowGear();
		Mechanisms.servoClosed();
		
		//start cam1
		cam = new Camera1("camStream");
		cam.start();
			
		//start cam2
		//cam2 = new Camera2("camStream2");
		//cam2.start();
		
		
		users.addDefault("Two Users", twoUsers);
		users.addObject("Single User", singleUser);
		
		SmartDashboard.putData("Number of Users", users);    
		    
		//Zero out and get the Yaw of the robot from the Gyro
		DriveTrain.zeroYaw();
		double Kp = 0.03;
		
		

		
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		SmartDashboard.putData("Auto choices", autonomous.chooser);
		
		color= DriverStation.getInstance().getAlliance();
		
		if (color == DriverStation.Alliance.Blue){
			autonomous.isRed = false;
		}
		if(color == DriverStation.Alliance.Red){
			autonomous.isRed = true;
		}
		System.out.println("Auto selected: " + autoSelected);
		DriveTrain.resetEncoders();
		//Zero out and get the Yaw of the robot from the Gyro
		DriveTrain.zeroYaw();
		DriveTrain.setup();
		Mechanisms.gearClosed();
		Mechanisms.servoClosed();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		autonomous.doAutonomous();
}
	
	
	public void teleopInit(){
		DriveTrain.zeroYaw();
		DriveTrain.setup();
		Mechanisms.servoClosed();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		outputSensors();
		if(joyStick.getZ() < -0.04 || joyStick.getZ() > 0.04){
			DriveTrain.joyMove();
		}
		else{
			DriveTrain.teleopCorrect(-joyStick.getY());
		}
		joyStick.doJoyButtons();
		
		if(users.getSelected() == twoUsers){
			joyStick.doOpButtons();		
			}
		
		
		/*
		double rectX1;
		double rectX2;
		double centerX;
		synchronized(imgLock){
			centerX = this.centerX;
			rectX1 = this.rectX1;
			rectX2 = this.rectX2;
			System.out.println("Center is :"+ centerX);
			System.out.println("r1 is :" + rectX1);
			System.out.println("r2 is : " + rectX2);

		}
		*/
		
		//put the data on the smart dashboard for the encoders.
		SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
		SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());		
		SmartDashboard.putBoolean("Gyro Calibrating", DriveTrain.isCalibrating());
		SmartDashboard.putNumber("Yaw: ", DriveTrain.getYaw());
		SmartDashboard.putNumber("Shooter Power OpStick", Mechanisms.convertThrottle(joyStick.getOpSlider()));
		SmartDashboard.putNumber("Shooter Power Driver Stick", Mechanisms.convertThrottle(joyStick.getOpSlider()));
		SmartDashboard.putNumber("Servo Position", Mechanisms.getServoAngle());
		SmartDashboard.putNumber("Joystick Y", joyStick.getY());
		SmartDashboard.putNumber("Joystick Z", joyStick.getZ());
	}

	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		outputSensors();
		//System.out.println(joyStick.joystick.getRawButton(1));
		//System.out.println(DriveTrain.getYaw());
		
		/*try to do the following command
		try {
			Arduino.readOnce();
		//If it does not work do the following
		} catch (IOException e) {
			System.out.println("ERROR Reading From Arduino!");
		}
		*/
	}
	
	public static void outputSensors(){
		SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
		SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
		SmartDashboard.putNumber("Yaw: ", DriveTrain.getYaw());
		SmartDashboard.putNumber("Left Rate per Second", DriveTrain.rateEncoderL());
		SmartDashboard.putNumber("Right Rate per Second", DriveTrain.rateEncoderR());
		SmartDashboard.putNumber("Joystick Y", joyStick.getY());
		SmartDashboard.putNumber("Joystick Z", joyStick.getZ());
	}
}

