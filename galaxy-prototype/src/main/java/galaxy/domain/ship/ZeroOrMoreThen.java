package galaxy.domain.ship;

public class ZeroOrMoreThen<T extends Number> {

  private final T value;

  public ZeroOrMoreThen(T value, double threshold) {
    if (value.doubleValue() > 0 && value.doubleValue() < threshold) {
      throw new IllegalArgumentException("value must be 0 or equals or more then %s".formatted(threshold));
    }
    this.value = value;
  }

  public T value() {
    return value;
  }
}
