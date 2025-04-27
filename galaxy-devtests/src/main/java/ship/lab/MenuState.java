package ship.lab;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.event.PopupState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuState extends BaseAppState {
  
  private static final Logger logger = LoggerFactory.getLogger(MenuState.class);
  
  private final Node gui = new Node("menu-gui-scene");
  
  public MenuState(Node guiNode) {
    guiNode.attachChild(gui);
  }
  
  @Override
  protected void initialize(Application app) {
  
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
  
  // TODO contact point is irrelevant?
  public void showMenuAt(Vector3f contactPoint) {
    Vector3f screenCoordinates = getApplication().getCamera().getScreenCoordinates(contactPoint);
    logger.debug("screen coords = {}", screenCoordinates);
    
    Container container = new Container();
    container.addChild(new Label("place part"));
    
    container.addChild(new Button("block"));
    container.addChild(new Button("left"));
    container.addChild(new Button("right"));
    
    container.setLocalTranslation(screenCoordinates);
    
    getState(PopupState.class).showPopup(
        container,
        PopupState.ClickMode.ConsumeAndClose,
        (ps) -> container.removeFromParent(),
        new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f)
    );
    
  }
}
