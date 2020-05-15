package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import sample.Main;
import sample.gameclass.Enemy;
import sample.gameclass.EnemyMoney;
import sample.gameclass.MagicMoney;
import sample.gameclass.MyFish;
import static sample.Main.*;

import java.io.File;

public class Controller {
    @FXML
    private Button returnButton; //кнопка
    //public Text text;

    @FXML
    public void returnButton(){
        Main.isFinish = false; //финальная сцена закрылась
        initializeGame(); //инициализация игры
        //игра началась
        Main.isStart = true;
        flag = true;
    }
    //инициализация игры
    private static void initializeGame(){
        enemies.clear(); //очищаем массив рыб
        gameRoot.getChildren().removeAll();
        gameRoot.getChildren().clear();
        score = 0; //обнуляем счет
        player = new MyFish(PLAYER_SIZE, PLAYER_X, PLAYER_Y); //новый игрок
        enemyMoney = new EnemyMoney(50); //создаем плохую монетку
        magicMoney = new MagicMoney(50); //создаем волшебную монетку

        //заполняем массив рыб
        for(int i = 0; i < NUMBER_FISH; i++) {
            double rand = Math.random(); //рандом [0, 1)
            if(rand >= 0.5) {
                enemies.add(new Enemy(true)); //добавляем рыбку, которая плывет слева направо
            }
            else {
                enemies.add(new Enemy(false)); //добавляем рыбку, которая плывет справа налево
            }
            gameRoot.getChildren().add(enemies.get(i));
        }
        gameRoot.getChildren().add(player);
        gameRoot.getChildren().add(magicMoney); //добавляем монетку в игровой узел
        gameRoot.getChildren().add(enemyMoney); //добавляем монетку в игровой узел
        //картинка рыб
        File imageEnemyFish = new File("src/sample/Resources/pictures/enemy.png");
        for(Enemy fishEnemy: enemies) { //для каждой рыбки
            enemyFish = new Image(imageEnemyFish.toURI().toString(), fishEnemy.getSize(), fishEnemy.getSize(), false, false);
            fishEnemy.setImage(enemyFish); //устанавливаем картинку
        }
    }
}
