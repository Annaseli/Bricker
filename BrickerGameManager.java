package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.*;
import src.gameobjects.*;

import java.util.Random;

/**
 * This class is responsible for game initialization, holding references for game
 * objects and calling update methods for every update iteration. Entry point for code should be
 * in a main method in this class.
 * @author Anna Seliverstov
 */
public class BrickerGameManager extends GameManager {

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BORDER_WIDTH = 5;
    private static final int BORDER_HEIGHT = 5;
    private static final int BALL_SPEED = 200;
    private static final float BALL_SIZE = 28.2f;
    private static final int NUM_OF_LIVES = 4;
    private static final int HEART_SIZE = 20;
    private static final int NUM_OF_ROWS_OF_BRICKS = 5;
    private static final int NUM_OF_COLS_OF_BRICKS = 8;
    private static final int PADDLE_WIDTH = 100;
    private static final int BRICK_AND_PADDLE_HEIGHT = 15;
    private static final float CENTER = 0.5f;
    private static final int MIN_DIS_FROM_EDGE = 2;
    private static final int TARGET_FRAME_RATE = 40;


    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter livesGraphicCounter;
    private Counter livesNumericCounter;
    private GameObjectCollection gameObjectCollection;
    private Counter bricksCounter;

    /**
     * Constructor. Inherits from GameManager constructor.
     * @param windowTitle - Game's title.
     * @param windowDimensions - pixel dimensions for game window height x width.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * Calling this function should initialize the game window. It should initialize objects in the game
     * window - ball, paddle, walls, life counters, bricks. This version of the game has 5 rows, 8 columns
     * of bricks.
     *
     * Overrides:
     * initializeGame in class danogl.GameManager
     *
     * @param imageReader - an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader - a SoundReader instance for reading soundclips from files for rendering event
     *                    sounds.
     * @param inputListener - an InputListener instance for reading user input.
     * @param windowController - controls visual rendering of the game window and object renderers.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        windowController.setTargetFramerate(TARGET_FRAME_RATE);
        livesGraphicCounter = new Counter(NUM_OF_LIVES);
        livesNumericCounter = new Counter(NUM_OF_LIVES);
        bricksCounter = new Counter();
        windowDimensions = windowController.getWindowDimensions();
        gameObjectCollection = gameObjects();

        //creating ball
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound soundCollision = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, soundCollision);
        repositionBall(ball);
        gameObjectCollection.addGameObject(ball);

        //creating paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, BRICK_AND_PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, MIN_DIS_FROM_EDGE);
        paddle.setCenter(new Vector2(windowDimensions.x() * CENTER,
                windowDimensions.y() - 2 * BRICK_AND_PADDLE_HEIGHT));
        gameObjectCollection.addGameObject(paddle);

        createWalls();

        //creating background
        Renderable backgroundImage = imageReader.readImage
                ("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjectCollection.addGameObject(background, Layer.BACKGROUND);

        createBricks(imageReader, soundReader, inputListener);

        //creating graphical life counter
        Renderable lifeImage = imageReader.readImage("assets/heart.png", true);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(
                new Vector2(BORDER_WIDTH + 1, windowDimensions.y() - HEART_SIZE),
                new Vector2(HEART_SIZE, HEART_SIZE),
                livesGraphicCounter, lifeImage, gameObjectCollection, NUM_OF_LIVES);
        gameObjectCollection.addGameObject(graphicLifeCounter, Layer.BACKGROUND);

        //creating numerical life counter
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(livesNumericCounter,
                new Vector2(BORDER_WIDTH + 1, windowDimensions.y() - 2 * HEART_SIZE - BORDER_WIDTH),
                new Vector2(HEART_SIZE, HEART_SIZE), gameObjectCollection);
        gameObjectCollection.addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /**
     * Creates the left, right and top walls for the screen.
     */
    private void createWalls() {
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);
        gameObjectCollection.addGameObject(leftWall, Layer.STATIC_OBJECTS);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()), null);
        gameObjectCollection.addGameObject(rightWall, Layer.STATIC_OBJECTS);
        GameObject topWall = new GameObject(new Vector2(BORDER_WIDTH, 0),
                new Vector2(windowDimensions.x() - BORDER_WIDTH, BORDER_HEIGHT), null);
        gameObjectCollection.addGameObject(topWall, Layer.STATIC_OBJECTS);
    }

    /**
     * Creates the 40 bricks one by one in the game and sets the bricks counter to the number of bricks.
     * @param imageReader - an ImageReader instance for reading images from files for rendering of objects.
     */
    private void createBricks(ImageReader imageReader, SoundReader soundReader,
                              UserInputListener inputListener) {
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        float brick_width = (windowDimensions.x() - 2 * BORDER_WIDTH - NUM_OF_COLS_OF_BRICKS -
                NUM_OF_ROWS_OF_BRICKS) / NUM_OF_COLS_OF_BRICKS;
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjectCollection, this,
                imageReader, soundReader, inputListener, windowController, windowDimensions);
        for (int i = 0; i < NUM_OF_ROWS_OF_BRICKS; i++) {
            Vector2 brickPosition = new Vector2(2 * BORDER_WIDTH + 1,
                    i*(BRICK_AND_PADDLE_HEIGHT + 1) + BORDER_HEIGHT); //initialized to leftmost brick :)
            for (int j = 0; j < NUM_OF_COLS_OF_BRICKS; j++) {
                Vector2 brickSize = new Vector2(brick_width, BRICK_AND_PADDLE_HEIGHT);
                GameObject brick = new Brick(brickPosition, brickSize, brickImage,
                        brickStrategyFactory.getStrategy(), bricksCounter);
                gameObjectCollection.addGameObject(brick, Layer.STATIC_OBJECTS);
                bricksCounter.increment();
                brickPosition = brickPosition.add(new Vector2(brick_width + 1, 0));
            }
        }
    }

    /**
     * reposiotions the ball objects on the screen.
     * @param ball - ball GameObject.
     */
    public void repositionBall(GameObject ball) {
        ball.setCenter(windowDimensions.mult(CENTER));
        ball.setVelocity(setRandomBallVal());
    }

    /**
     * Sets the ball in the canter of the screen to fly to a random direction.
     * @return random velocity
     */
    private Vector2 setRandomBallVal() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        return new Vector2(ballVelX, ballVelY);
    }

    /**
     * Code in this function is run every frame update.
     *
     * Overrides:
     * update in class danogl.GameManager
     *
     * @param deltaTime - time between updates. For internal use by game engine. You do not need to call
     *                  this method yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (GameObject obj: gameObjectCollection) {
            // delete gray balls if they felt.
            if ((obj instanceof Puck || obj instanceof Status) && obj.getCenter().y() > windowDimensions.y()) {
                gameObjectCollection.removeGameObject(obj);
            }
        }
        checkEndGame();
    }

    /**
     * This function checks if the game is over. If the paddle missed the ball, one life would be
     * decreased. If no lives left - messages about game over and asks the player to play again.
     * If all the brick are broken - messages about winning and asks the player to play again.
     */
    private void checkEndGame() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight < 0) {
            repositionBall(ball);
        } else if (ballHeight > windowDimensions.y()) {
            livesGraphicCounter.decrement();
            livesNumericCounter.decrement();
            repositionBall(ball);
        }
        if (livesGraphicCounter.value() == 0) {
            prompt = "You lose!";
        } else if (bricksCounter.value() <= 0) {
            prompt = "You win!";
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Entry point for game. Should contain:
     * 1. An instantiation call to BrickerGameManager constructor.
     * 2. A call to run() method of instance of BrickerGameManager.
     * Should initialize game window of dimensions (x,y) = (700,500).
     * @param args - argument from the user.
     */
    public static void main(String[] args) {
        new BrickerGameManager(
                "Bricker", new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}

