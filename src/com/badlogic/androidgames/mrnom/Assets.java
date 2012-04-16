package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

/**
 * Stores all the pixmap and sound objects that we have loaded from the assets.
 * By doing this, we can then call an asset by doing something like this:
 * <p/>
 * game.getGraphics().drawPixmap(Assets.background, 0, 0);
 * <p/>
 * or something like:
 * <p/>
 * Assets.click.play(1);
 * <p/>
 * Since everything in this class is public and NOT final, you have to be
 * careful not to override anything unless you mean to. Kind of an antipattern,
 * but whateverrr, im not being graded.
 * <p/>
 * TODO: hide the assets behind setters and getters in a singleton class.
 */
public class Assets
{
    public static Pixmap background;
    public static Pixmap logo;
    public static Pixmap mainMenu;
    public static Pixmap buttons;
    public static Pixmap help1;
    public static Pixmap help2;
    public static Pixmap help3;
    public static Pixmap numbers;
    public static Pixmap ready;
    public static Pixmap pause;
    public static Pixmap gameOver;
    public static Pixmap headUp;
    public static Pixmap headLeft;
    public static Pixmap headDown;
    public static Pixmap headRight;
    public static Pixmap tail;
    public static Pixmap stain1;
    public static Pixmap stain2;
    public static Pixmap stain3;

    public static Sound click;
    public static Sound eat;
    public static Sound bitten;
} 
