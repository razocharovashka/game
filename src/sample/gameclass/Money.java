package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Money extends GameObject {
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Money() {
        this.object = new Rectangle(10, 10, Color.YELLOW);
        this.setTranslateX((int) (Math.random() * 790 + 10));
        this.setTranslateY((int) (Math.random() * 6) * 100);
    }
}
