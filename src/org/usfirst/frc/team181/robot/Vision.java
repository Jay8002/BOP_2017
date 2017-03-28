package org.usfirst.frc.team181.robot;

import edu.wpi.first.wpilibj.networktables.*;
public class Vision {
	
	NetworkTable networkTable;
	
	public Vision(){
		networkTable = NetworkTable.getTable("GRIP/contours");
	}
	
	

}
