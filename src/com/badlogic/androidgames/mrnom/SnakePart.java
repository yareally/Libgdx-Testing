package com.badlogic.androidgames.mrnom;

/**
 * Creates the interconnected parts of Mr. Nom, the snake.
 * We don't distinguish between head and tail parts class wise, so they just use this class and Snake.
 */
public class SnakePart
{
    public int x, y;

    /**
     * Creates a new segment for Mr. Nom
     *
     * @param x - horizontal location to put the segment on the screen
     * @param y - vertical location to put the segment on the screen
     */
    public SnakePart (int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
