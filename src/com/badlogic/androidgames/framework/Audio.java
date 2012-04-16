package com.badlogic.androidgames.framework;

/**
 * Audio interface allows us to create new Music and Sound instances. The methods Audio.newMusic() and Audio.newSound()
 * both take a filename as an argument and throw an IOException in case the loading process fails (when the specified
 * file does not exist or is corrupt)
 */
public interface Audio {
    /**
     * a music instance represents a streamed audio file
     * @param filename - asset files in our application APK file
     * @return
     */
    public Music newMusic(String filename);

    /**
     * a sound instance represents a short sound effect that we keep entirely in memory
     * @param filename - asset files in our application APK file
     * @return
     */
    public Sound newSound(String filename);
}
