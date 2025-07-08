package primitives;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import mesh.FlatShadedMesh;
import mesh.face.Face;
import mesh.face.QuadFace;

import java.util.List;

public class QuadTextureTest extends SimpleApplication {

  private static final float cellExtent = 4f;

  public static void main(String[] args) {
    QuadTextureTest app = new QuadTextureTest();
    app.start();
  }

  public QuadTextureTest() {
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
    float xExtent = cellExtent * 3f;
    float zExtent = cellExtent * 3f;

    Face face = new QuadFace(
        new Vector3f(0.5f * xExtent, 0, zExtent),
        new Vector3f(xExtent, 0, -zExtent),
        new Vector3f(-xExtent, 0, -zExtent),
        new Vector3f(-xExtent, 0, zExtent)
    );

    Material material = new Material(assetManager, Materials.UNSHADED);
    Texture texture = assetManager.loadTexture("chatgpt-seamless.png");
    texture.setWrap(Texture.WrapMode.Repeat);
    material.setTexture("ColorMap", texture);
    // material.getAdditionalRenderState().setWireframe(true);

    FlatShadedMesh mesh = new FlatShadedMesh(List.of(face));
    mesh.scaleTextureCoordinates(new Vector2f(4, 4));

    Geometry quad0 = new Geometry("quad0", mesh);
    quad0.setMaterial(material);
    rootNode.attachChild(quad0);

    Geometry quad1 = new Geometry("quad1", mesh);
    quad1.setMaterial(material);
    quad1.move(0, 0, 2f * cellExtent * 3);
    rootNode.attachChild(quad1);
  }
}
