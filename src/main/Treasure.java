/*
 * Treasure Class:
 *
 * Models a collectible 'treasure' object
 */
package main;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Treasure {

    // instance variables
    private Image image;
    private ImageView view;
    private Rectangle hitbox;
    private boolean open;

    /**
     * Constructor for a simple treasure chest object
     */
    public Treasure(int ID, double x, double y) {
        this.image = new Image("resources/treasureChest.png");
        this.open = false;
        this.view = new ImageView();
        this.view.setImage(image);
        view.setViewport(new Rectangle2D(0, 0, 32, 32));
        view.setX(x);
        view.setY(y);
        hitbox = new Rectangle(x, y, 32, 32);
        hitbox.setFill(Color.GREEN);
        hitbox.setVisible(false);
        hitbox.setId(String.valueOf(ID));
    }

    public void openNow(){
        open = true;
        view.setViewport(new Rectangle2D(32,0,32,32));
    }

    public boolean isOpen() {
        return open;
    }

    public ImageView getView() {
        return this.view;
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }
}
