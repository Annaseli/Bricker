package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

/**
 * Represents one brick to break in the game.
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private final Counter counter;

    /**
     * Constructor. Construct a new GameObject instance.
     * @param topLeftCorner - Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions - Width and height in window coordinates.
     * @param renderable - The renderer representing the object. Can be null, in which case
     //* @param collisionStrategy - the event that happens when ball hits the brick.
     * @param counter - global bricks counter.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    /**
     * Overrides:
     * onCollisionEnter in class danogl.GameObject
     * @param other - other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other instanceof Ball) {
            super.onCollisionEnter(other, collision);
            collisionStrategy.onCollision(this, other, counter);
        }

    }
}
