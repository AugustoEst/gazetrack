package gazetrack;

import java.util.StringTokenizer;
import org.zeromq.ZMQ;

public class TobiiStream extends Thread 
{
	// Connection variables
	// For now the library only supports I/O in the same device
	private String ip_address = "127.0.0.1";
	private String socket;
	
	// Gaze variables
	private float gazeX, gazeY;
	private double timestamp;
	private String gazeState;
	
	// Left eye variables
	private int leftEyePresent;
	private float leftEyeX, leftEyeY, leftEyeZ;
	private float leftEyeNormX, leftEyeNormY, leftEyeNormZ;
	
	// Right eye variables
	private int rightEyePresent;
	private float rightEyeX, rightEyeY, rightEyeZ;
	private float rightEyeNormX, rightEyeNormY, rightEyeNormZ;
	
	// Thread variables
    private boolean running;

    
    /**
     * a Constructor, usually called at the beginning of execution so
     * it starts listening for the Tobii gaze data stream. 
     * 
     * This requires 'TobiiStream.exe' to be running and displaying 
     * gaze data. You can download this application from:
	 * https://hci.soc.napier.ac.uk/GazeTrack/TobiiStream.zip
	 * 
	 * @param socket
     */
    public TobiiStream(String socket)
    {
        timestamp = -1;
        gazeX = -1; 
        gazeY = -1;
        
        leftEyePresent = 0;			// 0 (not present), 1 (present)
        leftEyeX = -1;
        leftEyeY = -1;
        leftEyeZ = -1;
        leftEyeNormX = -1;
        leftEyeNormY = -1;
        leftEyeNormZ = -1;
        
        rightEyePresent = 0;		// 0 (not present), 1 (present)
        rightEyeX = -1;
        rightEyeY = -1;
        rightEyeZ = -1;
        rightEyeNormX = -1;
        rightEyeNormY = -1;
        rightEyeNormZ = -1;
        
        running = false;
        gazeState = "Present";		// Present, NotPresent
        this.socket = socket;
        
        start();
    }
    
    
    /**
     * Returns the user's gaze position (X)
     * 
     * @return gaze position in x
     */
    public float getGazeX() 			{ return gazeX; }
    
    
    /**
     * Returns the user's gaze position (Y)
     * 
     * @return gaze position in y
     */
    public float getGazeY() 			{ return gazeY; }
    
    
    /**
     * Returns the user's gaze position (Y)
     * 
     * @return timestamp of the last gaze event
     */
    public double getTimestamp() 		{ return timestamp; }
    
    
    /**
     * Returns true if the eye-tracker is capturing
     * the user's gaze
     * 
     * @return true if the user's gaze is present
     */
    public boolean gazePresent()		{ return "Present".equals(gazeState); }
    
    
    /**
     * Returns true if the user's left eye is
     * being captured by the Tobii camera
     * 
     * @return true if user's left eye is present
     */
    public boolean leftEyePresent() 	{ return leftEyePresent == 1; }
    
    
    /**
     * Returns the user's left eye position.
     * This is the X position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return left eye position in x (mm)
     */
    public float getLeftEyeX()			{ return leftEyeX; }
    
    
    /**
     * Returns the user's left eye position.
     * This is the Y position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return left eye position in y (mm)
     */
    public float getLeftEyeY()			{ return leftEyeY; }
   
    
    /**
     * Returns the user's left eye position.
     * This is the Z position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return left eye position in z (mm)
     */
    public float getLeftEyeZ()			{ return leftEyeZ; }
    
    
    /**
     * Returns the user's left eye (normalized) position (X)
     * 
     * @return left left eye position in x (normalized)
     */
    public float getLeftEyeNormX()  	{ return leftEyeNormX; }
    
    
    /**
     * Returns the user's left eye (normalized) position (Y)
     * 
     * @return left eye position in y (normalized)
     */
    public float getLeftEyeNormY()		{ return leftEyeNormY; }
    
    
    /**
     * Returns the user's left eye (normalized) position (Z)
     * 
     * @return left eye position in z (normalized)
     */
    public float getLeftEyeNormZ()		{ return leftEyeNormZ; }
    
    
    /**
     * Returns true if the user's right eye is
     * being captured by the Tobii camera
     * 
     * @return true if user's right eye is present
     */
    public boolean rightEyePresent()	{ return rightEyePresent == 1; }
    
    
    /**
     * Returns the user's right eye position.
     * This is the X position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return right eye position in x (mm)
     */
    public float getRightEyeX()			{ return rightEyeX; }
    
    
    /**
     * Returns the user's right eye position.
     * This is the Y position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return right eye position in y (mm)
     */
    public float getRightEyeY()			{ return rightEyeY; }
   
    
    /**
     * Returns the user's right eye position.
     * This is the Z position given in space coordinates (mm)  
     * relative to the center of the screen
     * 
     * @return right eye position in z (mm)
     */
    public float getRightEyeZ()			{ return rightEyeZ; }
    
    
    /**
     * Returns the user's right eye (normalized) position (X)
     * 
     * @return right eye position in x (normalized)
     */
    public float getRightEyeNormX()  	{ return rightEyeNormX; }
    
    
    /**
     * Returns the user's right eye (normalized) position (Y)
     * 
     * @return right eye position in y (normalized)
     */
    public float getRightEyeNormY()		{ return rightEyeNormY; }
    
    
    /**
     * Returns the user's right eye (normalized) position (Z)
     * 
     * @return right eye position in z (normalized)
     */
    public float getRightEyeNormZ()		{ return rightEyeNormZ; }
    
    
    /**
     * Starts the thread that listens for gaze data streams
     */
    public void start()
    {
        running = true;
        super.start();
    }
    
    
    /**
     * Thread that listens for gaze data from the TobiiStream.exe
     */
    public void run()
    {   	
        try
        {
        	ZMQ.Context context = ZMQ.context(1);
        	
        	// Create a ZMQ connection using the PUB-SUB pattern
        	// http://zguide.zeromq.org/page:all#Getting-the-Message-Out
        	ZMQ.Socket jeroSocket = context.socket(ZMQ.SUB);
        	
        	// For now the library only supports I/O in the same device
        	jeroSocket.connect("tcp://" + ip_address + ":" + socket);
        	
        	// Subscribe to four filters: 
        	// TobiiStream:		timestamp, x, y
        	// TobiiState: 		Unknown, Present, NotPresent
        	// TobiiLeftEye: 	leftEyePresent, leftEyeX, leftEyeY, leftEyeZ, leftEyeNormX, leftEyeNormY, leftEyeNormZ
        	// TobiiRightEye:	rightEyePresent, rightEyeX, rightEyeY, rightEyeZ, rightEyeNormX, rightEyeNormY, rightEyeNormZ;
        	jeroSocket.subscribe("TobiiStream".getBytes(ZMQ.CHARSET));
        	jeroSocket.subscribe("TobiiState".getBytes(ZMQ.CHARSET));
        	jeroSocket.subscribe("TobiiLeftEye".getBytes(ZMQ.CHARSET));
        	jeroSocket.subscribe("TobiiRightEye".getBytes(ZMQ.CHARSET));
            
            while (running)
            {      
            	//  Use trim to remove the tailing '0' character
            	String gazeData = jeroSocket.recvStr(0).trim();
            	            	
            	StringTokenizer st;
            	st = new StringTokenizer(gazeData, " ");
            	
            	// Holds the ZMQ filter value
            	String streamSource = st.nextToken();
            	
            	// Get data based on the appropriate filter
            	if ("TobiiStream".equals(streamSource))
            	{
            		timestamp = Double.parseDouble(st.nextToken());
	                gazeX = Float.parseFloat(st.nextToken());
	                gazeY = Float.parseFloat(st.nextToken());
            	}
            	else if ("TobiiState".equals(streamSource)) 
        		{
            		gazeState = st.nextToken();
            		
            		if ("Unknown".equals(gazeState)) System.out.println("##project.name##: "
            				+ "Cannot find the Tobii eye-tracker. Check if the eye-tracker is connected, "
            				+ "and the Tobii software is running.");
        		}
            	else if ("TobiiLeftEye".equals(streamSource))
            	{
            		// 0: Left eye not present
            		// 1: Left eye present
            		leftEyePresent = Integer.parseInt(st.nextToken());
            		
            		leftEyeX = Float.parseFloat(st.nextToken());
            		leftEyeY = Float.parseFloat(st.nextToken());
            		leftEyeZ = Float.parseFloat(st.nextToken());
            		
            		leftEyeNormX = Float.parseFloat(st.nextToken());
            		leftEyeNormY = Float.parseFloat(st.nextToken());
            		leftEyeNormZ = Float.parseFloat(st.nextToken());	
            	}
            	else if ("TobiiRightEye".equals(streamSource))
            	{
            		// 0: Right eye not present
            		// 1: Right eye present
            		rightEyePresent = Integer.parseInt(st.nextToken());
            		
            		rightEyeX = Float.parseFloat(st.nextToken());
            		rightEyeY = Float.parseFloat(st.nextToken());
            		rightEyeZ = Float.parseFloat(st.nextToken());
            		
            		rightEyeNormX = Float.parseFloat(st.nextToken());
            		rightEyeNormY = Float.parseFloat(st.nextToken());
            		rightEyeNormZ = Float.parseFloat(st.nextToken());
            	}
            }
            jeroSocket.close();
            context.term();
            return;
        }
        catch (Exception e)
        {
        	System.out.println(e);
        }
    }    
    
    
    /**
     * Ends the thread that listens for GazeDataStreams.
     * Executed automatically when the Processing sketch is closed
     */
    public void terminate()
    {
    	running = false;
    	System.out.println("##project.name##: TobiiStream disconnected.");
    }
}