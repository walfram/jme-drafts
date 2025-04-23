package galaxy.containers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerPackingTest {

  private static final Logger logger = LoggerFactory.getLogger(ContainerPackingTest.class);
  
  private final int[] containers = {
      // 10 to 200 (step 10)
      10, 20, 30, 40, 50, 60, 70, 80, 90, 100,
      110, 120, 130, 140, 150, 160, 170, 180, 190, 200,
      
      // 250 to 1000 (step 50)
      250, 300, 350, 400, 450, 500, 550, 600, 650, 700,
      750, 800, 850, 900, 950, 1000,
      
      // 1100 to 9000 (step 100)
      1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000,
      2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000,
      3100, 3200, 3300, 3400, 3500, 3600, 3700, 3800, 3900, 4000,
      4100, 4200, 4300, 4400, 4500, 4600, 4700, 4800, 4900, 5000,
      5100, 5200, 5300, 5400, 5500, 5600, 5700, 5800, 5900, 6000,
      6100, 6200, 6300, 6400, 6500, 6600, 6700, 6800, 6900, 7000,
      7100, 7200, 7300, 7400, 7500, 7600, 7700, 7800, 7900, 8000,
      8100, 8200, 8300, 8400, 8500, 8600, 8700, 8800, 8900, 9000
  };
  
  double findMinimumK(int n, double rx, double ry, double rz) {
    double k = 0.1;
    double step = 0.01;
    while (true) {
      int nx = (int) Math.floor(rx * k);
      int ny = (int) Math.floor(ry * k);
      int nz = (int) Math.floor(rz * k);
      if (nx * ny * nz >= n) {
        return k;
      }
      k += step;
    }
  }
  
  @Test
  void should_split_containers() {
    int rx = 3, ry = 1, rz = 5;
    
    for (int n : containers) {
      double k = findMinimumK(n, rx, ry, rz);
      int nx = (int) Math.floor(rx * k);
      int ny = (int) Math.floor(ry * k);
      int nz = (int) Math.floor(rz * k);
      int capacity = nx * ny * nz;
      
      logger.debug("Cubes: {} | k: {} | Dimensions: {} x {} x {} | Capacity: {}", n, k, rx * k, ry * k, rz * k, capacity);
    }
  }
  
}
