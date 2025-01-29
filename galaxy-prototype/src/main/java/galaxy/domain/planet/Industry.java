package galaxy.domain.planet;

public record Industry(double value, double max) {

  public Industry {
    if (value > max) {
      throw new IllegalArgumentException("Industry value %.02f cannot be more then %.02f".formatted(value, max));
    }
  }
}
