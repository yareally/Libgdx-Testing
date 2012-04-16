package com.badlogic.androidgames.mrnom;

import android.graphics.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

import java.util.List;


public class GameScreen extends Screen
{
    enum GameState
    {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState state = GameState.Ready;
    World world;
    int    oldScore = 0;
    String score    = "0";

    /**
     * Calls the superclass constructor and creates a new World instance. The game screen will be in the ready after the
     * constructor returns to the caller
     *
     * @param game
     */
    public GameScreen(Game game)
    {
        super(game);
        this.world = new World();
    }

    /**
     * fetches the TouchEvents and KeyEvents from the input module and then delegate the update to one of the four
     * update methods that we implement for each state based on the current state
     *
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    @Override
    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();

        if (this.state == GameState.Ready) {
            this.updateReady(touchEvents);
        }
        if (this.state == GameState.Running) {
            this.updateRunning(touchEvents, deltaTime);
        }
        if (this.state == GameState.Paused) {
            this.updatePaused(touchEvents);
        }
        if (this.state == GameState.GameOver) {
            this.updateGameOver(touchEvents);
        }
    }

    /**
     * This method checks if the button in the middle of the screen was pressed. If it has, then we initiate
     * a screen trasition back to the main menu screen
     *
     * @param touchEvents
     */
    private void updateGameOver(List<TouchEvent> touchEvents)
    {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x >= 128 && event.x <= 192 && event.y >= 200 && event.y <= 264) {
                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    this.game.setScreen(new MainMenuScreen(this.game));
                    return;
                }
            }
        }
    }

    /**
     * this method checks whether one of the menu options was touched and changes the state accordingly
     *
     * @param touchEvents
     */
    private void updatePaused(List<TouchEvent> touchEvents)
    {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 80 && event.x <= 240) {
                    if (event.y > 100 && event.y <= 148) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        this.state = GameState.Running;
                        return;
                    }
                    if (event.y > 148 && event.y < 196) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        this.game.setScreen(new MainMenuScreen(this.game));
                        return;
                    }
                }
            }
        }
    }

    /**
     * First checks whether the pause button in the top-left corner of the screen was pressed. If that's the case,
     * it sets the state to paused. It then checks whether one of the controller buttons at the bottom of the screen
     * was pressed. *Note* that we don't check for touch-up events here, but for touch-down events.
     * If either of the buttons was pressed, we tell the Snake instance of the World to turn left or right.
     * After all the touch events have been checked, we tell the world to update itself with the given delta time
     * IF the World signals that the game is over, we change the state accordingly, and also play the bitten.ogg sound.
     * Next we check if the old score we have cached is different from the score that the World stores. If it is,
     * then we know two things: Mr Nom has eaten a stain and the score string must be changed. In that case,
     * we play the eat.ogg sound.
     *
     * @param touchEvents
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime)
    {
        int len = touchEvents.size();

        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y < 64) {
                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    this.state = GameState.Paused;
                    return;
                }
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (event.x < 64 && event.y > 416) {
                    this.world.snake.turnLeft();

                }
                if (event.x > 256 && event.y > 416) {
                    this.world.snake.turnRight();
                }
            }
        }

        this.world.update(deltaTime);

        if (this.world.gameOver) {
            if (Settings.soundEnabled) {
                Assets.bitten.play(1);
            }
            this.state = GameState.GameOver;
        }
        if (this.oldScore != this.world.score) {
            this.oldScore = this.world.score;
            this.score = "" + this.oldScore;
            if (Settings.soundEnabled) {
                Assets.eat.play(1);
            }
        }
    }

    /**
     * updateReady() called when the screen is in the ready state. All it does is check if the screen was touched
     * if that's the case, it changes the state to running
     *
     * @param touchEvents from input module
     */
    private void updateReady(List<TouchEvent> touchEvents)
    {
        if (!touchEvents.isEmpty()) {
            this.state = GameState.Running;
        }
    }

    /**
     * this method draws the background image, as that is needed in all states. Next it calls the respective drawing
     * method for the state we are in. Finally it renders Mr.Nom's world and draws the score at the bottom-center
     * of the screen
     *
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    @Override
    public void present(float deltaTime)
    {
        Graphics g = this.game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        this.drawWorld(this.world);
        if (this.state == GameState.Ready) {
            this.drawReadyUI();
        }
        if (this.state == GameState.Running) {
            this.drawRunningUI();
        }
        if (this.state == GameState.Paused) {
            this.drawPausedUI();
        }
        if (this.state == GameState.GameOver) {
            this.drawGameOverUI();
        }

        this.drawText(g, this.score, g.getWidth() / 2 - this.score.length() * 20 / 2, g.getHeight() - 42);

    }

    private void drawText(Graphics g, String line, int x, int y)
    {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            }
            else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }


    /**
     *
     */
    private void drawGameOverUI()
    {
        Graphics g = this.game.getGraphics();

        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    /**
     *
     */
    private void drawPausedUI()
    {
        Graphics g = this.game.getGraphics();

        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    /**
     *
     */
    private void drawRunningUI()
    {
        Graphics g = this.game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    /**
     *
     */
    private void drawReadyUI()
    {
        Graphics g = this.game.getGraphics();

        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    /**
     * The drawWorld() method draws the world, as we just discussed. It starts off by choosing the Pixmap to use
     * use for rendering the stain,and then draws it and centers it horizontally at its screen position. Next we
     * render all the tail parts of Mr.Nom. Finally we choose which Pixmap of the head to use based on Mr.Nom's
     * direction, and draw that Pixmap at the position of the head in screen coordinates. We also center the image
     * around that position.
     *
     * @param world
     */
    private void drawWorld(World world)
    {
        Graphics g = this.game.getGraphics();
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(0);
        Stain stain = world.stain;

        Pixmap stainPixmap = null;

        if (stain.type == Stain.TYPE_1) {
            stainPixmap = Assets.stain1;
        }
        if (stain.type == Stain.TYPE_2) {
            stainPixmap = Assets.stain2;
        }
        if (stain.type == Stain.TYPE_3) {
            stainPixmap = Assets.stain3;
        }

        int x = stain.x * 32;
        int y = stain.y * 32;
        g.drawPixmap(stainPixmap, x, y);

        int len = snake.parts.size();

        for (int i = 1; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assets.tail, x, y);
        }

        Pixmap headPixmap = null;
        if (snake.direction == Snake.UP) {
            headPixmap = Assets.headUp;
        }
        if (snake.direction == Snake.LEFT) {
            headPixmap = Assets.headLeft;
        }
        if (snake.direction == Snake.DOWN) {
            headPixmap = Assets.headDown;
        }
        if (snake.direction == Snake.RIGHT) {
            headPixmap = Assets.headRight;
        }

        x = head.x * 32 + 16;
        y = head.y * 32 + 16;

        if (headPixmap != null) {
            g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
        }

    }

    /**
     * This gets called when the activity is paused or the game screen is replaced by another screen. That's
     * the perfect place to save our settings. First we set the state of the game to paused. If the paused()
     * method got called due to the activity being paused, this will guarantee that the user will be asked to
     * resume the game when they return to it. Then we check whether the game screen is in a game-over state
     * If that's the case, we add the score the player achieved to the high scores(or not depending on the value)
     * and save all the settings to the external storage
     * Screen.pause() will be called when the game is paused
     */
    @Override
    public void pause()
    {
        if (this.state == GameState.Running) {
            this.state = GameState.Paused;
        }

        if (this.world.gameOver) {
            Settings.addScore(this.world.score);
            Settings.save(this.game.getFileIO());
        }
    }

    /**
     * Screen.resume() will be called when the game is resumed
     */
    @Override
    public void resume()
    {
    }

    /**
     * Will be called by the Game instance in case Game.setScreen() is called. The game instance will dispose of the
     * current Screen via this method and thereby give the Screen an opportunity to release all its system resources
     * (like graphical assets stored in Pixmaps) to make room for the new screen's resources in memory. The call to
     * Screen.dispose() method is also the last opportunity for a screen to make sure that any information that needs
     * persistence is saved.
     */
    @Override
    public void dispose()
    {
    }
}
