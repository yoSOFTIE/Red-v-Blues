/**
 * Level class - abstract
 *  this class outlines what's necessary for a
 *  class to be considered a full 'level' by our
 *  main game.
 *
 *  In particular, every 'Level' object must:
 *  1) provide us an image object to use
 *  2) give us a count for the number of enemies to spawn
 *  3) give us player start coordinates/position
 *  4) give us the starting coordinates for all enemies
 *  5) give us an arraylist of hitboxes for all the walls
 *  6) give us a 2D array "map" for the level
 */
package main;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public abstract class Level {

    public abstract Image getImage();

    public abstract double[] playerStart();

    public abstract int getEnemyCount();

    public abstract int treasureCount();

    public abstract ArrayList<Double[]> getEnemyCoords();

    public abstract ArrayList<Double[]> treasureCoords();

    public abstract ArrayList<Rectangle> getWalls();

    public abstract char[][] getMap();
}
