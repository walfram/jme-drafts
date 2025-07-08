package primitives;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.MBox;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;

public class BoxTextureTest extends SimpleApplication {

  private static final float cellExtent = 4f;

  public static void main(String[] args) {
    BoxTextureTest app = new BoxTextureTest();
    app.start();
  }

  public BoxTextureTest() {
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
    float xExtent = 3f * cellExtent;
    float yExtent = 3f * cellExtent;
    float zExtent = 3f * cellExtent;
    Mesh source = new MBox(xExtent, yExtent, zExtent, 2, 2, 2);

    Mesh mesh = new DMesh(source, (v, n) -> {
      if (Math.abs(v.x) == xExtent) {
        if (Math.abs(v.y) != yExtent) {
          v.x += 2f * Math.signum(v.x) * cellExtent;
        }
      }

//      if (Math.abs(v.z) == zExtent) {
//        if (Math.abs(v.y) != yExtent) {
//          v.z *= 2f;
//        }
//      }
    });

    Geometry box = new Geometry("box", mesh);

    Material material = new Material(assetManager, Materials.UNSHADED);
    material.setTexture("ColorMap", assetManager.loadTexture("chatgpt-seamless.png"));

    box.setMaterial(material);

    rootNode.attachChild(box);
  }
}
