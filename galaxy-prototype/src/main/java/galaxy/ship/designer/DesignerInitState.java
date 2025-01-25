package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import debug.DebugGrid;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;

public class DesignerInitState extends BaseAppState {

  private final Node rootNode;

  public DesignerInitState(Node rootNode) {
    this.rootNode = rootNode;
  }

  @Override
  protected void initialize(Application application) {
    FlyByCamera camera = getState(FlyCamAppState.class).getCamera();
    camera.setMoveSpeed(100);
    camera.setZoomSpeed(0);
    camera.setDragToRotate(true);

    AxesVisualizer axesVisualizer = new AxesVisualizer(application.getAssetManager(), 256, 1);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    new DebugGrid(application.getAssetManager(), 32f).attachTo(rootNode);

    MyCamera.setNearFar(application.getCamera(), application.getCamera().getFrustumNear(), 16384f);

    application.getCamera().setLocation(new Vector3f(129.27052f, 93.810875f, 311.66748f));
    application.getCamera().setRotation(new Quaternion(-0.025077702f, 0.9745218f, -0.14280961f, -0.17112498f));

    rootNode.addLight(new AmbientLight(ColorRGBA.White));
    rootNode.addLight(new DirectionalLight(application.getCamera().getDirection(), ColorRGBA.White));

    GuiGlobals.initialize(application);
    BaseStyles.loadGlassStyle();
    GuiGlobals.getInstance().getStyles().setDefaultStyle(BaseStyles.GLASS);
  }

  @Override
  protected void cleanup(Application application) {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void onDisable() {

  }
}
