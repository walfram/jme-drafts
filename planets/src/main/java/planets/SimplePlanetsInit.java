package planets;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import jme3utilities.debug.AxesVisualizer;

public class SimplePlanetsInit extends BaseAppState {

  private final Node rootNode;

  public SimplePlanetsInit(Node rootNode) {
    this.rootNode = rootNode;
  }

  @Override
  protected void initialize(Application app) {
    FlyByCamera flyCam = getState(FlyCamAppState.class).getCamera();
    flyCam.setMoveSpeed(100);
    flyCam.setZoomSpeed(0);
    flyCam.setDragToRotate(true);

    Geometry debugGrid = new Geometry("debug-grid", new Grid(8, 8, 2f * 32f));
    debugGrid.setMaterial(new Material(app.getAssetManager(), Materials.UNSHADED));
    debugGrid.getMaterial().setColor("Color", ColorRGBA.Blue);
    rootNode.attachChild(debugGrid);
    debugGrid.center();
    
    rootNode.addLight(new AmbientLight(ColorRGBA.White));

    AxesVisualizer axesVisualizer = new AxesVisualizer(app.getAssetManager(), 256, 2);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    app.getCamera().setLocation(new Vector3f(81.72028f, 124.937645f, 166.4307f));
    app.getCamera().setRotation(new Quaternion(-0.05812068f, 0.9378021f, -0.28288633f, -0.1926775f));
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
