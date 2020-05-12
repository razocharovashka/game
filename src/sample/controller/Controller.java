package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import sample.Main;
import sample.gameclass.Enemy;
import sample.gameclass.MyFish;
import static sample.Main.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button returnButton;
    public Text text;

    @FXML
    public void returnButton(){
        Main.isFinish = false;
        //startNanoTime = currTime;// - 10_000_000_000L;
        initializeGame();
        Main.isStart = true;
        flag = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setText("" + score);
    }

    private static void initializeGame(){
        neutralFishes.clear();
        //score = 0;
        player = new MyFish(PLAYER_SIZE);
        player.setTranslateX(PLAYER_X);
        player.setTranslateY(PLAYER_Y);

        for(int i = 0; i < NUMBER_FISH; i++) {
            double rand = Math.random();
            if(rand >= 0.5) {
                neutralFishes.add(new Enemy(true));
            }
            else {
                neutralFishes.add(new Enemy(false));
            }
            gameRoot.getChildren().add(neutralFishes.get(i));
        }
        gameRoot.getChildren().add(player);

        File imageEnemyFish = new File("src/sample/Resources/pictures/enemy.png");
        for(Enemy fishEnemy: neutralFishes) {
            enemyFish = new Image(imageEnemyFish.toURI().toString(), fishEnemy.getSize(), fishEnemy.getSize(), false, false);
            fishEnemy.setImage(enemyFish);
        }
    }


}
