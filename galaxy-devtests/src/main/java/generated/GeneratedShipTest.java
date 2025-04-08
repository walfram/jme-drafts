package generated;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;

public class GeneratedShipTest extends SimpleApplication {
  
  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    GeneratedShipTest app = new GeneratedShipTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    new QuickAppSetup(8f, 32).applyTo(this);
    
    stateManager.attach(new ShipModelState(rootNode));
    stateManager.attach(new UboatShipState(rootNode));
    
    stateManager.attach(new ModularShipState(rootNode));
    
    new QuickChaseCamera(cam, inputManager).attachTo(rootNode);
  }
}
