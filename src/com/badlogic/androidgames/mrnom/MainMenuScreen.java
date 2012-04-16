package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;

import java.util.List;

/**
 * Loads the main menu for the game.
 */
public class MainMenuScreen extends Screen
{
    public MainMenuScreen(Game game)
    {
        super(game);
    }

    /**
     * Called when a user touches the screen.
     * Update the screen state accordingly - Game instance will call once in
     * each iteration of the main loop.
     *
     * @param deltaTime - how much time has passed since it was last updated
     * (seconds)
     */
    @Override
    public void update(float deltaTime)
    {
        Graphics graphics = this.game.getGraphics();
        List<Input.TouchEvent> touchEvents = this.game.getInput().getTouchEvents();
        this.game.getInput().getKeyEvents();
        
        int length = touchEvents.size();
        
        for (int i = 0; i < length; ++i) {
            Input.TouchEvent event = touchEvents.get(i);
            
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                if (this.inBounds(event, 0, graphics.getHeight() - 64, 64, 64)) {
                    // user has touched the sound on/off area...
                    Settings.soundEnabled = !Settings.soundEnabled; // turns sound on or off
                    
                    if (Settings.soundEnabled) {
                        Assets.click.play(1); // plays sound on touching the button (if it's on)
                    }
                }
                if (this.inBounds(event, 64, 220, 192, 42)) {
                    // user has touched the PLAY button for the game (starts a new game)
                    this.game.setScreen(new GameScreen(this.game));

                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }

                if (this.inBounds(event, 64, 220 + 42, 192, 42)) {
                    // user has touched the HIGHSCORES button area
                    this.game.setScreen(new HighscoreScreen(this.game));

                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }

                if (this.inBounds(event, 64, 220 + 84, 192, 42)) {
                    this.game.setScreen(new HelpScreen(this.game));

                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    return;
                }
            }
        }
    }

    /**
     * Determines if the user touch is within the bounds of the event area or not. If it is, then return true.
     * @param event - event in question the user performed (probably a touch event)
     * @param x - horizontal area on the screen of the touch by pixel
     * @param y - vertical area on the screen of the touch by pixel
     * @param width - width of area of the event area on the screen where the potential event action happened
     * @param height - height of the area on the screen where the potential event action happened
     * @return true if the event is in bounds
     */
    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height)
    {
        if (event.x > x && event.x < (x + width) - 1
            && event.y > y && event.y < (y + height) - 1) {
            return true;
        }
        return false;
    }


    /**
     * Renders the main menu for the game
     *
     * @param deltaTime - how much time has passed since it was last updated
     * (seconds)
     */
    @Override
    public void present(float deltaTime)
    {
        Graphics graphics = this.game.getGraphics();
        /*  loads the background...since we do it at 0,0,
            it basically erases the existing framebuffer for us, so no need to call Graphics.clear()
         */
        graphics.drawPixmap(Assets.background, 0,0);
        //loads the logo
        graphics.drawPixmap(Assets.logo, 32, 20);
        // loads the main menu button image graphics
        graphics.drawPixmap(Assets.mainMenu, 64, 220);
        
        if (Settings.soundEnabled) {
            // load the sound enabled button
            graphics.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
        }
        else {
            // load the sound disabled button
            graphics.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
        }
    }

    /**
     * Screen.pause() will be called when the game is paused
     */
    @Override
    public void pause()
    {
        Settings.save(this.game.getFileIO());
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
     * Will be called by the Game instance in case Game.setScreen() is called.
     * The game instance will dispose of the current Screen via this method and
     * thereby give the Screen an opportunity to release all its system
     * resources (like graphical assets stored in Pixmaps) to make room for the
     * new screen's resources in memory. The call to Screen.dispose() method is
     * also the last opportunity for a screen to make sure that any information
     * that needs persistence is saved.
     */
    @Override
    public void dispose()
    {
        // nothing needed
    }
}
