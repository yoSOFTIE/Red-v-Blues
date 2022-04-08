/*
 * Movable OBject (MOB) Class:
 * 
 * Parent class for movable characters/objects
 * e.g. player and enemies.
 */
package main;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;

public class MOB {

	private ArrayList<Image> sprites;
	protected final ImageView sprite;
	private final SpriteAnimation anim;
	protected Rectangle hitBox;
    private double lastX;
    private double lastY;
    private int width;
    private final Duration speed;
    private Level lvl;

    /**
     * Constructor for a character/movable entity
     */
	public MOB(Level lvl) {
		this.lvl = lvl;
		this.width = 32;   // sets the number of pixels to move
		this.lastX = 0.0;  // stores the last translateX value
		this.lastY = 0.0;  // stores the last translateY value
		this.sprites = new ArrayList<>();
		this.sprite = new ImageView();
		sprite.setViewport(new Rectangle2D(0, 0, 32, 32));
		// create the hitbox for the MOB
		this.hitBox = initHitBox();
		// setup our timeline/animation
		this.speed = Duration.millis(500);
		this.anim = new SpriteAnimation(sprite, speed, 4, 4, 0, 0, 32,32);
	}

	/**
	 * Initialize our rectangular hitbox for our mob
	 * and bind the x & y coordinates to our imageview
     *
     * NOTE: A better approach would create multiple
     * rectangles for each MOB, to fit to the shape
     * of the image, or if you only need a rectangle
     * then just use the bounds of the imageview.
	 * @return	the new rectangle/hitbox
	 */
	private Rectangle initHitBox() {
		Rectangle box = new Rectangle(30, 30);	//make it slightly smaller than one 32x32 square
		box.setVisible(false);	// hide the actual shape for the hitbox
		box.xProperty().bind(sprite.xProperty().add(1));	// offset the 1px border
		box.yProperty().bind(sprite.yProperty().add(1));	// offset the 1px border
		return box;
	}

    /**
     * setLevel: mutator/setter method to reset the
     * level reference variable in our object. Used
     * whenever changing levels.
     * @param lvl   the new level we're 'playing'
     */
	public void setLevel(Level lvl) {
	    this.lvl = lvl;
    }

    /**
     * Set the starting coordinates for the MOB
     * @param x		the starting X position as offset from center
     * @param y		the starting Y value as offset from center
     */
    public void setStartPoint(double x, double y) {
        sprite.setX(x);
        sprite.setY(y);
    }

	/**
	 * Adds the given Array of string to our
	 * arraylist for sprite use, as Images
	 * @param images	the list of image names in resources
	 *                  to find and add to our arraylist
	 */
	protected void initSpriteImages(String[] images) {
		for (String s : images) {
			Image i = new Image("resources/" + s + ".png");
			sprites.add(i);
		}
		this.sprite.setImage(sprites.get(2));
	}

	/**
	 * Accessor/getter method for the imageview
	 * @return	the sprite imageview
	 */
	public ImageView getView() {
		return this.sprite;
	}

	/**
	 * Accessor/getter method for the hitbox
	 * @return  the rectangle hitbox for this MOB
	 */
	public Rectangle getHitbox() {
		return this.hitBox;
	}

    /**
     * Checks to see if the MOB can legally move according to
     * the last move command; i.e. make sure the space
     * isn't a wall.
     * @param dir   the direction we're trying to move, U,D,L,R
     * @return      true if we can move/not a wall, false if we
     *              can't move/the space is a wall
     */
	private synchronized boolean canMove(char dir) {
		int x = (int) Math.round(sprite.getX() / 32);
		int y = (int) Math.round(sprite.getY() / 32);
		boolean bool = false;
		switch (dir) {
			case 'U': bool = !(lvl.getMap()[y-1][x] == 'W'); break;
			case 'D': bool = !(lvl.getMap()[y+1][x] == 'W'); break;
			case 'L': bool = !(lvl.getMap()[y][x-1] == 'W'); break;
			case 'R': bool = !(lvl.getMap()[y][x+1] == 'W'); break;
		}
		return bool;
	}

	/**
	 * Move the MOB up
	 */
	public void moveUp() {
		if (canMove('U')) {
			moveNow(0, 'U', -1);
		}
	}

	/**
	 * Move the MOB left
	 */
	public void moveLeft() {
		if (canMove('L')) {
			moveNow(1, 'L', -1);
		}
	}

	/**
	 * Move the MOB down
	 */
	public void moveDown() {
		if (canMove('D')) {
			moveNow(2, 'D', 1);
		}
	}

	/**
	 * move the MOB right
	 */
	public void moveRight() {
		if (canMove('R')) {
			moveNow(3, 'R', 1);
		}
	}

    /**
     * Actually move the MOB now:
     * @param img   the index of the sprite sheet/image
     * @param dir   the direction to move: U,D,L,R
     * @param mult  the multiplier for the actual move
     *              calculation: e.g. if moving left,
     *              current X + ((width) * -1) = -width
     */
	private void moveNow(int img, char dir, int mult) {
        anim.play();
        this.sprite.setImage(sprites.get(img));
        if (dir == 'U' || dir == 'D') {
            lastY = sprite.getY();
            sprite.setY((mult * width) + lastY);
        } else {
            lastX = sprite.getX();
            sprite.setX((mult * width) + lastX);
        }
    }
}