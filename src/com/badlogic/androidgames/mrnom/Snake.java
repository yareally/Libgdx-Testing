package com.badlogic.androidgames.mrnom;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates Mr. Nom as a whole
 */
public class Snake
{
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    /** Holds the snake parts (his sprites) **/
    public List<SnakePart> parts = new ArrayList<SnakePart>();

    public int direction;

    /**
     * Creates a new snake for the game.
     */
    public Snake()
    {
        this.direction = UP;
        this.parts.add(new SnakePart(5, 6));
        this.parts.add(new SnakePart(5, 7));
        this.parts.add(new SnakePart(5, 8));
    }

    /**
     * Turns the snake left... left = 1 down = 2 right = 3
     */
    public void turnLeft()
    {
        this.direction += 1;

        if (this.direction > RIGHT ) {
            this.direction = UP;
        }
    }

    /**
     * Turns the snake right...
     */
    public void turnRight()
    {
        this.direction -= 1;

        if (this.direction < UP) {
            this.direction = RIGHT;
        }
    }

    /**
     * Snake eats some food in front of it.
     */
    public void eat()
    {
        SnakePart end = this.parts.get(this.parts.size() - 1);
        this.parts.add(new SnakePart(end.x, end.y));
    }

    /**
     * Snake moves ahead to the next spot.
     */
    public void advance()
    {
        SnakePart head = this.parts.get(0);
        int len = parts.size() - 1;

        for (int i = len; i > 0; i--) {
            SnakePart before = parts.get(i - 1);
            SnakePart part = parts.get(i);
            part.x = before.x;
            part.y = before.y;
        }

        if (direction == UP) { // moves one right
            head.y -= 1;
        }
        if (direction == LEFT) { // moves one left
            head.x -= 1;
        }
        if (direction == DOWN) { // moves one down
            head.y += 1;
        }
        if (direction == RIGHT) { // moves one to the right
            head.x += 1;
        }

        // keeps him from going out of bounds
        if (head.x < 0) {
            head.x = 9;
        }
        if (head.x > 9) {
            head.x = 0;
        }
        if (head.y < 0) {
            head.y = 12;
        }
        if (head.y > 12) {
            head.y = 0;
        }

    }

    public boolean checkBitten()
    {
        int len = parts.size();
        SnakePart head = parts.get(0);

        for (int i = 1; i < len; ++i) {
            SnakePart part = parts.get(i);

            if (part.x == head.x && part.y == head.y) {
                return true;
            }
        }
        return false;
    }
}
