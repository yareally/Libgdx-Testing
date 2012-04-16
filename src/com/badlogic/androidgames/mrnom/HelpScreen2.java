package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;

import java.util.List;

/**
 * The second game helpscreen
 */
public class HelpScreen2 extends Screen
{
    /**
     * Creates a new help screen
     * @param game - the game to create the help screen
     */
    public HelpScreen2(Game game)
    {
        super(game);
    }

    /**
     * Called when a user touches the screen.
     * Update the screen state accordingly - Game instance will call once in each iteration of the main loop.
     *
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    @Override
    public void update(float deltaTime)
    {
        List<Input.TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();
        Graphics graphics = this.game.getGraphics();
        int length = touchEvents.size();
        
        for (int i = 0; i < length; ++i) {
            Input.TouchEvent event = touchEvents.get(i);

            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if (event.x > graphics.getWidth() - 64 && event.y > graphics.getHeight() - 64) {
                    this.game.setScreen(new HelpScreen3(this.game));

                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }
            }
        }
    }

    /**
     * Loads the actual help screen for the game (the asset images and such)
     *
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    @Override
    public void present(float deltaTime)
    {
        Graphics graphics = this.game.getGraphics();
        graphics.drawPixmap(Assets.background, 0, 0);
        graphics.drawPixmap(Assets.help2, 64, 100);
        graphics.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    /**
     * Screen.pause() will be called when the game is paused
     */
    @Override
    public void pause()
    {
        // nothing needed
    }

    /**
     * Screen.resume() will be called when the game is resumed
     */
    @Override
    public void resume()
    {
        // nothing needed
    }

    /**
     * will be called by the Game instance in case Game.setScreen() is called. The game instance will
     * dispose of the current Screen via this method and thereby give the Screen an opportunity to
     * release all its system resources (like graphical assets stored in Pixmaps) to make room
     * for the new screen's resources in memory. The call to Screen.dispose() method is also the last
     * opportunity for a screen to make sure that any information that needs persistence is saved.
     */
    @Override
    public void dispose()
    {
        // nothing needed
    }
}
