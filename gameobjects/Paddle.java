package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * One of the main game objects. Repels the ball against the bricks.
 */
public class Paddle extends GameObject {
    private static final int MOVEMENT_SPEED = 400;

    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistanceFromEdge;

    /**\
     * Constructor. Construct a new GameObject instance.
     * @param topLeftCorner - Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions - Width and height in window coordinates.
     * @param renderable - The renderer representing the object. Can be null, in which case
     * @param windowDimensions- dimensions of widgets to be displayed.
     * @param inputListener - listener that waits for user's command: moving left or right.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * Overrides:
     * update in class danogl.GameObject. Waits for user's command: moving left or right.
     * Maintains the paddle to steak inside the screen's borders.
     * @param deltaTime -
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movement = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movement = movement.add(Vector2.LEFT);
        } else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movement = movement.add(Vector2.RIGHT);
        }
        setVelocity((movement.mult(MOVEMENT_SPEED)));
        float border = windowDimensions.x() - minDistanceFromEdge - getDimensions().x();
        if (minDistanceFromEdge > getTopLeftCorner().x()) {
            setTopLeftCorner(new Vector2(minDistanceFromEdge, getTopLeftCorner().y()));
        } else if (getTopLeftCorner().x() > border) {
            setTopLeftCorner(new Vector2(border, getTopLeftCorner().y()));
        }
    }
}

