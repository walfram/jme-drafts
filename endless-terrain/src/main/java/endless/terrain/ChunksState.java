package endless.terrain;

import cells.Cell;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import endless.terrain.heightmap.Heightmap;
import java.util.HashMap;
import java.util.Map;

public class ChunksState extends BaseAppState {

  private final Map<Cell, Heightmap> heightmapCache = new HashMap<>(256);
  
  @Override
  protected void initialize(Application app) {
    
  }

  @Override
  protected void cleanup(Application app) {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void onDisable() {

  }
}
