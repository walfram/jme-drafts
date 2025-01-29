package galaxy.domain.planet;

public record Planet(Size size, Resources resources, Population population, Industry industry) {

  public static Planet homeWorld() {
    return new Planet(new Size(1000), new Resources(10), new Population(1000, 1000), new Industry(1000, 1000));
  }

  public static Planet daughterWorld() {
    return new Planet(new Size(500), new Resources(10), new Population(500, 500), new Industry(500, 500));
  }

  public Effort effort() {
    return new Effort(population, industry);
  }
}
