package galaxy.planet;

public record Population(double value, double max) {

  public Population {
    if (value > max)
      throw new IllegalArgumentException("Population value %.02f cannot be more then %.02f".formatted(value, max));
  }
  
}
