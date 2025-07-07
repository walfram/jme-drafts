package mesh.face;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;

import java.util.List;

public interface Face {
  
  List<Triangle> triangles();

  List<Vector3f> points();
}
