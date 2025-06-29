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
import jme3utilities.mesh.Icosphere;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import mesh.TubeMesh;

public class Saucer2ShipTest extends SimpleApplication {

  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    Saucer2ShipTest app = new Saucer2ShipTest();
    app.start();
  }
  
  public Saucer2ShipTest() {
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
    
    Geometry core = new Geometry("core", new FlatShadedMesh(new Icosphere(1, 3f * cellExtent)));
    core.setMaterial(material);
    rootNode.attachChild(core);
    
    Geometry hull = new Geometry("hull", new TubeMesh(16, 4f * cellExtent, 8f * cellExtent, cellExtent));
    hull.setMaterial(material);
    hull.rotate(-FastMath.HALF_PI, 0, 0);
    rootNode.attachChild(hull);
  }
}
