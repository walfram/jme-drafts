package galaxy.ship.model;

public record Shields(double power) implements Weighted, ShipComponent {

  @Override
  public double weight() {
    return power;
  }
}
