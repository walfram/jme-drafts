package endless.terrain;

import java.util.HashSet;
import java.util.Set;

public class Difference<T> {

  private final Set<T> base;
  private final Set<T> constrainBy;

  public Difference(Set<T> base, Set<T> constrainBy) {
    this.base = base;
    this.constrainBy = constrainBy;
  }

  public Set<T> asSet() {
    Set<T> result = new HashSet<>(base);
    result.removeAll(constrainBy);
    return result;
  }
}
