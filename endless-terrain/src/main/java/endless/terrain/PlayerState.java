package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import jme3utilities.MySpatial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(PlayerState.class);

  private final Node scene = new Node("player-scene");

  private float speed = 0f;
  private float altitude = 0f;
  
  public PlayerState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application app) {
    Spatial tankSpatial = app.getAssetManager().loadModel("Models/HoverTank/Tank2.mesh.xml");
    logger.debug("player tankSpatial bound = {}", tankSpatial.getWorldBound());
    scene.attachChild(tankSpatial);

    Material material = app.getAssetManager().loadMaterial("Models/HoverTank/tank.j3m");
    tankSpatial.setMaterial(material);
    material.setColor("Ambient", ColorRGBA.White);

    List<Spatial> spatials = MySpatial.listSpatials(tankSpatial);
    for (Spatial spatial : spatials) {
      logger.debug("spatial = {}", spatial);
    }

//    getState(CameraState.class).follow(scene);
    getState(CameraState.class).chase(scene);
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

  public void speedUp(double value, double tpf) {
    float inc = (float) (value * tpf);
    speed = Float.min(1f, speed + inc);
  }

  public void speedDown(double value, double tpf) {
    float inc = (float) (value * tpf);
    speed = Float.max(0f, speed - inc);
  }

  // TODO use yaw angle field, to avoid extreme angle?
  public void yaw(double value, double tpf) {
    float angle = (float) (value * tpf) * -1f;
    Vector3f up = scene.getLocalRotation().mult(Vector3f.UNIT_Y);
    Quaternion yaw = new Quaternion().fromAngleNormalAxis(angle, up);
    Quaternion mult = yaw.mult(scene.getLocalRotation());
    scene.setLocalRotation(mult);
  }

  // TODO use pitch angle field, to avoid extreme angle?
  public void pitch(double value, double tpf) {
    float angle = (float) (value * tpf) * -1f;
    Vector3f right = scene.getLocalRotation().mult(Vector3f.UNIT_X);
    Quaternion pitch = new Quaternion().fromAngleNormalAxis(angle, right);
    Quaternion mult = pitch.mult(scene.getLocalRotation());
    scene.setLocalRotation(mult);
  }

  public void altitudeUp(double value, double tpf) {
    float inc = (float) (value * tpf);
    altitude = Float.min(1f, altitude + inc);
  }

  public void altitudeDown(double value, double tpf) {
    float inc = (float) (value * tpf);
    altitude = Float.max(0f, altitude - inc);
  }

  public float speedValue() {
    return speed;
  }
  
  public float altitudeValue() {
    return altitude;
  }
  
}
