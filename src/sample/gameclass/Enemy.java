package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

import static sample.Main.*;
//класс Enemy (рыбки), наследуется от класса Fish
public class Enemy extends Fish{
    private int velocity; //скорость
    private Image image; //картинка
    private boolean flag; //движется вправо - true или влево - false
    //вернуть флаг
    //нужна для установки разных картинок для рыб, которые плывут влево и вправо
    public boolean isFlag() {
        return flag;
    }
    //получить скорость
    public int getVelocity() {
        return velocity;
    }
    //конструктор
    public Enemy(boolean flag) {
        this.size = (int) (Math.random() * 100 + 10); //размер рыбки
        this.velocity = Main.VELOCITY; //скорость рыбки
        this.flag = flag; //флаг - влево или вправо движется
        this.object = new Rectangle(size, size, Color.BLACK); //объект рыбки - черный прямоугольник
        this.setTranslateY((int) (Math.random() * 6) * 100); //координата У - 6 уровней (0, 100, 200, 300, 400, 500)
        if(this.flag) { //рыбка плывет вправо
            this.setTranslateX((int) (Math.random() * 10000) * (-1)); //координата Х где-то далеко от левой границы
        }
        else { //рыбка плывет влево
            this.setTranslateX((int) (Math.random() * 10000) + 1000); //координата Х где-то далеко от правой границы
        }

        this.getChildren().add(object);
    }
    //получить размер
    public int getSize() {
        return size;
    }
    //установить размер
    public void setSize(int size) {
        this.size = size;
    }

    //
    @Override //переопределенный метод
    //движение рыбки
    public void Move(int x, int y) {
        //если рыбка и игрок пересекаются
        if(this.getBoundsInParent().intersects(player.getBoundsInParent())){
            //если рыбка меньше игрока
            if(this.getSize() < player.getSize()) {
                //если рыбка видна
                //для того, чтобы счетчик увеличивался 1 раз, а не при каждом обновлении и наложении рыб
                if(this.isVisible()) {
                    score++; //увеличиваем счет
                }
                this.setVisible(false); //рыбка больше не видна
            }
            else { //если рыбка больше игрока или такого же размера
                isStart = false; //игра окончена
                isFinish = true; //финальная сцена
            }
        }
        if(this.flag) { //рыбка плывет вправо
            this.setTranslateX(this.getTranslateX() + x); //прибавляем скорость к текущей координате Х
        }
        else {
            this.setTranslateX(this.getTranslateX() - x); //вычитаем скорость из текущей координаты Х
        }
    }
    //установить кортинку
    public void setImage(Image image){
        this.image = image;
    }
    //получить картинку
    public Image getImage(){
        return this.image;
    }
}
