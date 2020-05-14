package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Money extends GameObject {
    private Image image; //картинка
    //получить картинку
    public Image getImage() {
        return image;
    }
    //установить картинку
    public void setImage(Image image) {
        this.image = image;
    }
    //конструктор
    public Money() {
        this.object = new Rectangle(10, 10, Color.YELLOW); //объект монетки - желтый прямоугольник
        this.setTranslateX((int) (Math.random() * 790 + 10)); //координата Х
        this.setTranslateY((int) (Math.random() * 6) * 100); //координата У
    }
}
