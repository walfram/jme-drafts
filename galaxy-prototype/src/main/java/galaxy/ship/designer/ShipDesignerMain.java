package galaxy.ship.designer;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class ShipDesignerMain extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    ShipDesignerMain app = new ShipDesignerMain();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    stateManager.attach(new DesignerInitState(rootNode));
    
    // stateManager.attach(new ShipParamsWidgetState(guiNode));
    
    stateManager.attach(new ShipDesignIOState());
    
    stateManager.attach(new GeneratedShipState());
    
    stateManager.attach(new ShipDesignUiState(guiNode));
    stateManager.attach(new ShipDesignSceneState(rootNode));
  }
}
