package com.badlogic.androidgames.framework.impl;

//~--- non-JDK imports --------------------------------------------------------

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;
import com.badlogic.androidgames.framework.*;

public abstract class AndroidGame extends Activity implements Game
{
    private Audio                 audio;
    private FileIO                fileIO;
    private Graphics              graphics;
    private Input                 input;
    private AndroidFastRenderView renderView;
    private Screen                screen;
    private WakeLock              wakeLock;

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = this.getResources().getConfiguration().orientation
            == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape
            ? 480
            : 320;
        int frameBufferHeight = isLandscape
            ? 320
            : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
        float scaleX = (float) frameBufferWidth / this.getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight / this.getWindowManager().getDefaultDisplay().getHeight();

        this.renderView = new AndroidFastRenderView(this, frameBuffer);
        this.graphics = new AndroidGraphics(this.getAssets(), frameBuffer);
        this.fileIO = new AndroidFileIO(this.getAssets());
        this.audio = new AndroidAudio(this);
        this.input = new AndroidInput(this, this.renderView, scaleX, scaleY);
        this.screen = this.getStartScreen();
        this.setContentView(this.renderView);

        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);

        this.wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    /**
     *
     */
    @Override
    public void onResume()
    {
        super.onResume();
        this.wakeLock.acquire();
        this.screen.resume();
        this.renderView.resume();
    }

    /**
     *
     */
    @Override
    public void onPause()
    {
        super.onPause();
        this.wakeLock.release();
        this.renderView.pause();
        this.screen.pause();

        if (isFinishing()) {
            this.screen.dispose();
        }
    }

    /**
     * @return
     */
    @Override
    public Input getInput()
    {
        return this.input;
    }

    /**
     * @return
     */
    @Override
    public FileIO getFileIO()
    {
        return this.fileIO;
    }

    /**
     * @return
     */
    @Override
    public Graphics getGraphics()
    {
        return this.graphics;
    }

    /**
     * @return
     */
    @Override
    public Audio getAudio()
    {
        return this.audio;
    }

    /**
     * @param screen
     */
    @Override
    public void setScreen(Screen screen)
    {
        if (screen == null) {
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    /**
     * @return
     */
    public Screen getCurrentScreen()
    {
        return this.screen;
    }
}

