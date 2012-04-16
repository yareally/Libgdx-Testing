package com.badlogic.androidgames.mrnom;

import java.util.Random;

/**
 * User: Nichole
 * Date: 1/5/12
 * Time: 2:25 AM
 */
public class World
{
    /**
     * the world’s width and height in cells, the value we increment the score with each time Mr. Nom eats a
     * stain, the initial time interval used to advance Mr. Nom (called a tick), and the value we
     * decrement the tick each time Mr. Nom has eaten ten stains in order to speed up things a
     * little.
     */
    static final int   WORLD_WIDTH     = 10;
    static final int   WORLD_HEIGHT    = 13;
    static final int   SCORE_INCREMENT = 10;
    static final float TICK_INITIAL    = 0.5f;
    static final float TICK_DECREMENT  = 0.05f;

    /**
     * hold a Snake instance, a Stain instance, a boolean that stores whether the game is over, and the current score.
     */
    public Snake snake;
    public Stain stain;
    public boolean gameOver = false;
    public int     score    = 0;

    /**
     * the 2D array we’ll use to place a new stain; an instance of the Random class, through which we’ll produce
     * random numbers to place the stain and generate its type; the time accumulator variable, tickTime, to which
     * we’ll add the frame delta time; and the current duration of a tick, which defines how
     * often we advance Mr. Nom.
     */
    boolean[][] fields = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random  random     = new Random();
    float   tickTime   = 0;
    static float tick = TICK_INITIAL;

    /**
     * In the constructor we create an instance of the Snake class, which will have the initial
     * configuration shown in Figure 6–6. We also place the first random stain via the
     * placeStain() method.
     */
    public World()
    {
        snake = new Snake();
        placeStain();
    }

    /**
     * The placeStain() method implements the placement strategy discussed earlier. We
     * start off by clearing the cell array. Next we set all the cells occupied by parts of the
     * snake to true. Finally we scan the array for a free cell starting at a random position.
     * Once we have found a free cell, we create a Stain with a random type. Note that if all
     * cells are occupied by Mr. Nom, then the loop will never terminate. We’ll make sure that
     * will never happen in the next method.
     */
    private void placeStain()
    {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = snake.parts.size();

        for (int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }

        int stainX = random.nextInt(WORLD_WIDTH);
        int stainY = random.nextInt(WORLD_HEIGHT);

        while (true) {
            if (!fields[stainX][stainY]) {
                break;
            }
            stainX += 1;

            if (stainX >= WORLD_WIDTH) {
                stainX = 0;
                stainY += 1;

                if (stainY >= WORLD_HEIGHT) {
                    stainY = 0;
                }
            }
        }
        stain = new Stain(stainX, stainY, random.nextInt(3));
    }

    /**
     * The update() method is responsible for updating the World and all the objects in it
     * based on the delta time we pass to it. This method will be called each frame in the game
     * screen so that the World is updated constantly. We start off by checking whether the
     * game is over. If that’s the case, then we don’t need to update anything, of course. Next
     * we add the delta time to our accumulator. The while loop will use up as many ticks as
     * have been accumulated (e.g., when tickTime is 1.2 and one tick should take 0.5
     * seconds, we can update the world twice, leaving 0.2 seconds in the accumulator). This
     * is called a fixed-time-step simulation.
     * <p/>
     * In each iteration we first subtract the tick interval from the accumulator. Next we tell Mr.
     * Nom to advance. We check if he has bitten himself afterward, and set the game-over
     * flag if that’s the case. Finally we check whether Mr. Nom’s head is in the same cell as
     * the stain. If that’s the case we increment the score and tell Mr. Nom to grow. Next we
     * check whether Mr. Nom is composed of as many parts as there are cells in the world. If
     * that’s the case, the game is over and we return from the function. Otherwise we place a
     * new stain with the placeStain() method. The last thing we do is check whether Mr.
     * Nom has just eaten ten more stains. If that’s the case and our threshold is above zero,
     * we decrease it by 0.05 seconds. The tick will be shorter and thus make Mr. Nom move
     * faster.
     *
     * @param deltaTime responsible for updating the World and all objects in it based on this
     */
    public void update(float deltaTime)
    {
        if (gameOver) {
            return;
        }
        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            snake.advance();

            if (snake.checkBitten()) {
                gameOver = true;
                return;
            }

            SnakePart head = snake.parts.get(0);

            if (head.x == stain.x && head.y == stain.y) {
                score += SCORE_INCREMENT;
                snake.eat();
                if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true;
                    return;
                }
                placeStain();

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }
}