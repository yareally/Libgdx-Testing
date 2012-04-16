package com.badlogic.androidgames.framework.impl;

import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;

import java.io.IOException;
import java.io.InputStream;

public class AndroidGraphics implements Graphics
{
/*  All the drawing methods except Graphics.clear() will automatically perform
    blending for each pixel they touch. We could disable blending on a
    case-by-case basis to speed up the drawing a little bit, but that would
    complicate our implementation. Usually we can get away with having blending
    enabled all the time for simple games like Mr. Nom.
*/

    private AssetManager assets;
    private Bitmap       frameBuffer;
    private Canvas       canvas;
    private Paint        paint;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    /**
     * Creates an implementation of the Graphics Interface that draws pixels,
     * lines, rectangles and Pixmaps to the frameBuffer. Also creates a Canvas
     * object to draw on and Paint object to do the actual drawing.
     * 
     * @param assets - Used to load frameBuffer instances
     * @param frameBuffer - Canvas to paint graphics to.
     */
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer)
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    /**
     * Loads an image given in either JPEG or PNG format. We specify
     * a desired format for the resulting Pixmap, which is a hint for
     * the loading mechanism. The resulting Pixmap  might have a
     * different format. We do this so we can somewhat control the
     * memory footprint of our loaded images (e.g., by loading RGB888
     * or ARGB8888 images as RGB565 or ARGB4444 images).
     * 
     * @param fileName - Specifies an asset in our application’s APK file
     * @param format - one of the formats specified in the PixmapFormat enum
     * (ARGB8888, ARGB4444, RGB565, etc)
     * @return our image in a pixel map.
     */
    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format)
    {
        Config config = null;
        if (format == PixmapFormat.RGB565) {
            config = Config.RGB_565;
        }
        else if (format == PixmapFormat.ARGB4444) {
            config = Config.ARGB_4444;
        }
        else {
            config = Config.ARGB_8888;
        }

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = this.assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) {
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565) {
            format = PixmapFormat.RGB565;
        }
        else if (bitmap.getConfig() == Config.ARGB_4444) {
            format = PixmapFormat.ARGB4444;
        }
        else {
            format = PixmapFormat.ARGB8888;
        }

        return new AndroidPixmap(bitmap, format);
    }

    /**
     * Clears the complete frameBuffer with the given color.
     * All colors in our little framework will be specified as 32-bit
     * ARGB8888 values (Pixmaps might of course have a different format).
     * 
     * @param color - The color to clear
     */
    @Override
    public void clear(int color)
    {
        this.canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    /**
     * Sets the pixel at (x,y) in the frameBuffer to the given color.
     * Coordinates outside the screen will be ignored. This is called clipping.
     *  
     * @param x - the horizontal location of the pixel.
     * @param y - the vertical location of the pixel.
     * @param color the color to set the pixel to. Probably ARGB8888
     */
    @Override
    public void drawPixel(int x, int y, int color)
    {
        this.paint.setColor(color);
        this.canvas.drawPoint(x, y, this.paint);
    }

    /**
     * Similar to drawPixel. Specify a start point and an endpoint for the line
     * and a color for the line. Coordinates outside the screen will be ignored.
     * 
     * @param x - horizontal start point
     * @param y - vertical start point
     * @param x2 - horizontal end point
     * @param y2 - vertical end point
     * @param color - the color for the line. Probably ARGB8888
     */
    @Override
    public void drawLine(int x, int y, int x2, int y2, int color)
    {
        this.paint.setColor(color);
        this.canvas.drawLine(x, y, x2, y2, this.paint);
    }

    /**
     * Draws a rectangle to the frameBuffer.
     * 
     * @param x - horizontal start point in the top left corner
     * @param y - vertical start point in the top left corner
     * @param width - rectangle width
     * @param height - rectangle height
     * @param color - color to fill the rectangle with.
     */
    @Override
    public void drawRect(int x, int y, int width, int height, int color)
    {
        this.paint.setColor(color);
        this.paint.setStyle(Style.FILL);
        this.canvas.drawRect(x, y, x + width - 1, y + width - 1, this.paint);
    }

    /**
     * Draws rectangular portions of a Pixmap to the frameBuffer. The (x,y)
     * coordinates specify the top-left corner’s position of the Pixmap’s target
     * location in the frameBuffer. srcX and srcY specify the corresponding
     * top-left corner of the rectangular region that is used from the Pixmap,
     * given in the Pixmap’s own coordinate system.
     * 
     * @param pixmap - the pixmap to draw to.
     * @param x - Pixmap's horizontal start point in the top left corner.
     * @param y - Pixmap's vertical start point in the top left corner.
     * @param srcX - horizontal start point in the rectangle.
     * @param srcY - vertical start point in the rectangle.
     * @param srcWidth - size of the portion should we take from the Pixmap.
     * @param srcHeight - size of the portion should we take from the Pixmap.
     */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight)
    {
        this.srcRect.left = srcX;
        this.srcRect.top = srcY;
        this.srcRect.right = srcX + srcWidth - 1;
        this.srcRect.bottom = srcY + srcHeight - 1;

        this.dstRect.left = x;
        this.dstRect.top = y;
        this.dstRect.right = x + srcWidth - 1;
        this.dstRect.bottom = y + srcHeight - 1;

        this.canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, this.srcRect, this.dstRect, null);
    }

    /**
     * Draws the Pixmap to the frameBuffer. The (x,y) coordinates specify the
     * top-left corner’s position of the Pixmap’s target location in the
     * frameBuffer.
     * 
     * @param pixmap - the pixmap to draw to.
     * @param x - Pixmap's horizontal start point in the top left corner.
     * @param y - Pixmap's vertical start point in the top left corner.
     */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y)
    {
        this.canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
    }

    /**
     * Get the width of the frameBuffer in pixels
     * 
     * @return - the width in pixels.
     */
    @Override
    public int getWidth()
    {
        return this.frameBuffer.getWidth();
    }

    /**
     * Get the height of the frameBuffer in pixels
     *
     * @return - the height in pixels.
     */
    @Override
    public int getHeight()
    {
        return this.frameBuffer.getHeight();
    }
}