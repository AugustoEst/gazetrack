GazeTrack
=========

This library enables the use of different eye-trackers on the Processing environment. 

It works out-of-the-box with the popular Tobii EyeX tracker one the Windows platform.


Instructions
------------

Extract the library to your Processing\libraries folder.

Make sure your Tobii EyeX is installed, pluged in and the Eye Tracking feature is enabled.

Run *Processing\librariesGazeTrack\examples\GazeTrackEyeXGazeStream\GazeTrackEyeXGazeStream.exe*

Import the library to your Processing sketch (import gazetrack.*;)


Using other eye-tracking hardware
---------------------------------

The library uses a **UDP** connection on port **11000**.

It expects a string with the following _example messages_:

**"325.2687;769.212"** / GazeX and GazeY
  
**"NotPresent"** / Invokes gazeStopped() (the user's gaze stopped being tracked)    
  
**"Present"** / Invokes gazeStarted() (the user's gaze started being tracked)          
