package com.badlogic.androidgames.framework;

/**
 * Created by IntelliJ IDEA. User: admin Date: 9/18/11 Time: 5:07 AM To change
 * this template use File | Settings | File Templates.
 */

/**
 * methods to start playing the music stream, pausing and stopping it, and setting it to loop playback,
 * which means it will start from the beginning automatically when it reaches the end of the audio file.
 *
 */
public interface Music {

    /**
     *
     */
    public void play();

    /**
     *
     */
    public void stop();

    /**
     *
     */
    public void pause();

    /**
     *
     * @param looping
     */
    public void setLooping(boolean looping);

    /**
     *
     * @param volume float in the range of 0 (silent) to 1 (maximum volume)
     */
    public void setVolume(float volume);

    /**
     *
     * @return
     */
    public boolean isPlaying();

    /**
     *
     * @return
     */
    public boolean isStopped();

    /**
     * start from the beginning automatically when it reaches the end of the audio file
     * @return
     */
    public boolean isLooping();

    /**
     * this will close any system resources, such as the file the audio was streamed from
     */
    public void dispose();
}