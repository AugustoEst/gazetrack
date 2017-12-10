package gazetrack;

import processing.core.*;

/**
 * GazeTrack is an eye-tracking library for Processing. It works with most 
 * commercial Tobii eye-trackers, including the EyeX and the 4C (tested), 
 * and most Tobii-enabled laptops. 
 * 
 * Before you start, make sure the 'TobiiStream.exe' is running and 
 * displaying gaze data. You can download this application from:
 * http://hci.soc.napier.ac.uk/GazeTrack/TobiiStream.zip
 *
 * @example Basic
 */

public class GazeTrack 
{
	private PApplet myParent;
	private TobiiStream gazeStream;

	
	/**
	 * The GazeTrack constructor
	 * 
	 * @param theParent
	 */
	public GazeTrack(PApplet theParent) 
	{
		myParent = theParent;
		myParent.registerMethod("dispose", this);
		
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
		
		gazeStream = new TobiiStream();
	}
				
	
	/**
	 * Returns the user's latest gaze position (X)
	 * 
	 * @return gaze position in x
	 */
	public float getGazeX()
	{
		return gazeStream.getGazeX();
	}
	
	
	/**
	 * Returns the user's latest gaze position (Y)
	 * 
	 * @return gaze position in y
	 */
	public float getGazeY()
	{
		return gazeStream.getGazeY();
	}
	
	
	/**
	 * Returns the timestamp for the latest gaze event
	 * 
	 * @return timestamp of the last gaze event
	 */
	public double getTimestamp()
	{
		return gazeStream.getTimestamp();
	}
	
	
	/**
	 * Returns true if the eye-tracker is capturing
     * the user's gaze
	 * 
	 * @return true if the user's gaze is present
	 */
	public boolean gazePresent()
	{
		return gazeStream.gazePresent();
	}
	
	
	/**
	 * Anything in here will be called automatically when the parent
	 * sketch shuts down. For instance, this might shut down a thread
	 * used by this library
	 */
	public void dispose()
	{
		gazeStream.terminate();
	}
	
}