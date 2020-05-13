package sample.gameclass;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

import static sample.Main.*;

public class Enemy extends Fish{
    private int velocity;
    private Image image;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

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
            this.setTranslateX((int) (Math.random() * 10000) * (-1));
        }
        else {
            this.setTranslateX((int) (Math.random() * 10000) + 1000);
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
                if(this.isVisible()) {
                    score++;
                }
                this.setVisible(false);
            }
            else {
                isStart = false;
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

    public void setImage(Image image){
        this.image = image;
    }
    public Image getImage(){
        return this.image;
    }
}
