package gamestate;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.net.URL;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import emmanuel.flappy.GameArtifact;

public abstract class GameState implements GameArtifact
{
//	name of the game state
    protected String name;
//    image of the game state
    protected Image img;
//    game state manager to manage which game state should be displayed
    protected GameStateManager gsm;
    
    /**
     * 
     * @param name
     * @param imgDir
     * @param gsm
     */
    public GameState(String name, String imgDir, GameStateManager gsm) {
        this.name = name;
        this.img = new Image(this.getClass().getResourceAsStream(imgDir));
        this.gsm = gsm;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setImageDir(String imgDir) {
        this.img = new Image(this.getClass().getResourceAsStream(imgDir));
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void playSound(URL urlPath) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(urlPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        clip.start();
    }
    
    public void playSuccessSound() {
        this.playSound(this.getClass().getResource("/assets/audios/point.wav"));
    }
    
    public void playInputSound() {
        this.playSound(this.getClass().getResource("/assets/audios/wing.wav"));
    }
    
    public void playCollisionSound() {
        this.playSound(this.getClass().getResource("/assets/audios/hit.wav"));
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}