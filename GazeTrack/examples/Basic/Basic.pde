/* 
GazeTrack: Basic demo
-
In this demo, a cursor representing the
user's gaze is drawn as a white circle.
-

Before you run this demo, make sure the
Tobii eye-tracker (EyeX, 4C) is connected
to the computer, and that the Tobii software 
is running and calibrated to your eyes.

Finally, make sure the 'TobiiStream.exe' is 
running and displaying gaze data. You can
download this application from:
http://hci.soc.napier.ac.uk/GazeTrack/TobiiStream.zip

by Augusto Esteves
http://hci.soc.napier.ac.uk
https://github.com/AugustoEst/gazetrack
*/

import gazetrack.*;

GazeTrack gazeTrack;

void setup() 
{
  fullScreen();
  
  // Gaze cursor param.
  noFill();
  stroke(50, 100);
  strokeWeight(4);
  
  gazeTrack = new GazeTrack(this);
}

void draw() 
{
  background(255);
  
  if (gazeTrack.gazePresent())
  {
    ellipse(gazeTrack.getGazeX(), gazeTrack.getGazeY(), 80, 80);
    
    // Print the tracker's timestamp for the gaze cursor above
    println("Latest gaze data at: " + gazeTrack.getTimestamp());
  }
}