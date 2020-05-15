package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

import static sample.Main.*;
import static sample.Main.magicMoney;

public class MagicFish extends MyFish {

    public MagicFish(int size, int x, int y) {
        super(size, x, y);
        this.setTranslateX(x); //координата Х
        this.setTranslateY(y); //координата У
        this.setImage("magicFish");
        this.setVelocity(PLAYER_VELOCITY + 1);
        this.object = new Rectangle(size, size, Color.YELLOW); //объект игрока - желтый прямоугольник
        this.getChildren().add(object);
    }

    @Override
    public void Move(int x, int y) {
        if((this.getTranslateX() + x > 5) && (this.getTranslateY() + y > 5) &&
                (this.getTranslateX() + x < WIDTH - this.size - 20) &&
                (this.getTranslateY() + y < HEIGHT - this.size - 40)) {
            this.setTranslateX(this.getTranslateX() + x); //прибавить к текущей координате Х скорость
            this.setTranslateY(this.getTranslateY() + y); //прибавить к текущей координате У скорость
        }
        //если игрок и монетка пересекаются
        if(this.getBoundsInParent().intersects(enemyMoney.getBoundsInParent())) {
            if(enemyMoney.isVisible()) {
                player = new MyFish(PLAYER_SIZE, (int)this.getTranslateX(), (int)this.getTranslateY());
                gameRoot.getChildren().add(player);
            }
            enemyMoney.setVisible(false); //монетка не видна
        }
    }

    @Override
    public void setImage(String imageName) {
        File imageFileRight = new File("src/sample/Resources/pictures/" + imageName + "Right.png");
        File imageFileLeft = new File("src/sample/Resources/pictures/" + imageName + "Left.png");
        lastSide = new Image(imageFileLeft.toURI().toString(), PLAYER_SIZE, PLAYER_SIZE, false, false);
        fishLeft = new Image(imageFileLeft.toURI().toString(), PLAYER_SIZE, PLAYER_SIZE, false, false);
        fishRight = new Image(imageFileRight.toURI().toString(), PLAYER_SIZE, PLAYER_SIZE, false, false);
    }
}
