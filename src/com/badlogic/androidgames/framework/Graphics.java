package com.badlogic.androidgames.framework;

public interface Graphics {

/*  All the drawing methods except Graphics.clear() will automatically perform
    blending for each pixel they touch, as outlined in the previous section.
    We could disable blending on a case-by-case basis to speed up the drawing a
    little bit, but that would complicate our implementation.
    Usually we can get away with having blending enabled all the time for
    simple games like Mr. Nom.
*/

    /**
     * Encodes the different pixel formats we will support.
     * We have the different methods of our Graphics interface
     * @see "http://en.wikipedia.org/wiki/RGBA_color_space" (for ARGB8888)
     */
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
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
     * @return The loaded image
     */
    public Pixmap newPixmap(String fileName, PixmapFormat format);

    /**
     * Clears the complete framebuffer with the given color.
     * All colors in our little framework will be specified as 32-bit
     * ARGB8888 values (Pixmaps might of course have a different format).
     * 
     * @param color - The color to clear
     * @see "http://en.wikipedia.org/wiki/RGBA_color_space"
     */
    public void clear(int color);

    /**
     * Sets the pixel at (x,y) in the framebuffer to the given color.
     * Coordinates outside the screen will be ignored. This is called clipping.
     *
     * @param x - the horizontal location of the pixel.
     * @param y - the vertical location of the pixel.
     * @param color the color to set the pixel to. Probably ARGB8888 or RGB565
     * @see "http://en.wikipedia.org/wiki/RGBA_color_space"
     */
    public void drawPixel(int x, int y, int color);

    /**
     * Similar to drawPixel. Specify a start point and an endpoint for the line
     * and a color for the line. Coordinates outside the screen will be ignored.
     *
     * @param x - horizontal start point
     * @param y - vertical start point
     * @param x2 - horizontal end point
     * @param y2 - vertical end point
     * @param color - the color for the line. Probably ARGB8888
     * @see "http://en.wikipedia.org/wiki/RGBA_color_space"
     */
    public void drawLine(int x, int y, int x2, int y2, int color);

    /**
     * Draws a rectangle to the framebuffer.
     * 
     * @param x - horizontal start point in the top left corner
     * @param y - vertical start point in the top left corner
     * @param width - rectangle width
     * @param height - rectangle height
     * @param color - color to fill the rectangle with.
     * @see "http://en.wikipedia.org/wiki/RGBA_color_space" Probably ARGB8888
     */
    public void drawRect(int x, int y, int width, int height, int color);

    /**
     * Draws rectangular portions of a Pixmap to the framebuffer. The (x,y)
     * coordinates specify the top-left corner’s position of the Pixmap’s target
     * location in the framebuffer. srcX and srcY specify the corresponding
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
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    /**
     * Draws the Pixmap to the framebuffer. The (x,y) coordinates specify the
     * top-left corner’s position of the Pixmap’s target location in the
     * framebuffer.
     * 
     * @param pixmap - the pixmap to draw to.
     * @param x - Pixmap's horizontal start point in the top left corner.
     * @param y - Pixmap's vertical start point in the top left corner.
     */
    public void drawPixmap(Pixmap pixmap, int x, int y);

    /**
     * Get the width of the framebuffer in pixels
     * @return width in pixels
     */
    public int getWidth();

    /**
     * Get the height of the framebuffer in pixels
     * @return height in pixels
     */
    public int getHeight();
} 
