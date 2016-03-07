import gazetrack.*;

GazeTrack gt;
color backgroundColor;

void setup()
{
  fullScreen();
  rectMode(CENTER);
  noStroke();
  fill(#4FA25C);
  
  gt = new GazeTrack(this);
  backgroundColor = #FFFFFF;
}

void draw()
{
  background(backgroundColor);
  
  rect(gt.getGazeX(), gt.getGazeY(), 20, 20);
}

void gazeStopped()
{
  println("Where is the user?!");
  backgroundColor = #FFFFFF;
}

void gazeStarted()
{
  println("Here he is!");
  backgroundColor = #ADFFB1;
}

