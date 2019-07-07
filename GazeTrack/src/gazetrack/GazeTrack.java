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
	private TobiiStream gazeStream;
	private int sketchWidth, sketchHeight;
	
	/**
	 * The GazeTrack constructor (default ZMQ socket)
	 * 
	 * @param theParent
	 */
	public GazeTrack(PApplet theParent)						{ init(theParent, "5556"); }
	

	/**
	 * The GazeTrack constructor (specific ZMQ socket).
	 * 
	 * This should be used when the TobiiStream.exe asks
	 * for a new socket port (due to the default being 
	 * in use already)
	 * 
	 * @param theParent
	 * @param socket
	 */
	public GazeTrack(PApplet theParent, String socket)		{ init(theParent, socket); }
	
	
	/**
	 * Starts the library after the GazeTrack constructor
	 * is called
	 * 
	 * @param theParent
	 * @param socket
	 */
	private void init(PApplet theParent, String socket)
	{
		sketchWidth = theParent.sketchWidth();
		sketchHeight = theParent.sketchHeight();
		
		theParent.registerMethod("dispose", this);
		
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
		
		gazeStream = new TobiiStream(socket);
	}
				
	
	/**
	 * Returns the user's latest gaze position (X)
	 * 
	 * @return gaze position in x
	 */
	public float getGazeX()									{ return gazeStream.getGazeX(); }
	
	
	/**
	 * Returns the user's latest gaze position (Y)
	 * 
	 * @return gaze position in y							
	 */
	public float getGazeY()									{ return gazeStream.getGazeY(); }
	
	
	/**
	 * Returns the timestamp for the latest gaze event
	 * 
	 * @return timestamp of the last gaze event
	 */
	public double getTimestamp()							{ return gazeStream.getTimestamp(); }
	
	
	/**
	 * Returns true if the eye-tracker is capturing
     * the user's gaze
	 * 
	 * @return true if the user's gaze is present
	 */
	public boolean gazePresent()							{ return gazeStream.gazePresent(); }
	
	
	/**
     * Returns true if the eye-tracker is capturing
     * the user's left eye
     * 
     * @return true if user's left eye is present
     */
	public boolean leftEyePresent()							{ return gazeStream.leftEyePresent(); }
	
	
	/**
     * Returns the user's left eye position.
     * This is the X position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return left eye position in x (mm)
     */
    public float getLeftEyeXmm()							{ return gazeStream.getLeftEyeX(); }
    
    
    /**
     * Returns the user's left eye position.
     * This is the Y position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return left eye position in y (mm)
     */
    public float getLeftEyeYmm()							{ return gazeStream.getLeftEyeY(); }
   
    
    /**
     * Returns the user's left eye position.
     * This is the Z position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return left eye position in z (mm)
     */
    public float getLeftEyeZmm()							{ return gazeStream.getLeftEyeZ(); }
    
    
    /**
     * Returns the user's left eye position (normalized X)
     * 
     * @return left left eye position in x (normalized)
     */ 
    public float getLeftEyeX()  							{ return sketchWidth - gazeStream.getLeftEyeNormX() * sketchWidth; }
    
    
    /**
     * Returns the user's left eye position (normalized Y)
     * 
     * @return left eye position in y (normalized)
     */
    public float getLeftEyeY()								{ return gazeStream.getLeftEyeNormY() * sketchHeight; }
    
    
    /**
     * Returns the user's left eye position (normalized Z)
     * 
     * @return left eye position in z (normalized)
     */
    public float getLeftEyeZ()								{ return gazeStream.getLeftEyeNormZ(); }
	
	
	/**
     * Returns true if the eye-tracker is capturing
     * the user's right eye
     * 
     * @return true if user's right eye is present
     */
	public boolean rightEyePresent()						{ return gazeStream.rightEyePresent(); }
	
	
	/**
     * Returns the user's right eye position.
     * This is the X position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return right eye position in x (mm)
     */
    public float getRightEyeXmm()							{ return gazeStream.getRightEyeX(); }
    
    
    /**
     * Returns the user's right eye position.
     * This is the Y position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return right eye position in y (mm)
     */
    public float getRightEyeYmm()							{ return gazeStream.getRightEyeY(); }
   
    
    /**
     * Returns the user's right eye position.
     * This is the Z position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return right eye position in z (mm)
     */
    public float getRightEyeZmm()							{ return gazeStream.getRightEyeZ(); }
    
    
    /**
     * Returns the user's right eye position (normalized X)
     *  
     * @return right right eye position in x (normalized)
     */
    public float getRightEyeX()  							{ return sketchWidth - gazeStream.getRightEyeNormX() * sketchWidth; }
    
    
    /**
     * Returns the user's right eye position (Y)
     * 
     * @return right eye position in y (normalized)
     */
    public float getRightEyeY()								{ return gazeStream.getRightEyeNormY() * sketchHeight; }
    
    
    /**
     * Returns the user's right eye position (normalized Z)
     * 
     * @return right eye position in z (normalized)
     */
    public float getRightEyeZ()								{ return gazeStream.getRightEyeNormZ(); }
    
    
    /**
     * Returns the user's head position (X)
     * 
     * @return head position in x
     */
    public float getHeadPositionX()							{ return gazeStream.getHeadPositionX(); }
    
    
    /**
     * Returns the user's head position (Y)
     * 
     * @return head position in y
     */
    public float getHeadPositionY()							{ return gazeStream.getHeadPositionY(); }
    
    
    /**
     * Returns the user's head position (Z)
     * 
     * @return head position in z
     */
    public float getHeadPositionZ()							{ return gazeStream.getHeadPositionZ(); }
    
    
    /**
     * Returns the user's head rotation (Pitch)
     * 
     * @return head rotation (pitch)
     */
    public float getHeadRotationX()							{ return gazeStream.getHeadRotationX(); }
    
    
    /**
     * Returns the user's head rotation (Yaw)
     * 
     * @return head rotation (yaw)
     */
    public float getYaw()							{ return gazeStream.getHeadRotationY(); }
    
    
    /**
     * Returns the user's head rotation (Roll)
     * 
     * @return head rotation (roll)
     */
    public float getHeadRotationZ()							{ return gazeStream.getHeadRotationZ(); }
	
	
	/**
	 * Anything in here will be called automatically when the parent
	 * sketch shuts down. For instance, this might shut down a thread
	 * used by this library
	 */
	public void dispose()									{ gazeStream.terminate(); }
}