package ships;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import common.CameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.IrregularCylinder;
import org.slf4j.Logger;

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
    
    float radius = 8f;
    float height = 40f;
    
    // must add up to 120 degrees
    float thetaMinor = 30f * FastMath.DEG_TO_RAD;
    float thetaMajor = 90f * FastMath.DEG_TO_RAD;
    
    Geometry test = new Geometry("test", new IrregularCylinder(height, radius, 2f * radius, thetaMajor, thetaMinor));
    test.setMaterial(material);
    rootNode.attachChild(test);
  }
}
