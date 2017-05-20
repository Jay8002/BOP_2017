//Created by Matthew Shelto and Laila Yost in 2017

//This class is needed and can not be renamed. It is where the robot looks for instructions.
//Certain methods are essential and can not be renamed, as the robot looks for them for different modes.


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
	
	//Create needed variables
	
	//Determines What side of the field the robot is on (Important for autonomous)
	DriverStation.Alliance color;
	
	//Makes A Instance of Camera classes, which is a thread.
	private Camera1 cam;
	//private Camera2 cam2;
	
	//Public non-changable variables telling Camera dimentions.
	public static final int IMG_WIDTH = 240;
	public static final int IMG_HEIGHT = 480;
	
	//Makes Instance of Vision Thread class
	private VisionThread visionThread;
	
	//sets up variables for vision processing ARE THESE NEEDED?
	private double centerX = 0.0;
	private double rectX1;
	private double rectX2;
	
	
	//Makes instance of autonomous class
	autonomous autonomous = new autonomous();
	//IS NEEDED ??
	String autoSelected;

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Display the autonomous mode chooser on the Smart Dashboard.
		SmartDashboard.putData("Auto choices", autonomous.chooser);
		
		DriveTrain.setup();
		
		//Reset all of the mechanical devices and pnumatics when the robot starts up.
		DriveTrain.lowGear();
		Mechanisms.servoClosed();
		
		//start streaming the camera in a new thread.
		cam = new Camera1("camStream");
		cam.start();
			
		//Zero out and get the Yaw of the robot from the Gyro
		DriveTrain.zeroYaw();
				
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
	//This Method is automaticly called by the robot when autonomous mode is about to start.
	public void autonomousInit() {
		//Put the choices for autonomous mode on the SmartDashboard
		SmartDashboard.putData("Auto choices", autonomous.chooser);
		
		//What alliance color are we?
		color= DriverStation.getInstance().getAlliance();
		
		//Send the color we are to a variable in the autonomous class
		if (color == DriverStation.Alliance.Blue){
			autonomous.isRed = false;
		}
		if(color == DriverStation.Alliance.Red){
			autonomous.isRed = true;
		}
		
		//used for debugging
		System.out.println("Auto selected: " + autoSelected);
		
		//Make sure the endocers read 0
		DriveTrain.resetEncoders();
		//Zero out and get the angle of the robot from the Gyro
		DriveTrain.zeroYaw();
		//Det up DriveTrain and close all mechanisms
		DriveTrain.setup();
		Mechanisms.gearClosed();
		Mechanisms.servoClosed();

	}

	/**
	 * This function is called in a infinite loop during autonomous. It only ends when autonomous does.
	 */
	@Override
	public void autonomousPeriodic() {
		autonomous.doAutonomous();
}
	
	//Robot calls this method when teleop is about ot start.
	public void teleopInit(){
		DriveTrain.zeroYaw();
		DriveTrain.setup();
		Mechanisms.servoClosed();
	}

	/**
	 * This function is called in an infinate loop during teleop. It only ends when teleop does.
	 */
	@Override
	public void teleopPeriodic() {
		//Show sensor data on SmartDashboard
		outputSensors();
		//If the joystick is being turned drive normally.
		if(joyStick.getZ() < -0.04 || joyStick.getZ() > 0.04){
			DriveTrain.resetEncoders();
			DriveTrain.joyMove();
		}
		//If the joystick is not being turned, use the drive straight program to prevent curving.
		else{
			DriveTrain.teleopCorrectD(-joyStick.getY());
		}
		//Check if any buttons are being pressed on the Driver or Operator Joysticks
		joyStick.doJoyButtons();	
		//joyStick.doOpButtons();		

		
		//Used for an old Vision Test program IS THIS NEEDED??
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
	 * This function is called in an infinate loop during test mode. Will not end untill test mode does.
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
	
	//Displays different debug data onto the Smart Dashboardd.
	public static void outputSensors(){
		SmartDashboard.putBoolean("Lock closed", Mechanisms.lock_closed);
		SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
		SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
		SmartDashboard.putNumber("Yaw: ", DriveTrain.getYaw());
		SmartDashboard.putNumber("Left Rate per Second", DriveTrain.rateEncoderL());
		SmartDashboard.putNumber("Right Rate per Second", DriveTrain.rateEncoderR());
		SmartDashboard.putNumber("Joystick Y", joyStick.getY());
		SmartDashboard.putNumber("Joystick Z", joyStick.getZ());
	}
}

