package endless.terrain.heightmap;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReIndexedVertices {

  private static final Logger logger = LoggerFactory.getLogger(ReIndexedVertices.class);
  
  public List<Integer> reindex(int baseQuads, int partition) {
    int size = baseQuads / partition;
    logger.debug("base quads = {}, partitioned quads (size) = {}, partition = {}", baseQuads, size, partition);

    int indexSize = size * size * 2 * 3;
    logger.debug("index size = {}", indexSize);

    List<Integer> indices = new ArrayList<>(indexSize);

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        // q - quad
        int q0 = row * baseQuads * partition + col * partition;
        int q1 = q0 + (partition - 1) * baseQuads + partition - 1;
        int q2 = q0 + partition - 1;

        // t - triangle
        int t0 = q0 * 2;
        int t1 = q1 * 2;
        int t2 = q2 * 2;

        // v - vertex
        int v0 = t0 * 3;
        int v1 = t1 * 3 + 1;
        int v2 = t2 * 3 + 2;
        
        indices.add(v0);
        indices.add(v1);
        indices.add(v2);

        int q3 = q0 + (partition - 1) * baseQuads;

        int t3 = q0 * 2 + 1;
        int t4 = q3 * 2 + 1;
        int t5 = q1 * 2 + 1;
        // logger.debug("\t({}, {}, {}) :: ({}, {}, {})", t0, t1, t2, t3, t4, t5);
        
        int v3 = t3 * 3;
        int v4 = t4 * 3 + 1;
        int v5 = t5 * 3 + 2;
        
        indices.add(v3);
        indices.add(v4);
        indices.add(v5);

        // logger.debug("\tcol = {}, (v0={},v1={},v2={}) (v3={},v4={},v5={})", col, v0, v1, v2, v3, v4, v5);
      }
    }

    return indices;
  }
}
