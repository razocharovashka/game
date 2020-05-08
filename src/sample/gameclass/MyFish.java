package sample.gameclass;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

public class MyFish extends Fish {
    public MyFish(int height, int width) {
        this.object = new Rectangle(height, width, Color.RED);

        this.getChildren().add(object);
    }

    @Override
    public void Move(int x, int y) {
        //
        if((this.getTranslateX() + x > 5) && (this.getTranslateY() + y > 5) &&
                (this.getTranslateX() + x < 795 - Main.PLAYER_WIDTH) && (this.getTranslateY() + y < 595 - Main.PLAYER_HEIGHT)) {
            this.setTranslateX(this.getTranslateX() + x);
            this.setTranslateY(this.getTranslateY() + y);
        }
    }
}
