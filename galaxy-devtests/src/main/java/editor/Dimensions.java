package editor;

import java.util.stream.IntStream;

public class Dimensions {
  public int[] x() {
    return null;
  }
  
  public int[] y() {
    return null;
  }
  
  public int[] z() {
    return null;
  }
  
  public static class Test extends Dimensions {
    @Override
    public int[] x() {
      return sequence(2);
    }
    
    @Override
    public int[] y() {
      return sequence(1);
    }
    
    @Override
    public int[] z() {
      return sequence(-3, 4);
    }
    
    private int[] sequence(int lower, int upper) {
      return IntStream.range(lower, upper + 1).toArray();
    }
    
    private int[] sequence(int n) {
      return IntStream.range(-n, n + 1).toArray();
    }
  }
}
