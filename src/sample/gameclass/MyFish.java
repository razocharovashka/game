package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.io.File;
import java.lang.management.PlatformLoggingMXBean;

import static sample.Main.*;
//класс MyFish (игрок), наследуется от класса Fish
public class MyFish extends Fish {
    //конструктор
    public MyFish(int size, int x, int y) {
        this.size = size; //размер
        this.setTranslateX(x); //координата Х
        this.setTranslateY(y); //координата У
        this.setImage("myFish");
        this.setVelocity(PLAYER_VELOCITY);
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
        if(this.getBoundsInParent().intersects(magicMoney.getBoundsInParent())) {
            //если монетка видна
            //для того, чтобы счетчик увеличивался 1 раз, а не при каждом обновлении и наложении рыб
            if(magicMoney.isVisible()) {
                score += 10; //увеличить счетчик на 10
                player = new MagicFish(PLAYER_SIZE, (int)this.getTranslateX(), (int)this.getTranslateY());
                gameRoot.getChildren().add(player);
            }
            magicMoney.setVisible(false); //монетка не видна
        }

        if(this.getBoundsInParent().intersects(enemyMoney.getBoundsInParent())) {
            if(enemyMoney.isVisible()) {
                player = new EvilFish(PLAYER_SIZE, (int) this.getTranslateX(), (int) this.getTranslateY());
                gameRoot.getChildren().add(player);
            }
            enemyMoney.setVisible(false); //монетка не видна
        }
    }

    public void setImage(String imageName) {
        File imageFileRight = new File("src/sample/Resources/pictures/" + imageName + "Right.png");
        File imageFileLeft = new File("src/sample/Resources/pictures/" + imageName + "Left.png");
        lastSide = new Image(imageFileLeft.toURI().toString(), PLAYER_SIZE, PLAYER_SIZE, false, false);
        fishLeft = new Image(imageFileLeft.toURI().toString(), PLAYER_SIZE, PLAYER_SIZE, false, false);
        fishRight = new Image(imageFileRight.toURI().toString(), PLAYER_SIZE, PLAYER_SIZE, false, false);
    }
}
