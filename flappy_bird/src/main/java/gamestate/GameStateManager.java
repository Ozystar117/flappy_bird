/**
 * 
 */
package gamestate;

/**
 * @author Emmanuel Ozioma
 *
 */
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.canvas.GraphicsContext;
import java.util.Stack;
import emmanuel.flappy.GameArtifact;

public class GameStateManager implements GameArtifact
{
    private Stack<GameState> gameStates;
    
    public GameStateManager() {
        (this.gameStates = new Stack<GameState>()).push(new MenuState(this));
    }
    
    public Stack<GameState> getGameStates() {
        return this.gameStates;
    }
    
    public void update() {
        this.gameStates.peek().update();
    }
    
    public void render(final GraphicsContext gc) {
        this.gameStates.peek().render(gc);
    }
    
    public void keyPressed(final KeyEvent e) {
        this.gameStates.peek().keyPressed(e);
    }
    
    public void mouseClicked(final MouseEvent e) {
        this.gameStates.peek().mouseClicked(e);
    }
}
