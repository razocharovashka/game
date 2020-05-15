package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Money extends GameObject {
    protected int size; //размер
    //получить размер
    public int getSize() {
        return size;
    }
    //установить размер
    public void setSize(int size) {
        this.size = size;
    }
    public abstract void setImage();
}
