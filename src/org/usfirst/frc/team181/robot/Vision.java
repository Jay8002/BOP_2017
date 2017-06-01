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

	public Vision(){
		defaultArray [0] = 1.0;
		NetworkTable.setIPAddress("10.1.81.2");		
		//Get the network table made by GRIP
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}
	
	//While the thread is running...
	public void run(){
		//infinite loop
		while(true){
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
			catch(ArrayIndexOutOfBoundsException exception){
			
			}
		}
			
	}
	
	//Return the centerpoint between both objects.
	public static double getCenter(){
		
		double center = (right + left)/2.0;
		
		return center;
	}
	
	
}

