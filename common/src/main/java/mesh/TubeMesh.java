package mesh;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import mesh.face.QuadFace;

import java.util.ArrayList;
import java.util.List;

public class TubeMesh extends FlatShadedMesh {
  public TubeMesh(int radialSamples, float ri, float ro, float height) {
    super(trianglesOf(radialSamples, ri, ro, height));
  }
  
  private static List<Triangle> trianglesOf(int radialSamples, float ri, float ro, float height) {
    float angleDelta = FastMath.TWO_PI / radialSamples;
    
    List<QuadFace> quadFaces = new ArrayList<>(radialSamples * 4);
    Quaternion rotation = new Quaternion();
    
    for (int i = 0; i < radialSamples; i++) {
      rotation.fromAngleNormalAxis(i * angleDelta, Vector3f.UNIT_Z);
      Vector3f v0 = rotation.mult(new Vector3f(ri, 0, height));
      Vector3f v0Neg = rotation.mult(new Vector3f(ri, 0, -height));
      Vector3f v1 = rotation.mult(new Vector3f(ro, 0, height));
      Vector3f v1Neg = rotation.mult(new Vector3f(ro, 0, -height));
      
      rotation.fromAngleNormalAxis((i + 1) * angleDelta, Vector3f.UNIT_Z);
      Vector3f v2 = rotation.mult(new Vector3f(ro, 0, height));
      Vector3f v2Neg = rotation.mult(new Vector3f(ro, 0, -height));
      Vector3f v3 = rotation.mult(new Vector3f(ri, 0, height));
      Vector3f v3Neg = rotation.mult(new Vector3f(ri, 0, -height));
      
      QuadFace front = new QuadFace(v0, v1, v2, v3);
      QuadFace back = new QuadFace(v0Neg, v3Neg, v2Neg, v1Neg);
      QuadFace outer = new QuadFace(v1, v1Neg, v2Neg, v2);
      QuadFace inner = new QuadFace(v0Neg, v0, v3, v3Neg);
      
      quadFaces.add(front);
      quadFaces.add(back);
      quadFaces.add(outer);
      quadFaces.add(inner);
    }

    return quadFaces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .toList();
  }
  
}
