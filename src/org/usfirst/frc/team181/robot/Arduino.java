package org.usfirst.frc.team181.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Arduino {
	
	public static void read_constant() throws IOException{
		
		//open the input file coming from the Arduino
		FileReader file = new FileReader("/dev/ttyACM0");
		//This variable is able to preform functions on the previously opened file.
		BufferedReader input = new BufferedReader(file);
		
		//loop until there is still things to read. If the arduino stops sending, this will stop reading.
		for(int i=0; i<=300; i++){
			//print the following message
			System.out.println("Serial input: (" + input.readLine() + ")");
			
		}
		//close the file, so that we do not have problems with it.
		file.close();
		
	}
	
	public static void readOnce() throws IOException{
		//open the input file coming from the Arduino
		FileReader file = new FileReader("/dev/ttyACM0");
		//This variable is able to preform functions on the previously opened file.
		BufferedReader input = new BufferedReader(file);
		
			//print the following message
			System.out.println("Serial input: (" + input.readLine() + ")");
		//close the file, so that we do not have problems with it.
		file.close();
	}

}
