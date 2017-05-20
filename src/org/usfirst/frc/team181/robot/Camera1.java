// this program was created by Matthew Shelto and Laila Yost in 2017
// This class is responsible for controlling a camera attached into the robo rio.
//We usually put the output on the smart dashboard
//The comments are the same as the Camera2.java file.

package org.usfirst.frc.team181.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera1 extends Thread {
	
	private String streamname;
	private UsbCamera camera;
	
	public Camera1(String streamname) {
		this.streamname = streamname;
		camera = CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void run() {
		camera.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);

		CvSink cvSink = CameraServer.getInstance().getVideo();
		CvSource outputStream = CameraServer.getInstance().putVideo(streamname, 640, 480);

		Mat mat = new Mat();
		
		while (!Thread.interrupted()) {
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
		return this.camera;
	}
}

