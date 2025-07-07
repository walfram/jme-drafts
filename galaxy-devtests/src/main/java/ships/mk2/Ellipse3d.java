package ships.mk2;

import com.jme3.math.FastMath;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import math.ellipse.Ellipse;
import math.ellipse.EllipseXY;
import math.ellipse.EllipseXZ;
import math.ellipse.EllipseYZ;
import mesh.FlatShadedMesh;
import mesh.face.Face;
import mesh.face.SymmetricQuadFace;
import mesh.face.TriangleFace;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class Ellipse3d extends FlatShadedMesh {
  private static final Logger logger = getLogger(Ellipse3d.class);

  public Ellipse3d(float xExtent, float yExtent, float zExtent, int numberOfFrames, int numberOfPoints) {
    super(ellipse3dTriangles(xExtent, yExtent, zExtent, numberOfFrames, numberOfPoints));
  }

  private static List<Triangle> ellipse3dTriangles(float xExtent, float yExtent, float zExtent, int numberOfFrames, int numberOfPoints) {
    Ellipse xz = new EllipseXZ(zExtent, xExtent, numberOfFrames);
    logger.debug("xz slices = {}", xz.points());
    List<Vector3f> xExtents = xz.points().stream().filter(v -> (v.x > 0)).filter(v -> (Math.abs(v.x) > FastMath.ZERO_TOLERANCE)).toList();
    logger.debug("xExtents = {}", xExtents);
    logger.debug("xExtents.size() = {}", xExtents.size());

    Ellipse yz = new EllipseYZ(zExtent, yExtent, numberOfFrames);
    logger.debug("yz slices = {}", yz.points());
    List<Vector3f> yExtents = yz.points().stream().filter(v -> (v.y > 0)).filter(v -> (Math.abs(v.y) > FastMath.ZERO_TOLERANCE)).toList();
    logger.debug("yExtents = {}", yExtents);
    logger.debug("yExtents.size() = {}", yExtents.size());

    if (xExtents.size() != yExtents.size()) {
      throw new RuntimeException("xExtents.size() != yExtents.size()");
    }

    List<List<Vector3f>> slices = new ArrayList<>(xExtents.size());

    for (int i = 0; i < xExtents.size(); i++) {
      boolean isLast = i == xExtents.size() - 1;

      float major = xExtents.get(i).x;
      float minor = yExtents.get(i).y;

      float z = xExtents.get(i).z;

      Ellipse xy = new EllipseXY(isLast ? minor : major, minor, numberOfPoints);

      List<Vector3f> slice = xy.points();

      slice.forEach(p -> p.z = z);
      slices.add(slice);
    }

    logger.debug("slices.size() = {}", slices.size());

    // TODO check faces size
    List<Face> faces = new ArrayList<>(slices.size() * numberOfPoints);

    for (int i = 0; i < slices.size() - 1; i++) {
      List<Vector3f> current = slices.get(i);
      List<Vector3f> next = slices.get(i + 1);

      for (int j = 0; j < numberOfPoints; j++) {
        Face face = new SymmetricQuadFace(current.get(j), next.get(j), next.get((j + 1) % numberOfPoints), current.get((j + 1) % numberOfPoints));
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
//    float yThreshold = 0.5f * yExtent;
//    float xThreshold = 0.75f * xExtent;
//
//    for (Face face : faces) {
//      for (Vector3f v : face.points()) {
//        if (Math.abs(v.y) >= (yThreshold)) {
//          v.y = yThreshold * Math.signum(v.y);
//        }
//
//        if (Math.abs(v.x) >= (xThreshold)) {
//          v.x = xThreshold * Math.signum(v.x);
//        }
//      }
//    }

    return faces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .toList();
  }
}
