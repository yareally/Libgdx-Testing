package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

import java.util.List;

/**
 * User: Nichole Date: 1/4/12 Time: 11:07 PM
 */
public class HighscoreScreen extends Screen
{
    String[] lines = new String[5];

    public HighscoreScreen(Game game)
    {
        super(game);

        for (int i = 0; i < 5; i++) {
            this.lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }
    }

    /**
     * check for whether a touch-up event hit the button in the bottom-left corner. If thats the case, we play the click
     * sound and transition back to the MainMenuScreen
     *
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
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
     * render the background image first as usual, followed by the "HIGHSCORES" portion of the Assets.mainmenu image.
     * next we loop though the five strings for each high-score line we created in the constructor. We draw each line
     * with the drawText() method. First line starts at (20,100), the next line is rendered at (20,150), and so on. We
     * just increase the y-coordinate for text rendering by 50 pixels for each line so that we have a nice vertical
     * spacing between the lines. Finish the method by drawing our button
     *
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    @Override
    public void present(float deltaTime)
    {
        Graphics g = this.game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.mainMenu, 64, 20, 0, 42, 196, 42);

        int y = 100;
        for (int i = 0; i < 5; i++) {
            this.drawText(g, this.lines[i], 20, y);
            y += 50;
        }

        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
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
     * Screen.pause() will be called when the game is paused
     */
    @Override
    public void pause()
    {
    }

    /**
     * Screen.resume() will be called when the game is resumed
     */
    @Override
    public void resume()
    {
    }

    /**
     * will be called by the Game instance in case Game.setScreen() is called. The game instance will dispose of the
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