package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.FileIO;

import java.io.*;

/**
 * Holds all the info related to displaying the user setting screen for the
 * game. Info here that the user changes will get stored on the sdcard.
 */
public class Settings
{
    /**
     * Determines whether sounds are played back or not.
     */
    public static boolean soundEnabled = true;

    /**
     * Default current high scores (currently showing 5)
     */
    public static int[] highscores = {100, 80, 50, 30, 10};

    /**
     * Filename to save settings under on the sdcard
     */
    private static final String DEFAULT_FILENAME = ".mrnom";

    /**
     * Number of high scores to show (currently set to how many we have in the
     * array)
     */
    private static final int MAX_DISPLAYED_SCORES = 5;


    /**
     * Loads the user's settings for the game from the sdcard. Assumes the sound
     * setting and each high score is stored on a separate line and reads them
     * in.
     * <p/>
     * If anything goes wrong (like sdcard not found), we fall back to the
     * defaults we set in the class properties.
     *
     * @param files - input/output object to read/write to the sdcard
     */
    public static void load(FileIO files)
    {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(DEFAULT_FILENAME)));
            soundEnabled = Boolean.parseBoolean(in.readLine());

            for (int i = 0; i < MAX_DISPLAYED_SCORES; ++i) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        }
        catch (IOException e) {
            // not needed, we have defaults in the class
        }
        catch (NumberFormatException e) {
            // also okay, we have defaults once again
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e) {
                // nothing needed for now
            }
        }
    }

    /**
     * Saves the user's settings to the sdcard (/sdcard/.mrnom). Settings are
     * serialized. Sound setting and each high score. If something goes wrong,
     * we just use the default values instead assigned to the class properties.
     *
     * @param files - input/output object to read/write to the sdcard
     */
    public static void save(FileIO files)
    {
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(DEFAULT_FILENAME)));
            out.write(Boolean.toString(soundEnabled));

            for (int i = 0; i < MAX_DISPLAYED_SCORES; ++i) {
                out.write(Integer.toString(highscores[i]));
            }
        }

        catch (IOException e) {
            // not needed
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e) {
                // nothing neededdddd
            }
        }
    }

    /**
     * Adds a high score to the game in the correct order of best score to
     * worse. We currently hold a max of 5.
     *
     * @param score - new high score to add.
     */
    public static void addScore(int score)
    {
        for (int i = 0; i < MAX_DISPLAYED_SCORES; ++i) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; --j) {
                    highscores[j] = highscores[j - 1];
                    highscores[i] = score;
                    break;
                }
            }
        }
    }
}
