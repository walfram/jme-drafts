package mesh;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import mesh.face.Face;
import mesh.face.TriangleFace;

import java.util.ArrayList;
import java.util.List;

public class CustomCylinder extends FlatShadedMesh {
  
  public CustomCylinder(
      float frontRadius, float backRadius, float height,
      int numRadialSamples,
      boolean generateFrontCircle, boolean generateBackCircle,
      float bevelFactor
  ) {
    super(trianglesOf(frontRadius, backRadius, height, numRadialSamples, generateFrontCircle, generateBackCircle, bevelFactor));
  }
  
  private static List<Face> trianglesOf(
      float frontRadius, float backRadius, float height,
      int numRadialSamples,
      boolean generateFrontCircle, boolean generateBackCircle,
      float bevelFactor) {
    List<Face> triangles = new ArrayList<>();
    float halfHeight = height / 2f;
    
    // Angles for radial segments
    float[] angles = new float[numRadialSamples];
    float theta = FastMath.TWO_PI / numRadialSamples;
    
    for (int i = 0; i < numRadialSamples; i++) {
      if (bevelFactor > 0) {
        if (i % 2 == 0) {
          angles[i] = theta * i + theta * bevelFactor;
        } else {
          angles[i] = theta * i + theta * (1f - bevelFactor);
        }
      } else {
        angles[i] = theta * i;
      }
    }
    
    // Points on front (+Z) and back (-Z) circles
    Vector3f[] frontCircle = new Vector3f[numRadialSamples];
    Vector3f[] backCircle = new Vector3f[numRadialSamples];
    
    for (int i = 0; i < numRadialSamples; i++) {
      float cos = (float) Math.cos(angles[i]);
      float sin = (float) Math.sin(angles[i]);
      
      frontCircle[i] = new Vector3f(frontRadius * cos, frontRadius * sin, halfHeight);
      backCircle[i] = new Vector3f(backRadius * cos, backRadius * sin, -halfHeight);
    }
    
    Vector3f frontCenter = new Vector3f(0f, 0f, halfHeight);
    Vector3f backCenter = new Vector3f(0f, 0f, -halfHeight);
    
    // Back cap (-Z)
    if (generateBackCircle) {
      for (int i = 0; i < numRadialSamples; i++) {
        int next = (i + 1) % numRadialSamples;
        triangles.add(new TriangleFace(backCenter, backCircle[next], backCircle[i]));
      }
    }
    
    // Front cap (+Z)
    if (generateFrontCircle) {
      for (int i = 0; i < numRadialSamples; i++) {
        int next = (i + 1) % numRadialSamples;
        triangles.add(new TriangleFace(frontCenter, frontCircle[i], frontCircle[next]));
      }
    }
    
    // Side triangles (connect back to front)
    for (int i = 0; i < numRadialSamples; i++) {
      int next = (i + 1) % numRadialSamples;
      
      Vector3f b0 = backCircle[i];
      Vector3f b1 = backCircle[next];
      Vector3f f0 = frontCircle[i];
      Vector3f f1 = frontCircle[next];
      
      if (frontRadius == 0f) {
        // Cone tip at front
        triangles.add(new TriangleFace(b0, b1, frontCenter));
      } else if (backRadius == 0f) {
        // Cone tip at back
        triangles.add(new TriangleFace(backCenter, f1, f0));
      } else {
        // Frustum/cylinder
        triangles.add(new TriangleFace(b0, b1, f0));
        triangles.add(new TriangleFace(f0, b1, f1));
      }
    }
    
    return triangles;
  }
  
}
