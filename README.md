GazeTrack
=========

This library enables the use of different eye-trackers on the **Processing** environment. 

It works out-of-the-box with the popular **Tobii EyeX** tracker one the **Windows** platform.

http://forum.processing.org/two/discussion/7606/gazetrack-a-processing-library-for-eye-tracking


Instructions
------------

Extract **GazeTrack** to your _Processing\libraries_ folder.

Make sure your Tobii EyeX is installed, pluged in and the Eye Tracking feature is enabled.

Run *GazeTrackEyeXGazeStream\GazeTrackEyeXGazeStream.exe*

Import the library to your Processing sketch (import gazetrack.*;)


Using other eye-tracking hardware
---------------------------------

The library uses a **UDP** connection on port **11000**.

It expects a string with the following example messages:

**"325.268;769.212"** / GazeX and GazeY
  
**"NotPresent"** / Invokes *gazeStopped()* (the user's gaze stopped being tracked)    
  
**"Present"** / Invokes *gazeStarted()* (the user's gaze started being tracked)
