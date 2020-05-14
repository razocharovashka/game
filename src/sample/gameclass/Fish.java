package sample.gameclass;
//абстрактный класс Fish, наследуется от GameObject
public abstract class Fish extends GameObject {
    protected int size; //размер
    public abstract void Move(int x, int y); //абстрактный метод движения
}
