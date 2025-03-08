package galaxy.domain.ship;

public class ShipDesign implements Weighted, Producible {

  private final Drives drives;
  private final Weapons weapons;
  private final Shields shields;
  private final Cargo cargo;

  public ShipDesign(double drives, int guns, double caliber, double shields, double cargo) {
    if (drives == 0 && guns == 0 && caliber == 0 && shields == 0 && cargo == 0) {
      throw new IllegalArgumentException("All arguments cannot be zero!");
    }

    this.drives = new Drives(new ZeroOrMoreThen<>(drives, 1).value());

    if (guns != 0 && caliber == 0) {
      throw new IllegalArgumentException("");
    }

    if (guns == 0 && caliber != 0) {
      throw new IllegalArgumentException();
    }

    this.weapons = new Weapons(new ZeroOrMoreThen<>(guns, 1).value(), new ZeroOrMoreThen<>(caliber, 1).value());
    this.shields = new Shields(new ZeroOrMoreThen<>(shields, 1).value());
    this.cargo = new Cargo(new ZeroOrMoreThen<>(cargo, 1).value());
  }

  public static ShipDesign minimal() {
    return new ShipDesign(1, 0, 0, 0, 0);
  }

  @Override
  public String toString() {
    return "ShipDesign{%s,%s,%s,%s}".formatted(drives, weapons, shields, cargo);
  }

  @Override
  public double weight() {
    return drives.weight() + weapons.weight() + shields.weight() + cargo.weight();
  }

  @Override
  public double requiredMaterialAmount() {
    return weight() * 10.0;
  }

  public Drives drives() {
    return drives;
  }

  public Weapons weapons() {
    return weapons;
  }

  public Shields shields() {
    return shields;
  }

  public Cargo cargo() {
    return cargo;
  }
  
  public boolean hasCargo() {
    return cargo().volume() > 0;
  }
}
