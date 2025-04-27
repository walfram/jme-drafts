package ship.lab;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResult;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.input.Button;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ClickState extends BaseAppState {
  
  private static final Logger logger = LoggerFactory.getLogger(ClickState.class);
  private static final FunctionId FUNC_RIGHT_CLICK = new FunctionId("right-click");
  
  @Override
  protected void initialize(Application app) {
    InputMapper inputMapper = GuiGlobals.getInstance().getInputMapper();
    
    inputMapper.map(FUNC_RIGHT_CLICK, Button.MOUSE_BUTTON2);
    inputMapper.addStateListener((func, value, tpf) -> onRightClick(value, tpf), FUNC_RIGHT_CLICK);
  }
  
  private void onRightClick(InputState value, double tpf) {
    if (value != InputState.Off)
      return;
    
    Optional<CollisionResult> collision = getState(CollisionState.class).clickCollision();
    
    collision.ifPresent(collisionResult -> {
      logger.debug("right click @{}", collisionResult.getContactPoint());
      getState(EditorCameraState.class).centerOn(collisionResult.getContactPoint());
      getState(MenuState.class).showMenuAt(collisionResult.getContactPoint());
    });
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
