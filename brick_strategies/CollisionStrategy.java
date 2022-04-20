package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * General type for brick strategies, part of decorator pattern implementation. All brick strategies
 * implement this interface.
 */
public abstract interface CollisionStrategy {
    /**
     * To be called on brick collision.
     * @param thisObj object from collision.
     * @param otherObj object from collision.
     * @param counter global brick counter.
     */
    public abstract void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);

    /**
     * All collision strategy objects should hold a reference to the global game object collection and
     * be able to return it.
     * @return global game object collection whose reference is held in object.
     */
    public abstract GameObjectCollection getGameObjectCollection();
}
