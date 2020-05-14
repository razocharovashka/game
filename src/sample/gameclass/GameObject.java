package sample.gameclass;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
//абстрактный класс GameObject, наследуется от Pane
public abstract class GameObject extends Pane {
    protected Rectangle object; //объект - прямоугольник
}
