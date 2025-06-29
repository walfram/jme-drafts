package ships;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import common.CameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.IrregularCylinder;

public class CylinderShipTest extends SimpleApplication {
  
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
    
    float thetaMinor = 20f * FastMath.DEG_TO_RAD;
    float thetaMajor = 100f * FastMath.DEG_TO_RAD;
    
    Geometry test = new Geometry("test", new IrregularCylinder(height, radius, 2f * radius, thetaMajor, thetaMinor));
    test.setMaterial(material);
    rootNode.attachChild(test);
    
    stateManager.getState(CameraState.class).updateLocation(new Vector3f(0, 0, 150));
    stateManager.getState(CameraState.class).lookAt(new Vector3f(), Vector3f.UNIT_Y);
  }
}
