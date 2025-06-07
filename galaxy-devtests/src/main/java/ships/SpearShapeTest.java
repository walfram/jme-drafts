package ships;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import common.CameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import jme3utilities.MyMesh;
import jme3utilities.mesh.Prism;
import materials.ShowNormalsMaterial;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SpearShapeTest extends SimpleApplication {
  
  private static final Logger logger = getLogger(SpearShapeTest.class);
  
  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    SpearShapeTest app = new SpearShapeTest();
    app.start();
  }
  
  public SpearShapeTest() {
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
    
//    Mesh source = new Cylinder(2, 3, cellExtent, 2f * cellExtent, cellExtent, true, false);
    
    Mesh source = new Prism(3, 2f * cellExtent, 0.5f * cellExtent, true);
    
    Deformation deformation = (v, n) -> {
      if (v.y < 0)
        return;
      
      v.z *= 0.5f;
      v.x *= 0.5f;
      
      v.z -= 0.5f * cellExtent;
    };
    
    Mesh topMesh = new DMesh(source, deformation);
    
    Geometry top = new Geometry("top", topMesh);
    top.setMaterial(material);
    rootNode.attachChild(top);
    top.move(0, 0.25f * cellExtent, 0);
    
    Deformation mirror = (v, n) -> {
      v.y *= -1;
    };
    
    Mesh bottomMesh = new DMesh(topMesh, mirror);
    MyMesh.reverseWinding(bottomMesh);
    
    Geometry bottom = new Geometry("bottom", bottomMesh);
    bottom.setMaterial(material);
    rootNode.attachChild(bottom);
    bottom.move(0, -0.25f * cellExtent, 0);
  }
}
