package ship.lab;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import ship.common.DebugAxesState;
import ship.common.DebugGridState;
import ship.common.CameraState;
import ship.common.LemurState;

public class ShipLabMain extends SimpleApplication {
  
  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    ShipLabMain app = new ShipLabMain();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    final float cellExtent = 4f;
    
    stateManager.attach(new DebugGridState(rootNode, cellExtent));
    stateManager.attach(new DebugAxesState(rootNode));
    stateManager.attach(new CameraState());
    
    stateManager.attach(new LemurState());
    
//    stateManager.attach(new CollisionState());
//    stateManager.attach(new ClickState());
//    stateManager.attach(new MenuState(guiNode));
    
    stateManager.attach(new ShipState());
    
    stateManager.attach(new HullState(rootNode, cellExtent));
    stateManager.attach(new ContainerState(rootNode, cellExtent));
    stateManager.attach(new ContainerUiState(guiNode));
  }
}
