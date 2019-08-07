public class Target 
{  
  int x, y;                                             // target location
  int T_SIZE = 100;                                     // target size
  boolean target_state = false;                         // target state (on/true or off/false)
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
  
  // The target was selected: change the target state (on/off)
  // and update its shape (svg image)
  public void select()
  {
    target_state = !target_state;
    
    if (target_state) switch_pic = loadShape("on.svg");
    else switch_pic = loadShape("off.svg");
  }
  
  // What is the target state (on/off)? 
  public boolean isOn()
  {
    return target_state;
  }
}