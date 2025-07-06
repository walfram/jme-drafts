package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import misc.DebugPointMesh;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class CigarShipTest extends SimpleApplication {
  
  private static final Logger logger = getLogger(CigarShipTest.class);
  
  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    CigarShipTest app = new CigarShipTest();
    app.start();
  }
  
  public CigarShipTest() {
    super(
        new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
        new ConstantVerifierState(),
        
        new DebugGridState(cellExtent, false),
        new DebugAxesState(),
        new ChaseCameraState(),
        new LemurState()
    );
  }
  
  @Override
  public void simpleInitApp() {
    Material material = new ShowNormalsMaterial(assetManager);
    
    float zExtent = cellExtent * 16;
    float xExtent = cellExtent * 8f;
    float yExtent = cellExtent * 4f;
//    int axisSamples = 16;
    
    float centerX = 0f; // X-coordinate of the ellipse center
    float centerY = 0f; // Y-coordinate of the ellipse center
    float semiMajorAxisA = 5f; // Half-width along X-axis (or major axis if aligned with X)
    float semiMinorAxisB = 3f; // Half-height along Y-axis (or minor axis if aligned with Y)
    int numberOfPoints = 14; // The desired number of points on the ellipse
    
    int slices = 10;
    
    List<Vector3f> points = new ArrayList<>();
    
    Ellipse xz = new EllipseXZ(zExtent, xExtent, numberOfPoints);
//    points.addAll(xz.points());
    
    Ellipse yz = new EllipseYZ(zExtent, yExtent, numberOfPoints);
//    points.addAll(yz.points());
    
    for (int i = 0; i < slices; i++) {
      float major = xz.points().get(i).x;
      float minor = yz.points().get(i).y;
      
      float z = xz.points().get(i).z;
      
      Ellipse xy = new EllipseXY(major, minor, numberOfPoints);
      List<Vector3f> slice = xy.points();
      slice.forEach(p -> p.z = z);
      points.addAll(slice);
    }
    
    
    Geometry debug = new Geometry("debug", new DebugPointMesh(points));
    debug.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debug.getMaterial().setFloat("PointSize", 4f);
    rootNode.attachChild(debug);
  }
  
  private List<Vector3f> generateEllipsePoints(float centerX, float centerY,
                                       float semiMajorAxisA, float semiMinorAxisB,
                                       int numPoints) {
    List<Vector3f> points = new ArrayList<>(numPoints);
    
    for (int i = 0; i < numPoints; i++) {
      // Calculate the angle for the current point, distributing evenly around the ellipse
      // The angle ranges from 0 to 2*PI radians (a full circle)
      float theta = (float) (2 * Math.PI * i / numPoints);
      
      // Parametric equations for an ellipse:
      // x = h + a * cos(theta)
      // y = k + b * sin(theta)
      // where (h,k) is the center, 'a' is semi-major axis, 'b' is semi-minor axis
      float x = centerX + semiMajorAxisA * (float) Math.cos(theta);
      float y = centerY + semiMinorAxisB * (float) Math.sin(theta);
      
      // In JMonkeyEngine, points are Vector3f. Since we are in the X-Y plane, Z is 0.
      points.add(new Vector3f(x, y, 0f));
    }
    
    return points;
  }
}
