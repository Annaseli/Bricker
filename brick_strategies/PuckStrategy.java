package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces several pucks instead of
 * brick once removed.
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator{
    private static final int NUM_OF_PUCKS = 3;
    private static final float PUCK_SPEED = 200 * 1.2f;
    private static final float PUCK_SIZE = 28.2f;

    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructor.
     * @param toBeDecorated object to decorate with the current collision strategy.
     * @param imageReader -
     * @param soundReader -
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        gameObjectCollection = toBeDecorated.getGameObjectCollection();
    }

    /**
     * Sets the ball in the canter of the screen to fly to a random direction.
     * @return random velocity
     */
    private Vector2 setRandomPuckVal() {
        Random rand = new Random();
        Vector2[] velocity = {(Vector2.DOWN.add(Vector2.LEFT)).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT)).mult(PUCK_SPEED), Vector2.DOWN.mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.9f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.9f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.8f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.8f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.7f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.7f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.6f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.6f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.5f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.5f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.4f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.4f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.3f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.3f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.2f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.2f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.RIGHT.mult(0.1f))).mult(PUCK_SPEED),
                (Vector2.DOWN.add(Vector2.LEFT.mult(0.1f))).mult(PUCK_SPEED)};
        return velocity[rand.nextInt(velocity.length)];
    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     * Specified by:
     * onCollision in interface CollisionStrategy
     * Overrides:
     * onCollision in class RemoveBrickStrategyDecorator
     * @param thisObj object from collision.
     * @param otherObj object from collision.
     * @param counter global brick counter.

     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable puckImage = imageReader.readImage("assets/mockBall.png", true);
        Sound soundCollision = soundReader.readSound("assets/blop_cut_silenced.wav");
        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            Puck puck = new Puck(Vector2.ZERO, new Vector2(PUCK_SIZE, PUCK_SIZE), puckImage, soundCollision);
            puck.setCenter(thisObj.getCenter());
            puck.setVelocity(setRandomPuckVal());
            gameObjectCollection.addGameObject(puck);
        }
    }
}
