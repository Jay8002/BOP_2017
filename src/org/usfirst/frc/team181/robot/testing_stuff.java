package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.networktables.*;

public class testing_stuff extends Thread{
	
	NetworkTable table;
	double defaultArray [] = new double [0];
	double [] x;
	double [] y;
	static double top;
	static double bottom;
	public testing_stuff(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
	}
	
	public void run(){
		while(true){
			x = table.getNumberArray("centerX", defaultArray); 
			y = table.getNumberArray("centerY" , defaultArray);
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
	public static double getCenter(){
		
		double center = (bottom - top)/2.0;
		
		return center;
	}
	
}

