package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import debug.QuickLemurSetup;

public class DesignerInitState extends BaseAppState {

  private final Node rootNode;

  public DesignerInitState(Node rootNode) {
    this.rootNode = rootNode;
  }

  @Override
  protected void initialize(Application application) {
    new QuickAppSetup(4f, 32).applyTo((SimpleApplication) application);

    new QuickLemurSetup().applyTo(application);
    
    new QuickChaseCamera(application.getCamera(), application.getInputManager()).init(rootNode);
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
