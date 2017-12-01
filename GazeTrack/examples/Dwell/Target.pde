public class Target 
{  
  int x, y;                                             // target location
  int T_SIZE = 100;                                     // target size
  boolean selected_flag = false;                        // has the target been selected since the dwell started?
  boolean target_state = false;                         // target state (on/true or off/false)
  double dwell_started;                                 // when did the user start looking at the target?
  boolean dwell_flag = false;                           // is the user looking at the target?
  PShape switch_pic = loadShape("off.svg");             // target shape (svg image)
  
  
  public Target(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  
  public void draw()
  {
    shape(switch_pic, x, y, T_SIZE, T_SIZE);
  }
  
  
  // Is the gaze cursor over the target?
  public boolean gazeOver(float gx, float gy)
  { 
    return gy < y + T_SIZE && gy > y && gx < x + T_SIZE && gx > x;  
  }
  
  
  // Record when the user started gazing at the target
  // and update 'dwell_flag'
  public void dwellStart(double timestamp)
  {
    dwell_started = timestamp;
    dwell_flag = true;
  }
  
  
  // Has the target been selected since the 
  // user starting looking at it?
  public boolean wasSelected()
  {
    return selected_flag;
  }
  
  
  // The target was selected: change the target state (on/off)
  // and update its shape (svg image)
  public void select()
  {
    target_state = !target_state;
    selected_flag = true;
    
    if (target_state) switch_pic = loadShape("on.svg");
    else switch_pic = loadShape("off.svg");
  }
  
  
  // The user stopped looking at the target:
  // update 'selected_flag' and 'dwell_flag'
  public void dwellStop()
  {
    selected_flag = false;  
    dwell_flag = false;
  }
   
  // When did the user start looking at the target?
  public double dwellStartTime()
  {
    return dwell_started;
  }
  
  
  // Is the user looking at the target?
  public boolean dwellHasStarted()
  {
    return dwell_flag;
  }
  
  
  // What is the target state (on/off)? 
  public boolean isOn()
  {
    return target_state;
  }
}