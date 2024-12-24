package endless.terrain.lod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LodLevelTest {

  private static final Logger logger = LoggerFactory.getLogger(LodLevelTest.class);

  // treat quads as dots (points)
  BiFunction<Integer, Integer, List<Integer>> reindex = (baseQuads, partition) -> {
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
  };
  
  @Test
  // TODO remove magic numbers
  void test_reindex_8x8() {
    List<Integer> by2 = reindex.apply(8, 2);
    assertEquals(4 * 4 * 3 * 2, by2.size());
    
    List<Integer> by4 = reindex.apply(8, 4);
    assertEquals(2 * 2 * 3 * 2, by4.size());
    
    List<Integer> by8 = reindex.apply(8, 8);
    assertEquals(3 * 2, by8.size());
  }
  
  @Test
  // TODO remove magic numbers
  void test_reindex_4x4() {
    List<Integer> by2 = reindex.apply(4, 2);
    assertEquals(4 * 3 * 2, by2.size());
    
    List<Integer> by4 = reindex.apply(4, 4);
    assertEquals(3 * 2, by4.size());
  }
  
}
