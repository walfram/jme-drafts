package galaxy.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import galaxy.tests.Splitted;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoSplitTest {

  private static final Logger logger = LoggerFactory.getLogger(CargoSplitTest.class);
  
  @Test
  void test_cargo_split_3() {
    int containers = 4 * 50 + 2;
    int rows = 4;
    
    List<Integer> splitted = new Splitted(containers, rows).split();
    
    assertEquals(4, splitted.size());
    
    assertEquals(51, splitted.get(0));
    assertEquals(50, splitted.get(1));
    assertEquals(50, splitted.get(2));
    assertEquals(51, splitted.get(3));
  }
  
  @Test
  void test_cargo_split_2() {
    int containers = 332;
    int rows = 3;
    
    List<Integer> splitted = new Splitted(containers, rows).split();
    
    assertEquals(3, splitted.size());
    
    assertEquals(111, splitted.get(0));
    assertEquals(110, splitted.get(1));
    assertEquals(111, splitted.get(2));
  }
  
  @Test
  void test_cargo_split() {
    int containers = 332;
    int rows = 4;
    
    List<Integer> splitted = new Splitted(containers, rows).split();
    
    assertEquals(4, splitted.size());
    
    assertEquals(83, splitted.get(0));
    assertEquals(83, splitted.get(1));
    assertEquals(83, splitted.get(2));
    assertEquals(83, splitted.get(3));
  }
  
}
