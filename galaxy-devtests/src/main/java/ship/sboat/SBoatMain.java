package ship.sboat;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import common.CameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;

public class SBoatMain extends SimpleApplication {
  
  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    SBoatMain app = new SBoatMain();
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
    
//    stateManager.attach(new SBoatMk5State(rootNode));
//    stateManager.attach(new SBoatMk7State(rootNode));
//    stateManager.attach(new SBoatMk9State(rootNode));
    
  }
}
