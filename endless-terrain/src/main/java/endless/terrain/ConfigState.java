package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

public class ConfigState extends BaseAppState {

  private final float cellExtent;

  public ConfigState() {
    this(2048f);
  }

  public ConfigState(float cellExtent) {
    this.cellExtent = cellExtent;
  }

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

  public float cellExtent() {
    return cellExtent;
  }
}
