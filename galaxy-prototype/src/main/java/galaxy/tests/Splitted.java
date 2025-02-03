package galaxy.tests;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Splitted {

  private final int containers;
  private final int rows;

  public Splitted(int containers, int rows) {
    this.containers = containers;
    this.rows = rows;
  }

  public List<Integer> split() {
    int baseSize = containers / rows;
    int extra = containers % rows;

    List<Integer> list = IntStream.range(0, rows).map(idx -> baseSize).boxed().collect(toCollection(ArrayList::new));

    int left = 0;
    int right = list.size() - 1;
    boolean fromStart = true;
    
    while (extra > 0) {
      if (fromStart) {
        list.set(left, list.get(left) + 1);
        left++;
      } else {
        list.set(right, list.get(right) + 1);
        right++;
      }
      
      fromStart = !fromStart;
      extra--;
    }
    
    return list;
  }
}
