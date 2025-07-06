package ships.mk1;

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
    
    float thetaMinor = 20f * FastMath.DEG_TO_RAD;
    float thetaMajor = 100f * FastMath.DEG_TO_RAD;
    
    Geometry bridge = new Geometry("bridge", new IrregularCylinder(2f * 2f * cellExtent, cellExtent, 2f * cellExtent, thetaMajor, thetaMinor, true, false));
    bridge.setMaterial(material);
    bridge.move(0, 0, 2.5f * 2f * cellExtent);
    rootNode.attachChild(bridge);
    
    Geometry hull = new Geometry("hull", new IrregularCylinder(3f * 2f * cellExtent, 2f * cellExtent, 2f * cellExtent, thetaMajor, thetaMinor, false, false));
    hull.setMaterial(material);
    rootNode.attachChild(hull);

//    Geometry engine = new Geometry("engine", new FlatShadedMesh(new Cylinder(2, 8, 2.5f * cellExtent, 2f * 2f * cellExtent, true)));
    Geometry engine = new Geometry("engine", new IrregularCylinder(2f * 2f * cellExtent, 2.5f * cellExtent, 80f * FastMath.DEG_TO_RAD, 40f * FastMath.DEG_TO_RAD));
    engine.setMaterial(material);
    engine.move(0, 0, -2f * 2f * cellExtent - cellExtent);
//    engine.rotate(0, 0, FastMath.QUARTER_PI * 0.5f);
    rootNode.attachChild(engine);

//    stateManager.getState(CameraState.class).updateLocation(new Vector3f(0, 0, 150));
//    stateManager.getState(CameraState.class).lookAt(new Vector3f(), Vector3f.UNIT_Y);
  }
}
