package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Temporary paddle object for the new paddle that a specific collision strategy adds to the game in the
 * middle of the screen that disappears after 3 collisions with the ball.
 */
public class MockPaddle extends Paddle{
    private final GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;

    public static boolean isInstantiated;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels). Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     * @param inputListener listener object for user input.
     * @param windowDimensions  dimensions of game window.
     * @param gameObjectCollection collection of all game objects.
     * @param minDistanceFromEdge  border for paddle movement.
     * @param numCollisionsToDisappear number of collisions with the ball.
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener, Vector2 windowDimensions,
                      GameObjectCollection gameObjectCollection, int minDistanceFromEdge,
                      int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        isInstantiated = true;
    }

    /**
     * Overrides:
     * onCollisionEnter in class danogl.GameObject
     * @param other game object that it collied with.
     * @param collision -
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (numCollisionsToDisappear == 1) {
            gameObjectCollection.removeGameObject(this);
            isInstantiated = false;
        } else if (other instanceof Ball)
            numCollisionsToDisappear--;
    }
}
