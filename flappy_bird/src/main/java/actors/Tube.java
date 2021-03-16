
package actors;

import emmanuel.flappy.GameArtifact;

import java.awt.Rectangle;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tube implements GameArtifact {
//	the position of the tube on the x-axis
    private int x;
//  the position of the top tube on the y-axis
    private int topY;
//    the position of the bottom tube on the y-axis
    private int botY;
    private int dx;
//    the width of the tubes
    private int width;
//    height of the top tube
    private int topHeight;
//    height of the bottom tube
    private int botHeight;
//    image of the top tube
    private Image topImg;
//    image of the bottom tube
    private Image botImg;
//    Rectangle used for collision detection
    private Rectangle topRect;
    private Rectangle botRect;

    /**
     * 
     * @param x
     */
    public Tube(int x) {
//    	initialise instance variables
        this.x = x;
        this.dx = 3;
        this.topY = 0;
        this.width = 50;
        this.initialiseTubeDimensions();
        this.topImg = new Image(this.getClass().getResourceAsStream("/assets/sprites/top-pipe2.png"));
        this.botImg = new Image(this.getClass().getResourceAsStream("/assets/sprites/pipe-green.png"));
        this.topRect = new Rectangle(this.x, this.topY, this.width, this.topHeight);
        this.botRect = new Rectangle(this.x, this.topY, this.width, this.botHeight);
    }

    /**
     * set the position of the top tube on the y-axis to be at the top of the game window
     * then generate a random height for the top tube
     * set the y position of the bottom tube
     */
    public void initialiseTubeDimensions() {
        Random random = new Random();
        int max = 501;
        int min = 200;
        this.topHeight = random.nextInt(max - min) + min;
        int gap = 200;
        int botY = this.topY + this.topHeight + gap;
        int botHeight = 800 - botY;
        this.botY = botY;
        this.botHeight = botHeight;
    }

    /**
     * 
     * @return the position of the tube on the x-axis
     */
    public int getX() {
        return this.x;
    }

    /**
     * 
     * @return the position of the top tube on the y-axis
     */
    public int getTopY() {
        return this.topY;
    }

    /**
     * 
     * @return the change in the x position of the tube
     */
    public int getDx() {
        return this.dx;
    }

    /**
     * 
     * @return the width of the tubes
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * 
     * @return the height of the top tube
     */
    public int getTopHeight() {
        return this.topHeight;
    }

    /**
     * 
     * @return the pos of the bottom tube on the y-axis
     */
    public int getBotY() {
        return this.botY;
    }

    /**
     * 
     * @return the height of the bottom tube
     */
    public int getBotHeight() {
        return this.botHeight;
    }

    /**
     * 
     * @return the image of the bottom tube
     */
    public Image getBotImg() {
        return this.botImg;
    }

    public Rectangle getBotRect() {
        return this.botRect;
    }

    public Image getTopImg() {
        return this.topImg;
    }

    public Image getBotImage() {
        return this.botImg;
    }

    public Rectangle getTopRect() {
        return this.topRect;
    }

    /**
     * move the tube to the left 
     */
    public void move() {
        this.x -= this.dx;
    }

    @Override
    public void update() {
        this.move();
//        update the location of the rect used for collision detection
        this.topRect.setLocation(this.x, this.topY);
        this.topRect.setBounds(this.x, this.topY, this.width, this.topHeight);
        this.botRect.setBounds(this.x, this.botY, this.width, this.botHeight);
        
        if (this.x < 0) {
            this.x = 480;
            this.initialiseTubeDimensions();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.topImg, (double)this.x, (double)this.topY, (double)this.width, (double)this.topHeight);
        gc.drawImage(this.botImg, (double)this.x, (double)this.botY, (double)this.width, (double)this.botHeight);
    }
}

