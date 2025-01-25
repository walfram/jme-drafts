package galaxy.ship.model;

public class ShipDesign {

  private final double engines;
  private final int weapons;
  private final double firepower;
  private final double shields;
  private final double cargo;

  public ShipDesign(double engines, int weapons, double firepower, double shields, double cargo) {
    if (engines == 0 && weapons == 0 && firepower == 0 && shields == 0 && cargo == 0) {
      throw new IllegalArgumentException("All arguments cannot be zero!");
    }

    this.engines = new ZeroOrMoreThen<>(engines, 1).value();

    if (weapons != 0 && firepower == 0) {
      throw new IllegalArgumentException();
    }

    if (weapons == 0 && firepower != 0) {
      throw new IllegalArgumentException();
    }

    this.weapons = new ZeroOrMoreThen<>(weapons, 1).value();
    this.firepower = new ZeroOrMoreThen<>(firepower, 1).value();
    this.shields = new ZeroOrMoreThen<>(shields, 1).value();
    this.cargo = new ZeroOrMoreThen<>(cargo, 1).value();
  }
}
