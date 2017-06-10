//Created by Matthew Shelto and Laila Yost in 2017


//This class is a very basic vision processing program.
//It reads data output from NetworkTables. The data being sent to NetworkTables is read by GRIP on a Raspberry Pi.

//WATCH VIDEO ON PI AND VISION PROCESSING

package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.networktables.*;


//Is a seperate thread
public class Vision extends Thread{
	static //initialize variables
	public NetworkTable table;
	static double [] defaultArray = new double [1];
	double [] x = new double [10];
	double [] y;
	static double left;
	static double right;
	double [] testArray = new double [10];
	static double center;
	public Vision(){
		defaultArray [0] = 1.0;
		NetworkTable.setIPAddress("10.1.81.2");		
		//Get the network table made by GRIP
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}
	
	//While the thread is running...
	public void run(){
		//loop if thread is not having problems
		while(!Thread.interrupted()){
			try{
				//Get the x position of all detected objects. Put them in an array.
				x = table.getNumberArray("centerX", defaultArray); 
				//figure out which stored object is higher up. Store variables appropriately.
				if(x[0] > x[1]){
					left = x[1];
					right =x[0];
				}
				if(x[0] < x[1]){
					left = x[0];
					right = x[1];
				}
				
			}
			//If there are not enough contours detected.
			catch(ArrayIndexOutOfBoundsException exception){
			System.out.println("Not enough contours have been detected.");
			}
		}
		if(Thread.interrupted()){
			System.out.println("The vision thread was inturrupted. error. warning?");
		}
	}
	
	//Return the centerpoint between both objects.
	public static double getCenter(){
		
		center = (right + left)/2.0;
		
		center = convertCenter(center);
		
		return center;
	}
	
	//convert the centerpoint from a scale of -1 to 1. zero is in the center of the camera.
	public static double convertCenter(double center){
		double output = -(1-(center/256))+(center/256);
		return output;
	}
	
	
}

