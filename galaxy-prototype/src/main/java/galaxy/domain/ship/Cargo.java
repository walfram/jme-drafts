package galaxy.domain.ship;

public record Cargo(double volume) implements Weighted, ShipComponent {

  @Override
  public double weight() {
    return volume;
  }
}
