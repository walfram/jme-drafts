package galaxy.ship.designer.model;

public class IntegerSequenceImpl extends AbstractSequenceExt<Integer> {

  private int value;
  
  public IntegerSequenceImpl(int initialValue) {
    this.value = initialValue;
  }

  @Override
  public Integer getObject() {
    return value;
  }

  @Override
  public void setObject(Integer o) {
    if (o < 0) {
      value = 0;
      incrementVersion();
    } else {
      value = o;
      incrementVersion();
    }
  }

  @Override
  public Integer getNextObject() {
    return value + 1;
  }

  @Override
  public Integer getPreviousObject() {
    if (value == 0)
      return 0;
    
    return value - 1;
  }

  @Override
  public void updateBy(int delta) {
    setObject(value + delta);
  }
}
