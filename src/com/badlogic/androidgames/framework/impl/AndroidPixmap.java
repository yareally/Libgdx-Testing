package com.badlogic.androidgames.framework.impl;

import android.graphics.Bitmap;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;
import com.badlogic.androidgames.framework.Pixmap;

/**
 * PixelMaps are a way of displaying images (or programmatically drawn images) on the screen so they can
 * stretch if the device is not the same resolution as the image (i.e. image scaling)
 *
 * @see "http://pixelmapping.wikispaces.com/Pixel+mapping+explained"
 * @see "http://pixelmapping.wikispaces.com/"
 */
public class AndroidPixmap implements Pixmap
{
    protected Bitmap       bitmap;
    private   PixmapFormat format;

    /**
     * Creates a new PixelMap to display on the screen.
     *
     * @param bitmap - the image you want to load into the PixelMap
     * @param format - the format of the PixelMap (probably ARGB8888 or RGB565)
     */
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format)
    {
        this.bitmap = bitmap;
        this.format = format;
    }

    /**
     * @return width of the Pixmap in pixels
     */
    @Override
    public int getWidth()
    {
        return this.bitmap.getWidth();
    }

    /**
     * @return height of the Pixmap in pixels
     */
    @Override
    public int getHeight()
    {
        return this.bitmap.getHeight();
    }

    /**
     * @return PixelFormat that the Pixmap is stored with in RAM (probably ARGB8888 or RGB565)
     */
    @Override
    public PixmapFormat getFormat()
    {
        return this.format;
    }

    /**
     * Pixmap instances use up memory and potentially other system resources. If we no longer need
     * them, we should dispose of them with this method.
     */
    @Override
    public void dispose()
    {
        this.bitmap.recycle();
    }
}