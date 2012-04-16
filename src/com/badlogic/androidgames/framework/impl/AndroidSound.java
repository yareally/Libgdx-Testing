package com.badlogic.androidgames.framework.impl;

import android.media.SoundPool;
import com.badlogic.androidgames.framework.Sound;

public class AndroidSound implements Sound
{
    private int       soundId;
    private SoundPool soundPool;

    /**
     *
     * @param soundPool
     * @param soundId
     */
    public AndroidSound(SoundPool soundPool, int soundId)
    {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    /**
     *
     * @param volume
     */
    @Override
    public void play(float volume)
    {
        this.soundPool.play(this.soundId, volume, volume, 0, 0, 1);
    }

    /**
     *
     */
    @Override
    public void dispose()
    {
        this.soundPool.unload(this.soundId);
    }
}
