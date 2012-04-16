package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

/**
 * Main class for the game.
 */
public class MrNomGame extends AndroidGame
{
    @Override
    public Screen getStartScreen()
    {
        return new LoadingScreen(this);
    }
}