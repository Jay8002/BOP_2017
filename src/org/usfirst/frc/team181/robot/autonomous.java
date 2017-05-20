//This program was created by Matthew Shelto and Laila Yost inn 2017
//This class is responsible for directing everything that happens in autonomous.

package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team181.robot.Robot;

public class autonomous {
	
	//create the ratio button selector for the autonomous modes.
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
	final String pidTest = "Test PID";
	final String in81 = "81in";
	
	//CONSOLODATE THESE BOOLEANS. PLEASE!!
	
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
	
	//for pidTest
	boolean pid_forward1 = false;
	
	double scaler = 0.03;
	
	//stores what side of the field we are on.
	boolean isRed = false;
	
	//when class is instantiated create all the chooser buttons. Set do nothing to default.
	public autonomous(){
		chooser.addDefault("Do nothing", doNothing);
		chooser.addObject("Center Gear", gearCenter);
		chooser.addObject("Boiler", boiler);
		//chooser.addObject("Airship Left Gear", gearLeft);
		chooser.addObject("<-- Center Gear Left", center_lineLeft);
		chooser.addObject("line Only", lineOnly);
		chooser.addObject("Center Gear Right -->", center_lineRight);
		chooser.addObject("Side gear and shoot",  rightShoot);
		//chooser.addObject("Airship Right Gear", gearRight);
		chooser.addObject("Center Gear and Shoot", centerShoot);
		chooser.addObject("Mid Right Gear", midRight);
		chooser.addObject("Mid Left Gear", midLeft);
		//chooser.addObject("Test PID", pidTest);
		//chooser.addObject("81in", in81);
	}

	//actually does autonomous
	public void doAutonomous(){
		
		//each case is for one autonomous mode. The cases are determined by which ratio button is selected.
		switch (chooser.getSelected()) {
		//Puts the gear on the center peg
		case gearCenter:
			Robot.outputSensors();
			if(center_forward1 == false){
				//drive forward  .6 speed for 81 inches.
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
					center_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			
				else if (center_wait1 == false){
						//wait for .5 secs
						Timer.delay(.5);
						//put the gear on peg.
						Mechanisms.gearOpen();
						//wait .5 seconds
						Timer.delay(.5);
						center_wait1 = true;
				}
						
				else if(center_backUp1 == false){
					//drive backwards .5 speed for 15 inches
						DriveTrain.pidBackwardDistance(-.5);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							center_backUp1 = true;
							DriveTrain.stop();
							//pull the plunger back in
							Mechanisms.gearClosed();
							DriveTrain.resetEncoders();
						}							
			}
			break;
			
			// put the center gear on and shoot.
		case centerShoot:
			Robot.outputSensors();
			if(centerShoot_forward1 == false){
				//drive forward .7 speed for 81 inches
				DriveTrain.pidForwardDistance(.7);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
					centerShoot_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			
				else if (centerShoot_wait1 == false){
						//wait .5 secs
						Timer.delay(.5);
						//push the gear out
						Mechanisms.gearOpen();
						//wait .5 secs
						Timer.delay(.5);
						centerShoot_wait1 = true;
				}
						
				else if(centerShoot_backUp1 == false){
						//drive backwards .5 speed for 15 inches
						DriveTrain.pidBackwardDistance(-.5);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							centerShoot_backUp1 = true;
							DriveTrain.stop();
							//close the gear mechanism
							Mechanisms.gearClosed();
							//turn the shooter on .78 speed
							Mechanisms.shooterOn(.78);
							//if we are on the red team turn left if blue right.
							if(isRed == true){
								DriveTrain.turn(-65);
							}
							if(isRed == false){
								DriveTrain.turn(65);
							}
							DriveTrain.resetEncoders();
						}							
				}
				else if (centerShoot_backUp2 == false){
					//drive backwards .5 speed for 45 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -45 || DriveTrain.readEncoderR() <= -45){
						centerShoot_backUp2 = true;
						DriveTrain.stop();
						DriveTrain.resetEncoders();
						//open shooter door
						Mechanisms.openShooter();
						
					}
				}
			break;
			
			
		case center_lineRight:
			
				Robot.outputSensors();
				if(center_rightForward1 == false){
				// drive forward .6 speed for 81 inches
					DriveTrain.pidForwardDistance(.6);
					Robot.outputSensors();
					
					if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
						center_rightForward1 = true;
						DriveTrain.stop();
						DriveTrain.resetEncoders();
					}
			}
					
				else if (center_rightWait1 == false){
					//wait .5 inches
						Timer.delay(.5);
						//open the gear mechanism
						Mechanisms.gearOpen();
						//wait .5 seconds
						Timer.delay(.5);
						center_rightWait1 = true;
				}
						
				else if(center_rightBackUp1 == false){
					//drive backwards .55 speed for 20 inches
						DriveTrain.pidBackwardDistance(-.5);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
							DriveTrain.stop();
							//close the gear mechanism
							Mechanisms.gearClosed();
							//turn right 58 degrees
							DriveTrain.turn(58);
							//wait .5 seconds
							Timer.delay(.5);
							DriveTrain.resetEncoders();
							center_rightBackUp1 = true;
						}	
						System.out.println("Starting 2nd half");
				}
								
				else if(center_rightForward2 == false){
					//Drive forward .75 speed for 60 inches
					DriveTrain.pidForwardDistance(.75);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() > 60 || DriveTrain.readEncoderR() > 60){
						DriveTrain.stop();
						DriveTrain.resetEncoders();
						center_rightForward2 = true;
					}
					
				}
			break;
			//put the gear on the center peg. Then drive off to the left.
		case center_lineLeft:
			
			Robot.outputSensors();
			if(center_leftForward1 == false){
			//drive forward .6 speed for 81 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
					center_leftForward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
				
			else if (center_leftWait1 == false){
					//wait .5 secs
					Timer.delay(.5);
					//push the gear out
					Mechanisms.gearOpen();
					//wait .5 seconds
					Timer.delay(.5);
					center_leftWait1 = true;
			}
					
			else if(center_leftBackUp1 == false){
				//drive backwards .5 speed for 20 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						 //close the gear
						Mechanisms.gearClosed();
						//turn left 58 degrees
						DriveTrain.turn(-58);
						//wait .5 secs
						Timer.delay(.5);
						DriveTrain.resetEncoders();
						center_leftBackUp1 = true;
					}							
			}
			
			else if(center_leftForward2 == false){
				//Drive forward .75 speed for 60 inches 
				DriveTrain.pidForwardDistance(.75);
				Robot.outputSensors();
				if(DriveTrain.readEncoderL() > 60 || DriveTrain.readEncoderR() > 60){
					DriveTrain.stop();
					DriveTrain.resetEncoders();
					center_leftForward2 = true;
				}
				
			}
		break;
		
		// put the gear on the right side
		case gearRight:
			Robot.outputSensors();
			if(right_forward1 == false){
			//drive forwards .6 speed for 70 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 70 || DriveTrain.readEncoderR() >= 70){
					/// if on red team turn left 60 degrees if blue other direction
					if(isRed == true){
						DriveTrain.turn(-60);
					}
					if(isRed == false){
						DriveTrain.turn(60);
					}
					right_forward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (right_forward2 == false){
				// drive forward .6 speed 84 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 84 || DriveTrain.readEncoderR() >= 84){
					right_forward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (right_wait1 == false){
					//wait .5 secs
					Timer.delay(.5);
					//put gear on
					Mechanisms.gearOpen();
					//wait .5 secs
					Timer.delay(.5);
					right_wait1 = true;
			}
					
			else if(right_backUp1 == false){
				//drive backwards .5 speed for 20 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						 //close the gear plunger
						Mechanisms.gearClosed();
						DriveTrain.resetEncoders();
						right_backUp1 = true;
					}							
			}
			
		break;
		
			//put the gear on the right side
		case midRight:
			Robot.outputSensors();
			if(midRight_forward1 == false){
			//forward .6 speed 106 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 106 || DriveTrain.readEncoderR() >= 106){
					//turn 63 degrees left
					DriveTrain.turn(-63);
					midRight_forward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (midRight_forward2 == false){
				//forward .6 speed 24 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 24 || DriveTrain.readEncoderR() >= 24){
					midRight_forward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (midRight_wait1 == false){
				//wait .5 secs
					Timer.delay(.5);
					//push gear out
					Mechanisms.gearOpen();
					//wait .5 secs
					Timer.delay(.5);
					midRight_wait1 = true;
			}
					
			else if(midRight_backUp1 == false){
				//backwards .5 speed 20 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						 //close gear mechanism
						Mechanisms.gearClosed();
						//turn to front??
						DriveTrain.turn(0);
						DriveTrain.resetEncoders();
						midRight_backUp1 = true;
					}							
			}
			
		break;
		
		
		//put gear on left side
		case midLeft:
			Robot.outputSensors();
			if(midLeft_forward1 == false){
			//forward .6 speed 106 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 106 || DriveTrain.readEncoderR() >= 106){
					//turn 63 inches
					DriveTrain.turn(63);
					midLeft_forward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (midLeft_forward2 == false){
				//forwards .6 speed 24 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 24 || DriveTrain.readEncoderR() >= 24){
					midLeft_forward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (midLeft_wait1 == false){
					//wati .5 secs
					Timer.delay(.5);
					//push gear out
					Mechanisms.gearOpen();
					//wait .5 secs
					Timer.delay(.5);
					midLeft_wait1 = true;
			}
					
			else if(midLeft_backUp1 == false){
				//back .5 speed 20 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						 //pull gear plunger in 
						Mechanisms.gearClosed();
						//turn to front
						DriveTrain.turn(0);
						DriveTrain.resetEncoders();
						midLeft_backUp1 = true;
					}							
			}
			
		break;
		
		//align against boiler
		case boiler:
			Robot.outputSensors();
			if(boiler_forward1 == false){
				// forward .6 speed 110 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 110 || DriveTrain.readEncoderR() >= 110){
					boiler_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
					
				else if (boiler_wait1 == false){
				//wait .5 secs
						Timer.delay(.5);
						//push gear out
						Mechanisms.gearOpen();
						//wait .5 secs
						Timer.delay(.5);
						boiler_wait1 = true;
				}
						
				else if(boiler_backUp1 == false){
					//back .5 speed 15 secs
						DriveTrain.pidBackwardDistance(-.5);
						Robot.outputSensors();
						if(DriveTrain.readEncoderL() <= -15 || DriveTrain.readEncoderR() <= -15){
							boiler_backUp1 = true;
							DriveTrain.stop();
							//close gear mechanism
							Mechanisms.gearClosed();
							DriveTrain.resetEncoders();
						}							
			}
			break;
			
			
			//Does nothing. Well not really.
		case doNothing:
			//stops motor
			DriveTrain.stop();
			//puts sensor data on dashboard. (see-does something)
			Robot.outputSensors();
			break;
			
			//goes past a line.
		case lineOnly:
				Robot.outputSensors();
			//drive forward .7 speed 93 inches
			if (DriveTrain.readEncoderL() < 93 && DriveTrain.readEncoderR() < 93){
				DriveTrain.pidForwardDistance(.7);
				SmartDashboard.putNumber("Left Distance", DriveTrain.readEncoderL());
				SmartDashboard.putNumber("Right Distance", DriveTrain.readEncoderR());
				
			}
			else if (DriveTrain.readEncoderL() >= 93 && DriveTrain.readEncoderR() >= 93){
				//stops when done
			  	DriveTrain.stop();
			  }
			  break;
			
		default:
			// Put default auto code here
			break;
	
	// shoots in the boiler (Named badly)
		case rightShoot:
			Robot.outputSensors();
			if(RshootForward1 == false){
			//forward .6 speed 106 in
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 106 || DriveTrain.readEncoderR() >= 106){
					//if red turn left 63 degrees. If blue other direction
					if (isRed == true){
						DriveTrain.turn(-63);
					}
					if(isRed == false){
						DriveTrain.turn(63);
					}
					RshootForward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (RshootForward2 == false){
				//forward .6 speed 24 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 24 || DriveTrain.readEncoderR() >= 24){
					RshootForward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (RshootWait1 == false){
					//wait .5 secs
					Timer.delay(.5);
					//push gear out
					Mechanisms.gearOpen();
					//wait .5 secs
					Timer.delay(.5);
					RshootWait1 = true;
			}
					
			else if(RshootBackUp1 == false){
				//backwards .5 speed 60 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -60 || DriveTrain.readEncoderR() <= -60){
						 DriveTrain.stop();
						 //pull gear in
						Mechanisms.gearClosed();
						//shooter on .75 speed
						Mechanisms.shooterOn(.75);
						DriveTrain.resetEncoders();
						DriveTrain.zeroYaw();
						//if we are red turn 15 degrees right if not other direction
						if(isRed == true){
							DriveTrain.turn(15);
						}
						if (isRed == false){
							DriveTrain.turn(-15);
						}
						//Mechanisms.agitateFuel(); We removed the agitator
						
						//open shooter door
						Mechanisms.openShooter();
						RshootBackUp1 = true;
					}							
			}
			break;
			
			
		//put the gear on the left side
		case gearLeft:
			Robot.outputSensors();
			if(leftForward1 == false){
			//forward .6 speed 70 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if(DriveTrain.readEncoderL() >= 70 || DriveTrain.readEncoderR() >= 70){
					//turn 60 degrees right
					DriveTrain.turn(60);
					leftForward1  = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
		}
			else if (leftForward2 == false){
				//forward .6 speed 84 inches
				DriveTrain.pidForwardDistance(.6);
				Robot.outputSensors();
				
				if (DriveTrain.readEncoderL() >= 84 || DriveTrain.readEncoderR() >= 84){
					leftForward2 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			else if (leftWait1 == false){
					//wait .5 secs
					Timer.delay(.5);
					//push gear out
					Mechanisms.gearOpen();
					//wait .5 secs
					Timer.delay(.5);
					leftWait1 = true;
			}
					
			else if(leftBackUp1 == false){
				//backwards .5 speed 20 inches
					DriveTrain.pidBackwardDistance(-.5);
					Robot.outputSensors();
					if(DriveTrain.readEncoderL() <= -20 || DriveTrain.readEncoderR() <= -20){
						 DriveTrain.stop();
						Mechanisms.gearClosed();
						DriveTrain.resetEncoders();
						leftBackUp1 = true;
					}							
			}
			
		break;
		
		//test for driving in a straight line (Currently set to backwards. Don't forget)
		case pidTest:
			Robot.outputSensors();
			if(pid_forward1 == false){
				//backwards .5 speed 81 inches
				DriveTrain.pidBackwardDistance(-.5);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() <= -81 || DriveTrain.readEncoderR() <= -81){
					pid_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
					
				else if (center_wait1 == false){
						//wait .5 secs
						Timer.delay(.5);
						//push gear out
						Mechanisms.gearOpen();
						//wait .5 secs
						Timer.delay(.5);
						center_wait1 = true;
				}
				Mechanisms.gearClosed();
			break;
					
//go 81 inches forward
		case in81:
			
			Robot.outputSensors();
			if(pid_forward1 == false){
				//go forward .6 speed for 81 inches
				DriveTrain.move(.6, 0);
				Robot.outputSensors();
			
				if(DriveTrain.readEncoderL() >= 81 || DriveTrain.readEncoderR() >= 81){
					pid_forward1 = true;
					DriveTrain.stop();
					DriveTrain.resetEncoders();
				}
			}
			}
			
	}	
}
