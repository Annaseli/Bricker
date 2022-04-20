package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{
    private static final int MIN_DISTANCE_FROM_EDGE= 5;
    private static final int NUM_COLLISIONS_TO_DISAPPEAR = 3;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final float CENTER = 0.5f;

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces extra paddle to game
     * window which remains until colliding NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game
     * objects.
     * @param toBeDecorated basic CollisionStrategy to decorate.
     * @param imageReader -
     * @param inputListener -
     * @param windowDimensions screen's dimensions.
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = toBeDecorated.getGameObjectCollection();
        MockPaddle.isInstantiated = false;
    }

    /**
     * Adds additional paddle to game and delegates to held object.
     *
     * Specified by:
     * onCollision in interface CollisionStrategy
     *
     * Overrides:
     * onCollision in class RemoveBrickStrategyDecorator
     *
     * @param thisObj object from collision.
     * @param otherObj object from collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (MockPaddle.isInstantiated) return;
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject mockPaddle = new MockPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, gameObjectCollection, MIN_DISTANCE_FROM_EDGE,
                NUM_COLLISIONS_TO_DISAPPEAR);
        mockPaddle.setCenter(new Vector2(windowDimensions.x() * CENTER, windowDimensions.y() * CENTER));
        gameObjectCollection.addGameObject(mockPaddle);
    }
}
