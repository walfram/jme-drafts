package galaxy.containers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerSplitTest {
  
  private static final Logger logger = LoggerFactory.getLogger(ContainerSplitTest.class);
  
  @Test
  void test_division() {
    for (int i = 1; i < 256; i++) {
      for (int d = 1; d < 10; d++) {
        int rem = i % d;
        
        if (rem == 0) {
          logger.debug("{}/{} rem 0", i, d);
        }
      }
    }
  }
  
  @Test
  void should_split_raw() {
    int containers = 111;
    // int containers = 59;
    
    int baseX = 3;
    int baseY = 2;
    int baseZ = 8;
    
    int baseVolume = baseX * baseY * baseZ;
    logger.debug("base volume = {}", baseVolume);
    
    int batches = containers / baseVolume;
    int remainder = containers % baseVolume;
    
    logger.debug("batches = {}, remainder = {}", batches, remainder);
    
    int half = containers / 2;
    int halfRem = containers % half;
    logger.debug("half = {}, half rem = {}", half, halfRem);
  }
  
}
