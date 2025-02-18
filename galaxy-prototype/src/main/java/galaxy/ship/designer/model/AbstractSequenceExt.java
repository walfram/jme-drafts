package galaxy.ship.designer.model;

import com.simsilica.lemur.SequenceModels.AbstractSequence;

public abstract class AbstractSequenceExt<T> extends AbstractSequence<T> {

  public abstract void updateBy(int delta);
  
}
