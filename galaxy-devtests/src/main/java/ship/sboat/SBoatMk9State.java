package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

import java.util.ArrayList;
import java.util.List;

public class SBoatMk9State extends BaseAppState {
  
  private static final float cellExtent = 4f;
  private final Node scene = new Node("scene");
  
  public SBoatMk9State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
    
    List<Triangle> triangles = generateCylinder(cellExtent, 3f * cellExtent, 2f * 3f * cellExtent, 8, true, false, 0.2f);
    
    Mesh mesh = new FlatShadedMesh(triangles);
    Geometry geometry = new Geometry("hull", mesh);
    geometry.setMaterial(material);
    scene.attachChild(geometry);
  }
  
  private List<Triangle> generateCylinder(
      float frontRadius, float backRadius, float height,
      int numRadialSamples,
      boolean generateFrontCircle, boolean generateBackCircle,
      float adjustFactor
  ) {
    List<Triangle> triangles = new ArrayList<>();
    float halfHeight = height / 2f;
    
    // Angles for radial segments
    float[] angles = new float[numRadialSamples];
    float theta = FastMath.TWO_PI / numRadialSamples;
    
    for (int i = 0; i < numRadialSamples; i++) {
      if (adjustFactor > 0) {
        if (i % 2 == 0) {
          angles[i] = theta * i + theta * adjustFactor;
        } else {
          angles[i] = theta * i + theta * (1f - adjustFactor);
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
        triangles.add(new Triangle(backCenter, backCircle[next], backCircle[i]));
      }
    }
    
    // Front cap (+Z)
    if (generateFrontCircle) {
      for (int i = 0; i < numRadialSamples; i++) {
        int next = (i + 1) % numRadialSamples;
        triangles.add(new Triangle(frontCenter, frontCircle[i], frontCircle[next]));
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
        triangles.add(new Triangle(b0, b1, frontCenter));
      } else if (backRadius == 0f) {
        // Cone tip at back
        triangles.add(new Triangle(backCenter, f1, f0));
      } else {
        // Frustum/cylinder
        triangles.add(new Triangle(b0, b1, f0));
        triangles.add(new Triangle(f0, b1, f1));
      }
    }
    
    return triangles;
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
