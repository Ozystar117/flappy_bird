package emmanuel.flappy;

import actors.Bird;
import gamestate.GameStateManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application implements Runnable {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 480;
    private Pane pane;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc;
    
//    USED FOR THE GAME LOOP
    private Thread game;
    private long startTime;
    private long elapsed;
    private boolean running;
    
//    an instance of the game state manager
    GameStateManager gsm = new GameStateManager();
//    the bird to be displayed on the canvas
    Bird testBird = new Bird(100, 100);
    
//    Anonymous keyboard event handler class
    EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>(){

        public void handle(KeyEvent event) {
            gsm.keyPressed(event);
        }
    };
    
//    Anonymous mouse event handler class
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){

        public void handle(MouseEvent event) {
            gsm.mouseClicked(event);
        }
    };

    @Override
    public void start(Stage stage) {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, 480.0, 800.0);
        stage.setScene(this.scene);
        stage.setMaxWidth(480.0);
        stage.setMaxHeight(800.0);
        stage.show();
        this.canvas = new Canvas(480.0, 800.0);
        this.pane.getChildren().add(this.canvas);
        this.gc = this.canvas.getGraphicsContext2D();
        this.running = true;
        this.drawBackground(Color.LIGHTBLUE);
        this.game = new Thread(this, "game");
        this.game.start();
    }

    public void drawBackground(Color color) {
        this.gc.drawImage(new Image(this.getClass().getResourceAsStream("/assets/sprites/background-day.png")), 0.0, 0.0, 480.0, 800.0);
    }

    public void update() {
//    	check event handlers
        this.scene.setOnKeyPressed(this.keyHandler);
        this.scene.setOnMouseClicked(this.mouseHandler);
        
//        update
        this.gsm.update();
    }

    public void render() {
        this.gsm.render(this.gc);
    }

    @Override
    public void run() {
        System.out.println(" Run called");
        
        boolean shouldRender = false;
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;
        System.out.println(" fpsTimer: " + fpsTimer);
        
        // last update time in nanoseconds
        double then = System.nanoTime();
        double unprocessed = 0;
        
        while(running)
        {
            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerUpdate;
            //System.out.println(" unprocessed: " + unprocessed);
            then = now;
            //System.out.println(" now: " + now);
            //update queue
            while(unprocessed >= 1) // while there's something to process
            {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;
            }

            // render
            if(shouldRender)
            {
                fps++;
                render();
                shouldRender = false;
            }else{
                try{
                    Thread.sleep(1);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            // FPS Timer
            if(System.currentTimeMillis() - fpsTimer > 1000)
            {
                System.out.printf("%d fps %d updates", fps, updates);
                System.out.println();
                fps = 0;
                updates = 0;
                fpsTimer += 1000;
            }
        }
    }

    public synchronized void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        this.game = new Thread(this, "game");
        this.game.start();
    }

    public synchronized void stop() {
        if (!this.running) {
            return;
        }
        this.running = false;
        System.exit(0);
    }

    public static void main(String[] args) {
        App.launch(new String[0]);
    }
}

