package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter extends GameObject {
    private final Counter livesCounter;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final GameObjectCollection gameObjectCollection;
    private GameObject curObj;
    private final int numOnScreen;

    /**
     * Constructor.
     * @param livesCounter - global lives counter of game.
     * @param topLeftCorner - top left corner of renderer.
     * @param dimensions - dimensions of renderer.
     * @param gameObjectCollection - global game object collection
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        numOnScreen = livesCounter.value();
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.gameObjectCollection = gameObjectCollection;

        TextRenderable number = new TextRenderable(Integer.toString(livesCounter.value()));
        curObj = new GameObject(topLeftCorner, dimensions, number);
        gameObjectCollection.addGameObject(curObj, Layer.BACKGROUND);
    }

    /**
     * Decreases one life by removing the current counter from screen and replacing with the
     * new counter on the screen.
     *
     * Overrides:
     * update in class danogl.GameObject
     *
     * @param deltaTime -
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() != numOnScreen) {
            gameObjectCollection.removeGameObject(curObj, Layer.BACKGROUND);
            TextRenderable number = new TextRenderable(Integer.toString(livesCounter.value()));
            curObj = new GameObject(topLeftCorner, dimensions, number);
            gameObjectCollection.addGameObject(curObj, Layer.BACKGROUND);
        }
    }
}
