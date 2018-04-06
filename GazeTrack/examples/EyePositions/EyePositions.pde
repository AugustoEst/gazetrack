/* 
GazeTrack: Eye positions demo
-
In this demo, the user's eyes are represented 
by individual eye icons
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

PShape eye_pic;

void setup() 
{
  fullScreen();
  
  eye_pic = loadShape("eye.svg");
    
  gazeTrack = new GazeTrack(this);
  
  // If the TobiiStream.exe asked you to use a 
  // different socket port (e.g., 5656), use this instead:
  // gazeTrack = new GazeTrack(this, "5656");
}

void draw() 
{
  background(255);
      
  // Draws the LEFT eye if present
  if (gazeTrack.leftEyePresent())
  {
    // Defines the size of the eye icon, depending on how close or far away
    // the user is from the display
    float eye_size = 50 / gazeTrack.getLeftEyeNormZ();
    
    shape(eye_pic, gazeTrack.getLeftEyeX(), gazeTrack.getLeftEyeY(), eye_size, eye_size);
    
    println("Left eye is present:");
    println("X: " + gazeTrack.getLeftEyeXmm() + "mm from the center of the screen");
    println("Y: " + gazeTrack.getLeftEyeYmm() + "mm from the center of the screen");
    println("Z: " + gazeTrack.getLeftEyeZ() + "mm from the display");
    println();
  }
  
  // Draws the RIGHT eye if present
  if (gazeTrack.rightEyePresent())
  {
    // Defines the size of the eye icon, depending on how close 
    // or far away the user is from the display
    float eye_size = 50 / gazeTrack.getRightEyeNormZ();
    
    shape(eye_pic, gazeTrack.getRightEyeX(), gazeTrack.getRightEyeY(), eye_size, eye_size);
    
    println("Right eye is present:");
    println("X: " + gazeTrack.getRightEyeXmm() + "mm from the center of the screen");
    println("Y: " + gazeTrack.getRightEyeYmm() + "mm from the center of the screen");
    println("Z: " + gazeTrack.getRightEyeZ() + "mm from the display");
    println();
  }
}
