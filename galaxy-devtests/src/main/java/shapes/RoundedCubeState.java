package shapes;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import jme3utilities.mesh.PointMesh;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import misc.DebugPointMesh;

import java.util.Arrays;

public class RoundedCubeState extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public RoundedCubeState(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    float[] points = {
        // from (0,0,0)
        0.125f, 0f,     0f,
        0f,     0.125f, 0f,
        0f,     0f,     0.125f,
        
        // from (0,0,1)
        0.125f, 0f,     1f,
        0f,     0.125f, 1f,
        0f,     0f,     0.875f,
        
        // from (0,1,0)
        0.125f, 1f,     0f,
        0f,     0.875f, 0f,
        0f,     1f,     0.125f,
        
        // from (0,1,1)
        0.125f, 1f,     1f,
        0f,     0.875f, 1f,
        0f,     1f,     0.875f,
        
        // from (1,0,0)
        0.875f, 0f,     0f,
        1f,     0.125f, 0f,
        1f,     0f,     0.125f,
        
        // from (1,0,1)
        0.875f, 0f,     1f,
        1f,     0.125f, 1f,
        1f,     0f,     0.875f,
        
        // from (1,1,0)
        0.875f, 1f,     0f,
        1f,     0.875f, 0f,
        1f,     1f,     0.125f,
        
        // from (1,1,1)
        0.875f, 1f,     1f,
        1f,     0.875f, 1f,
        1f,     1f,     0.875f
    };
    
    Mesh debugMesh = new DebugPointMesh(points);
    
    Geometry debugPoints = new Geometry("cube", debugMesh);
    debugPoints.setMaterial(new Material(app.getAssetManager(), Materials.UNSHADED));
    debugPoints.getMaterial().setColor("Color", ColorRGBA.Yellow);
    debugPoints.getMaterial().setFloat("PointSize", 4f);
    
    debugPoints.scale(10f);
    
    scene.attachChild(debugPoints);
    
    int[] indices = {
        // corner at (0,0,0) → vertices 0,1,2
        0, 1, 2,
        // corner at (0,0,1) → vertices 3,4,5
        3, 4, 5,
        // corner at (0,1,0) → vertices 6,7,8
        6, 7, 8,
        // corner at (0,1,1) → vertices 9,10,11
        9, 10, 11,
        // corner at (1,0,0) → vertices 12,13,14
        12, 13, 14,
        // corner at (1,0,1) → vertices 15,16,17
        15, 16, 17,
        // corner at (1,1,0) → vertices 18,19,20
        18, 19, 20,
        // corner at (1,1,1) → vertices 21,22,23
        21, 22, 23,
    };
   
    Mesh source = new Mesh();
    source.setMode(Mesh.Mode.Triangles);
    source.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(points));
    source.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indices));
    source.updateBound();
    source.updateCounts();
    
    Geometry geometry = new Geometry("cube", new FlatShadedMesh(source));
    geometry.setMaterial(new ShowNormalsMaterial(app.getAssetManager()));
    geometry.scale(10f);
    scene.attachChild(geometry);
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
