package gazetrack;

import java.util.StringTokenizer;
import org.zeromq.ZMQ;

public class TobiiStream extends Thread 
{
	// Gaze variables
	private float gazeX, gazeY;
	private double timestamp;
	private String gazeState;
	
	// Thread variables
    private boolean running;

    
    /**
     * a Constructor, usually called at the beginning of execution so
     * it starts listening for the Tobii gaze data stream. 
     * 
     * This requires 'TobiiStream.exe' to be running and displaying 
     * gaze data. You can download this application from:
	 * https://hci.soc.napier.ac.uk/GazeTrack/TobiiStream.zip
     */
    public TobiiStream()
    {
        timestamp = -1;
        gazeX = -1; 
        gazeY = -1;
        running = false;
        gazeState = "Present";
        
        start();
    }
    
    
    /**
     * Returns the user's gaze position (X)
     * 
     * @return gaze position in x
     */
    public float getGazeX()
    {
    	return gazeX;
    }
    
    
    /**
     * Returns the user's gaze position (Y)
     * 
     * @return gaze position in y
     */
    public float getGazeY()
    {
    	return gazeY;
    }
    
    
    /**
     * Returns the user's gaze position (Y)
     * 
     * @return timestamp of the last gaze event
     */
    public double getTimestamp()
    {
    	return timestamp;
    }
    
    
    /**
     * Returns true if the eye-tracker is capturing
     * the user's gaze
     * 
     * @return true if the user's gaze is present
     */
    public boolean gazePresent()
    {
    	return gazeState.equals("Present");
    }
    
    
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
        	jeroSocket.connect("tcp://127.0.0.1:5556");
        	
        	// Subscribe to two filters: 
        	// TobiiStream: timestamp, x, y
        	// TobiiState: Unknown, Present, NotPresent
        	jeroSocket.subscribe("TobiiStream".getBytes(ZMQ.CHARSET));
        	jeroSocket.subscribe("TobiiState".getBytes(ZMQ.CHARSET));
            
            while (running)
            {      
            	//  Use trim to remove the tailing '0' character
            	String gazeData = jeroSocket.recvStr(0).trim();
            	            	
            	StringTokenizer st;
            	st = new StringTokenizer(gazeData, " ");
            	
            	// Holds the ZMQ filter value
            	String streamSource = st.nextToken();
            	
            	// Get data based on the appropriate filter
            	if (streamSource.equals("TobiiStream"))
            	{
            		timestamp = Double.parseDouble(st.nextToken());
	                gazeX = Float.parseFloat(st.nextToken());
	                gazeY = Float.parseFloat(st.nextToken());
            	}
            	else if (streamSource.equals("TobiiState")) 
        		{
            		gazeState = st.nextToken();
            		
            		if (gazeState.equals("Unknown")) System.out.println("##project.name##: "
            				+ "Cannot find the Tobii eye-tracker. Check if the eye-tracker is connected, "
            				+ "and the Tobii software is running.");
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