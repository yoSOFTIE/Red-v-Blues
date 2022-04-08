/*
 * Enemy Class:
 *
 * This models a generic 'Enemy' MOB
 */
package main;

import javafx.scene.paint.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends MOB {

	// instance variables
    private long lastMove;

    /**
     * Constructor for a simple 'Enemy' MOB
     */
	public Enemy(Level lvl, int num) {
		super(lvl);
		// add the actual images to our arraylist
		initSpriteImages(new String[] {"enemyUp", "enemyLeft", "enemyDown", "enemyRight"});
        hitBox.setId("ENEMY" + num);
        hitBox.setFill(Color.RED);
		this.lastMove = 0;
	}

    /**
     * See if an enemy is ready to move
     */
	public void moveCheck(long frameTime) {
	    // make sure we've finished the previous move
        // before moving again
		if (lastMove == 0) {
            lastMove = System.nanoTime();
        } else if (frameTime - lastMove > 490000000) {
		    lastMove = frameTime;
			this.move();
		}
	}
	/**
	 * Simple randomized movement for enemy
	 * sprites/objects
	 */
	private void move() {
		// get a random number between 0 and 3 for our direction
		switch (ThreadLocalRandom.current().nextInt(0, 4)) {
			case 0: moveUp(); break;
			case 1: moveLeft(); break;
			case 2: moveDown(); break;
			case 3: moveRight(); break;
		}
	}
}
