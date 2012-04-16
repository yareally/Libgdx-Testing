package com.badlogic.androidgames.framework.impl;

import android.view.View;
import android.view.View.OnKeyListener;
import com.badlogic.androidgames.framework.Input.KeyEvent;
import com.badlogic.androidgames.framework.Pool;
import com.badlogic.androidgames.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHandler implements OnKeyListener
{
    boolean[] pressedKeys = new boolean[128];
    Pool<KeyEvent> keyEventPool;
    List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>();
    List<KeyEvent> keyEvents       = new ArrayList<KeyEvent>();

    /**
     *
     * @param view
     */
    public KeyboardHandler(View view)
    {
        PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>()
        {
            public KeyEvent createObject()
            {
                return new KeyEvent();
            }
        };
        this.keyEventPool = new Pool<KeyEvent>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /**
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKey(View v, int keyCode, android.view.KeyEvent event)
    {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
            return false;
        }

        synchronized (this) {
            KeyEvent keyEvent = this.keyEventPool.newObject();
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();

            if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                keyEvent.type = KeyEvent.KEY_DOWN;

                if (keyCode > 0 && keyCode < 127) {
                    this.pressedKeys[keyCode] = true;
                }
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = KeyEvent.KEY_UP;

                if (keyCode > 0 && keyCode < 127) {
                    this.pressedKeys[keyCode] = false;
                }
            }
            this.keyEventsBuffer.add(keyEvent);
        }
        return false;
    }

    /**
     *
     * @param keyCode
     * @return
     */
    public boolean isKeyPressed(int keyCode)
    {
        if (keyCode < 0 || keyCode > 127) {
            return false;
        }
        return this.pressedKeys[keyCode];
    }

    /**
     *
     * @return
     */
    public List<KeyEvent> getKeyEvents()
    {
        synchronized (this) {
            int len = this.keyEvents.size();
            for (int i = 0; i < len; i++) {
                this.keyEventPool.free(this.keyEvents.get(i));
            }
            this.keyEvents.clear();
            this.keyEvents.addAll(this.keyEventsBuffer);
            this.keyEventsBuffer.clear();
            return this.keyEvents;
        }
    }
}