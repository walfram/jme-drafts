package editor;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.simsilica.state.CameraState;
import debug.QuickAppSettings;

public class ShipEditorMain extends SimpleApplication {
  
  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    ShipEditorMain app = new ShipEditorMain();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    final float cellExtent = 8f;
    
    stateManager.attach(new DebugGridState(rootNode, cellExtent));
    stateManager.attach(new DebugAxesState(rootNode));
    stateManager.attach(new EditorCameraState());
    
    stateManager.attach(new EditorLemurState());
    
    stateManager.attach(new CollisionState());
    
    stateManager.attach(new ClickState());
    
    stateManager.attach(new MenuState(guiNode));
    
    
  }
}
