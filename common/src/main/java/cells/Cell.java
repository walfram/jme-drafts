package cells;

import com.jme3.math.Vector3f;
import java.util.Set;

public interface Cell {

  Vector3f translation();

  Set<Cell> slice(int slices);

  Set<Cell> neighboursAll();

  Set<Cell> neighboursAdjacent();
  
  <T extends CellOffset> Cell relative(T offset);

  float extent();
}
