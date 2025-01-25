package demos;

import cells.Cell;
import com.jme3.math.Vector3f;
import java.util.List;

public class CellVertices {

  private final Cell source;

  public CellVertices(Cell source) {
    this.source = source;
  }

  public List<Vector3f> asList() {
    return List.of(
        source.translation().add(-source.extent(), 0, -source.extent()),
        source.translation().add(source.extent(), 0, -source.extent()),
        source.translation().add(-source.extent(), 0, source.extent()),
        source.translation().add(source.extent(), 0, source.extent())
    );
  }
}
