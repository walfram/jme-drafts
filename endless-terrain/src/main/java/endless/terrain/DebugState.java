package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;

public class DebugState extends BaseAppState {

  private final Node scene = new Node("debug-gui-scene");
  private BitmapText cameraPositionText;

  public DebugState(Node guiNode) {
    guiNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application app) {
    BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Console.fnt");

    cameraPositionText = new BitmapText(font);
    cameraPositionText.setLocalTranslation(10, app.getCamera().getHeight() - 10, 0);
    scene.attachChild(cameraPositionText);
  }

  @Override
  public void update(float tpf) {
    cameraPositionText.setText(
        "camera location = %s, direction = %s".formatted(
            getApplication().getCamera().getLocation(),
            getApplication().getCamera().getDirection()
        )
    );
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
