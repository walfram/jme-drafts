package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.core.VersionedReference;
import galaxy.domain.ship.ShipDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipDesignSceneState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(ShipDesignSceneState.class);
  
  private final Node scene = new Node("ship-design-scene-node");
  private VersionedReference<ShipDesign> designReference;

  public ShipDesignSceneState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application application) {
    Geometry geometry = new Geometry("ship", new Cylinder(5, 7, 1, 1, true));
    geometry.setMaterial(new Material(application.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md"));
    geometry.scale(10);
    scene.attachChild(geometry);

    designReference = getState(ShipDesignUiState.class).shipDesignReference();
  }

  @Override
  public void update(float tpf) {
    if (designReference.update()) {
      logger.debug("design ref updated {}", designReference.get());
    }
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
