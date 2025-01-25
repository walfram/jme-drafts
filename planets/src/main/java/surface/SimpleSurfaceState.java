package surface;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.MBox;
import mesh.FlatShadedMesh;
import noise.DefaultFastNoiseLite;
import noise.FastNoiseLite;

public class SimpleSurfaceState extends BaseAppState {

  private final Node scene = new Node("simple-surface-scene");

  private static final float extent = 32f * 9;
  private static final int slices = 64;

  private final FastNoiseLite noise = new DefaultFastNoiseLite();

  {
    noise.SetFrequency(0.0125f);
  }

  public SimpleSurfaceState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application application) {
    Mesh source = new MBox(extent, 0, extent, slices, 0, slices);

    DMesh mesh = new DMesh(source, (vertex, normal) -> {
      float e = noise.GetNoise(vertex.x, vertex.z);

      if (e > 0) {
        float m = 0f;

        if (e > 0.6f) {
          m = 1f;
        } else if (e > 0.3f) {
          m = 0.5f;
        }

        vertex.y = m * 32f;
//        normal.set(vertex.normalize());
        normal.y = vertex.y;
        normal.normalizeLocal();
      }
    });

    Geometry geometry = new Geometry("surface", mesh);

    Material material = new Material(application.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
//     material.getAdditionalRenderState().setWireframe(true);

    geometry.setMaterial(material);

    scene.attachChild(geometry);
  }

  @Override
  protected void cleanup(Application application) {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void onDisable() {

  }
}
