package com.badlogic.androidgames.framework;

public abstract class Screen {
    protected final Game game;

    /**
     *
     * @param game takes the Game Interface object and accesses the game's graphics to display them
     */
    public Screen(Game game) {
        this.game = game;
    }

    /**
     * update the screen state accordingly - Game instance will call once in each iteration of the main loop.
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    public abstract void update(float deltaTime);

    /**
     * update screen accordingly - Game instance will call once in each iteration of the main loop
     * @param deltaTime - how much time has passed since it was last updated (seconds)
     */
    public abstract void present(float deltaTime);

    /**
     * Screen.pause() will be called when the game is paused
     */
    public abstract void pause();

    /**
     * Screen.resume() will be called when the game is resumed
     */
    public abstract void resume();

    /**
     * will be called by the Game instance in case Game.setScreen() is called. The game instance will
     * dispose of the current Screen via this method and thereby give the Screen an opportunity to
     * release all its system resources (like graphical assets stored in Pixmaps) to make room
     * for the new screen's resources in memory. The call to Screen.dispose() method is also the last
     * opportunity for a screen to make sure that any information that needs persistence is saved.
     */
    public abstract void dispose();
}
