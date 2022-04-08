package main;

import java.io.File;
import java.net.URI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
	    double width;
	    double height;
	    // check the OS we're running on for stage dimensions
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            width = 976;    // 32px * 30 squares + 16px for frame
            height = 679;   // 32px * 20 squares + 39px for frame
        } else {
            width = 960;    // 32px * 30 squares, no frame on mac/linux
            height = 662;   // 32px * 20 squares, + 22px frame
        }
        
		// create our game/pane
		Parent root = new Game(primaryStage);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Red v. Blue");
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
