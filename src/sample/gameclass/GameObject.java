package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

//абстрактный класс GameObject, наследуется от Pane
public abstract class GameObject extends Pane {
    protected Image image;
    protected Rectangle object; //объект - прямоугольник

    public Image getImage() {return this.image;};
}
