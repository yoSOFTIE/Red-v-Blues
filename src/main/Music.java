package main;

import java.io.File;
import java.net.URI;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class Music {
	File fileBGM = new File("GoldenSunBattleTheme.wav");
    URI uriBGM = fileBGM.toURI();
    String retBGM = uriBGM.toString();
    
    File fileTreasureSound = new File("treasureSound.wav");
    URI uriTreasureSound = fileTreasureSound.toURI();
    String retTreasureSound = uriTreasureSound.toString();
    
    File fileDeath = new File("playerDeathSound.wav");
    URI uriDeath = fileDeath.toURI();
    String retDeath = uriDeath.toString();
    
    File fileFire = new File("fireSound.wav");
    URI uriFire = fileFire.toURI();
    String retFire = uriFire.toString();
	
	Music(){	
		
	}	
	
	public void playBGM() {
	    Media media = new Media(retBGM);
	    MediaPlayer player = new MediaPlayer(media);
	    player.setVolume(.7);
	    player.play();
	}
	
	public void playTreasureSound() {
		Media media = new Media(retTreasureSound);
	    MediaPlayer player = new MediaPlayer(media);
	    player.setVolume(.5);
	    player.play();
	}
	
	public void playDeath() {
		Media media = new Media(retDeath);
	    MediaPlayer player = new MediaPlayer(media);
	    player.setVolume(.5);
	    player.play();
	}
	
	public void playFire() {
		Media media = new Media(retFire);
	    MediaPlayer player = new MediaPlayer(media);
	    player.setVolume(.5);
	    player.play();
	}
}
