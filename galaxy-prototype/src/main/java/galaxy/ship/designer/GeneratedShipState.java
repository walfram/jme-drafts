package galaxy.ship.designer;

import static com.jme3.math.FastMath.PI;
import static com.jme3.math.FastMath.log;
import static com.jme3.math.FastMath.pow;
import static com.jme3.math.Vector3f.UNIT_X;
import static jme3utilities.math.MyMath.max;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import galaxy.domain.ship.ShipDesign;
import jme3utilities.MyMesh;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratedShipState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(GeneratedShipState.class);
  
  private Material material;
  private Mesh hullFrontMesh;
  private Vector3f baseHullFrontScale;
  private Mesh hullBackMesh;
  private Vector3f baseHullBackScale;

  @Override
  protected void initialize(Application app) {
    material = new Material(app.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");

    hullFrontMesh = new FlatShadedMesh(new Cylinder(2, 6, 0.125f, 1f, 1f, true, false));
    MyMesh.translate(hullFrontMesh, new Vector3f(0, 0, 0.5f));
    baseHullFrontScale = new Vector3f(2f, 1f, 6f);

    hullBackMesh = new FlatShadedMesh(new Cylinder(2, 6, 0.25f, 1f, 1f, true, false));
    MyMesh.rotate(hullBackMesh, new Quaternion().fromAngleNormalAxis(PI, UNIT_X.negate()));
    MyMesh.translate(hullBackMesh, new Vector3f(0, 0, -0.5f));

    baseHullBackScale = new Vector3f(2f, 1f, 1f);
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

  public Node generate(ShipDesign shipDesign) {
    Node ship = new Node("generated-ship");

    double weight = shipDesign.weight();
    float height = (float) calculateHeight(weight, 0.125, 1);
    logger.debug("height = {}, log(height) = {}", height, log(height));

    Geometry front = new Geometry("hull-front", hullFrontMesh);
    ship.attachChild(front);
    
    front.setMaterial(material);
    front.setLocalScale(baseHullFrontScale);

    Geometry back = new Geometry("hull-back", hullBackMesh);
    ship.attachChild(back);
    
    back.setMaterial(material);
    back.setLocalScale(baseHullBackScale);
    
    ship.scale(max(1f, log(height)));
    
    return ship;
  }

  private double calculateHeight(double volume, double minorRadius, double majorRadius) {
    double v3 = 3 * volume;
    
    double minRR = minorRadius * minorRadius;
    double majRR = majorRadius * majorRadius;
    double rr = minorRadius * majorRadius;
    
    return v3 / PI * (minRR + majRR + rr);
  }
}
