package com.badlogic.androidgames.framework;

/**
 * Created by IntelliJ IDEA. User: admin Date: 9/18/11 Time: 5:02 AM To change
 * this template use File | Settings | File Templates.
 */
public interface Game {

    /**
     *
     * @return input
     */
    public Input getInput();

    /**
     * @return fileIO
     */
    public FileIO getFileIO();

    /**
     * @return graphics
     */
    public Graphics getGraphics();


    /**
     * @return audio
     */
    public Audio getAudio();

    /**
     * Allows us to set the current Screen of the Game. These
     * methods will be implemented once, along with all the internal thread creation, window
     * management, and main loop logic that will constantly ask the current screen to present
     * and update itself.
     *
     * @param screen
     */
    public void setScreen(Screen screen);

    /**
     *
     * @return currently active screen
     */
    public Screen getCurrentScreen();

    /**
     * abstract method - if we create the AndroidGame instance for our actual game, we'll derive
     * from it and override the Game.getStartScreen() method, returning an instance to the first
     * screen of our game
     * @return instance to the first screen of the game (start screen)
     */
    public Screen getStartScreen();
}
