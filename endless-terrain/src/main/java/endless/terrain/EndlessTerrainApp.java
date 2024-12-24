package endless.terrain;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndlessTerrainApp extends SimpleApplication {

  private static final Logger logger = LoggerFactory.getLogger(EndlessTerrainApp.class);
  
  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    EndlessTerrainApp app = new EndlessTerrainApp();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  @Override
  public void simpleInitApp() {
    logger.debug("initializing...");
    stateManager.attach(new ConfigState());
    
    stateManager.attach(new InitState(rootNode));
    stateManager.attach(new TerrainState(rootNode));
    
//    stateManager.attach(new CameraState());
    stateManager.attach(new PlayerState(rootNode));
//    stateManager.attach(new PlayerControlsState());
    
    stateManager.attach(new PostProcessingState());
    
    stateManager.attach(new DebugState(guiNode));
  }
}
