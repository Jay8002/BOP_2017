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
	final String gearRight = "Right Gear";
	final String centerTarget = "CenterTarget";
	final String lineOnly = "lineOnly";
	final String rightShoot = "right gear and shoot";
	final String centerShoot = "Center Gear and Shoot";
	final String midRight = "Mid Right Gear";
	final String midLeft = "Mid Left Gear";
	
	//for centerShoot
	boolean centerShoot_forward1 = false;
	boolean centerShoot_wait1 = false;
	boolean centerShoot_backUp1 = false;
	boolean centerShoot_backUp2 = false;
	boolean centerShoot_turn2 = false;
	
	
	//for left gear
	boolean leftBackUp1  = false;
	boolean leftForward = false;
	boolean leftForward1 = false;
	boolean leftForward2 = false;
	boolean leftWait1 = false;
	
	
	
	//for center gear
	boolean center_forward1 = false;
	boolean center_wait1 = false;
	boolean center_backUp1 = false;
	
	//for diagonal gear
	boolean diagonal_forward1 = false;
	boolean diagonal_wait1 = false;
	boolean diagonal_backUp1 = false;
	
	//for right gear and shooter
	boolean RshootBackUp1 = false;
	boolean RshootForward1 = false;
	boolean RshootForward2 = false;
	boolean RshootWait1 = false;
	
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
	boolean right_backUp1 = false;
	boolean right_wait1 = false;
	
	//for boiler
	boolean boiler_backUp1 = false;
	boolean boiler_forward1 = false;
	boolean boiler_wait1 = false;
	
	
	//for mid right gear
	boolean midRight_forward1 = false;
	boolean midRight_forward2 = false;
	boolean midRight_backUp1 = false;
	boolean midRight_wait1 = false;
	
	
	//for mid left gear
	boolean midLeft_forward1 = false;
	boolean midLeft_forward2 = false;
	boolean midLeft_backUp1 = false;
	boolean midLeft_wait1 = false;
	
	public autonomous(){
		chooser.addDefault("Do nothing", doNothing);
		chooser.addObject("Center Gear", gearCenter);
		chooser.addObject("Boiler", boiler);
		chooser.addObject("Airship Left Gear", gearLeft);
		chooser.addObject("<-- Center Gear Left", center_lineLeft);
		chooser.addObject("line Only", lineOnly);
		chooser.addObject("Center Gear Right -->", center_lineRight);
		chooser.addObject("Boiler right gear and shoot",  rightShoot);
		chooser.addObject("Airship Right Gear", gearRight);
		chooser.addObject("Center Gear and Shoot", centerShoot);
		chooser.addObject("Mid Right Gear", midRight);
		chooser.addObject("Mid Left Gear", midLeft);
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
			
			
		case centerShoot:
			Robot.outputSensors();
			if(centerShoot_forward1 == false){
				
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
					centerShoot_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
					
				else if (centerShoot_wait1 == false){
						
						Timer.delay(.5);
						Mechanisms.gearOpen();
						Timer.delay(1);
						centerShoot_wait1 = true;
				}
						
				else if(centerShoot_backUp1 == false){
						DriveTrain.move(-.5, 0);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							centerShoot_backUp1 = true;
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.turn(-61);
							DriveTrain.resetEncoders();
						}							
				}
				else if (centerShoot_backUp2 == false){
					DriveTrain.move(-.5, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -60 || DriveTrain.readEncoderR() <= -60){
						centerShoot_backUp2 = true;
						DriveTrain.stop();
						DriveTrain.resetEncoders();
						Mechanisms.shooterOn(1);
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
						if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
							DriveTrain.stop();
							Mechanisms.gearClosed();
							DriveTrain.turn(58);
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
					if(DriveTrain.readEncoderL() > 60 || DriveTrain.readEncoderR() > 60){
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
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.turn(-58);
						Timer.delay(.5);
						DriveTrain.resetEncoders();
						center_leftBackUp1 = true;
					}							
			}
			
			else if(center_leftForward2 == false){
				DriveTrain.move(.75, 0);
				Robot.outputSensors();
				if(DriveTrain.readEncoderL() > 60 || DriveTrain.readEncoderR() > 60){
					DriveTrain.stop();

					DriveTrain.resetEncoders();
					center_leftForward2 = true;
				}
				
			}
		break;
		
		
		case gearRight:
			Robot.outputSensors();
			if(right_forward1 == false){
			
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 70 || DriveTrain.readEncoderR() >= 70){
					DriveTrain.turn(-60);
					right_forward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (right_forward2 == false){
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 84 || DriveTrain.readEncoderR() >= 84){
					right_forward2 = true;
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
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.resetEncoders();
						right_backUp1 = true;
					}							
			}
			
		break;
		
			
		case midRight:
			Robot.outputSensors();
			if(midRight_forward1 == false){
			
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 102 || DriveTrain.readEncoderR() >= 102){
					DriveTrain.turn(-65);
					midRight_forward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (midRight_forward2 == false){
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 24 || DriveTrain.readEncoderR() >= 24){
					midRight_forward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (midRight_wait1 == false){
					
					Timer.delay(.5);
					Mechanisms.gearOpen();
					Timer.delay(1);
					midRight_wait1 = true;
			}
					
			else if(midRight_backUp1 == false){
					DriveTrain.move(-.5, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.turn(0);
						DriveTrain.resetEncoders();
						midRight_backUp1 = true;
					}							
			}
			
		break;
		
		
		
		case midLeft:
			Robot.outputSensors();
			if(midLeft_forward1 == false){
			
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 102 || DriveTrain.readEncoderR() >= 102){
					DriveTrain.turn(65);
					midLeft_forward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (midLeft_forward2 == false){
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 24 || DriveTrain.readEncoderR() >= 24){
					midLeft_forward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (midLeft_wait1 == false){
					
					Timer.delay(.5);
					Mechanisms.gearOpen();
					Timer.delay(1);
					midLeft_wait1 = true;
			}
					
			else if(midLeft_backUp1 == false){
					DriveTrain.move(-.5, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.turn(0);
						DriveTrain.resetEncoders();
						midLeft_backUp1 = true;
					}							
			}
			
		break;
		
		
		case boiler:
			Robot.outputSensors();
			if(boiler_forward1 == false){
				
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 110 || DriveTrain.readEncoderR() >= 110){
					boiler_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
					
				else if (boiler_wait1 == false){
						
						Timer.delay(.5);
						Mechanisms.gearOpen();
						Timer.delay(1);
						boiler_wait1 = true;
				}
						
				else if(boiler_backUp1 == false){
						DriveTrain.move(-.5, 0);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							boiler_backUp1 = true;
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
	
	
		case rightShoot:
			Robot.outputSensors();
			if(RshootForward1 == false){
			
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 102 || DriveTrain.readEncoderR() >= 102){
					DriveTrain.turn(-65);
					RshootForward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (RshootForward2 == false){
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 24 || DriveTrain.readEncoderR() >= 24){
					RshootForward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (RshootWait1 == false){
					
					Timer.delay(.5);
					Mechanisms.gearOpen();
					Timer.delay(1);
					RshootWait1 = true;
			}
					
			else if(RshootBackUp1 == false){
					DriveTrain.move(-.5, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -60 || DriveTrain.readEncoderR() <= -60){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.resetEncoders();
						DriveTrain.zeroYaw();
						DriveTrain.turn(20);
						//Mechanisms.agitateFuel();
						Mechanisms.shooterOn(1);
						RshootBackUp1 = true;
					}							
			}
			break;
			
			
			
		case gearLeft:
			Robot.outputSensors();
			if(leftForward1 == false){
			
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 70 || DriveTrain.readEncoderR() >= 70){
					DriveTrain.turn(60);
					leftForward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (leftForward2 == false){
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 84 || DriveTrain.readEncoderR() >= 84){
					leftForward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (leftWait1 == false){
					
					Timer.delay(.5);
					Mechanisms.gearOpen();
					Timer.delay(1);
					leftWait1 = true;
			}
					
			else if(leftBackUp1 == false){
					DriveTrain.move(-.5, 0);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.resetEncoders();
						leftBackUp1 = true;
					}							
			}
			
		break;
		}

		
	}
	
	
}
