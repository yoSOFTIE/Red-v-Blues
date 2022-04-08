package main;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    // instance variables
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final double width;
    private final double height;
    private int lastIndex;  // the index of the last image shown

    /**
     * Constructor for our sprite animation:
     * @param imageView the imageview to work with
     * @param duration  the duration of a full animation cycle
     * @param count     the total number of frames in the animation
     * @param columns   the number of columns in the sprite sheet
     * @param offsetX   the x-coordinate of the very first frame
     * @param offsetY   the y-coordinate of the very first frame
     * @param width     the width of every frame
     * @param height    the height of every frame
     */
    public SpriteAnimation(ImageView imageView, Duration duration, int count,
           int columns, int offsetX, int offsetY, double width, double height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final double x = (index % columns) * width  + offsetX;
            final double y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}

/* REFERENCES:
 * 
 * SpriteAnimation.java borrowed from the following site:
 * Creating a Sprite Animation with JavaFX | Mike's Blog
 * http://blog.netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 */