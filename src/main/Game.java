/*
 * Main game object/class
 *
 * Sprites used from https://opengameart.org/content/700-sprites
 * by Philipp Lensenn, outer-court.com
 * CC Attribution 3.0 http://creativecommons.org/licenses/by/3.0/
 */
package main;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Game extends Pane implements Runnable {

	// instance variables
	private ArrayList<MOB> mobs;
	private ArrayList<Rectangle> hitBoxes;
	private ArrayList<Treasure> treasure;
	private Player player;
	private final ImageView background = new ImageView();
	private Level level;
	private SimpleIntegerProperty score;
	private int enemyCount;
	private int chestCount;
	private HBox GUIView;
	private Label scoreLbl; // text label for displaying current score
	private Label livesLbl; // text label for displaying remaining lives
	private Label healthLbl; // text label for displaying remaining health
	private Music music = new Music();;

	/**
	 * Constructor
	 */
	public Game(Stage stage) {
		// create the score counter
		this.score = new SimpleIntegerProperty(0);
		// create our mobs arraylist
		this.mobs = new ArrayList<>();
		// create the arraylist for MOB & wall hitboxes
		this.hitBoxes = new ArrayList<>();
		// create an arraylist for our treasure objects
		this.treasure = new ArrayList<>();

		this.enemyCount = 0;
		this.loadLevel(1); // load the first level of our game.
		this.initGUI(); // load/initialize the GUI
		// run our main loop once the stage is shown
		stage.setOnShown(e -> this.run());
	}

	/**
	 * initialized the gui/overlay to track player lives, level, total score, etc.
	 * 
	 * @return a new HBOX with our GUI elements
	 */
	private void initGUI() {

	}

	/**
	 * Loads the default/background images for our lvlMap
	 * 
	 * @param in the integer value for the lvlMap image to load all images are
	 *           'lvlMap' + in
	 */
	private void loadLevel(int in) {
		hitBoxes.clear(); // clear the list of hitboxes
		mobs.clear(); // remove any existing old mobs
		switch (in) {
		case 1:
			this.level = new Level1();
			break;
		case 2:
			this.level = new Level1(); // placeholder for second level
			break;
		}
		background.setImage(level.getImage()); // get the background image
		hitBoxes.addAll(level.getWalls()); // get all the wall hitboxes
		enemyCount = level.getEnemyCount(); // get the enemy count from the level
		chestCount = level.treasureCount(); // get the treasure count from the level
		this.getChildren().add(background); // add the lvlMap imageview to scene
		this.getChildren().addAll(hitBoxes); // add all the hitboxes for the walls to the scene
		this.initTreasure(chestCount); // add the treasure chests for the level
		this.initMobs(enemyCount); // initialize our mobs
	}

	/**
	 * Initializes the default mobs for a given level
	 */
	private void initMobs(int count) {
		player = (player == null ? new Player(this.level) : player); // load the player
		player.setStartPoint(level.playerStart()[0], level.playerStart()[1]);
		mobs.add(player); // add the player to the mob array first, so always index 0
		// add our enemies to the arraylist after the player
		for (int i = 0; i < count; i++) {
			mobs.add(new Enemy(this.level, i));
		}
		// set the starting coordinates for each enemy, skip the player/index 0
		for (int i = 1; i < mobs.size(); i++) {
			/*
			 * the enemy coordinates are stored in a 2-element array with index 0 = x-coord,
			 * and index 1 = y-coord
			 */
			Double[] coords = level.getEnemyCoords().get(i - 1);
			mobs.get(i).setStartPoint(coords[0], coords[1]);
		}
		// iterate over the list of mobs and...
		for (int i = 0; i < mobs.size(); i++) {
			MOB m = mobs.get(i);
			hitBoxes.add(m.getHitbox()); // add it's hitbox to our hitbox list
			this.getChildren().add(m.getHitbox()); // add the hitbox to the scene
			this.getChildren().add(m.getView()); // add it's view to our scene
		}
	}

	private void initTreasure(int count) {
		for (int i = 0; i < count; i++) {
			Double[] coords = level.treasureCoords().get(i);
			Treasure t = new Treasure(i, coords[0], coords[1]);
			treasure.add(t);
			hitBoxes.add(t.getHitbox());
			this.getChildren().addAll(t.getHitbox(), t.getView());
		}
	}

	private Treasure getTreasure(Rectangle r) {
		for (int i = 0; i < treasure.size(); i++) {
			if (treasure.get(i).getHitbox().equals(r)) {
				return treasure.get(i);
			}
		}
		return null;
	}

	/**
	 * Collision check method to determine if the player has hit enemies, or
	 * treasure/chests
	 */
	private void collisionCheck() {
		for (int i = 1; i < hitBoxes.size(); i++) {
			Rectangle r = hitBoxes.get(i); // get the hitbox to check
			boolean bool = player.getHitbox().intersects(r.getBoundsInParent()); // see if the boxes intersect
			if (bool) {
				if (r.getFill().equals(Color.RED)) { // RED = enemy collision
					player.takeDamage(1); // if we're the player, take damage
					music.playDeath();
				} else if (r.getFill().equals(Color.BLACK)) { // BLACK = fake wall
					// TODO fakeWallCollision();
					// play a secret sound, ala zelda.
				} else if (r.getFill().equals(Color.GREEN)) { // GREEN = treasure/chest
					Treasure t = getTreasure(r);
					if (!t.isOpen()) {
						score.set(score.get() + 10); // increase the score
						chestCount--; // decrease our global chest count to track end of level
						t.openNow(); // open the chest so it can't be opened twice
						music.playTreasureSound();
					}
				}
			}
		}
	}

	/**
	 * Main game loop method.
	 */
	private void play() {
		// Creates gameLoop, where most operations will be handled
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				// check if we're ready to move the enemies
				for (int i = 0; i < mobs.size(); i++) {
					MOB m = mobs.get(i);
					if (m instanceof Enemy) {
						((Enemy) m).moveCheck(arg0);
					}
				}
				// check for collisions with the player and other objects
				collisionCheck();
				// check if our player isDead/we need to respawn
				if (player.livesProperty().get() > 0 && player.isDead()) {
					player.reSpawn();
				}
				// check if the level is over
				if (chestCount == 0) {
					// DO Something for level over!
				}
			}
		};
		// start the loop now
		gameLoop.start();
	}

	/**
	 * Main runnable: 1) register our key handler 2) register our game over listener
	 * 3) starts playing the game
	 */
	@Override
	public void run() {
		// Uncomment to see the actual stage layout coordinates for the scene
		// this is useful to determine the width and height of the window frame
		// System.out.println("layoutX: " + this.getScene().getX());
		// System.out.println("layoutY: " + this.getScene().getY());
		this.getScene().setOnKeyPressed(e -> player.moveHandler(e));
		// this.getScene().setOnKeyPressed(player::moveHandler); java8+ method reference
		// style
		// start the game loop now
		music.playBGM();
		this.play();
	}
}
