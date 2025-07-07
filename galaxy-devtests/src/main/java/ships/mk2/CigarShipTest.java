package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import mesh.Face;
import mesh.FlatShadedMesh;
import mesh.QuadFace;
import mesh.TriangleFace;
import misc.DebugPointMesh;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class CigarShipTest extends SimpleApplication {
  
  private static final Logger logger = getLogger(CigarShipTest.class);
  
  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    CigarShipTest app = new CigarShipTest();
    app.start();
  }
  
  public CigarShipTest() {
    super(
        new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
        new ConstantVerifierState(),
        
        new DebugGridState(cellExtent, false),
        new DebugAxesState(),
        new ChaseCameraState(),
        new LemurState()
    );
  }
  
  @Override
  public void simpleInitApp() {

    float zExtent = cellExtent * 16;
    float xExtent = cellExtent * 8f;
    float yExtent = cellExtent * 4f;

    int numberOfPoints = 14; // The desired number of slices on the ellipse

    Ellipse xz = new EllipseXZ(zExtent, xExtent, numberOfPoints + 2);
    logger.debug("xz slices = {}", xz.points());
    List<Vector3f> xExtents = xz.points().stream().filter(v -> (v.x > 0)).filter(v -> (Math.abs(v.x) > FastMath.ZERO_TOLERANCE)).toList();
    logger.debug("xExtents = {}", xExtents);
    logger.debug("xExtents.size() = {}", xExtents.size());

    Ellipse yz = new EllipseYZ(zExtent, yExtent, numberOfPoints + 2);
    logger.debug("yz slices = {}", yz.points());
    List<Vector3f> yExtents = yz.points().stream().filter(v -> (v.y > 0)).filter(v -> (Math.abs(v.y) > FastMath.ZERO_TOLERANCE)).toList();
    logger.debug("yExtents = {}", yExtents);
    logger.debug("yExtents.size() = {}", yExtents.size());

    if (xExtents.size() != yExtents.size()) {
      throw new RuntimeException("xExtents.size() != yExtents.size()");
    }

    Geometry debugXz = new Geometry("debug-xz", new DebugPointMesh(xExtents));
    debugXz.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debugXz.getMaterial().setFloat("PointSize", 8f);
    debugXz.getMaterial().setColor("Color", ColorRGBA.Yellow);
    rootNode.attachChild(debugXz);

    Geometry debugYz = new Geometry("debug-yz", new DebugPointMesh(yExtents));
    debugYz.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debugYz.getMaterial().setFloat("PointSize", 8f);
    debugYz.getMaterial().setColor("Color", ColorRGBA.Blue);
    rootNode.attachChild(debugYz);

    List<List<Vector3f>> slices = new ArrayList<>(xExtents.size());

    for (int i = 0; i < xExtents.size(); i++) {
      float major = xExtents.get(i).x;
      float minor = yExtents.get(i).y;

      float z = xExtents.get(i).z;

      Ellipse xy = new EllipseXY(major, minor, numberOfPoints);
      List<Vector3f> slice = xy.points();

      slice.forEach(p -> p.z = z);
      slices.add(slice);
    }

    logger.debug("slices.size() = {}", slices.size());

    Geometry debug = new Geometry("debug", new DebugPointMesh(slices.stream().flatMap(List::stream).toArray(Vector3f[]::new)));
    debug.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debug.getMaterial().setFloat("PointSize", 4f);
    rootNode.attachChild(debug);

    List<Face> faces = new ArrayList<>(slices.size() * numberOfPoints);

    for (int i = 0; i < slices.size() - 1; i++) {
      List<Vector3f> current = slices.get(i);
      List<Vector3f> next = slices.get(i + 1);

      for (int j = 0; j < numberOfPoints; j++) {
        Face face = new QuadFace(current.get(j), next.get(j), next.get((j + 1) % numberOfPoints), current.get((j + 1) % numberOfPoints));
        faces.add(face);
      }
    }

    // AI generated internal tube - might be interesting
//    for (int i = 0; i < numberOfPoints; i++) {
//      Face face = new QuadFace(slices.get(slices.size() - 1).get(i), slices.get(0).get(i), slices.get(0).get((i + 1) % numberOfPoints), slices.get(slices.size() - 1).get((i + 1) % numberOfPoints));
//      faces.add(face);
//      face = new QuadFace(slices.get(slices.size() - 1).get(i), slices.get(slices.size() - 2).get(i), slices.get(slices.size() - 2).get((i + 1) % numberOfPoints), slices.get(slices.size() - 1).get((i + 1) % numberOfPoints));
//      faces.add(face);
//    }

    float zFront = xExtents.stream().max((l, r) -> Float.compare(l.z, r.z)).get().z;
    for (int i = 0; i < numberOfPoints; i++) {
      Face face = new TriangleFace(new Vector3f(0, 0, zFront), slices.get(0).get(i), slices.get(0).get((i + 1) % numberOfPoints));
      faces.add(face);
    }

    float zBack = xExtents.stream().min((l, r) -> Float.compare(l.z, r.z)).get().z;
    for (int i = 0; i < numberOfPoints; i++) {
      Face face = new TriangleFace(new Vector3f(0, 0, zBack), slices.get(slices.size() - 1).get((i + 1) % numberOfPoints), slices.get(slices.size() - 1).get(i));
      faces.add(face);
    }

    // flatten top and bottom, sides
    float yThreshold = 0.5f * yExtent;
    float xThreshold = 0.75f * xExtent;

    for (Face face : faces) {
      for (Vector3f v : face.points()) {
        if (Math.abs(v.y) >= (yThreshold)) {
          v.y = yThreshold * Math.signum(v.y);
        }

        if (Math.abs(v.x) >= (xThreshold)) {
          v.x = xThreshold * Math.signum(v.x);
        }
      }
    }

    Material material = new ShowNormalsMaterial(assetManager);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(faces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .toList()));
    hull.setMaterial(material);
    rootNode.attachChild(hull);
  }

}
