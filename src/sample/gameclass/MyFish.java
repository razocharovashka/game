package sample.gameclass;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.Main.*;

public class MyFish extends Fish {
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public MyFish(int size) {
        this.size = size;
        this.object = new Rectangle(size, size, Color.RED);
        this.getChildren().add(object);
    }

    @Override
    public void Move(int x, int y) {
        if((this.getTranslateX() + x > 5) && (this.getTranslateY() + y > 5) &&
                (this.getTranslateX() + x < WIDTH - this.size - 20) &&
                (this.getTranslateY() + y < HEIGHT - this.size - 40)) {
            this.setTranslateX(this.getTranslateX() + x);
            this.setTranslateY(this.getTranslateY() + y);
        }
    }
}
