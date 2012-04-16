package com.badlogic.androidgames.framework.impl;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.badlogic.androidgames.framework.Music;

import java.io.IOException;


public class AndroidMusic implements Music, OnCompletionListener
{
    private MediaPlayer mediaPlayer;
    private boolean isPrepared = false;

    /**
     *
     * @param assetDescriptor
     */
    public AndroidMusic(AssetFileDescriptor assetDescriptor)
    {
        this.mediaPlayer = new MediaPlayer();
        try {
            this.mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            this.mediaPlayer.prepare();
            this.isPrepared = true;
            this.mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    /**
     *
     */
    @Override
    public void dispose()
    {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
        }
        this.mediaPlayer.release();

    }

    /**
     *
     * @return
     */
    @Override
    public boolean isLooping()
    {
        return this.mediaPlayer.isLooping();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isPlaying()
    {
        return this.mediaPlayer.isPlaying();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isStopped()
    {
        return !this.isPrepared;
    }

    /**
     *
     */
    @Override
    public void play()
    {
        if (this.mediaPlayer.isPlaying()) {
            return;
        }

        try {
            synchronized (this) {
                if (!this.isPrepared) {
                    this.mediaPlayer.prepare();
                }
                this.mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param isLooping
     */
    @Override
    public void setLooping(boolean isLooping)
    {
        this.mediaPlayer.setLooping(isLooping);
    }

    /**
     *
     * @param volume
     */
    @Override
    public void setVolume(float volume)
    {
        this.mediaPlayer.setVolume(volume, volume);
    }

    /**
     *
     */
    @Override
    public void stop()
    {
        this.mediaPlayer.stop();
        synchronized (this) {
            this.isPrepared = false;
        }
    }

    /**
     *
     */
    @Override
    public void pause()
    {
        this.mediaPlayer.pause();

        try {
            synchronized (this) {
                if (!this.isPrepared) {
                    this.mediaPlayer.prepare();
                }
                this.mediaPlayer.stop();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param player
     */
    @Override
    public void onCompletion(MediaPlayer player)
    {
        synchronized (this) {
            this.isPrepared = false;
        }
    }
}