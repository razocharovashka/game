package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import sample.Main;
import sample.gameclass.Enemy;
import sample.gameclass.MyFish;
import static sample.Main.*;

import java.io.File;

public class Controller {
    @FXML
    private Button returnButton;
    //public Text text;

    @FXML
    public void returnButton(){
        Main.isFinish = false;
        initializeGame();
        Main.isStart = true;
        flag = true;
    }

    private static void initializeGame(){
        enemies.clear();
        score = 0;
        player = new MyFish(PLAYER_SIZE, PLAYER_X, PLAYER_Y);

        for(int i = 0; i < NUMBER_FISH; i++) {
            double rand = Math.random();
            if(rand >= 0.5) {
                enemies.add(new Enemy(true));
            }
            else {
                enemies.add(new Enemy(false));
            }
            gameRoot.getChildren().add(enemies.get(i));
        }
        gameRoot.getChildren().add(player);

        File imageEnemyFish = new File("src/sample/Resources/pictures/enemy.png");
        for(Enemy fishEnemy: enemies) {
            enemyFish = new Image(imageEnemyFish.toURI().toString(), fishEnemy.getSize(), fishEnemy.getSize(), false, false);
            fishEnemy.setImage(enemyFish);
        }
    }
}
