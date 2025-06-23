package ships;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import common.CameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.Face;
import mesh.FlatShadedMesh;
import mesh.QuadFace;
import mesh.TriangleFace;
import misc.DebugPointMesh;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class CylinderShipTest extends SimpleApplication {
  
  private static final Logger logger = getLogger(CylinderShipTest.class);
  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    CylinderShipTest app = new CylinderShipTest();
    app.start();
  }
  
  public CylinderShipTest() {
    super(
        new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
        new ConstantVerifierState(),
        
        new DebugGridState(cellExtent, false),
        new DebugAxesState(),
        new CameraState(),
        new LemurState()
    );
  }
  
  @Override
  public void simpleInitApp() {
    Material material = new ShowNormalsMaterial(assetManager);
    
    // must add up to 120 degrees
    float thetaMinor = 30f * FastMath.DEG_TO_RAD;
    float thetaMajor = 90f * FastMath.DEG_TO_RAD;
    
    // radius
    Vector3f origin = new Vector3f(10f, 0, 0);
    
    // half-height, positive
    Vector3f frontOffset = new Vector3f(0, 0, 10f);
    // half-height, negative
    Vector3f backOffset = new Vector3f(0, 0, -10f);
    
    Quaternion initial = new Quaternion().fromAngleAxis(15f * FastMath.DEG_TO_RAD, Vector3f.UNIT_Z);
    initial.multLocal(origin);
    
    Quaternion minor = new Quaternion().fromAngleAxis(thetaMinor, Vector3f.UNIT_Z);
    Quaternion major = new Quaternion().fromAngleAxis(thetaMajor, Vector3f.UNIT_Z);
    
    List<Vector3f> frontPoints = new ArrayList<>(6);
    List<Vector3f> backPoints = new ArrayList<>(6);
    
    // TODO 3 and 6 should be parameters
    List<Face> faces = new ArrayList<>(3 * 6);
    
    for (int i = 0; i < 6; i++) {
      if (i % 2 == 0) {
        minor.multLocal(origin);
      } else {
        major.multLocal(origin);
      }
      
      frontPoints.add(new Vector3f(origin).add(frontOffset));
      backPoints.add(new Vector3f(origin).add(backOffset));
    }
    
    for (int i = 0; i < 6; i++) {
      Face face = new TriangleFace(new Vector3f(frontOffset), frontPoints.get(i), frontPoints.get((i + 1) % 6));
      faces.add(face);
    }
    
    for (int i = 0; i < 6; i++) {
      Face face = new TriangleFace(new Vector3f(backOffset), backPoints.get((i + 1) % 6), backPoints.get(i));
      faces.add(face);
    }
    
    for (int i = 0; i < 6; i++) {
      Face face = new QuadFace(frontPoints.get(i), backPoints.get(i), backPoints.get((i + 1) % 6), frontPoints.get((i + 1) % 6));
      faces.add(face);
    }
    
    logger.debug("faces = {}", faces.size());
    
    Geometry debug = new Geometry("debug", new DebugPointMesh(
        faces.stream().flatMap(
            f -> f.triangles().stream().flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3()))
        ).toList()
    ));
    debug.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debug.getMaterial().setFloat("PointSize", 5f);
    rootNode.attachChild(debug);
    
    List<Triangle> triangles = faces.stream().flatMap(f -> f.triangles().stream()).toList();
    logger.debug("triangles = {}", triangles.size());
    
    Geometry test = new Geometry("test", new FlatShadedMesh(triangles));
    test.setMaterial(material);
    rootNode.attachChild(test);
  }
}
