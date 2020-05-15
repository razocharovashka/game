package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import sample.gameclass.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    //параметры экрана
    public static Scene mainScene; //главная сцена - игра
    public static Scene finalScene = null; //последняя сцена - после окончания игры
    public static final int WIDTH = 800; //ширина окна
    public static final int HEIGHT = 620; //высота окна

    public static GraphicsContext graphicsContext; //для графики

    //картинки для объектов
    public static Image fishLeft; //игрок движется влево
    public static Image fishRight; //игрок движется вправо
    public static Image backgroundImage; //фон
    public static Image lastSide; //последняя картинка игрока
    public static Image enemyFish; //картинка рыб
    public static Image moneyImage; //картинка монетки

    //параметры объектов
    public static Fish player; //моя рыбка - игрок
    public static Money enemyMoney; //монетка плохая
    public static Money magicMoney; //волшебная монетка
    public static List<Enemy> enemies; //рыбы
    public static int PLAYER_SIZE = 50; //размер игрока
    public static final int PLAYER_X = 400; //координата Х игрока
    public static final int PLAYER_Y = 300; //кордината У игрока
    public static final int PLAYER_VELOCITY = 2; //скорость игрока
    public static final int VELOCITY = 1; //скорость рыб
    public static final int NUMBER_FISH = 200; //количество рыб
    public static int score; //счет игрока - количество съеденных рыб
    //флаги начала и конца игры
    public static boolean isStart = true; //флаг старта игры
    public static boolean isFinish = false; //флаг конца игры
    public static boolean flag = true; //флаг для инициализации старта
    //
    private static ArrayList<String> input = new ArrayList<String>(); //динамический массив клавиш
    public static long startNanoTime = System.nanoTime(); //стартовое время в наносекундах

    public static Pane gameRoot = new Pane();  //узел, в котором происходит логика игры

    //создание главной сцены - самой игры
    private static Parent sceneCreate(){
        //создаем узел - корень игрового окна
        Group appRoot = new Group(); //у него будет группа узлов
        //графика
        Canvas canvas = new Canvas(WIDTH, HEIGHT); //позволяет отображать графику(узел для рисования)
        graphicsContext = canvas.getGraphicsContext2D();

        gameRoot.setPrefSize(WIDTH, HEIGHT); //устанавливаем размер узла с логикой игры
        //создаем игрока
        player = new MyFish(PLAYER_SIZE, PLAYER_X, PLAYER_Y);
        //player = new MagicFish(PLAYER_SIZE, PLAYER_X, PLAYER_Y);

        enemies = new ArrayList<Enemy>();
        for(int i = 0; i < NUMBER_FISH; i++) {
            double rand = Math.random(); //рандом [0, 1)
            //вероятность 50/50
            if(rand >= 0.5) {
                enemies.add(new Enemy(true)); //рыбка плывет вправо
            }
            else {
                enemies.add(new Enemy(false)); //рыбка плывет влево
            }
            gameRoot.getChildren().add(enemies.get(i)); //добавляем представление объекта (делаем дочений узел)
        }

        enemyMoney = new EnemyMoney(50); //создаем плохую монетку
        magicMoney = new MagicMoney(50); //создаем волшебную монетку

        gameRoot.getChildren().add(magicMoney); //добавляем монетку в игровой узел
        gameRoot.getChildren().add(enemyMoney); //добавляем монетку в игровой узел
        gameRoot.getChildren().add(player); //добавляем игрока в игровой узел
        appRoot.getChildren().add(gameRoot); //добавляем игровой узел в корень
        appRoot.getChildren().add(canvas);
        return appRoot;
    }

    //функция обработки нажатий
    private static void handler() {
        //
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() { //создаем анонимный внутренний класс
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString(); //считываем код нажатой клавиши
                if (!input.contains(code)) //сравниваем, есть ли такая клавиша в массив
                    input.add(code); //ее нет, добавляем код клавиши в массив
            }
        });

        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                input.remove(code); //удаляем клавишу из массива
            }
        });
    }

    //загрузка картинок
    private static void loadImage(){
        //игрок движется влево
        fishLeft = new Image(new File("src/sample/Resources/pictures/myFishLeft.png").toURI().toString(), 50, 50, false, false);
        //игрок движется вправо
        fishRight = new Image(new File("src/sample/Resources/pictures/myFishRight.png").toURI().toString(), 50, 50, false, false);
        //картинка фона
        backgroundImage = new Image(new File("src/sample/resources/pictures/background.png").toURI().toString(), 800, 600, false, false);
        //последнее направление игрока или при старте игры
        lastSide = fishLeft;
        //картинка рыбок
        File imageEnemyFish = new File("src/sample/Resources/pictures/enemy.png");
        for(Enemy fishEnemy: enemies) { //для каждой рыбки из массива
            //создаем новую картинку со своим размером
            enemyFish = new Image(imageEnemyFish.toURI().toString(), fishEnemy.getSize(), fishEnemy.getSize(), false, false);
            fishEnemy.setImage(enemyFish); //присваиваем картинку рыбке
        }

    }

    //обновление игры
    private static void update(long currentNanoTime){
        double t = (currentNanoTime - startNanoTime) / 1000000000.0; //сколько времени прошло с момента старта программы
        double finishTime = 60.0; //игра длится 60 секунд
        //System.out.println(t);
        //игра кончилась
        if(finishTime < t){
            startNanoTime = currentNanoTime; //новое время старта
            isStart = false; //игра кончилась
            isFinish = true; //финальная сцена
        }
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT); //очистка экрана
        graphicsContext.drawImage(backgroundImage, 0, 0); //рисуем фон
        if(magicMoney.isVisible()) { //если монетка видна
            //рисуем монетку
            graphicsContext.drawImage(magicMoney.getImage(), magicMoney.getTranslateX(), magicMoney.getTranslateY());
        }
        if(enemyMoney.isVisible()) { //если монетка видна
            //рисуем монетку
            graphicsContext.drawImage(enemyMoney.getImage(), enemyMoney.getTranslateX(), enemyMoney.getTranslateY());
        }

        //вывод строки счета
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 ); //шрифт
        graphicsContext.setFont(theFont); //установить шрифт и вывести
        graphicsContext.setFill(Color.WHITE); //установить цвет внутри
        graphicsContext.setStroke(Color.BLACK); //установить цвет обводки символов
        graphicsContext.setLineWidth(1); //установить ширину линии

        String textScore = "Score: " + score; //текст строки
        graphicsContext.fillText(textScore, 20, 30); //рисует на холсте текст с заливкой
        graphicsContext.strokeText(textScore, 20, 30); //обводка заданного текста

        String textTime = "Time: " + (int) (t);
        graphicsContext.fillText(textTime, 670, 30); //рисует на холсте текст с заливкой
        graphicsContext.strokeText(textTime, 670, 30); //обводка заданного текста

        for (Enemy fishEnemy : enemies) { //для каждой рыбки
            if(fishEnemy.isVisible()) { //если рыбка видна
                //рисуем рыбку
                graphicsContext.drawImage(fishEnemy.getImage(), fishEnemy.getTranslateX(), fishEnemy.getTranslateY());
            }
            fishEnemy.Move(fishEnemy.getVelocity(), 0); //двигаем рыбку
        }

        //обработка нажатий на клавиши
        //Если игрок не движется
        if (!input.contains("RIGHT") && !input.contains("LEFT") && !input.contains("UP")
                && !input.contains("DOWN")) {
            graphicsContext.drawImage(lastSide, player.getTranslateX(), player.getTranslateY()); //рисуем игрока
        }
        //если нажата клавиша влево
        if (input.contains("LEFT")){
            player.Move(player.getVelocity() * (-1), 0); //двигаем игрока влево
            graphicsContext.drawImage(fishLeft, player.getTranslateX(), player.getTranslateY()); //рисуем игрока
            lastSide = fishLeft; //запоминаем последнее направление
        }
        //если нажата клавиша вправо
        if (input.contains("RIGHT")){
            player.Move(player.getVelocity(), 0); //двигаем игрока вправо
            graphicsContext.drawImage(fishRight, player.getTranslateX(), player.getTranslateY()); //рисуем игрока
            lastSide = fishRight; //запоминаем последнее направление
        }
        //если нажата клавиша вверх
        if (input.contains("UP")) {
            player.Move(0, player.getVelocity() * (-1)); //двигаем игрока вверх
            graphicsContext.drawImage(lastSide, player.getTranslateX(), player.getTranslateY()); //рисуем игрока
        }
        //если нажата клавиша вниз
        if (input.contains("DOWN")) {
            player.Move(0, player.getVelocity()); //двигаем игрока вниз
            graphicsContext.drawImage(lastSide, player.getTranslateX(), player.getTranslateY()); //рисуем игрока
        }
    }

    @Override //аннотация переопределения метода
    //старт программы
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fish eating small fish"); //устанавливаем название заголовка
        primaryStage.setHeight(HEIGHT); //устанавливаем высоту
        primaryStage.setWidth(WIDTH); //устанавливаем ширину
        primaryStage.setResizable(false); //размер неизменен(нельзя увеличить экран вручную в процессе игры)
        mainScene = new Scene(sceneCreate()); //создаем главную игровую сцену

        try {
            //корень финальной сцены
            finalScene = new Scene(FXMLLoader.load(Main.class.getResource("Resources/view/sample.fxml")));
        } catch (IOException e) {
            e.printStackTrace(); //вывод стэка исключений
        }

        handler(); //вызываем обработчик нажатий
        loadImage(); //вызываем функцию загрузки картинок

        //
        new AnimationTimer() {
            public void handle(long currentNanoTime) { //currTime = currentNanoTime;

                if(isStart) { //игра запущена?
                    primaryStage.setScene(mainScene); //устанавливаем главую игровую сцену
                    if(flag) { //игра началась?
                        startNanoTime = currentNanoTime; //запоминаем стартовое время
                        flag = false;
                    }
                    update(currentNanoTime); //обновление
                }
                if(isFinish){ //игра окончена?
                    input.clear(); //очищаем массив клавиш
                    primaryStage.setScene(finalScene); //устанавливаем финальную сцену
                }
            }
        }.start(); //опять вызываем старт

        primaryStage.show(); //показать сцену
    }


    public static void main(String[] args) {
        launch(args);
    }
}
