package sample.gameclass;

import javafx.scene.image.Image;

//абстрактный класс Fish, наследуется от GameObject
public abstract class Fish extends GameObject {
    protected int size; //размер
    protected int velocity;

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    public abstract void Move(int x, int y); //абстрактный метод движения
    //получить размер
    public int getSize() {
        return size;
    }
    //установить размер
    public void setSize(int size) {
        this.size = size;
    }
}
