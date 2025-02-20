package galaxy.ship.designer.model;

public class DoubleSequenceImpl extends AbstractSequenceExt<Double> {

  private double value = 0;

  public DoubleSequenceImpl(double initialValue) {
    this.value = initialValue;
  }

  @Override
  public Double getObject() {
    return value;
  }

  @Override
  public void setObject(Double o) {
    if (o < 0) {
      value = 0;
      incrementVersion();
    } else if (o > 0 && o < 1) {
      value = 0;
      incrementVersion();
    } else {
      value = o;
      incrementVersion();
    }
  }

  @Override
  public Double getNextObject() {
    if (value == 0) {
      return 1.0;
    }

    return value + 0.01;
  }

  @Override
  public Double getPreviousObject() {
    if (value == 1) {
      value = 0.0;
    } else if (value == 0) {
      return 0.0;
    }

    return value - 0.01;
  }

  @Override
  public void updateBy(int delta) {
    setObject(value + delta);
  }
}
