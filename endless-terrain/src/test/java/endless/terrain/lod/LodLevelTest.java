package endless.terrain.lod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import endless.terrain.heightmap.ReIndexedVertices;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LodLevelTest {

  private static final Logger logger = LoggerFactory.getLogger(LodLevelTest.class);

  ReIndexedVertices reindex = new ReIndexedVertices();
  
  @Test
  // TODO remove magic numbers
  void test_reindex_8x8() {
    List<Integer> by2 = reindex.reindex(8, 2);
    assertEquals(4 * 4 * 3 * 2, by2.size());
    
    List<Integer> by4 = reindex.reindex(8, 4);
    assertEquals(2 * 2 * 3 * 2, by4.size());
    
    List<Integer> by8 = reindex.reindex(8, 8);
    assertEquals(3 * 2, by8.size());
  }
  
  @Test
  // TODO remove magic numbers
  void test_reindex_4x4() {
    List<Integer> by2 = reindex.reindex(4, 2);
    assertEquals(4 * 3 * 2, by2.size());
    logger.debug("by2 = {}", by2);
    
    List<Integer> by4 = reindex.reindex(4, 4);
    assertEquals(3 * 2, by4.size());
    logger.debug("by4 = {}", by4);
  }
  
}
