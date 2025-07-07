package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.BeveledBox;
import mesh.CustomCylinder;
import mesh.FlatShadedMesh;
import ships.ExtentZOf;

public class CigarShipMk3Test extends SimpleApplication {

  private static final float cellExtent = 4f;
  private static final float cellSize = 2f * cellExtent;

  public static void main(String[] args) {
    CigarShipMk3Test app = new CigarShipMk3Test();
    app.start();
  }

  public CigarShipMk3Test() {
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
    // material.getAdditionalRenderState().setWireframe(true);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 6, 2f * cellExtent, 5 * cellSize, true)));
    hull.setMaterial(material);
    hull.scale(1, 2, 1);
    rootNode.attachChild(hull);
  }
}
