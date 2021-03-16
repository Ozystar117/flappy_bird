
package emmanuel.flappy;

import javafx.scene.canvas.GraphicsContext;

public interface GameArtifact {
    public void update();

    public void render(GraphicsContext gc);
}

