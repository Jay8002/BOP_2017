//Created by Matthew Shelto and Laila Yost in 2017
//This class is run as a seperate thread, that controls a second camera attached to the robo rio.
// We never used this.
package org.usfirst.frc.team181.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera2 extends Thread {
	
	private String streamname2;
	private UsbCamera camera2;
	
	//when setting up the camera start capturing automatically
	public Camera2(String streamname) {
		this.streamname2 = streamname;
		camera2 = CameraServer.getInstance().startAutomaticCapture();
	}
	// when the thread is started 
	public void run() {
		//set the camera resolution
		camera2.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);
		//get the stream and output it.
		CvSink cvSink = CameraServer.getInstance().getVideo();
		CvSource outputStream = CameraServer.getInstance().putVideo(streamname2, 640, 480);

		Mat mat = new Mat();
		//while the thread is still running
		while (!Thread.interrupted()) {
			//if a frame can not be found, throw an error.
			if (cvSink.grabFrame(mat) == 0) {
				outputStream.notifyError(cvSink.getError());
				continue;
			}
			Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
					new Scalar(255, 255, 255), 5);
			outputStream.putFrame(mat);
		}
		this.setDaemon(true);
	}
	
	public UsbCamera getCamera() {
		return this.camera2;
	}
}

