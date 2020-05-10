package sample.gameclass;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

import static sample.Main.HEIGHT;
import static sample.Main.WIDTH;

public class MyFish extends Fish {
    public MyFish(int height, int width) {
        this.object = new Rectangle(height, width, Color.RED);

        this.getChildren().add(object);
    }

    @Override
    public void Move(int x, int y) {
        //
        if((this.getTranslateX() + x > 5) && (this.getTranslateY() + y > 5) &&
                (this.getTranslateX() + x < WIDTH - Main.PLAYER_WIDTH - 20) && (this.getTranslateY() + y < HEIGHT - Main.PLAYER_HEIGHT - 40)) {
            this.setTranslateX(this.getTranslateX() + x);
            this.setTranslateY(this.getTranslateY() + y);
        }
    }
}
