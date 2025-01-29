package galaxy.domain.ship;

public record Shields(double power) implements Weighted, ShipComponent {

  @Override
  public double weight() {
    return power;
  }
}
