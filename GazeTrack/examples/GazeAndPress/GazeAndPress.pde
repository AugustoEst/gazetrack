/* 
GazeTrack: Gaze and Press demo
-
In this demo, the user can turn a light switch
on or off by looking at it and pressing the
space bar on their keyboard
-

Before you run this demo, make sure the
Tobii eye-tracker (EyeX, 4C) is connected
to the computer, and that the Tobii software 
is running and calibrated to your eyes.

Finally, make sure the 'TobiiStream.exe' is 
running and displaying gaze data. You can
download this application from:
http://web.tecnico.ulisboa.pt/augusto.esteves/GazeTrack/TobiiStream.zip

by Augusto Esteves
http://web.tecnico.ulisboa.pt/augusto.esteves/
https://github.com/AugustoEst/gazetrack
*/

import gazetrack.*;

GazeTrack gazeTrack;
Target light_switch;

float latest_gaze_x, latest_gaze_y;

void setup() 
{
  fullScreen();
  
  // Gaze cursor param.
  strokeWeight(4);
  fill(255, 100);
    
  // The light switch object
  light_switch = new Target(width/2, height/2);
  
  gazeTrack = new GazeTrack(this);
  
  // If the TobiiStream.exe asked you to use a 
  // different socket port (e.g., 5656), use this instead:
  // gazeTrack = new GazeTrack(this, "5656");
}

void draw() 
{
  // Adjust the background color based on the light switch
  // state (on or off)
  if (light_switch.isOn()) background(255);
  else background(100);
  
  // Draw the light switch
  light_switch.draw();
  
  if (gazeTrack.gazePresent())
  {
    latest_gaze_x = gazeTrack.getGazeX();
    latest_gaze_y = gazeTrack.getGazeY();
    
    // Draw the gaze cursor with an outline if over the light switch
    if (light_switch.gazeOver(latest_gaze_x, latest_gaze_y)) stroke(0, 255, 0, 100);
    else noStroke();
    
    ellipse(latest_gaze_x, latest_gaze_y, 80, 80);
  }
}


void keyReleased() 
{
  if (key == CODED && keyCode == CONTROL) 
  {
    // Check if the gaze cursor is over the light switch
    // when the user presses 'CTRL' 
    if (light_switch.gazeOver(latest_gaze_x, latest_gaze_y))
      light_switch.select();
  }
}
