package gazetrack;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;


public class GazeStream extends Thread
{
	
    private float gazeX, gazeY;
    private boolean running;
    private GazeTrack myParent;
    
    
    /**
     * a Constructor, usually called at the beginning of execution so
     * it starts listening for gaze data streams.
     * 
     * @param myParent
     */
    public GazeStream(GazeTrack myParent)
    {
        gazeX = -1; 
        gazeY = -1;
        running = false;
        this.myParent = myParent;
        
        start();
    }
    
    
    /**
     * Returns the user's gaze position (X)
     * 
     * @return
     */
    public float getGazeX()
    {
    	return gazeX;
    }
    
    
    /**
     * Returns the user's gaze position (Y)
     * 
     * @return
     */
    public float getGazeY()
    {
    	return gazeY;
    }
    
    
    /**
     * Starts the thread that listens for GazeDataStreams
     */
    public void start()
    {
        running = true;
        super.start();
    }
    
    
    /**
     * Thread that listens for GazeDataStreams.
     * GazeDataStreams include the user's gaze information (X, Y)
     * and messages from the tracker hardware (e.g., device disconnected, user present, user not present)
     */
    public void run()
    {
        try
        {
            DatagramSocket dsocket = new DatagramSocket(11000);
            byte[] buffer = new byte[100];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            
            while (running)
            {
                dsocket.receive(packet);
                String msg = new String(buffer);
                
                if (!hasLetters(msg) && msg.contains(";"))
                {
                	StringTokenizer st;
                	st = new StringTokenizer(msg,";");
                	
                	gazeX = Float.parseFloat(st.nextToken());
                    gazeY = Float.parseFloat(st.nextToken());
                }
                else if (msg.contains("NotPresent"))
                {
                	myParent.gazeStopped();
                }
                else if (msg.contains("Present"))
                {
                	myParent.gazeStarted();
                }
                
                // Reset the length of the packet before reusing it.
                packet.setLength(buffer.length);	
            }
            
            dsocket.close();
            return;
        }
        catch (Exception e)
        {
        	System.out.println(e);
        }
    }
    
    
    /**
     * Checks if a String contains any letters.
     * 
     * Used (so far) to test if a GazeDataStream contains hardware
     * messages (e.g., device disconnected, user present)
     * 
     * @param testString
     * @return
     */
    private boolean hasLetters(String testString)
    {
    	for (int i = 0; i < testString.length(); i++)
    	{
    		if (Character.isLetter(testString.charAt(i))) return true;
    	}
    	return false;
    }
    
    
    /**
     * Ends the thread that listens for GazeDataStreams.
     * Executed automatically when the Processing sketch is closed.
     */
    public void terminate()
    {
    	running = false;
    	System.out.println("GazeTrack disconnected.");
    }
}