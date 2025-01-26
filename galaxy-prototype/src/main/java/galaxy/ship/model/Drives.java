package galaxy.ship.model;

public record Drives(double size) implements Weighted {

  @Override
  public double weight() {
    return size;
  }
}
