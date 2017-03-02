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
	
	
	//for center gear
	boolean center_forward1 = false;
	boolean center_wait1 = false;
	boolean center_backUp1 = false;
	
	//for right gear
	boolean right_forward1 = false;
	boolean right_wait1 = false;
	boolean right_backUp1 = false;
	boolean right_forward2 = false;

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
		chooser.addObject("lineOnly", lineOnly);
		SmartDashboard.putData("Auto choices", chooser);
		DriveTrain.setup();
		
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
		DriveTrain.setup();
		Mechanisms.gearClosed();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		

		switch (autoSelected) {

		case gearCenter:
			
			if(center_forward1 == false){
				
				DriveTrain.move(.6, 0);
				outputSensors();
				
				if(DriveTrain.readEncoderL() >= 60 && DriveTrain.readEncoderR() >= 60){
					center_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
					
				else if (center_wait1 == false){
						
						Timer.delay(.5);
						Mechanisms.gearOpen();
						Timer.delay(1);
						center_wait1 = true;
				}
						
				else if(center_backUp1 == false){
						DriveTrain.move(-.5, 0);
						outputSensors();
						if(DriveTrain.readEncoderL() <= -15 && DriveTrain.readEncoderR() <= -15){
							center_backUp1 = true;
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.resetEncoders();
						}							
			}
			break;
			
			
		case gearRight:
			
				outputSensors();
				if(right_forward1 == false){
				
					DriveTrain.move(.6, 0);
					outputSensors();
					
					if(DriveTrain.readEncoderL() >= 60 && DriveTrain.readEncoderR() >= 60){
						right_forward1 = true;
						DriveTrain.stop();
						DriveTrain.resetEncoders();
					}
			}
					
				else if (right_wait1 == false){
						
						Timer.delay(.5);
						Mechanisms.gearOpen();
						Timer.delay(1);
						right_wait1 = true;
				}
						
				else if(right_backUp1 == false){
						DriveTrain.move(-.5, 0);
						outputSensors();
						if(DriveTrain.readEncoderL() <= -15 && DriveTrain.readEncoderR() <= -15){
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.turn(50);
							Timer.delay(.5);
							DriveTrain.resetEncoders();
							right_backUp1 = true;
						}							
				}
				
				else if(right_forward2 == false){
					DriveTrain.move(1, 0);
					outputSensors();
					if(DriveTrain.readEncoderL() < 90 && DriveTrain.readEncoderR() < 90){
						DriveTrain.stop();
						DriveTrain.resetEncoders();
						right_forward2 = true;
					}
					
				}
			break;
			
			
		case doNothing:
			DriveTrain.stop();
			break;
			
			
		case lineOnly:
				
			//drive forward
			if (DriveTrain.readEncoderL() < 93 && DriveTrain.readEncoderR() < 93){
				DriveTrain.move(.7, 0);
				SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
				SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
				
			}
			else if (DriveTrain.readEncoderL() >= 93 && DriveTrain.readEncoderR() >= 93){
			  	DriveTrain.stop();
			  }
			  break;
			
		default:
			// Put default auto code here
			break;
		}
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
	
	public void outputSensors(){
		SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
		SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
		SmartDashboard.putNumber("Yaw: ", DriveTrain.getYaw());
	}
}

