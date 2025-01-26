package galaxy.ship.model;

public class ShipDesign implements Weighted, Producible {

  private final Drives drives;
  private final Weapons weapons;
  private final Shields shields;
  private final Cargo cargo;

  public ShipDesign(double drives, int weapons, double firepower, double shields, double cargo) {
    if (drives == 0 && weapons == 0 && firepower == 0 && shields == 0 && cargo == 0) {
      throw new IllegalArgumentException("All arguments cannot be zero!");
    }

    this.drives = new Drives(new ZeroOrMoreThen<>(drives, 1).value());

    if (weapons != 0 && firepower == 0) {
      throw new IllegalArgumentException();
    }

    if (weapons == 0 && firepower != 0) {
      throw new IllegalArgumentException();
    }

    this.weapons = new Weapons(new ZeroOrMoreThen<>(weapons, 1).value(), new ZeroOrMoreThen<>(firepower, 1).value());
    this.shields = new Shields(new ZeroOrMoreThen<>(shields, 1).value());
    this.cargo = new Cargo(new ZeroOrMoreThen<>(cargo, 1).value());
  }

  @Override
  public double weight() {
    return drives.weight() + weapons.weight() + shields.weight() + cargo.weight();
  }

  @Override
  public double requiredMaterialAmount() {
    return weight() * 10.0;
  }
}
