package wfc;

import cells.Side;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import socket.Socket;

public class Module {

  private final Spatial spatial;
  private final Quaternion rotation;
  private final SocketsOf sockets;
  
  public Module(Spatial spatial, Quaternion rotation) {
    this.spatial = spatial;
    this.rotation = rotation;
    this.sockets = new SocketsOf(spatial, rotation);
  }

  public boolean matches(Module other, Side side) {
    Socket socket = sockets.forSide(side);
    Socket that = other.sockets.forSide(side.opposite());
    return socket.matches(that);
  }
}
