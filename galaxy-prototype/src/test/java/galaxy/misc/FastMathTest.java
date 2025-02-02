package galaxy.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jme3.math.FastMath;
import org.junit.jupiter.api.Test;

public class FastMathTest {

  @Test
  void check_extrapolateLinear() {
    assertEquals(0.5f, FastMath.unInterpolateLinear(0, -10, 10));
    assertEquals(0.5f, FastMath.unInterpolateLinear(-5, -10, 0));
  }
  
}
