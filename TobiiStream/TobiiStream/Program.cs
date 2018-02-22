using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tobii.Interaction;
using Tobii.Interaction.Framework;
using ZeroMQ;

namespace TobiiStream
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("TobiiStream v2.0.2\n");

            // For now the library only supports I/O in the same device
            var ip_address = "tcp://127.0.0.1";
            var socket = "5556";
            var connected = false;

            // Define ZMQ properties (http://zeromq.org)
            // We use the PUB-SUB pattern
            // http://zguide.zeromq.org/page:all#Getting-the-Message-Out
            var publisher = new ZSocket(ZSocketType.PUB);

            // Try to establish a connection using 'ip_address' and 'socket'         
            while (!connected)
            { 
                try
                {
                    publisher.Bind(string.Format("{0}:{1}", ip_address, socket));
                    connected = true;
                    Console.WriteLine("SUCCESS: The connection to the GazeTrack Processing library has been correctly set up!\n");
                }
                catch (ZeroMQ.ZException zmq_ex)
                {
                    Console.WriteLine("ERROR: The connection to the GazeTrack Processing library could not be set up!");
                    Console.WriteLine("The socket port used by TobiiStream.exe to communicate with Processing is already in use by another application.\n");
                    Console.Write("Type in a new port (e.g., 5656) and press 'Enter': ");

                    socket = Console.ReadLine();
                    Console.Write("\n");
                }
            }

            // Make sure that the Tobii eye-tracker is powered,
            // and the tracking software is 'on'
            var host = new Host();

            // Prepare to be notified if the user state changes (present, not present)
            var userPresenceStateObserver = host.States.CreateUserPresenceObserver();

            // Create a gaze data stream (timestamp, x, y)
            var gazePointDataStream = host.Streams.CreateGazePointDataStream();

            // Print gaze data to the console, and publish it with ZMQ
            // ZMQ subscribe filter: "TobiiStream"
            gazePointDataStream.GazePoint((x, y, ts) => {
                var gazeData = string.Format("{0} {1} {2} {3}", "TobiiStream", ts, x, y);
                Console.WriteLine(gazeData);
                publisher.Send(new ZFrame(gazeData));
            });

            // If the user state changes, print this to the console
            // and publish it with ZMQ
            // ZMQ subscribe filter: "TobiiState"
            userPresenceStateObserver.WhenChanged(userPresenceState =>
            {
                if (userPresenceState.IsValid)
                {
                    var stateData = string.Format("{0} {1}", "TobiiState", userPresenceState.Value);
                    Console.WriteLine(stateData);
                    publisher.Send(new ZFrame(stateData));

                    if (userPresenceState.Value.ToString().Equals("Unknown"))
                        Console.WriteLine("Make sure the Tobii eye-tracker is connected to the computer, and that the Tobii software is running!\n");
                }
            });

            // Quit on key press
            Console.ReadKey();
            host.DisableConnection();
        }
    }
}