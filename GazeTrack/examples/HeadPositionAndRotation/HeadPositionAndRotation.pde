/* 
GazeTrack: Head position and rotation demo
-
In this demo, different emoticons are presented indicating
various properties of the head (position, rotation)

The following actions are available:

1. Turn your head to the left or right (yaw)
2. Turn your head up or down (pitch)
3. Tilt your head to the left or right (roll)

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

// Thresholds that define what we consider a left or right head turn, etc.
// Adjust based on your own experience and calibration
final float LOOK_LEFT_THRESHOLD = 0.3;
final float LOOK_RIGHT_THRESHOLD = -0.2;
final float LOOK_UP_THRESHOLD = 0.3;
final float LOOK_DOWN_THRESHOLD = 0.0;
final float TILT_LEFT_THRESHOLD = 0.07;
final float TILT_RIGHT_THRESHOLD = -0.2;

PShape ahead, look_down, look_up, look_left, look_right;
PImage tilt_left, tilt_right;

void setup() 
{
  fullScreen();
  shapeMode(CENTER);
  imageMode(CENTER);
  
  ahead       = loadShape("ahead.svg");
  look_down   = loadShape("look_down.svg");
  look_up     = loadShape("look_up.svg");
  look_left   = loadShape("look_left.svg");
  look_right  = loadShape("look_right.svg");
  tilt_left   = loadImage("tilt_left.png");
  tilt_right  = loadImage("tilt_right.png");
    
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
    // Calculate the head position
    float headX = width/2 + gazeTrack.getHeadPositionX();
    float headY = height/2 - gazeTrack.getHeadPositionY();
    float headZ = map(gazeTrack.getHeadPositionZ(), 0, 1000, 500, 50);  // Use the z-axis to vary the emoticon size
    
    // Calculate the latest rotations
    float currentYaw = gazeTrack.getHeadRotationY();
    float currentPitch = gazeTrack.getHeadRotationX();
    float currentRoll = gazeTrack.getHeadRotationZ();
    
    if (currentYaw > LOOK_LEFT_THRESHOLD) shape(look_left, headX, headY, headZ, headZ);         // Draw 'left' emoticon
    else if (currentYaw < LOOK_RIGHT_THRESHOLD) shape(look_right, headX, headY, headZ, headZ);  // Draw 'right' emoticon  
    else if (currentPitch > LOOK_UP_THRESHOLD) shape(look_up, headX, headY, headZ, headZ);      // Draw 'up' emoticon 
    else if (currentPitch < LOOK_DOWN_THRESHOLD) shape(look_down, headX, headY, headZ, headZ);  // Draw 'down' emoticon 
    else if (currentRoll > TILT_LEFT_THRESHOLD) image(tilt_left, headX, headY, headZ, headZ);   // Draw 'tilt L' emoticon 
    else if (currentRoll < TILT_RIGHT_THRESHOLD) image(tilt_right, headX, headY, headZ, headZ); // Draw 'tilt R' emoticon 
    else shape(ahead, headX, headY, headZ, headZ);
  }
}
