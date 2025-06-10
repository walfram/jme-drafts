package ships;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import jme3utilities.SimpleControl;

public class HoverControl extends SimpleControl {
  
  private float rotFreqX = 0.1f;
  private float rotFreqZ = 0.5f;
  private float rotAmpX = 0.25f;
  private float rotAmpZ = 0.125f;
  
  private float time = 0f;
  
  @Override
  protected void controlUpdate(float updateInterval) {
    super.controlUpdate(updateInterval);
    
    float rotX = FastMath.sin(time * rotFreqX) * rotAmpX;
    float rotZ = FastMath.cos(time * rotFreqZ) * rotAmpZ;
    
    Quaternion rotation = new Quaternion().fromAngles(rotX, 0, rotZ);
    getSpatial().setLocalRotation(rotation);
    
    time = (time + updateInterval) % (24 * 60 * 60);
  }
}
