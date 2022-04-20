package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Status;

import java.util.Random;

/**
 * Responsible for extending or shrinking a paddle.
 */
public class WidePaddleStrategy extends RemoveBrickStrategyDecorator{
    private static final float EXTEND = 1.2f;
    private static final float SHRINK = 0.8f;
    private static final int REGULAR_VEL = 200;

    private final ImageReader imageReader;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructor.
     * @param toBeDecorated Collision strategy that this strategy is obliged on.
     * @param imageReader -
     */
    public WidePaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        gameObjectCollection = toBeDecorated.getGameObjectCollection();
    }

    /**
     * On collision throws an arrow object from the broken brick. if a paddle cached it, it gets extended or
     * shrink.
     * @param thisObj object from collision.
     * @param otherObj object from collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Random rand = new Random();
        float changeWidth = EXTEND;
        Renderable statusImage = imageReader.readImage("assets/buffWiden.png", false);
        if (rand.nextBoolean()) {
            changeWidth = SHRINK;
            statusImage = imageReader.readImage("assets/buffNarrow.png", false);
        }
        GameObject status = new Status(Vector2.ZERO, thisObj.getDimensions(), statusImage,
                gameObjectCollection, changeWidth);
        status.setCenter(thisObj.getCenter());
        status.setVelocity(new Vector2(0, REGULAR_VEL * EXTEND));
        gameObjectCollection.addGameObject(status);
    }
}
