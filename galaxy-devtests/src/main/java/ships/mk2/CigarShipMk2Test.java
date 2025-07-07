package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;

public class CigarShipMk2Test extends SimpleApplication {

  private static final float cellExtent = 4f;

  public static void main(String[] args) {
    CigarShipMk2Test app = new CigarShipMk2Test();
    app.start();
  }

  public CigarShipMk2Test() {
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
    float xExtent = cellExtent * 4f;
    float yExtent = cellExtent * 4f;
    float zExtent = cellExtent * 16f;

    int numberOfPoints = 8;
    int numberOfFrames = 12;

    Material material = new ShowNormalsMaterial(assetManager);

    Geometry hull = new Geometry("hull", new Ellipse3d(xExtent, yExtent, zExtent, numberOfFrames, numberOfPoints, false));
    hull.setMaterial(material);
    rootNode.attachChild(hull);
  }
}
