package surface;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import common.CommonInitState;

public class SimpleNoiseSurface extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);
    
    SimpleNoiseSurface app = new SimpleNoiseSurface();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    stateManager.attach(new CommonInitState(rootNode));
    stateManager.attach(new SimpleSurfaceState(rootNode));
  }
}
