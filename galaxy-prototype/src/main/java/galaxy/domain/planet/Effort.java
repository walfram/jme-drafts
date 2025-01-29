package galaxy.domain.planet;

public record Effort(Population population, Industry industry) {

  public double value() {
    return industry().value() * 0.75 + population.value() * 0.25;
  }
}
