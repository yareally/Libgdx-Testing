package com.badlogic.androidgames.mrnom;

/**
 * The Stain objects are what Mr. Nom (the snake) eats
 * This handles all relevant code to the stains.
 */
public class Stain
{
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;

    public int x, y;
    public int type;

    /**
     * Creates a new stain object on the screen.
     *
     * @param x - the horizontal position to put the stain.
     * @param y - the vertical position to put the stain.
     * @param type - the type of stain we want to display
     */
    public Stain(int x, int y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
