package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;

public class InitState extends BaseAppState {

  private final Node rootNode;

  public InitState(Node rootNode) {
    this.rootNode = rootNode;
  }

  @Override
  protected void initialize(Application app) {
    FlyByCamera flyCam = getState(FlyCamAppState.class).getCamera();
    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(50f);
    flyCam.setZoomSpeed(0f);
    
    float cellExtent = getState(ConfigState.class).cellExtent();

    AxesVisualizer axesVisualizer = new AxesVisualizer(app.getAssetManager(), cellExtent, 2);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    Geometry debugGrid = new Geometry("debug-grid", new Grid(8, 8, 2f * cellExtent));
    debugGrid.setMaterial(new Material(app.getAssetManager(), Materials.UNSHADED));
    debugGrid.getMaterial().setColor("Color", ColorRGBA.Blue);
    rootNode.attachChild(debugGrid);
    debugGrid.center();

    app.getCamera().setLocation(new Vector3f(82.721375f, 78.605225f, 228.47423f));
    app.getCamera().setRotation(new Quaternion(-0.026715863f, 0.972733f, -0.1786568f, -0.14545964f));

    MyCamera.setNearFar(app.getCamera(), app.getCamera().getFrustumNear(), 32768f);
    
    rootNode.addLight(new AmbientLight(ColorRGBA.White));
    rootNode.addLight(new DirectionalLight(app.getCamera().getDirection(), ColorRGBA.White));
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
