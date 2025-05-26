package ship.sboat;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import jme3utilities.MyMesh;

// adapted from https://stackoverflow.com/questions/68696415/three-js-how-to-make-a-fully-beveled-cube
public class BeveledBox extends Mesh {
  
  public BeveledBox(float xExtent, float yExtent, float zExtent, float bevel) {
    setMode(Mode.Triangles);
    
    float hw = xExtent; // * 0.5f;
    float hh = yExtent; // * 0.5f;
    float hd = zExtent; // * 0.5f;
    
    float[] vertices = {
        // px
        hw, hh - bevel, -hd + bevel,   // 0
        hw, -hh + bevel, -hd + bevel,  // 1
        hw, -hh + bevel, hd - bevel,   // 2
        hw, hh - bevel, hd - bevel,    // 3
        
        // pz
        hw - bevel, hh - bevel, hd,    // 4
        hw - bevel, -hh + bevel, hd,   // 5
        -hw + bevel, -hh + bevel, hd,  // 6
        -hw + bevel, hh - bevel, hd,   // 7
        
        // nx
        -hw, hh - bevel, hd - bevel,   // 8
        -hw, -hh + bevel, hd - bevel,  // 9
        -hw, -hh + bevel, -hd + bevel, // 10
        -hw, hh - bevel, -hd + bevel,  // 11
        
        // nz
        -hw + bevel, hh - bevel, -hd,  // 12
        -hw + bevel, -hh + bevel, -hd, // 13
        hw - bevel, -hh + bevel, -hd,  // 14
        hw - bevel, hh - bevel, -hd,   // 15
        
        // py
        hw - bevel, hh, -hd + bevel,   // 16
        hw - bevel, hh, hd - bevel,    // 17
        -hw + bevel, hh, hd - bevel,   // 18
        -hw + bevel, hh, -hd + bevel,  // 19
        
        // ny
        hw - bevel, -hh, -hd + bevel,  // 20
        hw - bevel, -hh, hd - bevel,   // 21
        -hw + bevel, -hh, hd - bevel,  // 22
        -hw + bevel, -hh, -hd + bevel  // 23
    };
    setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
    
    int[] indices = {
        0, 2, 1, 3, 2, 0,
        4, 6, 5, 7, 6, 4,
        8, 10, 9, 11, 10, 8,
        12, 14, 13, 15, 14, 12,
        16, 18, 17, 19, 18, 16,
        20, 21, 22, 23, 20, 22,
        
        // link the sides
        3, 5, 2, 4, 5, 3,
        7, 9, 6, 8, 9, 7,
        11, 13, 10, 12, 13, 11,
        15, 1, 14, 0, 1, 15,
        
        // link the lids
        // top
        16, 3, 0, 17, 3, 16,
        17, 7, 4, 18, 7, 17,
        18, 11, 8, 19, 11, 18,
        19, 15, 12, 16, 15, 19,
        // bottom
        1, 21, 20, 2, 21, 1,
        5, 22, 21, 6, 22, 5,
        9, 23, 22, 10, 23, 9,
        13, 20, 23, 14, 20, 13,
        
        // corners
        // top
        3, 17, 4,
        7, 18, 8,
        11, 19, 12,
        15, 16, 0,
        // bottom
        2, 5, 21,
        6, 9, 22,
        10, 13, 23,
        14, 1, 20
    };
    setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indices));
    
//    MyMesh.generateFacetNormals(this);
    
    updateBound();
    updateCounts();
  }
}
