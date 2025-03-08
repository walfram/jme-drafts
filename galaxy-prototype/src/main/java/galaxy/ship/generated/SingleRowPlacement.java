package galaxy.ship.generated;

import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SingleRowPlacement implements BatchPlacement {
  public SingleRowPlacement(int batches) {
  }
  
  @Override
  public List<List<BatchPosition>> arrange(int batches) {
    List<List<BatchPosition>> arranged = new ArrayList<>();
    
    for (int z = 0; z < batches; z++) {
      arranged.add(List.of((z1, cellExtent) -> new Vector3f(0, 0, z1 * 2f * cellExtent)));
    }
    
    return arranged;
  }
}
