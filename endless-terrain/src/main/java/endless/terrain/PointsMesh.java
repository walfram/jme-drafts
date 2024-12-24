package endless.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.util.List;

public class PointsMesh extends Mesh {

  private final List<Vector3f> points;

  public PointsMesh(List<Vector3f> points) {
    this.points = points;
  }

  public Mesh create() {
    Mesh mesh = new Mesh();
    mesh.setMode(Mode.Points);

    FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(points.toArray(Vector3f[]::new));
    mesh.setBuffer(Type.Position, 3, positionBuffer);
    
    mesh.updateBound();
    mesh.updateCounts();
    
    return mesh;
  }
}
