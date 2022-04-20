package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructor.
     * @param gameObjectCollection - global game object collection managed by game manager.
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * To be called on brick collision. Removes the hit object. Decreases number of bricks.
     * @param thisObj - current object that eas hit to remove.
     * @param otherObj - object that hit current object.
     * counter - global brick counter.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS))
            counter.decrement();
    }

    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }
}
