
package actors;

import emmanuel.flappy.GameArtifact;

import java.awt.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bird implements GameArtifact {
//	the x and y position of the bird on the canvas
    private int x, y;
//  change of x and y positions
    private int dx, dy;
//  width and height of the bird 
    private int width, height;
//  the image of the bird
    private Image img;
//  collection of bird images used for animation
    private Image[] images;
//  index of the image to be displayed during animation
    private int imgIndex;
//  used to determine the frequency of animations
    private double timer = 0.0;
//  rectangle used for collision detection
    private Rectangle r;
    
    private final int GRAVITY = 1;
    private final int MAX_SPEED = 5;

    /**
     * @param x : position of the bird on the x-axis
     * @param y : position of the bird on the y-axis
     */
    public Bird(int x, int y) {
//    	initialise instance variables
        this.x = x;
        this.y = y;
        this.dx = 1;
        this.dy = 1;
        this.width = 40;
        this.height = 40;
        this.imgIndex = 0;
        this.images = new Image[4];
        this.setImages();
        this.r = new Rectangle(this.x, this.y, this.width, this.height);
    }

    /**
	 * @return the position of the bird on the x-axis
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the position of the bird on the y-axis
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the change in x
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * @return the change in y
	 */
	public int getDy() {
		return dy;
	}

	/**
	 * @return the width of the bird
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height of the bird
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the image of the bird
	 */
	public Image getImg() {
		return img;
	}

	/**
	 * @return the collection of images of the bird
	 */
	public Image[] getImages() {
		return images;
	}

	/**
	 * @return the rectangle used for collision detection
	 */
	public Rectangle getR() {
		return r;
	}
	
	/**
	 * Initialise the images used for animation
	 * called in the constructor
	 */
	public void setImages() {
        this.images[0] = new Image(this.getClass().getResourceAsStream("/assets/sprites/bluebird-downflap.png"));
        this.images[1] = new Image(this.getClass().getResourceAsStream("/assets/sprites/bluebird-midflap.png"));
        this.images[2] = new Image(this.getClass().getResourceAsStream("/assets/sprites/bluebird-upflap.png"));
        this.images[3] = new Image(this.getClass().getResourceAsStream("/assets/sprites/bluebird-midflap.png"));
    }

	/**
	 * Automatically make the bird to fall
	 * called in update
	 */
	public void move() {
        this.y += this.dy;
//      limit the acceleration
        if (this.dy < MAX_SPEED) {
            this.dy++;
        }
    }

	/**
	 * Make the bird jump
	 */
    public void act() {
        this.y -= 66;
//      set the speed to 0 after jumping, so it can start accelerating down (when move() is called in update())
        this.dy = 0;
    }

    @Override
    public void update() {
//    	update the image index (the timer condition ensures that it is called every 0.2s (every 5 frames instead of 12))
        if (this.timer % 5.0 == 0.0) {
        	if(this.imgIndex >= this.images.length - 1) {
        		this.imgIndex = 0;
        	}else {
        		this.imgIndex++;
        	}
        }
//      move the bird downwards
        this.move();
//        set the image of the bird to be displayed
        this.img = this.images[this.imgIndex];
//        update the position of the rectangle
        this.r.setLocation(this.x, this.y);
        this.timer++;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, this.x, this.y, this.width, this.height);
    }
}

