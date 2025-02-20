package planets;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import common.CommonInitState;

public class SimplePlanetsDemo extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);
    
    SimplePlanetsDemo app = new SimplePlanetsDemo();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    stateManager.attach(new CommonInitState(rootNode));
    stateManager.attach(new SimplePlanetState(rootNode));
  }
}
