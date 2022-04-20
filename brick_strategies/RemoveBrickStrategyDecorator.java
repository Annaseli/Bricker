package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
 * All strategy decorators should inherit from this class.
 */
abstract public class RemoveBrickStrategyDecorator implements CollisionStrategy{
    private final CollisionStrategy toBeDecorated;

    /**
     * Constrictor.
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Should delegate to held Collision strategy object.
     *
     * Specified by:
     * onCollision in interface CollisionStrategy
     *
     * @param thisObj object from collision.
     * @param otherObj object from collision.
     * @param counter global brick counter.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    /**
     * return held reference to global game object. Delegate to held object to be decorated.
     *
     * Specified by:
     * getGameObjectCollection in interface CollisionStrategy
     * @return game object collection
     */
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
