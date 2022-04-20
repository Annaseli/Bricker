package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Changes camera focus from ground to ball
 * until ball collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{
    private static final float EXTEND_WINDOW = 1.2f;
    private static final int COUNT_DOWN_VAl = 4;

    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;
    private final GameObjectCollection gameObjectCollection;
    private Boolean cameraChanged;

    /**
     * Constructor
     * @param toBeDecorated object to decorate with the current collision strategy.
     * @param windowController -
     * @param gameManager -
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
        this.gameObjectCollection = toBeDecorated.getGameObjectCollection();
        cameraChanged = false;
    }

    /**
     * Change camere position on collision and delegate to held CollisionStrategy.
     * @param thisObj object from collision.
     * @param otherObj object from collision.
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (cameraChanged || !(otherObj instanceof Ball) || otherObj instanceof Puck) return;
        cameraChanged = true;
        ballCollisionCountdownAgent = new BallCollisionCountdownAgent((Ball) otherObj, this,
                COUNT_DOWN_VAl);
        gameObjectCollection.addGameObject(ballCollisionCountdownAgent);
        gameManager.setCamera(new Camera(otherObj, Vector2.ZERO,
                windowController.getWindowDimensions().mult(EXTEND_WINDOW),
                windowController.getWindowDimensions()));
    }

    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange() {
        gameManager.setCamera(null);
        gameObjectCollection.removeGameObject(ballCollisionCountdownAgent);
        cameraChanged = false;
    }
}
