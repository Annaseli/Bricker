# Bricker

Bricker Game - advanced game that uses different behaviour after hitting a brick by the ball.
The different behaviours are chosen randomely from a list of different behaviours.

Uses Object Oriented Programming

###############################################################################

BrickerGameManager - the main class that starts the game.

Collision Strategies:
CollisionStrategy - This class makes is passed to a game object (the brick) and implements the
requested behavior on collision. If we want to change/extend/add the same behavior to other classes
we will not have to change the code of other classes. It's an interface.
BrickStrategyFactory - Creates randomly a strategy for a brick.
RemoveBrickStrategy- Simply removes the brick from the board, it is also the base implementation of the
decorator pattern.
AddPaddleStrategy - Adds a new paddle that disappears after several hits.
ChangeCameraStrategy - Changes the camera position so it follows the ball that hit the brick.
ChangePaddleWidthStrategy - Picks randomly between shrink/widen paddle status definers.
PuckStrategy - Adds three pucks with random velocities.
RemoveBrickStrategyDecorator - The abstract class of the decorator pattern.

All of them use inheritance from a concrete decorator that implemets a interface.

Game Objects:
Ball - The game object that represent a ball. It sets the sound of collision with other game objects.
Brick - The game object that represent a brick. It gets a collision strategy and activates the input collision
behavior on a detected collision.
GraphicLifeCounter - Gets the counter of lives and draws hearts that represent the number of lives left
for the player. This object is also a game object since it updates every frame.
NumericLifeCounter - Gets the counter of lives and draws a string that states the number of lives left
for the player. This object is also a game object since it updates every frame.
Paddle - The paddle game object, it responds to the users input and moves accordingly.
BallCollisionCountdownAgent - Counts the number of collision the ball has made and changes the camera
settings when it reached the count down value.
MockPaddle - This paddle is added to the game in addition to the main paddle when a brick is hit.
PaddleExtender - A status definer that collides only with the main paddle and widen\shorten the paddle
according to the extension factor it gets.
Puck - A new type of ball.
Status - A class that defines the behavior of the status definer that disappears after collision with the
paddle and changing the paddle's dimensions.
