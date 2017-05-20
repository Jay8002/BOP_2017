//Created by Matthew Shelto and Laila Yost in 2017


//This class is a very basic vision processing program.
//It reads data output from NetworkTables. The data being sent to NetworkTables is read by GRIP on a Raspberry Pi.

//WATCH VIDEO ON PI AND VISION PROCESSING

package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.networktables.*;

//Is a seperate thread
public class Vision extends Thread{
	//initialize variables
	NetworkTable table;
	double defaultArray [] = new double [0];
	double [] x;
	double [] y;
	static double top;
	static double bottom;
	

	public Vision(){
		//Get the network table made by GRIP
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}
	
	//While the thread is running...
	public void run(){
		//infinite loop
		while(true){
			//Get the x position of all detected objects. Put them in an array.
			x = table.getNumberArray("centerX", defaultArray); 
			
			//figure out which stored object is higher up. Store variables appropriately.
			if(x[0] > x[1]){
				top = x[0];
				bottom =x[1];
			}
			if(x[1] > x[0]){
				top = x[1];
				bottom = x[0];
			}
		}
			
	}
	
	//Return the centerpoint between both objects.
	public static double getCenter(){
		
		double center = (bottom - top)/2.0;
		
		return center;
	}
	
}

