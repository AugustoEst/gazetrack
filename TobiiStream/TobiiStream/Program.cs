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
            // For now the library only supports I/O in the same device
            var ip_address = "tcp://127.0.0.1:5556";

            // Define ZMQ properties (http://zeromq.org)
            // We use the PUB-SUB pattern
            // http://zguide.zeromq.org/page:all#Getting-the-Message-Out
            var publisher = new ZSocket(ZSocketType.PUB);
            publisher.Bind(ip_address);

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
                }
            });

            // Quit on key press
            Console.ReadKey();
            host.DisableConnection();
        }
    }
}