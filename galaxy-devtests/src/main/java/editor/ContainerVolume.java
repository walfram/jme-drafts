package editor;

public class ContainerVolume {
  private final int containerCount;
  
  public ContainerVolume(int containerCount) {
    this.containerCount = containerCount;
  }
  
  public Dimensions dimensions() {
    return new Dimensions.Test();
  }
}
