
package gamestate;

import emmanuel.flappy.App;
import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.PlayState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameOverState extends GameState {
    
    private int width = 300;
    private int height = 100;
    private int x = App.WIDTH / 2 - (width / 2);
    private int y = 400 - this.height / 2;

    public GameOverState(GameStateManager gsm) {
        super("Game Over State", "/assets/sprites/gameover.png", gsm);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
//        if ESC, ENTER or SPACE key is pressed, start a new game
        if (e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
//        	remove this game state from the stack
            this.gsm.getGameStates().pop();
//            add a new play state to the stack
            this.gsm.getGameStates().push(new PlayState(this.gsm));
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, this.x, this.y, this.width, this.height);
    }
}

