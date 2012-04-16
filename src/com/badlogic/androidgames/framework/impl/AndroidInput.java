package com.badlogic.androidgames.framework.impl;

import android.content.Context;
import android.os.Build;
import android.view.View;
import com.badlogic.androidgames.framework.Input;

import java.util.List;

public class AndroidInput implements Input
{
    private AccelerometerHandler accelHandler;
    private KeyboardHandler      keyHandler;
    private TouchHandler         touchHandler;

    /**
     * @param context
     * @param view
     * @param scaleX
     * @param scaleY
     */
    public AndroidInput(Context context, View view, float scaleX, float scaleY)
    {
        this.accelHandler = new AccelerometerHandler(context);
        this.keyHandler = new KeyboardHandler(view);

        if (Integer.parseInt(Build.VERSION.SDK) < 5) {
            this.touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        }
        else {
            this.touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
        }
    }

    /**
     * Figures out if a key was pressed or not by the corresponding numeric for the key that
     * the OS recognizes.
     *
     * @param keyCode - the number code that refers to a certain keyboard key that was pressed
     * @return whether the corresponding key is currently pressed or not
     */
    @Override
    public boolean isKeyPressed(int keyCode)
    {
        return this.keyHandler.isKeyPressed(keyCode);
    }

    /**
     * @param pointer - pointer ID for a finger: The first finger that goes down gets the pointer ID 0, the next the ID
     * 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps its ID for as
     * long as it is touching the screen. A new finger will get the the first free ID, which would be 0
     * in this example.
     * @return whether the given pointer is down
     */
    @Override
    public boolean isTouchDown(int pointer)
    {
        return this.touchHandler.isTouchDown(pointer);
    }

    /**
     * @param pointer - pointer ID for a finger: The first finger that goes down gets the pointer ID 0, the next the ID
     * 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps its ID for as
     * long as it is touching the screen. A new finger will get the the first free ID, which would be 0
     * in this example.
     * @return current x coordinates of given pointer returned by isTouchDown (note that this will be undefined if the
     *         corresponding pointer is not actually touching the screen
     */
    @Override
    public int getTouchX(int pointer)
    {
        return this.touchHandler.getTouchX(pointer);
    }

    /**
     * @param pointer - pointer ID for a finger: The first finger that goes down gets the pointer ID 0, the next the ID
     * 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps its ID for as
     * long as it is touching the screen. A new finger will get the the first free ID, which would be 0
     * in this example.
     * @return current y coordinates of given pointer returned by isTouchDown (note that this will be undefined if
     *         the corresponding pointer is not actually touching the screen
     */
    @Override
    public int getTouchY(int pointer)
    {
        return this.touchHandler.getTouchY(pointer);
    }

    /**
     * @return acceleration values of accelerometer x axis
     */
    @Override
    public float getAccelX()
    {
        return this.accelHandler.getAccelX();
    }

    /**
     * @return acceleration values of accelerometer y axis
     */
    @Override
    public float getAccelY()
    {
        return this.accelHandler.getAccelY();
    }

    /**
     *
     * @return acceleration values of accelerometer z axis
     */
    @Override
    public float getAccelZ()
    {
        return this.accelHandler.getAccelZ();
    }

    /**
     * event-based handling - these events are ordered according to when they occurred, with the newest event
     * being at the end of the list
     *
     * @return KeyEvent instances that were recorded since last time we called this method
     */
    @Override
    public List<KeyEvent> getKeyEvents()
    {

        return this.keyHandler.getKeyEvents();
    }

    /**
     * event-based handling - these events are ordered according to when they occurred, with the newest event
     * being at the end of the list
     *
     * @return TouchEvent instances that were recorded since last time we called this method
     */
    @Override
    public List<TouchEvent> getTouchEvents()
    {
        return this.touchHandler.getTouchEvents();
    }
}