package mesh.face;

import com.jme3.math.Triangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import java.util.Collection;
import java.util.List;

public interface Face {
  
  List<Triangle> triangles();

  List<Vector3f> points();

  List<Vector3f> normals();

  int triangleCount();

  List<Vector2f> texCoords();
}
