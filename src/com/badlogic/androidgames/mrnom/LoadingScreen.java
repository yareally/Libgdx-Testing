package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;

/**
 * Loads the splash (opening) screen for the game. Also loads all needed
 * resources to start the game up and initially run it.
 */
public class LoadingScreen extends Screen
{
    /**
     * Creates the loading screen
     *
     * @param game - the instance of the game to load
     */
    public LoadingScreen(Game game)
    {
        super(game);
    }

    /**
     * update the screen state accordingly - Game instance will call once in
     * each iteration of the main loop.
     *
     * @param deltaTime - how much time has passed since it was last updated
     * (seconds)
     */
    @Override
    public void update(float deltaTime)
    {
        Graphics g = this.game.getGraphics();
        Assets.background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", Graphics.PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", Graphics.PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", Graphics.PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("help1.png", Graphics.PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("help2.png", Graphics.PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("help3.png", Graphics.PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", Graphics.PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", Graphics.PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", Graphics.PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headUp = g.newPixmap("headup.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headLeft = g.newPixmap("headleft.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headDown = g.newPixmap("headdown.png", Graphics.PixmapFormat.ARGB4444);
        Assets.headRight = g.newPixmap("headright.png", Graphics.PixmapFormat.ARGB4444);
        Assets.tail = g.newPixmap("tail.png", Graphics.PixmapFormat.ARGB4444);
        Assets.stain1 = g.newPixmap("stain1.png", Graphics.PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("stain2.png", Graphics.PixmapFormat.ARGB4444);
        Assets.stain3 = g.newPixmap("stain3.png", Graphics.PixmapFormat.ARGB4444);
        Assets.click = this.game.getAudio().newSound("click.ogg");
        Assets.eat = this.game.getAudio().newSound("eat.ogg");
        Assets.bitten = this.game.getAudio().newSound("bitten.ogg");
        Settings.load(this.game.getFileIO());
        this.game.setScreen(new MainMenuScreen(this.game));
    }

    /**
     * update screen accordingly - Game instance will call once in each
     * iteration of the main loop
     *
     * @param deltaTime - how much time has passed since it was last updated
     * (seconds)
     */
    @Override
    public void present(float deltaTime)
    {
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
     * will be called by the Game instance in case Game.setScreen() is called.
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
    }


}