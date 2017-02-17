package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
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
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	final String defaultAuto = "Default";

	final String customAuto = "My Auto";
	final String autoTurning = "Turning";

	final String customAuto1 = "Encoder Auto";
	final String customAuto2 = "Gyro Auto";
	final String gear = "gear";
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	
	private final Object imgLock = new Object();
	
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);

		chooser.addObject("My Auto", customAuto);
		chooser.addObject("Turning", autoTurning);
		chooser.addObject("gear", gear);
		
		chooser.addObject("Encoder Auto", customAuto1);
		chooser.addObject("Gyro Auto", customAuto2);

		SmartDashboard.putData("Auto choices", chooser);
		CameraServer.getInstance().startAutomaticCapture();
		
		
		   UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		    
		    /*visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
		        if (!pipeline.filterContoursOutput().isEmpty()) {
		            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		            synchronized (imgLock) {
		                centerX = r.x + (r.width / 2);
		            }
		        }
		    });
		    visionThread.start();
		*/
		
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
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		DriveTrain.resetEncoders();
		//Zero out and get the Yaw of the robot from the Gyro
		DriveTrain.zeroYaw();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto1:
			if(DriveTrain.readEncoderL() < 1000) {
				DriveTrain.move(-0.6, 0);
			}
			else if(DriveTrain.readEncoderL() > 1000) {
				DriveTrain.stop();
			}
			SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
			SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
			break;

		case autoTurning:
			DriveTrain.move(DriveTrain.toClicks(35));
			//DriveTrain.turn(90);
			

		case customAuto2:
			DriveTrain.zeroYaw();  //zero encoder yaw axis
			while (isAutonomous()) {
				
			}
		case defaultAuto:
			
		case gear:
			
			//drive forward
			DriveTrain.move(DriveTrain.toClicks(48));
			//locate peg
			
			//release gear
				Mechanisms.gearOpen();
			//back up
				DriveTrain.move(DriveTrain.toClicks(48));
			//Prepare to shoot
				
			
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		DriveTrain.joyMove();
		joyStick.doButtons();
		
		//put the data on the smart dashboard for the encoders.
		SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
		SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());		
		SmartDashboard.putBoolean("Gyro Calibrating", DriveTrain.isCalibrating());
		SmartDashboard.putNumber("Yaw: ", DriveTrain.getYaw());
		SmartDashboard.putNumber("Shooter Power", joyStick.getSlider());
	}

	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		//System.out.println(joyStick.joystick.getRawButton(1));
		System.out.println(DriveTrain.getYaw());
		
		/*try to do the following command
		try {
			Arduino.readOnce();
		//If it does not work do the following
		} catch (IOException e) {
			System.out.println("ERROR Reading From Arduino!");
		}
		*/
	}
}

