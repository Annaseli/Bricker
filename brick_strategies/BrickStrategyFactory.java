package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory class for creating Collision strategies
 */
public class BrickStrategyFactory {
    private static final int ALL_STRATEGIES = 6;
    private static final int MAX_STRATEGIES = 3;
    private static final int DOUBLE = 5;
    private static final int REMOVE_BRICK = 0;

    private final GameObjectCollection gameObjectCollection;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;

    /**
     * Constructor.
     * @param gameObjectCollection collection of all game objects.
     * @param gameManager -
     * @param imageReader -
     * @param soundReader -
     * @param inputListener -
     * @param windowController -
     * @param windowDimensions screen's dimensions.
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader,
                                UserInputListener inputListener, WindowController windowController,
                                Vector2 windowDimensions) {
        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    private CollisionStrategy getStrategyHelper(CollisionStrategy collisionStrategy, int bottomBound,
                                                int topBound, int numOfChosenStrategies) {
        CollisionStrategy[] collisionStrategies = {
                new RemoveBrickStrategy(gameObjectCollection),
                new PuckStrategy(collisionStrategy, imageReader, soundReader),

                new AddPaddleStrategy(collisionStrategy, imageReader, inputListener, windowDimensions),
                new ChangeCameraStrategy(collisionStrategy, windowController, gameManager),
                new WidePaddleStrategy(collisionStrategy, imageReader),
        };

        int rand = bottomBound + new Random().nextInt(topBound - bottomBound);
        if (numOfChosenStrategies == MAX_STRATEGIES || rand == REMOVE_BRICK)
            return collisionStrategy;
        else if (rand == DOUBLE) {
            collisionStrategy = getStrategyHelper(collisionStrategy, 1, ALL_STRATEGIES - 1, numOfChosenStrategies);
            numOfChosenStrategies++;
            collisionStrategy = getStrategyHelper(collisionStrategy, 1, ALL_STRATEGIES, numOfChosenStrategies);
        } else
            collisionStrategy = collisionStrategies[rand];
        return collisionStrategy;
    }


    /**
     * method randomly selects between 5 strategies and returns one CollisionStrategy object which is a
     * RemoveBrickStrategy decorated by one of the decorator strategies, or decorated by two randomly
     * selected strategies, or decorated by one of the decorator strategies and a pair of additional two
     * decorator strategies.
     * @return CollisionStrategy object that it chosen randomly.
     */

    public CollisionStrategy getStrategy() {
        return getStrategyHelper(new RemoveBrickStrategy(gameObjectCollection),
                0, ALL_STRATEGIES, 0);
    }

}
