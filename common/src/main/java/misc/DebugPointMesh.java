package misc;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.util.List;

public class DebugPointMesh extends Mesh {

  public DebugPointMesh(List<Vector3f> points) {
    setMode(Mode.Points);

    FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(points.toArray(Vector3f[]::new));
    setBuffer(Type.Position, 3, positionBuffer);

    updateBound();
    updateCounts();
  }

}
