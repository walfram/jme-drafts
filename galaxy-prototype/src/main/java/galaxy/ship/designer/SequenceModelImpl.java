package galaxy.ship.designer;

import com.simsilica.lemur.SequenceModels.AbstractSequence;

public class SequenceModelImpl extends AbstractSequence<Double> {

  private double value = 0;

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
}
