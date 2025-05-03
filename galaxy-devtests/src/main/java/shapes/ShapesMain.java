package shapes;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import ship.common.CameraState;
import ship.common.DebugAxesState;
import ship.common.DebugGridState;
import ship.common.LemurState;

public class ShapesMain extends SimpleApplication {
  
  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    ShapesMain app = new ShapesMain();
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
    
//    stateManager.attach(new RoundedCubeState(rootNode));
    stateManager.attach(new TubeState(rootNode));
  }
}
