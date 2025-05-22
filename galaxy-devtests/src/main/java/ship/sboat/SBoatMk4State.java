package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SBoatMk4State extends BaseAppState {
  
  private static final Logger logger = getLogger(SBoatMk4State.class);
  private final Node scene = new Node("scene");
  
  public SBoatMk4State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    Mesh frontOctaCone = new OctaCone(
        new OctaCone.Base(10, 4, 4, 8),
        new OctaCone.Base(40, 8, 8, 24),
        20f
    );
    Geometry front = new Geometry("front", new FlatShadedMesh(frontOctaCone));
    front.setMaterial(material);
    scene.attachChild(front);
    front.move(0, 0, 60f);
    
    Mesh middleOctaCone = new OctaCone(
        new OctaCone.Base(40, 8, 8, 24),
        new OctaCone.Base(40, 8, 8, 24),
        40f
    );
    Geometry middle = new Geometry("middle", new FlatShadedMesh(middleOctaCone));
    middle.setMaterial(material);
    scene.attachChild(middle);
    
    Mesh backOctaCone = new OctaCone(
        new OctaCone.Base(40, 8, 8, 24),
        new OctaCone.Base(20, 4, 4, 12),
        10f
    );

    Geometry back = new Geometry("back", new FlatShadedMesh(backOctaCone));
    back.setMaterial(material);
    scene.attachChild(back);
    back.move(0, 0, -50f);
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
