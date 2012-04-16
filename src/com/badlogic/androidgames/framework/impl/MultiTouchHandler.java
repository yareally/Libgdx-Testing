package com.badlogic.androidgames.framework.impl;

import android.view.MotionEvent;
import android.view.View;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pool;
import com.badlogic.androidgames.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandler
{
    private boolean[] isTouched = new boolean[20];
    private int[]     touchX    = new int[20];
    private int[]     touchY    = new int[20];
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents       = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    private float scaleX;
    private float scaleY;

    /**
     * @param view
     * @param scaleX
     * @param scaleY
     */
    public MultiTouchHandler(View view, float scaleX, float scaleY)
    {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>()
        {
            @Override
            public TouchEvent createObject()
            {
                return new TouchEvent();
            }
        };

        this.touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /**
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerId = event.getPointerId(pointerIndex);
            TouchEvent touchEvent;

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = this.touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = this.touchX[pointerId] = (int) (event.getX(pointerIndex) * this.scaleX);
                    touchEvent.y = this.touchY[pointerId] = (int) (event.getY(pointerIndex) * this.scaleY);
                    this.isTouched[pointerId] = true;
                    this.touchEventsBuffer.add(touchEvent);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = this.touchEventPool.newObject();

                    touchEvent.type = TouchEvent.TOUCH_UP;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = this.touchX[pointerId] = (int) (event.getX(pointerIndex) * this.scaleX);
                    touchEvent.y = this.touchY[pointerId] = (int) (event.getY(pointerIndex) * this.scaleY);
                    this.isTouched[pointerId] = false;
                    this.touchEventsBuffer.add(touchEvent);
                    break;

                case MotionEvent.ACTION_MOVE:
                    int pointerCount = event.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        pointerIndex = i;
                        pointerId = event.getPointerId(pointerIndex);

                        touchEvent = this.touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = this.touchX[pointerId] = (int) (event.getX(pointerIndex) * this.scaleX);
                        touchEvent.y = this.touchY[pointerId] = (int) (event.getY(pointerIndex) * this.scaleY);
                        this.touchEventsBuffer.add(touchEvent);
                    }
                    break;
            }

            return true;
        }
    }

    /**
     * @param pointer
     * @return
     */
    @Override
    public boolean isTouchDown(int pointer)
    {
        synchronized (this) {
            return pointer < 0 || pointer >= 20
                ? false
                : this.isTouched[pointer];
        }
    }

    /**
     * @param pointer
     * @return
     */
    @Override
    public int getTouchX(int pointer)
    {
        synchronized (this) {
            return pointer < 0 || pointer >= 20
                ? 0
                : this.touchX[pointer];

        }
    }

    /**
     * @param pointer
     * @return
     */
    @Override
    public int getTouchY(int pointer)
    {
        synchronized (this) {
            return pointer < 0 || pointer >= 20
                ? 0
                : this.touchY[pointer];
        }
    }

    /**
     * @return
     */
    @Override
    public List<TouchEvent> getTouchEvents()
    {
        synchronized (this) {
            int len = this.touchEvents.size();

            for (int i = 0; i < len; i++) {
                this.touchEventPool.free(this.touchEvents.get(i));
            }

            this.touchEvents.clear();
            this.touchEvents.addAll(this.touchEventsBuffer);
            this.touchEventsBuffer.clear();
            return this.touchEvents;
        }
    }
} 