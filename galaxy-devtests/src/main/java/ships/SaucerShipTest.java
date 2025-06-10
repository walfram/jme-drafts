package ships;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import common.CameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import jme3utilities.MyMesh;
import jme3utilities.SimpleControl;
import jme3utilities.mesh.DomeMesh;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

import static jme3utilities.mesh.DomeMesh.*;

public class SaucerShipTest extends SimpleApplication {
  
  public static void main(String[] args) {
    SaucerShipTest app = new SaucerShipTest();
    app.start();
  }
  
  private static final float cellExtent = 4f;
  
  public SaucerShipTest() {
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
    
    Node ship = new Node("ship");
    
    Mesh bottomMesh = new Cylinder(2, 8, 15f, 30f, 5f, true, false);
    MyMesh.rotate(bottomMesh, new Quaternion().fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
    
    Geometry bottom = new Geometry("bottom", new FlatShadedMesh(bottomMesh));
    bottom.setMaterial(material);
    bottom.move(0, -2.5f, 0);
    ship.attachChild(bottom);
    
    Mesh topMesh = new Cylinder(2, 8, 30f, 15f, 5f, true, false);
    MyMesh.rotate(topMesh, new Quaternion().fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
    
    Geometry top = new Geometry("top", new FlatShadedMesh(topMesh));
    top.setMaterial(material);
    top.move(0, 2.5f, 0);
    ship.attachChild(top);
    
    Mesh bridgeMesh = new DomeMesh(8, 3, defaultTopU, defaultTopV, defaultUvScale, false);
    
    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(bridgeMesh));
    bridge.setMaterial(material);
    bridge.scale(15f, 5, 15f);
    bridge.move(0, 5, 0);
    ship.attachChild(bridge);
    
    rootNode.attachChild(ship);
    
    ship.addControl(new HoverControl());
    ship.addControl(new WobbleControl());
  }
}
