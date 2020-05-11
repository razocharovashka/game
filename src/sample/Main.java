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
import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import sample.gameclass.Enemy;
import sample.gameclass.MyFish;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Main extends Application {
    //
    public static Scene mainScene;
    public static Scene finalScene = null;
    public static final int HEIGHT = 620;
    public static final int WIDTH = 800;
    public static GraphicsContext graphicsContext;
    private static Image fishLeft;
    private static Image fishRight;
    private static Image backgroundImage;
    private static Image lastSide;
    public static Image enemyFish;
    //
    public static MyFish player;

    public static boolean isFinish = false;
    public static List<Enemy> neutralFishes;   //нейтральные рыбы
    public static int PLAYER_SIZE = 50;
    private static final int PLAYER_X = 400;
    private static final int PLAYER_Y = 300;
    private static final int PLAYER_VELOCITY = 5;
    public static final int VELOCITY = 3;
    //
    private static ArrayList<String> input = new ArrayList<String>(100);
    private static long startNanoTime = System.nanoTime();

    public static Pane gameRoot = new Pane();

    private static Parent sceneCreate(){
        //
        Group appRoot = new Group();
        //
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();
        //

        gameRoot.setPrefSize(WIDTH, HEIGHT);
        //
        player = new MyFish(PLAYER_SIZE);
        player.setTranslateX(PLAYER_X);
        player.setTranslateY(PLAYER_Y);

        neutralFishes = new ArrayList<Enemy>();
        for(int i = 0; i < 20; i++) {
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
        appRoot.getChildren().add(gameRoot);
        appRoot.getChildren().add(canvas);
        return appRoot;
    }

    private static void handler() {
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                if (!input.contains(code))
                    input.add(code);
            }
        });

        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                input.remove(code);
            }
        });
    }

    private static void loadImage(){
        fishLeft = new Image(new File("src/sample/Resources/pictures/fish2.png").toURI().toString(), 50, 50, false, false);
        fishRight = new Image(new File("src/sample/Resources/pictures/fish2_new.png").toURI().toString(), 50, 50, false, false);
        backgroundImage = new Image(new File("src/sample/resources/pictures/background.png").toURI().toString(), 800, 600, false, false);
        lastSide = fishLeft;

    }

    private static void update(long currentNanoTime){
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        graphicsContext.drawImage(backgroundImage, 0, 0);

        //File fishFile = new File("src/sample/resources/pictures/fish2.png");

        for (Enemy fishEnemy : neutralFishes) {
            if(fishEnemy.isVisible()) {
                //if(fishEnemy.isFlag()) {
                    enemyFish = new Image(new File("src/sample/Resources/pictures/enemy.png").toURI().toString(), fishEnemy.getSize(), fishEnemy.getSize(), false, false);
                    graphicsContext.drawImage(enemyFish, fishEnemy.getTranslateX(), fishEnemy.getTranslateY());
                //}
                //else {
                  //  enemyFish = new Image(new File("src/sample/Resources/pictures/enemyLeft.png").toURI().toString(), fishEnemy.getSize(), fishEnemy.getSize(), false, false);
                    //graphicsContext.drawImage(enemyFish, fishEnemy.getTranslateX(), fishEnemy.getTranslateY());
                //}
            }
            fishEnemy.Move(fishEnemy.getVelocity(), 0);
        }
        //
        //Если игрок не движется
        if (!input.contains("RIGHT") && !input.contains("LEFT") && !input.contains("UP")
                && !input.contains("DOWN")) {
            graphicsContext.drawImage(lastSide, player.getTranslateX(), player.getTranslateY());
        }

        if (input.contains("LEFT")){
            player.Move(PLAYER_VELOCITY * (-1), 0);
            graphicsContext.drawImage(fishLeft, player.getTranslateX(), player.getTranslateY());
            lastSide = fishLeft;
        }

        if (input.contains("RIGHT")){
            player.Move(PLAYER_VELOCITY * 1, 0);
            graphicsContext.drawImage(fishRight, player.getTranslateX(), player.getTranslateY());
            lastSide = fishRight;
        }

        if (input.contains("UP")) {
            player.Move(0, PLAYER_VELOCITY * (-1));
            graphicsContext.drawImage(lastSide, player.getTranslateX(), player.getTranslateY());
        }

        if (input.contains("DOWN")) {
            player.Move(0, PLAYER_VELOCITY * 1);
            graphicsContext.drawImage(lastSide, player.getTranslateX(), player.getTranslateY());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fish eating small fish");
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
        primaryStage.setResizable(false);
        mainScene = new Scene(sceneCreate());
        primaryStage.setScene(mainScene);

        try {
            finalScene = new Scene(FXMLLoader.load(Main.class.getResource("Resources/view/sample.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler();
        loadImage();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update(currentNanoTime);
                if(isFinish){
                    primaryStage.setScene(finalScene);
                }
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
