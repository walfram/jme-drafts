package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.geom.DMesh;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import mesh.IrregularCylinder;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class CigarShipMk3Test extends SimpleApplication {

  private static final Logger logger = getLogger(CigarShipMk3Test.class);

  private static final float cellExtent = 4f;
  private static final float cellSize = 2f * cellExtent;
  private Material material;

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
    material = new ShowNormalsMaterial(assetManager);
    // material.getAdditionalRenderState().setWireframe(true);

    hull();
    engine();
    bridge();
  }

  private void bridge() {
    Mesh mesh = new DMesh(new Box(1, 1, 1), (v, n) -> {
      if (v.y == -1) {
        if (v.z != -1) {
          v.z *= 0f;
        }
        v.x *= 0.5f;
      }
    });

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(mesh));
    bridge.setMaterial(material);
    bridge.scale(cellExtent, cellExtent, 2f * cellExtent);
    bridge.setLocalTranslation(0, 0, 2.5f * cellSize);
    rootNode.attachChild(bridge);
  }

  private void engine() {
    Geometry engineFront = new Geometry("engineFront", new FlatShadedMesh(new Cylinder(2, 16, 1.5f * cellExtent, cellSize, true)));
    engineFront.setMaterial(material);
    engineFront.setLocalTranslation(0, 0, -2f * cellSize);
    rootNode.attachChild(engineFront);

    Geometry engineBack = new Geometry("engineBack", new FlatShadedMesh(new Cylinder(2, 8, cellExtent, 0.7f * cellExtent, cellSize, true, false)));
    engineBack.setMaterial(material);
    engineBack.setLocalTranslation(0, 0, -3f * cellSize);
    rootNode.attachChild(engineBack);
  }

  private void hull() {
    float major = 90f * FastMath.DEG_TO_RAD;
    float minor = 30f * FastMath.DEG_TO_RAD;

    Geometry hull = new Geometry("hull", new IrregularCylinder(3 * cellSize, 1.5f * cellExtent, major, minor));
    hull.setMaterial(material);
    rootNode.attachChild(hull);

    DMesh mesh = new DMesh(new Box(1, 1, 1), (v, n) -> {
      if (v.y == 1) {
        if (v.z != -1) { // back
          v.z *= 0f;
        }

        v.x *= 0.25f;
      }
    });

    float[] angles = new float[]{
        0f, FastMath.DEG_TO_RAD * 120f, FastMath.DEG_TO_RAD * 240f
    };

    for (float angle : angles) {
      Geometry siding = new Geometry("siding", new FlatShadedMesh(mesh));
      siding.setMaterial(material);
      siding.scale(cellExtent, 0.5f * cellExtent, 2 * cellExtent);
      siding.move(0, 1.5f * cellExtent, 0);

      Node wrap = new Node("siding-wrap");
      wrap.attachChild(siding);
      wrap.rotate(0, 0, angle);
      rootNode.attachChild(wrap);
    }
  }
}
