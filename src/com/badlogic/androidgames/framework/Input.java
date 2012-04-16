package com.badlogic.androidgames.framework;

import java.util.List;

public interface Input
{
    /**
     * KeyEvent instance records its type, the key's code, and its Unicode character in case the event's type is KEY_UP
     */
    public static class KeyEvent
    {
        public static final int KEY_DOWN = 0;
        public static final int KEY_UP   = 1;

        public int  type;
        public int  keyCode;
        public char keyChar;
    }

    /**
     * The TouchEvent code is similar, and holds the TouchEvent’s type, the position of the finger relative to the UI
     * component’s origin, and the pointer ID that was given to the finger by the touchscreen driver. The pointer ID for
     * a finger will stay the same for as long as that finger is on the screen. The first finger that goes down gets the
     * pointer ID 0, the next the ID 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps
     * its ID for as long as it is touching the screen. A new finger will get the the first free ID, which would be 0 in
     * this example.
     */
    public static class TouchEvent
    {
        public static final int TOUCH_DOWN    = 0;
        public static final int TOUCH_UP      = 1;
        public static final int TOUCH_DRAGGED = 2;

        public int type;
        public int x, y;
        public int pointer;
    }

    /**
     * @param keyCode - the key that was pressed as a numeric value
     * @return whether the corresponding key is currently pressed or not
     */
    public boolean isKeyPressed(int keyCode);

    /**
     * @param pointer - pointer ID for a finger: The first finger that goes down gets the pointer ID 0, the next the ID
     * 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps its ID for as
     * long as it is touching the screen. A new finger will get the the first free ID, which would be 0
     * in this example.
     * @return whether the given pointer is down
     */
    public boolean isTouchDown(int pointer);

    /**
     * @param pointer - pointer ID for a finger: The first finger that goes down gets the pointer ID 0, the next the ID
     * 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps its ID for as
     * long as it is touching the screen. A new finger will get the the first free ID, which would be 0
     * in this example.
     * @return current x coordinates of given pointer returned by isTouchDown (note that this will be undefined if the
     *         corresponding pointer is not actually touching the screen
     */
    public int getTouchX(int pointer);

    /**
     * @param pointer - pointer ID for a finger: The first finger that goes down gets the pointer ID 0, the next the ID
     * 1, and so on. If two fingers are down and finger 0 is lifted, then finger 1 keeps its ID for as
     * long as it is touching the screen. A new finger will get the the first free ID, which would be 0
     * in this example.
     * @return current y coordinates of given pointer returned by isTouchDown (note that this will be undefined if
     *         the corresponding pointer is not actually touching the screen
     */
    public int getTouchY(int pointer);

    /**
     * @return acceleration values of accelerometer x axis
     */
    public float getAccelX();

    /**
     * @return acceleration values of accelerometer y axis
     */
    public float getAccelY();

    /**
     * @return acceleration values of accelerometer z axis
     */
    public float getAccelZ();

    /**
     * event-based handling - these events are ordered according to when they occurred, with the newest event
     * being at the end of the list
     *
     * @return KeyEvent instances that were recorded since last time we called this method
     */
    public List<KeyEvent> getKeyEvents();

    /**
     * event-based handling - these events are ordered according to when they occurred, with the newest event
     * being at the end of the list
     *
     * @return TouchEvent instances that were recorded since last time we called this method
     */
    public List<TouchEvent> getTouchEvents();
}