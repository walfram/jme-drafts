package mesh;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class IrregularCylinder extends FlatShadedMesh {

  public IrregularCylinder(float height, float radius, float thetaMajor, float thetaMinor) {
    this(height, radius, radius, thetaMajor, thetaMinor, true, true);
  }
  
  public IrregularCylinder(float height, float frontRadius, float backRadius, float thetaMajor, float thetaMinor) {
    this(height, frontRadius, backRadius, thetaMajor, thetaMinor, true, true);
  }
  
  public IrregularCylinder(float height, float frontRadius, float backRadius, float thetaMajor, float thetaMinor, boolean frontClosed, boolean backClosed) {
    super(trianglesOf(height, frontRadius, backRadius, thetaMajor, thetaMinor, frontClosed, backClosed));
  }
  
  private static List<Triangle> trianglesOf(float height, float frontRadius, float backRadius, float thetaMajor, float thetaMinor, boolean frontClosed, boolean backClosed) {
    Vector3f frontOffset = new Vector3f(0, 0, 0.5f * height);
    Vector3f backOffset = new Vector3f(0, 0, -0.5f * height);
    
    Vector3f frontOrigin = new Vector3f(frontRadius, 0, 0);
    Vector3f backOrigin = new Vector3f(backRadius, 0, 0);
    
    float initialAngle = (thetaMinor + thetaMajor) > FastMath.QUARTER_PI ? -0.5f * thetaMinor + 30f * FastMath.DEG_TO_RAD : 0.5f * thetaMinor;
    
    Quaternion initial = new Quaternion().fromAngleAxis(initialAngle, Vector3f.UNIT_Z);
    initial.multLocal(frontOrigin);
    initial.multLocal(backOrigin);
    
    Quaternion minor = new Quaternion().fromAngleAxis(thetaMinor, Vector3f.UNIT_Z);
    Quaternion major = new Quaternion().fromAngleAxis(thetaMajor, Vector3f.UNIT_Z);
    
    int faceCount = 2 * (int) (FastMath.TWO_PI / (thetaMajor + thetaMinor));
    
    List<Vector3f> frontPoints = new ArrayList<>(faceCount);
    List<Vector3f> backPoints = new ArrayList<>(faceCount);
    
    // 3 - front + back + side -> max number of faces
    List<Face> faces = new ArrayList<>(3 * faceCount);
    
    for (int i = 0; i < faceCount; i++) {
      if (i % 2 == 0) {
        minor.multLocal(frontOrigin);
        minor.multLocal(backOrigin);
      } else {
        major.multLocal(frontOrigin);
        major.multLocal(backOrigin);
      }
      
      frontPoints.add(new Vector3f(frontOrigin).add(frontOffset));
      backPoints.add(new Vector3f(backOrigin).add(backOffset));
    }
    
    if (frontClosed) {
      for (int i = 0; i < faceCount; i++) {
        Face face = new TriangleFace(new Vector3f(frontOffset), frontPoints.get(i), frontPoints.get((i + 1) % faceCount));
        faces.add(face);
      }
    }
    
    if (backClosed) {
      for (int i = 0; i < faceCount; i++) {
        Face face = new TriangleFace(new Vector3f(backOffset), backPoints.get((i + 1) % faceCount), backPoints.get(i));
        faces.add(face);
      }
    }
    
    for (int i = 0; i < faceCount; i++) {
      Face face = new QuadFace(frontPoints.get(i), backPoints.get(i), backPoints.get((i + 1) % faceCount), frontPoints.get((i + 1) % faceCount));
      faces.add(face);
    }
    
    return faces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .toList();
  }
  
}
