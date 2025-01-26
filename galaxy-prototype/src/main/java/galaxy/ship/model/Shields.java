package galaxy.ship.model;

public record Shields(double power) implements Weighted {

  @Override
  public double weight() {
    return power;
  }
}
