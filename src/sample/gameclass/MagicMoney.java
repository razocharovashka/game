package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class MagicMoney extends Money {

    //конструктор
    public MagicMoney(int size) {
        this.object = new Rectangle(20, 20, Color.YELLOW); //объект монетки - желтый прямоугольник
        this.setTranslateX((int) (Math.random() * 700 + 40)); //координата Х
        this.setTranslateY((int) (Math.random() * 5 + 1) * 100); //координата У
        this.setSize(size);
        this.setImage();
        this.getChildren().addAll(object);
    }

    @Override
    public void setImage() {
        File imageFile = new File("src/sample/Resources/pictures/money.png");
        this.image = new Image(imageFile.toURI().toString(), 50, 50, false, false);
    }
}