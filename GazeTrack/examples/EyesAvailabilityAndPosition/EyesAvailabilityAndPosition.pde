/* 
GazeTrack: Eye and Head positions demo
-
In this demo, different emoticons are presented indicating
various properties of the eyes (availability, position)

Try to blink with each eye, or cover the eye-tracker to see
the 'no eyes' emoticon

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

PShape both_eyes, no_eyes;
PImage no_left, no_right;

float latestEyeY, latestEyeZ;

void setup() 
{
  fullScreen();
  shapeMode(CENTER);
  imageMode(CENTER);

  both_eyes   = loadShape("both_eyes.svg");
  no_eyes     = loadShape("no_eyes.svg");
  no_left     = loadImage("no_left.png");
  no_right    = loadImage("no_right.png");
    
  gazeTrack = new GazeTrack(this);
  
  // If the TobiiStream.exe asked you to use a 
  // different socket port (e.g., 5656), use this instead:
  // gazeTrack = new GazeTrack(this, "5656");
}

void draw() 
{
  background(255); 
 
  if (gazeTrack.gazePresent())
  {
    if (!gazeTrack.leftEyePresent())
    {
      // The left eye is not available, use the information from the right eye
      updateRightEyePosition();
      printRightEyeInfo();
            
      // Draw 'no left' emoticon
      image(no_left, width/2, latestEyeY, latestEyeZ, latestEyeZ);
    }
    else if (!gazeTrack.rightEyePresent())
    {
      // The left eye is not available, use the information from the right eye
      updateLeftEyePosition();
      printLeftEyeInfo();
      
      // Draw 'no left' emoticon
      image(no_right, width/2, latestEyeY, latestEyeZ, latestEyeZ);
    }
    else
    {
      // You can use either the left or right eye information to draw the 'both eyes' emoticon
      updateRightEyePosition();
      
      printLeftEyeInfo();
      printRightEyeInfo();
      
      // Draw 'both eyes' emoticon
      shape(both_eyes, width/2, latestEyeY, latestEyeZ, latestEyeZ);
    }
  }
  else shape(no_eyes, width/2, latestEyeY, latestEyeZ, latestEyeZ);      // Draw 'no eyes' emoticon
}

void printLeftEyeInfo()
{
  println("Left eye is present:");
  println("X: " + gazeTrack.getLeftEyeXmm() + "mm from the center of the screen");
  println("Y: " + gazeTrack.getLeftEyeYmm() + "mm from the center of the screen");
  println("Z: " + gazeTrack.getLeftEyeZmm() + "mm from the screen");
  println();
}

void printRightEyeInfo()
{
  println("Right eye is present:");
  println("X: " + gazeTrack.getRightEyeXmm() + "mm from the center of the screen");
  println("Y: " + gazeTrack.getRightEyeYmm() + "mm from the center of the screen");
  println("Z: " + gazeTrack.getRightEyeZmm() + "mm from the screen");
  println();
}

void updateRightEyePosition()
{
  if (gazeTrack.getRightEyeY() > 0) latestEyeY = gazeTrack.getRightEyeY();  
  if (gazeTrack.getRightEyeZ() > 0) latestEyeZ = map(gazeTrack.getRightEyeZ(), 0, 1, 500, 50);  // Use the z-axis to vary the emoticon size
}

void updateLeftEyePosition()
{
  if (gazeTrack.getLeftEyeY() > 0) latestEyeY = gazeTrack.getLeftEyeY() + 10;
  if (gazeTrack.getLeftEyeZ() > 0) latestEyeZ = map(gazeTrack.getLeftEyeZ(), 0, 1, 500, 50);   // Use the z-axis to vary the emoticon size
}
