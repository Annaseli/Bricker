package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * One of the types of objects that can be set loose when a brick is hit.
 */
public class Status extends GameObject {
    private final GameObjectCollection gameObjectCollection;
    private final float changeWidth;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels). Note that (0,0) is the
     *                      top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case.
     * @param gameObjectCollection global game object collection managed by game manager
     * @param changeWidth float to multiply the paddle width to shrink or extend it.
     */
    public Status(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  GameObjectCollection gameObjectCollection, float changeWidth) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.changeWidth = changeWidth;
    }

    /**
     * Sets the object to collide only with paddle.
     * @param other game object that collided with.
     * @return true if the object is Paddle object and false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }

    /**
     * On collision object disappears and changes the paddle width - shrinks or extends it according to the
     * changeWidth parameter.
     * @param other object that collied with. (the paddle)
     * @param collision -
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameObjectCollection.removeGameObject(this);
        other.setDimensions(new Vector2(other.getDimensions().x() * changeWidth,
                other.getDimensions().y()));
    }
}

