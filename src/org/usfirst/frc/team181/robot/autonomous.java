package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team181.robot.Robot;

public class autonomous {
	
	
	public SendableChooser<String> chooser = new SendableChooser<>();
	
	//strings for sendable chooser
	final String doNothing = "Do Nothing";
	final String customAuto = "My Auto";
	final String autoTurning = "Turning";
	final String center_lineRight = "Right Line and Gear";
	final String center_lineLeft = "Left Line and Gear";
	final String customAuto1 = "Encoder Auto";
	final String customAuto2 = "Gyro Auto";
	final String gearCenter = "gear Center";
	final String boiler = "Boiler";
	final String gearLeft = "Left Gear";
	final String centerTarget = "CenterTarget";
	final String lineOnly = "lineOnly";
	final String boilerShoot = "boiler and shoot";
	
	
	//for center gear
	boolean center_forward1 = false;
	boolean center_wait1 = false;
	boolean center_backUp1 = false;
	
	//for center gear
	boolean shoot_forward1 = false;
	boolean shoot_wait1 = false;
	boolean shoot_backUp1 = false;
	boolean shoot_shoot = false;
	
	//for diagonal gear
	boolean diagonal_forward1 = false;
	boolean diagonal_wait1 = false;
	boolean diagonal_backUp1 = false;
	
	//for center gear and right line 
	boolean center_rightForward1 = false;
	boolean center_rightWait1 = false;
	boolean center_rightBackUp1 = false;
	boolean center_rightForward2 = false;
	
	//for center gear and left Line
	
	boolean center_leftForward1 = false;
	boolean center_leftWait1 = false;
	boolean center_leftBackUp1 = false;
	boolean center_leftForward2 = false;
	
	
	//for left gear
	boolean left_forward1 = false;
	boolean left_forward2 = false;
	boolean left_forward3 = false;
	boolean left_backwards1 = false;
	

	//for right gear
	boolean right_forward1 = false;
	boolean right_forward2 = false;
	boolean right_forward3 = false;
	boolean right_backwards1 = false;
	
	public autonomous(){
		chooser.addDefault("Do nothing", doNothing);
		chooser.addObject("Center Gear", gearCenter);
		chooser.addObject("Boiler", boiler);
		//chooser.addObject("Left Gear", gearLeft);
		chooser.addObject("<-- Center Gear Left", center_lineLeft);
		chooser.addObject("line Only", lineOnly);
		chooser.addObject("Center Gear Right -->", center_lineRight);
		chooser.addObject("boiler and shoot",  boilerShoot);
		
	}

	public void doAutonomous(){
		
		switch (chooser.getSelected()) {

		case gearCenter:
			Robot.outputSensors();
			if(center_forward1 == false){
				
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
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
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							center_backUp1 = true;
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.resetEncoders();
						}							
			}
			break;
			
			
		case center_lineRight:
			
				Robot.outputSensors();
				if(center_rightForward1 == false){
				
					DriveTrain.move(.6, 0);
					Robot.outputSensors();
					
					if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
						center_rightForward1 = true;
						DriveTrain.stop();
						DriveTrain.resetEncoders();
					}
			}
					
				else if (center_rightWait1 == false){
						Timer.delay(.5);
						Mechanisms.gearOpen();
						Timer.delay(1);
						center_rightWait1 = true;
				}
						
				else if(center_rightBackUp1 == false){
						DriveTrain.move(-.5, 0);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -18 || DriveTrain.readEncoderR() <= -18){
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.turn(50);
							Timer.delay(.5);
							DriveTrain.resetEncoders();
							center_rightBackUp1 = true;
						}	
						System.out.println("Starting 2nd half");
				}
								
				else if(center_rightForward2 == false){
					System.out.println("2nd half started");
					DriveTrain.move(.75, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() > 80 || DriveTrain.readEncoderR() > 80){
						DriveTrain.stop();
						DriveTrain.resetEncoders();
						center_rightForward2 = true;
					}
					
				}
			break;
			
		case center_lineLeft:
			
			Robot.outputSensors();
			if(center_leftForward1 == false){
			
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
					center_leftForward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
				
			else if (center_leftWait1 == false){
					
					Timer.delay(.5);
					Mechanisms.gearOpen();
					Timer.delay(1);
					center_leftWait1 = true;
			}
					
			else if(center_leftBackUp1 == false){
					DriveTrain.move(-.5, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -24 || DriveTrain.readEncoderR() <= -24){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.turn(-50);
						Timer.delay(.5);
						DriveTrain.resetEncoders();
						center_leftBackUp1 = true;
					}							
			}
			
			else if(center_leftForward2 == false){
				DriveTrain.move(.75, 0);
				Robot.outputSensors();
				if(DriveTrain.readEncoderL() > 80 || DriveTrain.readEncoderR() > 80){
					DriveTrain.stop();
					DriveTrain.resetEncoders();
					center_leftForward2 = true;
				}
				
			}
		break;
		
		
		case gearLeft:
			Robot.outputSensors();
			if(left_forward1 == false){
				Robot.outputSensors();
				DriveTrain.move(.6, 0);
				
				if(DriveTrain.readEncoderL() > 52 || DriveTrain.readEncoderR() > 52){
					DriveTrain.stop();					
					DriveTrain.turn(30);
					DriveTrain.resetEncoders();
					left_forward1 = true;
				}
			}
			else if (left_forward2 == false){
				Robot.outputSensors();
				DriveTrain.move(.5, 0);
				
				if(DriveTrain.readEncoderL() > 24 || DriveTrain.readEncoderR() > 24){
					DriveTrain.stop();
					Timer.delay(.5);
					Mechanisms.gearOpen();
					Timer.delay(1);
					DriveTrain.resetEncoders();
					left_forward2 = true;
				}
			}
			
			else if (left_backwards1 == false){
				Robot.outputSensors();
				DriveTrain.move(-.5, 0);
				if(DriveTrain.readEncoderL() < -24 || DriveTrain.readEncoderR() < -24){
				DriveTrain.stop();
				DriveTrain.turn(-30);
				DriveTrain.resetEncoders();
				left_backwards1 = true;
				
				}
			}
			
			
			else if (left_forward3 == false){
				Robot.outputSensors();
				DriveTrain.move(.75, 0);
				if(DriveTrain.readEncoderL() > 24 || DriveTrain.readEncoderR() > 24){
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				left_forward3 = true;	
				}
				

				
			}
			break;
			
		case boiler:
			Robot.outputSensors();
			if(center_forward1 == false){
				
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 110 || DriveTrain.readEncoderR() >= 110){
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
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							center_backUp1 = true;
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.resetEncoders();
						}							
			}
			break;
			
			
			
		case doNothing:
			DriveTrain.stop();
			Robot.outputSensors();
			break;
			
			
		case lineOnly:
				Robot.outputSensors();
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
	
	
		case boilerShoot:
			Robot.outputSensors();
			if(shoot_forward1 == false){
				
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 110 || DriveTrain.readEncoderR() >= 110){
					shoot_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
					
				else if (shoot_wait1 == false){
						
						Timer.delay(.5);
						Mechanisms.gearOpen();
						Timer.delay(1);
						shoot_wait1 = true;
				}
						
				else if(shoot_backUp1 == false){
						DriveTrain.move(-.5, 0);
						Mechanisms.shooterOn(1);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -54 || DriveTrain.readEncoderR() <= -54){
							shoot_backUp1 = true;
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.resetEncoders();
						}							
			}
			
				else if(shoot_shoot == false){
					Mechanisms.agitateFuel();
					
				}
			break;
			
		}

		
	}
	
	
}
