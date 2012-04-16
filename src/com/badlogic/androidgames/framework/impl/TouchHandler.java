package com.badlogic.androidgames.framework.impl;

import android.view.View.OnTouchListener;
import com.badlogic.androidgames.framework.Input.TouchEvent;

import java.util.List;

public interface TouchHandler extends OnTouchListener
{
    /**
     *
     * @param pointer
     * @return
     */
    public boolean isTouchDown(int pointer);

    /**
     *
     * @param pointer
     * @return
     */
    public int getTouchX(int pointer);

    /**
     *
     * @param pointer
     * @return
     */
    public int getTouchY(int pointer);

    /**
     *
     * @return
     */
    public List<TouchEvent> getTouchEvents();
}