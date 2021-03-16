/**
 * 
 */
package gamestate;

/**
 * @author Emmanuel Ozioma
 *
 */
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import javafx.scene.text.Font;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.util.Iterator;
import java.util.ArrayList;
import javafx.scene.image.Image;
import actors.Tube;
import java.util.List;
import actors.Bird;

public class PlayState extends GameState
{
    private Bird bird;
    private List<Tube> tubes;
    private Image baseImage;
    private Image[] images; 
    private int imgIndex = 0;
    
    private int score;
    private int highScore;
    private double timer;
    
    public PlayState( GameStateManager gsm) {
        super("Play State", "/assets/sprites/background-day.png", gsm);
        images = new Image[2];
        initImages();
        
        this.bird = new Bird(60, 400);
        this.score = 0;
        this.timer = 0.0;
        this.tubes = new ArrayList<Tube>();
        this.initialiseTubes();
        this.readHighScore();
    }
    
    public void initialiseTubes() {
         Tube tube1 = new Tube(480);
         Tube tube2 = new Tube(720);
        this.tubes.add(tube1);
        this.tubes.add(tube2);
    }
    
    public boolean collisionDetected() {
        for ( Tube t : this.tubes) {
            if (this.bird.getR().intersects(t.getTopRect()) || this.bird.getR().intersects(t.getBotRect())) {
                this.playCollisionSound();
                this.gameOver();
                return true;
            }
        }
        if (this.bird.getY() + this.bird.getHeight() > 800) {
            this.playCollisionSound();
            this.gameOver();
            return true;
        }
        return false;
    }
    
    public void drawText( String text,  double x,  double y,  int fontSize,  GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(fontSize));
        gc.fillText(text, x, y);
    }
    
    public void displayScore( int fontSize,  GraphicsContext gc) {
        this.drawText("Score: " + this.score, 240 - fontSize / 2, 8 + fontSize, fontSize, gc);
    }
    
    public void displayHighScore( int fontSize,  GraphicsContext gc) {
        this.drawText("Best: " + this.highScore, 480 - fontSize * 6, 8 + fontSize, fontSize, gc);
    }
    
    public boolean highScoreExists() {
    	Scanner scanner = null;
        try {
            scanner = new Scanner(new File("high_score.txt"));
        }
        catch (Exception e) {
        	return false;
        }
    	return true;
    }
    
    public void readHighScore() {
    	if(highScoreExists()) {
    		Scanner scanner = null;
            try {
                scanner = new Scanner(new File("high_score.txt"));
            }
            catch (Exception e) {
            	
            }
            this.highScore = scanner.nextInt();
    	}
    }
    
    public void updateScore() {
        if (this.timer % 30 == 0) {
            this.score++;
        }
    }
    
    public void updateHighScore() {
        if (this.score > this.highScore) {
            this.highScore = this.score;
        }
    }
    
    public void initImages() {
    	images[0] = new Image(this.getClass().getResourceAsStream("/assets/sprites/background-night.png"));
    	images[1] = new Image(this.getClass().getResourceAsStream("/assets/sprites/background-day.png"));
    }
    
    /**
     * Change the background image every 2 minutes
     */
    public void changeBackground() {
    	if (this.timer % 1200.0 == 0.0) {
        	if(this.imgIndex >= this.images.length - 1) {
        		this.imgIndex = 0;
        	}else {
        		this.imgIndex++;
        	}
        }
    }
    
    /**
     * Update the bg image to be rendered
     */
    public void updateBackground() {
    	changeBackground();
    	this.img = this.images[imgIndex];
    }
    
    /**
     * When the game is over, save the high score to a txt file 
     * then pop out the play state from the stack of game states
     * Finally, push a game over state to the stack
     */
    public void gameOver() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File("high_score.txt"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        writer.println(this.highScore);
        writer.close();
        this.gsm.getGameStates().pop();
        this.gsm.getGameStates().push((new GameOverState(this.gsm)));
    }
    
    /**
     * Handle keyboard events
     */
    @Override
    public void keyPressed( KeyEvent e) {
        super.keyPressed(e);
        if (e.getCode() == KeyCode.M) {
            this.gsm.getGameStates().pop();
            System.out.println(this.gsm.getGameStates());
        }
        if (e.getCode() == KeyCode.ESCAPE) {
            this.gsm.getGameStates().push(new PauseState(this.gsm));
        }
        if (e.getCode() == KeyCode.SPACE) {
            this.playInputSound();
            this.bird.act();
        }
    }
    
    /**
     * Handle mouse events
     */
    @Override
    public void mouseClicked( MouseEvent e) {
        super.mouseClicked(e);
        if (e.getButton() == MouseButton.PRIMARY) {
            this.bird.act();
        }
    }
    
    @Override
    public void update() {
        this.updateBackground();
        this.updateScore();
        this.updateHighScore();
//        check for collision
        this.collisionDetected();
        for ( Tube t : this.tubes) {
            t.update();
            if (t.getX() == this.bird.getX() && !this.collisionDetected()) {
                System.out.println("SOUND!!!");
                this.playSuccessSound();
            }
        }
        this.bird.update();
        this.timer++;
    }
    
    @Override
    public void render( GraphicsContext gc) {
        gc.drawImage(this.img, 0, 0, 480, 800);
        for (Tube t : this.tubes) {
            t.render(gc);
        }
        this.bird.render(gc);
        this.displayScore(23, gc);
        this.displayHighScore(18, gc);
    }
}
