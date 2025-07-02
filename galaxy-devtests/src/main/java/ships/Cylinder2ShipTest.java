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
import com.jme3.scene.shape.Cylinder;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import mesh.IrregularCylinder;

public class Cylinder2ShipTest extends SimpleApplication {
  
  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    Cylinder2ShipTest app = new Cylinder2ShipTest();
    app.start();
  }
  
  public Cylinder2ShipTest() {
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
    
    float thetaMajor = 90f * FastMath.DEG_TO_RAD;
    float thetaMinor = 30f * FastMath.DEG_TO_RAD;
    
    Geometry hull = new Geometry("hull", new IrregularCylinder(5 * 2f * cellExtent, 3f * cellExtent, thetaMajor, thetaMinor));
    hull.setMaterial(material);
    hull.rotate(0, 0, FastMath.DEG_TO_RAD * 60f);
    rootNode.attachChild(hull);
    
    Geometry aux = new Geometry("aux", new FlatShadedMesh(new Cylinder(2, 6, 2f * cellExtent, 2f * cellExtent, true)));
    aux.setMaterial(material);
    aux.move(0, 0, -5f * cellExtent -cellExtent);
    rootNode.attachChild(aux);
    
    Geometry engine = new Geometry("engine", new FlatShadedMesh(new Cylinder(2, 6, cellExtent, 3f * cellExtent, 4f * cellExtent, true, false)));
    engine.setMaterial(material);
    engine.move(0, 0, -5f * cellExtent - cellExtent -3f * cellExtent);
    rootNode.attachChild(engine);
  }
}
