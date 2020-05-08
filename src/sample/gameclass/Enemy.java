package sample.gameclass;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Fish{
    private Color color;
    private int size;
    private int velocity;

    public int getVelocity() {
        return velocity;
    }

    public Enemy(Color color, int size, int velocity) {
        this.color = color;
        this.size = size;
        this.velocity = velocity;
        this.object = new Rectangle(size, size, color);

        this.getChildren().add(object);
    }

    @Override
    public void Move(int x, int y) {
        if(x < 800) {
            this.setTranslateX(this.getTranslateX() + x);
            this.setTranslateY(this.getTranslateY() + y);
        }
        else {

        }

    }
}
