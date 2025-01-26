package galaxy.ship.model;

public record Weapons(int guns, double firepower) implements Weighted {

  @Override
  public double weight() {
    if (guns == 1)
      return firepower;
    
    return firepower + 0.5 * firepower * (guns - 1);
  }
}
