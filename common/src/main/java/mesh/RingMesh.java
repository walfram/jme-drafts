package mesh;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class RingMesh extends FlatShadedMesh {
  private static final Logger logger = getLogger(RingMesh.class);
  
  public RingMesh(int radialSamples, float height, float innerRadius, float outerRadius) {
    super(trianglesOf(radialSamples, height, innerRadius, outerRadius));
  }
  
  private static List<Triangle> trianglesOf(int radialSamples, float height, float innerRadius, float outerRadius) {
    float theta = FastMath.TWO_PI / radialSamples;
    
    Vector3f top = new Vector3f(innerRadius, 0, 0);
    Vector3f bottom = new Vector3f(innerRadius, 0, 0);
    Vector3f far = new Vector3f(outerRadius, 0, 0);
    
    List<Vector3f> topPoints = new ArrayList<>(radialSamples);
    List<Vector3f> bottomPoints = new ArrayList<>(radialSamples);
    List<Vector3f> farPoints = new ArrayList<>(radialSamples);
    
    Quaternion r = new Quaternion().fromAngleNormalAxis(theta, Vector3f.UNIT_Y);
    
    for (int i = 0; i < radialSamples; i++) {
      topPoints.add(r.multLocal(top).add(0, 0.5f * height, 0));
      bottomPoints.add(r.multLocal(bottom).add(0, -0.5f * height, 0));
      farPoints.add(r.multLocal(far).add(0, 0, 0));
    }
    
    logger.debug("topPoints: {}", topPoints);
    logger.debug("bottomPoints: {}", bottomPoints);
    logger.debug("farPoints: {}", farPoints);
    
    List<Face> faces = new ArrayList<>(3 * radialSamples);
    for (int i = 0; i < radialSamples; i++) {
      Face inner = new QuadFace(bottomPoints.get(i), topPoints.get(i), topPoints.get((i + 1) % radialSamples), bottomPoints.get((i + 1) % radialSamples));
      faces.add(inner);
      
      Face up = new QuadFace(topPoints.get(i), farPoints.get(i), farPoints.get((i + 1) % radialSamples), topPoints.get((i + 1) % radialSamples));
      faces.add(up);
      
      Face down = new QuadFace(bottomPoints.get(i), bottomPoints.get((i + 1) % radialSamples), farPoints.get((i + 1) % radialSamples), farPoints.get(i));
      faces.add(down);
    }
    
    return faces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .toList();
  }
  
}
