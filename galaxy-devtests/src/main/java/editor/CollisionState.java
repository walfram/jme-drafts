package editor;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import java.util.Optional;

public class CollisionState extends BaseAppState {
  
  private final BoundingBox collidable =
      new BoundingBox(new Vector3f(), Float.POSITIVE_INFINITY, 0, Float.POSITIVE_INFINITY);
  
  @Override
  protected void initialize(Application app) {
  
  }
  
  @Override
  protected void cleanup(Application app) {
  
  }
  
  @Override
  protected void onEnable() {
  
  }
  
  @Override
  protected void onDisable() {
  
  }
  
  public Optional<CollisionResult> clickCollision() {
    Vector2f click2d = getApplication().getInputManager().getCursorPosition().clone();
    Vector3f click3d = getApplication().getCamera().getWorldCoordinates(click2d, 0f).clone();
    Vector3f dir = getApplication().getCamera().getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();
    
    CollisionResults results = new CollisionResults();
    
    Ray ray = new Ray(click3d, dir);
    collidable.collideWith(ray, results);
    
    return Optional.ofNullable(results.getClosestCollision());
  }
  
  public Optional<CollisionResult> cameraCollision() {
    Vector3f location = getApplication().getCamera().getLocation();
    Vector3f direction = getApplication().getCamera().getDirection();
    
    CollisionResults results = new CollisionResults();
    
    Ray ray = new Ray(location, direction);
    collidable.collideWith(ray, results);
    
    return Optional.ofNullable(results.getClosestCollision());
  }
}
