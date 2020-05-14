package sample.gameclass;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.Main.*;
//класс MyFish (игрок), наследуется от класса Fish
public class MyFish extends Fish {
    //получить размер
    public int getSize() {
        return size;
    }
    //установить размер
    public void setSize(int size) {
        this.size = size;
    }
    //конструктор
    public MyFish(int size, int x, int y) {
        this.size = size; //размер
        this.setTranslateX(x); //координата Х
        this.setTranslateY(y); //координата У
        this.object = new Rectangle(size, size, Color.RED); //объект игрока - красный прямоугольник
        this.getChildren().add(object);
    }

    @Override //переопределенный метод
    //двигать игрока
    public void Move(int x, int y) {
        //ограничения про границам, чтобы игрок не выходил из зоны видимости
        if((this.getTranslateX() + x > 5) && (this.getTranslateY() + y > 5) &&
                (this.getTranslateX() + x < WIDTH - this.size - 20) &&
                (this.getTranslateY() + y < HEIGHT - this.size - 40)) {
            this.setTranslateX(this.getTranslateX() + x); //прибавить к текущей координате Х скорость
            this.setTranslateY(this.getTranslateY() + y); //прибавить к текущей координате У скорость
        }
        //если игрок и монетка пересекаются
        if(this.getBoundsInParent().intersects(money.getBoundsInParent())) {
            //если монетка видна
            //для того, чтобы счетчик увеличивался 1 раз, а не при каждом обновлении и наложении рыб
            if(money.isVisible()) {
                score += 10; //увеличить счетчик на 10
            }
            money.setVisible(false); //монетка не видна
        }
    }
}
