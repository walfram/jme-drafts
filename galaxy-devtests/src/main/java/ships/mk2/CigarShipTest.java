package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
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
    
    List<List<Vector3f>> points = new ArrayList<>(slices);
    
    Ellipse xz = new EllipseXZ(zExtent, xExtent, numberOfPoints + 2);
//    points.addAll(xz.points());
    logger.debug("xz points = {}", xz.points());

    Geometry debugXz = new Geometry("debug-xz", new DebugPointMesh(xz.points()));
    debugXz.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debugXz.getMaterial().setFloat("PointSize", 8f);
    debugXz.getMaterial().setColor("Color", ColorRGBA.Red);
    rootNode.attachChild(debugXz);

    Ellipse yz = new EllipseYZ(zExtent, yExtent, numberOfPoints + 2);
//    points.addAll(yz.points());
    logger.debug("yz points = {}", yz.points());

    Geometry debugYz = new Geometry("debug-yz", new DebugPointMesh(yz.points()));
    debugYz.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debugYz.getMaterial().setFloat("PointSize", 8f);
    debugYz.getMaterial().setColor("Color", ColorRGBA.Green);
//    rootNode.attachChild(debugYz);


    for (int i = 0; i < slices; i++) {
      float major = xz.points().get(i).x;
      float minor = yz.points().get(i).y;
      
      float z = xz.points().get(i).z;
      
      Ellipse xy = new EllipseXY(major, minor, numberOfPoints);
      List<Vector3f> slice = xy.points();
      logger.debug("slice[{}].size() = {}", i, slice.size());
      logger.debug("slice = {}", slice);

      slice.forEach(p -> p.z = z);
      points.add(slice);
    }

    logger.debug("points.size() = {}", points.size());

    logger.debug("points[0].size() = {}", points.get(0));
    logger.debug("points[last].size() = {}", points.get(points.size() - 1));
    
    Geometry debug = new Geometry("debug", new DebugPointMesh(points.stream().flatMap(List::stream).toArray(Vector3f[]::new)));
    debug.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debug.getMaterial().setFloat("PointSize", 4f);
    rootNode.attachChild(debug);
  }

}
