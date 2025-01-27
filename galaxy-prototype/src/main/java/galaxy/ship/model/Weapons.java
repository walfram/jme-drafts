package galaxy.ship.model;

public record Weapons(int guns, double caliber) implements Weighted, ShipComponent {

  @Override
  public double weight() {
    if (guns == 1)
      return caliber;
    
    return caliber + 0.5 * caliber * (guns - 1);
  }
}
