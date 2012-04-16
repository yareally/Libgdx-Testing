package com.badlogic.androidgames.framework;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap {
    /**
     *
     * @return width of the Pixmap in pixels
     */
    public int getWidth();

    /**
     *
     * @return height of the Pixmap in pixels
     */
    public int getHeight();

    /**
     *
     * @return PixelFormat that the Pixmap is stored with in RAM
     */
    public PixmapFormat getFormat();

    /**
     * Pixmap instances use up memory and potentially other system resources. If we no longer need
     * them, we should dispose of them with this method.
     */
    public void dispose();
}