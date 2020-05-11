package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

import java.io.File;

import static sample.Main.*;
import static sample.Main.player;

public class Enemy extends Fish{
    //private Color color;
    private int size;
    private int velocity;

    public boolean isFlag() {
        return flag;
    }

    private boolean flag;

    public int getVelocity() {
        return velocity;
    }

    public Enemy(boolean flag) {
        this.size = (int) (Math.random() * 100 + 10);;
        this.velocity = Main.VELOCITY;
        this.flag = flag;
        this.object = new Rectangle(size, size, Color.BLACK);
        this.setTranslateY((int) (Math.random() * 6) * 100);
        if(this.flag) {
            this.setTranslateX((int) (Math.random() * 1000) * (-1));
        }
        else {
            this.setTranslateX((int) (Math.random() * 1000) + 1000);
        }

        this.getChildren().add(object);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //
    @Override
    public void Move(int x, int y) {
        if(this.getBoundsInParent().intersects(player.getBoundsInParent())){
            if(this.getSize() < player.getSize()) {
                this.setVisible(false);
            }
            else {
                isFinish = true;
            }
        }
        if(this.flag) {
            this.setTranslateX(this.getTranslateX() + x);//
            this.setTranslateY(this.getTranslateY() + y);
        }
        else {
            this.setTranslateX(this.getTranslateX() - x);
            this.setTranslateY(this.getTranslateY() - y);
        }
    }
}
