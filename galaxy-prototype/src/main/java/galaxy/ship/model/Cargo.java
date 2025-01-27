package galaxy.ship.model;

public record Cargo(double volume) implements Weighted, ShipComponent {

  @Override
  public double weight() {
    return volume;
  }
}
