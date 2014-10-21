namespace GazeTrackEyeXGazeStream
{
    using EyeXFramework;
    using System;
    using Tobii.EyeX.Framework;
    using System.Net;
    using System.Net.Sockets;
    using System.Text;

    public static class Program
    {
        static UdpClient eyeSocket = new UdpClient(11000);
        // static byte[] gazeData = new byte[100];

        public static void Main(string[] args)
        {
            using (var eyeXHost = new EyeXHost())
            {
                try
                {
                    eyeSocket.Connect("localhost", 11000);

                }
                catch (Exception e)
                {
                    Console.WriteLine("Pursuit socket error: " + e);
                }

                eyeXHost.Start();

                eyeXHost.UserPresenceChanged += eyeXEventHandler;
                eyeXHost.EyeTrackingDeviceStatusChanged += eyeXEventHandler;

                // Create a gazetrack data stream: lightly filtered gaze point data.
                using (var lightlyFilteredGazeDataStream = eyeXHost.CreateGazePointDataStream(GazePointDataMode.LightlyFiltered))
                {
                    // Write the data to the UDP port and console.
                    lightlyFilteredGazeDataStream.Next += (s, e) => newDataLine(e);

                    // Let it run until a key is pressed.
                    Console.WriteLine("GazeTrack: Listening for gaze data from the Tobii EyeX, press <Enter> to exit...");
                    Console.In.Read();
                }
            }
        }


        private static void eyeXEventHandler(object sender, EventArgs e)
        {
            sendDataToEyeSocket(e.ToString());
        }


        private static void newDataLine(GazePointEventArgs e)
        {   
            String dataLine = e.X + ";" + e.Y;
            sendDataToEyeSocket(dataLine);
            
            Console.WriteLine("{0:0.00};{1:0.00}", e.X, e.Y);
        }


        private static void sendDataToEyeSocket(String dataLine)
        {
            byte[] gazeData = Encoding.UTF8.GetBytes(dataLine);

            try
            { 
                eyeSocket.Send(gazeData, gazeData.Length);
            }
            catch (Exception e)
            {
                Console.WriteLine("Pursuit socket error: " + e);
            }
        }
    }
}