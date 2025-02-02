package galaxy.tests;

import static com.jme3.math.FastMath.*;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import com.simsilica.lemur.geom.MBox;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickSetup;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeformedMeshShipTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    DeformedMeshShipTest app = new DeformedMeshShipTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(DeformedMeshShipTest.class);
  private static final float extent = 8f;
  
  @Override
  public void simpleInitApp() {
    new QuickSetup().applyTo(this);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    // material.getAdditionalRenderState().setWireframe(true);

    mk2(material);

    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }

  // deformed cylinder
  private void mk2(Material material) {
    Deformation deformation = (v, n) -> {
      float f = -0.0625f * v.z + 1.25f;

      if (Math.abs(v.z) == 4 || Math.abs(v.z) == 8) {
        f = 1;
      }
      
      logger.debug("v = {}, f = {}", v, f);
      Vector3f vv = new Vector3f(v.x, v.y, 0).multLocal(f);
      
      v.x = vv.x;
      v.y = vv.y;
    };
    
    Mesh mesh = new DMesh(new Cylinder(7, 8, 5, 24, true), deformation);
    Geometry geometry = new Geometry("mk2", new FlatShadedMesh(mesh));
    geometry.setMaterial(material);
    rootNode.attachChild(geometry);
  }

  // ellipse
  private void mk1(Material material) {
    MBox box = new MBox(extent, extent, extent, 4, 4, 8);

    float rX = extent * 1.5f;
    float rY = extent * 1.25f;
    float rZ = extent * 3f;

    float threshold = extent * 2.5f;

    Deformation deformation = (vert, normal) -> {
      float theta = atan2(vert.y, vert.x);
      float phi = asin(vert.z / sqrt(pow(vert.x, 2) + pow(vert.y, 2) + pow(vert.z, 2)));
      
      vert.x = rX * cos(theta) * cos(phi);
      vert.y = rY * sin(theta) * cos(phi);
       vert.z = clamp(rZ * sin(phi), -threshold, threshold);
//      vert.z = rZ * sin(phi);
    };

    Mesh mesh = new DMesh(box, deformation);

    Geometry geometry = new Geometry("box", new FlatShadedMesh(mesh));

    logger.debug("bounds = {}", geometry.getWorldBound());

    geometry.setMaterial(material);

    rootNode.attachChild(geometry);
  }
}
