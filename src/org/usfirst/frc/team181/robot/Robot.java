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

	final String doNothing = "Do Nothing";

	final String customAuto = "My Auto";
	final String autoTurning = "Turning";

	final String customAuto1 = "Encoder Auto";
	final String customAuto2 = "Gyro Auto";
	final String gearCenter = "gear Center";
	final String gearRight = "Right Gear";
	final String gearLeft = "Left Gear";
	final String centerTarget = "CenterTarget";
	final String lineOnly = "lineOnly";
	
	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 480;
	
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double rectX1;
	private double rectX2;
	
	private final Object imgLock = new Object();
	
	double goalDistance = 0;
	double totalDistance = 0;


	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Do nothing", doNothing);
		chooser.addObject("Center Gear", gearCenter);
		chooser.addObject("Right Gear", gearRight);
		chooser.addObject("Left Gear", gearLeft);
		chooser.addObject("Center to Target", centerTarget);
		chooser.addObject("lineOnly", lineOnly);
		SmartDashboard.putData("Auto choices", chooser);
		
		DriveTrain.lowGear();
		Mechanisms.servoClosed();
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
		

		    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
		        if (!pipeline.filterContoursOutput().isEmpty()) {
		        	Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
			        Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
		            synchronized (imgLock) {
		            	centerX = Math.abs(r1.x - r2.x) / 2;
		            	rectX1 = r1.x;
		            	rectX2  = r2.x;
		            }
		        }
		        else {
		        	System.out.println("NOT SEEING ANYTHING!");
		        }
		    });
		    visionThread.start();
		
		
		    
		    
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
		//autoSelected = SmartDashboard.getString("Auto Selector", doNothing);
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
		
		case lineOnly:
			if(centerX > IMG_WIDTH/2){
				
			}
			double centerX;
			synchronized(imgLock){
				centerX = this.centerX;
			}
			double turn = centerX - (IMG_WIDTH / 2 );
			DriveTrain.move(-0.6, turn * 0.005);
			
		case centerTarget:
			
	/*
		case customAuto1:
			if(DriveTrain.readEncoderL() < 1000) {
				DriveTrain.move(-0.6, 0);
			}
			else if(DriveTrain.readEncoderL() > 1000) {
				DriveTrain.stop();
			}
			SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
			SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
			SmartDashboard.putNumber("Throttle value", Mechanisms.convertThrottle());
			break;

		case autoTurning:
			DriveTrain.move(DriveTrain.toClicks(35));
			//DriveTrain.turn(90);
	*/
		 
		case doNothing:
			DriveTrain.stop();
			break;
		case gearCenter:
				
			//drive forward
			if (DriveTrain.readEncoderL() < 5 && DriveTrain.readEncoderR() < 5){
				DriveTrain.move();
				SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
				SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
				
			}
			  if (DriveTrain.readEncoderL() >= 5 && DriveTrain.readEncoderR() >= 5){
			  	DriveTrain.stop();
			  }
			  break;
			  /*

			//release gear
				Mechanisms.gearOpen();
							
				if (DriveTrain.readEncoderL() < -12 && DriveTrain.readEncoderR() < -12){
					DriveTrain.move();
					
				}
				if (DriveTrain.readEncoderL() >= 75 && DriveTrain.readEncoderR() >= 75){
					DriveTrain.turn(90);	
					if (DriveTrain.readEncoderL() < -36 && DriveTrain.readEncoderR() < -36){
						DriveTrain.move();
					}
					if (DriveTrain.readEncoderL() >= -36 && DriveTrain.readEncoderR() >= -36){
						DriveTrain.turn(-90);
						
						if (DriveTrain.readEncoderL() < -36 && DriveTrain.readEncoderR() < -36){
							
							DriveTrain.move();	
						}	
							
						if (DriveTrain.readEncoderL() >= -36 && DriveTrain.readEncoderR() >= -36){
							
						}
					}
					
				}
			  }
			//Prepare to shoot
			  
			  break;
			  */
		case gearRight:
			
			if (DriveTrain.readEncoderL() < 75 && DriveTrain.readEncoderR() < 75){
				DriveTrain.resetEncoders();
				DriveTrain.move();
			}
			if (DriveTrain.readEncoderL() >= 75 && DriveTrain.readEncoderR() >= 75){
				//turn left
				DriveTrain.turn(-25);
				
				if (DriveTrain.readEncoderL() < 8 && DriveTrain.readEncoderR() < 8){
					DriveTrain.resetEncoders();
					DriveTrain.move();
				}
				if (DriveTrain.readEncoderL() >= 8 && DriveTrain.readEncoderR() >= 8){
					Mechanisms.gearOpen();					
				
					if (DriveTrain.readEncoderL() < -10 && DriveTrain.readEncoderR() < 8){
						DriveTrain.resetEncoders();
						DriveTrain.move();
					}
				}
				}
			
			//backup
			DriveTrain.move(-10);
			
			//turn back
			DriveTrain.turn(25);
			
			break;
		case gearLeft:
			//go foward
			DriveTrain.move(75);
			
			//turn
			DriveTrain.turn(25);
			
			
			//forward to place gear
			DriveTrain.move(8);
			
			//open gear
			Mechanisms.gearOpen();
			
			//back up
			DriveTrain.move(-10);
			
			//turn back
			DriveTrain.turn(-25);
			
			break;
			
		default:
			// Put default auto code here
			break;
		}
	}
	
	
	
	public void teleopInit(){
		Mechanisms.servoClosed();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		DriveTrain.joyMove();
		joyStick.doButtons();
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
		
		
		//put the data on the smart dashboard for the encoders.
		SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
		SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());		
		SmartDashboard.putBoolean("Gyro Calibrating", DriveTrain.isCalibrating());
		SmartDashboard.putNumber("Yaw: ", DriveTrain.getYaw());
		SmartDashboard.putNumber("Shooter Power", Mechanisms.convertThrottle());
		SmartDashboard.putNumber("Servo Position", Mechanisms.getServoAngle());
	}

	

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
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
}

