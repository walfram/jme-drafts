package galaxy.domain.ship;

public record Drives(double size) implements Weighted, ShipComponent {

  @Override
  public double weight() {
    return size;
  }
}
