/*
 * Player Class:
 * 
 * Models a 'player' controlled MOB/character
 */
package main;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Player extends MOB {

    // instance variables
	private final SimpleBooleanProperty playerDied;
	private final SimpleIntegerProperty lives;
	private final SimpleIntegerProperty health;
	private double startX;
	private double startY;

	public Player(Level lvl) {
		super(lvl);     // call the super constructor with the level we were passed
		this.playerDied = new SimpleBooleanProperty(false);
        this.health = new SimpleIntegerProperty(3);
        this.lives = new SimpleIntegerProperty(3);
		// add the actual images to our arraylist
		initSpriteImages(new String[] {"playerUp", "playerLeft", "playerDown", "playerRight"});
        hitBox.setId("PLAYER");
        hitBox.setFill(Color.BLUE);
		this.startX = lvl.playerStart()[0];
		this.startY = lvl.playerStart()[1];
	}

	public boolean isDead() {
	    return this.playerDied.get();
    }

    /**
     * Accessor/getter for remaining lives property
     * @return  reaming lives, as observable int
     */
    public SimpleIntegerProperty livesProperty() {
	    return this.lives;
    }

    /**
     * Accessor/getter for remaining health property
     * @return  reaming health, as observable int
     */
    public SimpleIntegerProperty healthProperty() {
        return this.health;
    }

    /**
     * Handles respawning a player.
     */
    public void reSpawn(){
        lives.set(lives.get() - 1);
        health.set(3);
	    sprite.setX(startX);
	    sprite.setY(startY);
	    this.playerDied.set(false);
    }

	/**
	 * Damage calculation method for player character
	 * 
	 * @param	dmg the integer value for the damage
	 * 			dealt to the player
	 */
	public void takeDamage(int dmg) {
		if (dmg > 0 && health.get() > dmg) {
			health.set(health.get() - dmg);
		} else if (health.get() <= dmg && lives.get() > 0) {
		    playerDied.set(true);
		}
	}

	/**
	 * Key handler for player movement
	 *
	 * @param key	the keycode to respond to
	 */
	 public void moveHandler(KeyEvent key) {
		switch(key.getCode().getName()) {
			// handle W, numpad-up, and normal Up arrow
			case "W":
			case "KP_Up":
			case "Up":
				moveUp(); break;
			// handle S, numpad-down, and normal Down arrow
			case "S":
			case "KP_Down":
			case "Down":
				moveDown(); break;
			// handle A, numpad-left and normal left arrow
			case "A":
			case "KP_Left":
			case "Left":
				moveLeft(); break;
			// handle D, numpad-right, and normal right arrow
			case "D":
			case "KP_Right":
			case "Right":
				moveRight(); break;
		}
	}
}
