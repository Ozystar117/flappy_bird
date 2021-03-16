/**
 * 
 */
package gamestate;

/**
 * @author Emmanuel Ozioma
 *
 */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PauseState extends GameState
{
    private int playBtnImgWidth;
    private int playBtnImgHeight;
    
    public PauseState( GameStateManager gsm) {
        super("Pause State", "/assets/sprites/play2.png", gsm);
        this.playBtnImgWidth = 200;
        this.playBtnImgHeight = 200;
    }
    
    @Override
    public void keyPressed( KeyEvent e) {
        super.keyPressed(e);
        if (e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.SPACE) {
            this.gsm.getGameStates().pop();
        }
    }
    
    @Override
    public void mouseClicked( MouseEvent e) {
        super.mouseClicked(e);
        if (e.getButton() == MouseButton.PRIMARY) {
            this.gsm.getGameStates().pop();
        }
    }
    
    @Override
    public void update() {
    }
    
    @Override
    public void render( GraphicsContext gc) {
        gc.drawImage(this.img, (double)(240 - this.playBtnImgWidth / 3), (double)(400 - this.playBtnImgHeight / 2), (double)this.playBtnImgWidth, (double)this.playBtnImgHeight);
    }
}
