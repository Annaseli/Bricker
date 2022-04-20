package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Display a graphic object on the game window showing as many widgets as lives left.
 */
public class GraphicLifeCounter extends GameObject {
    private static final int SPACE_BETWEEN_CORNERS = 22;

    private final Counter livesCounter;
    private final GameObjectCollection gameObjectsCollection;
    private final GameObject[] livesArr;
    private int numOfHearts;

    /**
     * Constructor.
     * @param widgetTopLeftCorner - top left corner of left most life widgets. Other widgets
     *                            will be displayed to its right, aligned in hight.
     * @param widgetDimensions - dimensions of widgets to be displayed.
     * @param livesCounter - global lives counter of game.
     * @param widgetRenderable - image to use for widgets.
     * @param gameObjectsCollection - global game object collection managed by game manager.
     * @param numOfLives - global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter,
                              Renderable widgetRenderable, GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.livesCounter = livesCounter;
        numOfHearts = livesCounter.value();
        this.gameObjectsCollection = gameObjectsCollection;
        livesArr = new GameObject[numOfLives];

        for (int i = 0; i < numOfLives; i++) {
            livesArr[i] = new GameObject(new Vector2(widgetTopLeftCorner.x() + SPACE_BETWEEN_CORNERS * i,
                    widgetTopLeftCorner.y()), widgetDimensions, widgetRenderable);
            gameObjectsCollection.addGameObject(livesArr[i], Layer.BACKGROUND);
        }
    }

    /**
     * Decreases one life by removing one heart from the screen.
     *
     * Overrides:
     * update in class danogl.GameObject
     *
     * @param deltaTime -
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() != numOfHearts) {
            gameObjectsCollection.removeGameObject(livesArr[numOfHearts - 1], Layer.BACKGROUND);
            numOfHearts = livesCounter.value();
        }
    }
}
