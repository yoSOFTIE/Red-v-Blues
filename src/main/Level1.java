/*
 * Level1 class; implements the 'Level' interface
 *
 * models the layout for the first level of our game
 */
package main;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Level1 extends Level {

    // static level variables
    private ArrayList<Rectangle> walls;
    private ArrayList<Double[]> enemyCoords;
    private ArrayList<Double[]> chestCoords;
    private int enemyCount;
    private int chestCount;
    private double[] playerCoords;
    private char[][] level;
    private final Image background;

    /**
     * Constructor for our level object
     * W => Wall
     * B => Breakable or fake wall
     * C => Chest 
     * E => Enemy
     * P => Player start and respawn coord
     */
    public Level1() {
        this.walls = new ArrayList<>();
        this.enemyCoords = new ArrayList<>();
        this.chestCoords = new ArrayList<>();
        this.background = new Image("resources/maze1.png");
        this.enemyCount = 0;
        this.chestCount = 0;
        this.playerCoords = new double[2];
        this.level = new char[][] {
        	{'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ','P',' ',' ',' ',' ',' ','C','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','E',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W','W','W','W','W','W','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ','E',' ',' ',' ',' ',' ',' ',' ','W','W','W','W','W','W','W','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ',' ','E',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ','W','W',' ',' ',' ','W','W',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W','C',' ',' ',' ','W','W',' ',' ',' ','W','W',' ',' ',' ',' ',' ','W'},
        	{'W',' ','E',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ','E',' ','W','W',' ',' ',' ',' ',' ',' ',' ','E',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ','W','W','C',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ','C','W'},
        	{'W','W','W','W',' ',' ','W','W',' ',' ',' ',' ',' ','W','W','W','W','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W','W','W','W',' ','E','W','W',' ',' ',' ',' ',' ','W','W','W','W','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ','W'},
        	{'W',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ','E',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ','W'},
        	{'W','C',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','W','W',' ',' ',' ',' ',' ','W'},
        	{'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}
        };
    }

    /**
     * Create a new wall/hitbox for the given coordinates
     * from our level layout. We use boxes slgihtly smaller
     * than a given grid unit, else we'll always intersect
     * the box when moving into an adjacent space.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param fake  is this a 'fake' wall?
     * @return      the new Rectangle/wall
     */
    private Rectangle newWall(double x, double y, boolean fake) {
        Rectangle box = new Rectangle(x, y, 32, 32);
        box.setId("WALL_" + x + "_" + y);
        box.setX(x);
        box.setY(y);
        box.setFill((fake ? Color.BLACK : Color.SADDLEBROWN));
        box.setVisible(false);  // hide the actual box so we see the level image
        return box;
    }

    /**
     * build the actual rectangles for the walls
     */
    private void parseLevel() {
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                char c = level[i][j];
                if (c == 'W' || c == 'B' || c == 'E' || c == 'P' || c == 'C') {
                    double x = (j * 32); // calculate the X coordinate
                    double y = (i * 32); // calculate the y coordinate
                    if (c == 'E') {
                        enemyCount++;
                        enemyCoords.add(new Double[]{x, y});
                    } else if (c == 'P') {
                        playerCoords[0] = x;
                        playerCoords[1] = y;
                    } else if (c == 'C') {
                        chestCount++;
                        chestCoords.add(new Double[]{x, y});
                    } else {
                        walls.add(newWall(x, y, c == 'B'));
                    }
                }
            }
        }
    }

    /**
     * Accessor/getter method for the raster level image
     * @return  the image object for our level
     */
    public Image getImage() {
        return this.background;
    }
    /**
     * Accessor/getter method for player start coordinates
     * @return  the double array with x & y coords
     */
    public double[] playerStart() {
        return this.playerCoords;
    }

    /**
     * Accessor/getter method for treasure coordinates
     * @return  the arraylist of coordinates as double[]
     *          for chest/treasure items in our level
     */
    public ArrayList<Double[]> treasureCoords(){
        return this.chestCoords;
    }

    public int treasureCount() {
        return this.chestCount;
    }

    /**
     * Accessor/getter method for enemy count
     * @return  the number of enemies in the level
     *          as an integer
     */
    public int getEnemyCount() {
        return this.enemyCount;
    }

    /**
     * Accessor/getter method for starting enemy coordinates
     * @return  the arraylist of starting coordinates
     */
    public ArrayList<Double[]> getEnemyCoords() {
        return this.enemyCoords;
    }

    /**
     * Accessor/getter method for the array of walls
     * @return  the arraylist of walls/rectangles
     */
    public ArrayList<Rectangle> getWalls() {
        this.parseLevel();
        return walls;
    }

    /**
     * Accessor/getter for the 2D array map of the levl
     * @return  the 2D char array/map
     */
    public char[][] getMap() {
        return this.level;
    }
}
