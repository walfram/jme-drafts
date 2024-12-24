package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class DebugState extends BaseAppState {

  private final Node scene = new Node("debug-gui-scene");
  private BitmapText cameraPositionText;
  private BitmapText speedValueText;
  private BitmapText altitudeValueText;

  public DebugState(Node guiNode) {
    guiNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application app) {
    BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Console.fnt");

    cameraPositionText = new BitmapText(font);
    cameraPositionText.setLocalTranslation(10, app.getCamera().getHeight() - 10, 0);
    scene.attachChild(cameraPositionText);

    speedValueText = new BitmapText(font);
    speedValueText.setLocalTranslation(10, app.getCamera().getHeight() - 10 - cameraPositionText.getHeight(), 0);
    scene.attachChild(speedValueText);

    altitudeValueText = new BitmapText(font);
    altitudeValueText.setLocalTranslation(10,
        app.getCamera().getHeight() - 10 - cameraPositionText.getHeight() - speedValueText.getHeight(), 0);
    scene.attachChild(altitudeValueText);
  }

  @Override
  public void update(float tpf) {
    Vector3f location = getApplication().getCamera().getLocation();
    Vector3f direction = getApplication().getCamera().getDirection();
    cameraPositionText.setText(
        "camera location [x:%.4f, y:%.4f, z:%.4f]; direction = [x:%.4f, y:%.4f, z:%.4f]".formatted(
            location.x, location.y, location.z,
            direction.x, direction.y, direction.z
        )
    );

    speedValueText.setText(
        "speed = %.3f".formatted(getState(PlayerState.class).speedValue())
    );
    
    altitudeValueText.setText(
        "altitude = %.3f".formatted(getState(PlayerState.class).altitudeValue())
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
