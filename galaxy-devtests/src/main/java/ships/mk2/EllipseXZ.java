package ships.mk2;

import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class EllipseXZ implements Ellipse {
  
  private final float majorAxis;
  private final float minorAxis;
  private final int numberOfPoints;
  
  public EllipseXZ(float majorAxis, float minorAxis, int numberOfPoints) {
    this.majorAxis = majorAxis;
    this.minorAxis = minorAxis;
    this.numberOfPoints = numberOfPoints;
  }
  
  @Override
  public List<Vector3f> points() {
    List<Vector3f> points = new ArrayList<>(numberOfPoints);
    
    for (int i = 0; i < numberOfPoints; i++) {
      float theta = (float) (2 * Math.PI * i / numberOfPoints);
      
      // Parametric equations for an ellipse:
      // x = h + a * cos(theta)
      // y = k + b * sin(theta)
      // where (h,k) is the center, 'a' is semi-major axis, 'b' is semi-minor axis
      float z = majorAxis * (float) Math.cos(theta);
      float x = minorAxis * (float) Math.sin(theta);
      
      points.add(new Vector3f(x, 0, z));
    }
    
    return points;
  }
}
