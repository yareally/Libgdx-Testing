package com.badlogic.androidgames.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable
{
    private AndroidGame game;
    private Bitmap      framebuffer;
    private Thread renderThread = null;
    private SurfaceHolder holder;
    private volatile boolean running = false;

    /**
     *
     * @param game
     * @param framebuffer
     */
    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }

    /**
     *
     */
    public void resume()
    {
        this.running = true;
        this.renderThread = new Thread(this);
        this.renderThread.start();
    }

    /**
     *
     */
    public void run()
    {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while (this.running) {
            if (!this.holder.getSurface().isValid()) {
                continue;
            }

            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            this.game.getCurrentScreen().update(deltaTime);
            this.game.getCurrentScreen().present(deltaTime);

            Canvas canvas = this.holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(this.framebuffer, null, dstRect, null);
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     *
     */
    public void pause()
    {
        this.running = false;
        while (true) {
            try {
                this.renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }
}