package planets;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

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
    stateManager.attach(new SimplePlanetsInit(rootNode));
    stateManager.attach(new SimplePlanetState(rootNode));
  }
}
