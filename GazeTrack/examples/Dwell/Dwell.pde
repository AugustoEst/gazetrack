/* 
GazeTrack: Dwell demo
-
In this demo, the user can turn a light switch
on or off by looking at it for a short amount
of time (DWELL_TIME)
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

If you enjoyed this demo, 
and would like to make a contribution to the project:
ETH: 0xED6A9bA7d99D8cb55037d9c68C60998eA17eCfC7
BTC: 3QSrrQdET35F2CdaZKSK1PnCrRc7np9mdQ
XLM: GD263F3X5D5ULX7TBXF6ULPGKEICAHJEO22ZOJABNVJSCYPEJW6JBU7G
*/

import gazetrack.*;

GazeTrack gazeTrack;
Target light_switch;

// Cursor parameters
int DWELL_TIME = 750;        // how long does it take to select a target
int cursor_color = 255;      // cursor color (for dwell progression feedback)     

void setup() 
{
  fullScreen();
  
  // Gaze cursor param.
  strokeWeight(3);
  stroke(50, 100);
  
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
    float gaze_x = gazeTrack.getGazeX();
    float gaze_y = gazeTrack.getGazeY();
    
    // Draw the gaze cursor
    fill(cursor_color, 150);
    ellipse(gaze_x, gaze_y, 80, 80);
  
    // Check if the gaze cursor is over the light switch
    if (light_switch.gazeOver(gaze_x, gaze_y))
    {
      // If the gaze cursor just moved over the
      // light swit, record when this happened
      if (!light_switch.dwellHasStarted())
        light_switch.dwellStart(gazeTrack.getTimestamp());
      
      // If the gaze cursor is already over the
      // the target, update the cursor color and check
      // if enough dwell time has passed (DWELL_TIME)
      else
      {        
        // Dwell loading filler (from white to black)
        if (cursor_color > 0 && !light_switch.wasSelected()) cursor_color -= 4;      
        
        if (gazeTrack.getTimestamp() > light_switch.dwellStartTime() + DWELL_TIME && !light_switch.wasSelected())
        {
          cursor_color = 255;
          light_switch.select();
        }
      }
    }
    // If the user has stopped looking at the target,
    // refresh the gaze cursor
    else if (light_switch.dwellHasStarted())
    {
      cursor_color = 255;
      light_switch.dwellStop();
    }
  }
}