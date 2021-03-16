package gamestate;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuState extends GameState
{
    public MenuState( GameStateManager gsm) {
        super("Menu State", "/assets/sprites/message.png", gsm);
    }
    
    public String getName() {
        return super.getName();
    }
    
    public void setName( String name) {
        super.setName(name);
    }
    
    @Override
    public void keyPressed( KeyEvent e) {
        super.keyPressed(e);
        if (e.getCode() == KeyCode.SPACE) {
            System.out.println("Space pressed!!!");
            this.gsm.getGameStates().push(new PlayState(this.gsm));
        }
    }
    
    @Override
    public void mouseClicked( MouseEvent e) {
        super.mouseClicked(e);
        if (e.getButton() == MouseButton.PRIMARY) {
            this.gsm.getGameStates().push(new PlayState(this.gsm));
        }
    }
    
    @Override
    public void update() {
    }
    
    @Override
    public void render( GraphicsContext gc) {
        gc.drawImage(this.img, 120.0, 200.0, 240.0, 400.0);
    }
}