/**
 * GazeTrack: A Processing library for eye-tracking
 * This library enables the use of different eye-trackers on the Processing environment.
 * https://github.com/AugustoEst/gazetrack
 *
 * Copyright (c) 2014 Augusto Esteves http://www.mysecondplace.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      Augusto Esteves http://www.mysecondplace.org
 * @modified    10/14/2014
 * @version     0.0.1 (1)
 */

package gazetrack;

import processing.core.*;
import java.lang.reflect.Method;


public class GazeTrack
{
	
	// myParent is a reference to the parent sketch
	PApplet myParent;
	private GazeStream dataStream;
	private Method gazeStopped, gazeStarted;
	
	
	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 * 
	 * @example GazeTrackProcessing
	 * @param myParent
	 */
	public GazeTrack(PApplet myParent)
	{
		this.myParent = myParent;
		myParent.registerMethod("dispose", this);
		initGazeTrackMethods();

		welcome();
		
		dataStream = new gazetrack.GazeStream(this);
	}
	
	
	/**
	 * Initializes two invoke methods in GazeTrack:
	 * gazeStopped and gazeStarted
	 */
	private void initGazeTrackMethods()
	{
		try 
		{
			gazeStopped = myParent.getClass().getMethod("gazeStopped");
		}
		catch (Exception e) 
		{ 
			System.out.println("GazeTrack: missing or wrong 'gazeStopped()' method");
			gazeStopped = null;
		}
		
		try 
		{
			gazeStarted = myParent.getClass().getMethod("gazeStarted");
		}
		catch (Exception e) 
		{ 
			System.out.println("GazeTrack: missing or wrong 'gazeStarted()' method");
			gazeStarted = null;
		}
	}

	
	/**
	 * Invoke method: gazeStopped
	 * Invoked when the user's gaze stops being tracked
	 */
	public void gazeStopped()
	{
		if (gazeStopped != null)
		{
			try
			{
				gazeStopped.invoke(myParent);
			}
			catch (Exception e) {}
		}
	}
	
	
	/**
	 * Invoke method: gazeStarted
	 * Invoked when the user's gaze starts being tracked
	 */
	public void gazeStarted()
	{
		if (gazeStarted != null)
		{
			try
			{
				gazeStarted.invoke(myParent);
			}
			catch (Exception e) {}
		}
	}

		
	/**
	 * Welcome message to be shown at the beginning of the execution of the library
	 */
	private void welcome()
	{
		System.out.println("GazeTrack: A Processing library for eye-tracking (0.0.1) by Augusto Esteves http://www.mysecondplace.org");
	}
	
	
	/**
	 * Returns the user's gaze position (X)
	 * based on the sketch's width
	 * 
	 * @return
	 */
	public float getGazeX()
	{
		return dataStream.getGazeX();
	}
	
	
	/**
	 * Returns the user's gaze position (Y)
	 * based on the sketch's height
	 * 
	 * @return
	 */
	public float getGazeY()
	{
		return dataStream.getGazeY();
	}
	
	
	
	/**
	 * Anything in here will be called automatically when the parent
	 * sketch shuts down. For instance, this might shut down a thread
	 * used by this library
	 */
	public void dispose()
	{
		dataStream.terminate();
	}
}

