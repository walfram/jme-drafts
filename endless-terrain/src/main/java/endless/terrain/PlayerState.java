package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import java.util.List;
import jme3utilities.MySpatial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(PlayerState.class);

  private final Node scene = new Node("player-scene");

  public PlayerState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application app) {
    Spatial tankSpatial = app.getAssetManager().loadModel("Models/HoverTank/Tank2.mesh.xml");
    logger.debug("player tankSpatial bound = {}", tankSpatial.getWorldBound());
    scene.attachChild(tankSpatial);

    Material material = app.getAssetManager().loadMaterial("Models/HoverTank/tank.j3m");
    tankSpatial.setMaterial(material);
    material.setColor("Ambient", ColorRGBA.White);

    List<Spatial> spatials = MySpatial.listSpatials(tankSpatial);
    for (Spatial spatial: spatials) {
      logger.debug("spatial = {}", spatial);
    }
    
//    getState(CameraState.class).follow(scene);
    getState(CameraState.class).chase(scene);
  }

  @Override
  protected void cleanup(Application app) {
  }

  @Override
  protected void onEnable() {
  }

  @Override
  protected void onDisable() {
  }

}
