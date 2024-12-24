package planets;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import com.simsilica.lemur.geom.MBox;
import noise.FastNoiseLite;
import noise.FastNoiseLite.FractalType;
import noise.FastNoiseLite.NoiseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePlanetState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(SimplePlanetState.class);

  private static final float radius = 32f;
  
  private final Node scene = new Node("simple-planet-scene");
  private final FastNoiseLite noise = new FastNoiseLite(42);
  {
    noise.SetFrequency(0.05f);
    noise.SetNoiseType(NoiseType.Value);
    noise.SetFractalType(FractalType.PingPong);
  }

  public SimplePlanetState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application application) {
    Mesh source = new MBox(radius, radius, radius, 8, 8, 8);

    Deformation deform = (vert, normal) -> {
      float v = noise.GetNoise(vert.x, vert.y, vert.z);

      vert.normalizeLocal();
      if (v > 0) {
        vert.multLocal(radius * (1 + v));
      } else {
        vert.multLocal(radius);
      }

      normal.set(vert.normalize());
    };

    DMesh mesh = new DMesh(source, deform);

    Geometry geometry = new Geometry("simple-planet", mesh);

    Material material = new Material(application.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
     material.getAdditionalRenderState().setWireframe(true);

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
