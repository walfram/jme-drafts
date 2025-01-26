package galaxy.ship.model;

public record Cargo(double volume) implements Weighted {

  @Override
  public double weight() {
    return volume;
  }
}
