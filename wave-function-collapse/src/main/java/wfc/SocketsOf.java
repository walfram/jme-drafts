package wfc;

import cells.Side;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Arrays;
import java.util.List;
import jme3utilities.MyMesh;
import socket.Socket;
import socket.Vector3fSocket;

public class SocketsOf {

  private final Spatial spatial;
  private final Quaternion rotation;

  public SocketsOf(Spatial spatial, Quaternion rotation) {
    this.spatial = spatial;
    this.rotation = rotation;
  }

  public Socket forSide(Side side) {
    Vector3f[] vertices = MyMesh.listVertexLocations(spatial, null).toVectorArray();

    List<Vector3f> filtered = Arrays.stream(vertices)
        .map(rotation::mult)
        .filter(v -> side.filter(v, 0.5f))
        .toList();

    return new Vector3fSocket(side, filtered);
  }
}
