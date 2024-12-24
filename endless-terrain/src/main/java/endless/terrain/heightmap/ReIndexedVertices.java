package endless.terrain.heightmap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReIndexedVertices implements BiFunction<Integer, Integer, List<Integer>> {

  private static final Logger logger = LoggerFactory.getLogger(ReIndexedVertices.class);
  
  @Override
  public List<Integer> apply(Integer baseQuads, Integer partition) {
    int size = baseQuads / partition;
    logger.debug("base quads = {}, partitioned quads (size) = {}, partition = {}", baseQuads, size, partition);

    int indexSize = size * size * 2 * 3;
    logger.debug("quad index size = {}", indexSize);

    List<Integer> indices = new ArrayList<>(indexSize);

    for (int row = 0; row < size; row++) {
      logger.debug("row = {}", row);
      for (int col = 0; col < size; col++) {
        int q0 = row * baseQuads * partition + col * partition;
        int q1 = q0 + (partition - 1) * baseQuads + partition - 1;
        int q2 = q0 + partition - 1;

        indices.addAll(List.of(q0, q1, q2));

        int q3 = q0 + (partition - 1) * baseQuads;
        logger.debug("\tcol = {}, (q0={},q1={},q2={}) (q0={},q3={},q1={})", col, q0, q1, q2, q0, q3, q1);

        indices.addAll(List.of(q0, q3, q1));

        // TODO quad index to triangle index
      }
    }

    return indices;
  }
}
