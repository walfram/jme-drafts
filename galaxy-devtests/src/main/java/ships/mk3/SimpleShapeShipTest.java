package ships.mk3;

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
import mesh.FlatShadedMesh;
import ships.ExtentZOf;

public class SimpleShapeShipTest extends SimpleApplication {

  private static final float cellExtent = 4f;

  public static void main(String[] args) {
    SimpleShapeShipTest app = new SimpleShapeShipTest();
    app.start();
  }

  public SimpleShapeShipTest() {
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

    int radialSamples = 6;

    Geometry hull = new Geometry("hull", new FlatShadedMesh(
        new Cylinder(2, radialSamples, 3f * cellExtent, 5 * 2f * cellExtent, true))
    );
    hull.setMaterial(material);
    rootNode.attachChild(hull);

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(
        new Cylinder(2, radialSamples, cellExtent, 3f * cellExtent, 2f * cellExtent, true, false)
    ));
    bridge.setMaterial(material);
    bridge.move(0, 0, new ExtentZOf(hull).value() + new ExtentZOf(bridge).value());
    rootNode.attachChild(bridge);

    Geometry engine = new Geometry("engine", new FlatShadedMesh(
       new Cylinder(2, radialSamples, cellExtent, 2f * cellExtent, 2f * cellExtent, true, false)
    ));
    engine.setMaterial(material);
    engine.move(0, 0, - new ExtentZOf(hull).value() - new ExtentZOf(engine).value());
    rootNode.attachChild(engine);
  }
}
