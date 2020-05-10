package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends Application {
    //
    public static Scene mainScene;
    public static final int HEIGHT = 600;
    public static final int WIDTH = 800;
    private static GraphicsContext graphicsContext;
    //
    private static MyFish player;
//    private static Enemy fish1;
//    private static Enemy fish2;
//    private static Enemy fish3;
//    private static Enemy fish4;
//    private static Enemy fish5;
//    private static Enemy fish6;

    private static List<Enemy> neutralFishes;   //нейтральные рыбы
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 30;
    private static final int PLAYER_X = 400;
    private static final int PLAYER_Y = 300;
    private static final int VELOSITY = 1;
    //
    private static ArrayList<String> input = new ArrayList<String>();
    private static long startNanoTime = System.nanoTime();
    private static int counter = 0;

    private static Parent sceneCreate(){
        //
        Group appRoot = new Group();
        //
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();
        //
        Pane gameRoot = new Pane();
        gameRoot.setPrefSize(WIDTH, HEIGHT);
        //
        player = new MyFish(PLAYER_HEIGHT, PLAYER_WIDTH);
        player.setTranslateX(PLAYER_X);
        player.setTranslateY(PLAYER_Y);

        neutralFishes = new ArrayList<Enemy>();
        for(int i = 0; i < 100; i++) {
            int size = (int) (Math.random() * 100 + 10);
            int locationY = (int) (Math.random() * 6) * 100;//
            int locationLeftX = (int) (Math.random() * 10000) * (-1);
            int locationRightX = (int) (Math.random() * 10000) + 1000;
            double rand = Math.random();
            if(rand >= 0.5) {
                neutralFishes.add(new Enemy(Color.BLACK, size, VELOSITY, true));
                neutralFishes.get(i).setTranslateX(locationLeftX);
            }
            else {
                neutralFishes.add(new Enemy(Color.BLACK, size, VELOSITY, false));
                neutralFishes.get(i).setTranslateX(locationRightX);
            }
            neutralFishes.get(i).setTranslateY(locationY);
            gameRoot.getChildren().add(neutralFishes.get(i));
        }
            //есть min max
          //  max -= min;
        //(int) (Math.random() * ++max) + min
//            int size = (int) (Math.random() * 100 + 10);
//            fish = new Enemy(Color.BLACK, size, VELOSITY, true);
//            fish.setTranslateX(-110);
//            fish.setTranslateY(120);
//
//
//            size = (int) (Math.random() * 100 + 10);
//            fish2 = new Enemy(Color.BLACK, size, VELOSITY, false);
//            fish2.setTranslateX(800);
//            fish2.setTranslateY(300);

        //gameRoot.getChildren().add(fish);
        //gameRoot.getChildren().add(fish2);
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

    private static void update(long currentNanoTime){
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        //File backgroundImage = new File("src/sample/resources/pictures/background.png");
        //graphicsContext.drawImage(new Image(backgroundImage.toURI().toString(), 800, 600, false, false), 0, 0);
//        fish = list.get(0);
////        //
////        int i = 1;
////        counter++;
////        //ок
////        if((counter == 1000)&&(i < list.size())) {
////            fish = list.get(i);
////            fish.Move(fish.getVelocity(), 0);
////            i++;
////            counter = 0;
////

        for(Enemy fish: neutralFishes) {
            fish.Move(fish.getVelocity(), 0);
        }
        //

        if (input.contains("LEFT")){
            player.Move(-2, 0);
        }

        if (input.contains("RIGHT")){
            player.Move(2, 0);
        }

        if (input.contains("UP")) {
            player.Move(0, -2);
        }

        if (input.contains("DOWN")) {
            player.Move(0, 2);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fish eating small fish");
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
        mainScene = new Scene(sceneCreate());
        primaryStage.setScene(mainScene);

        handler();
//        Sprite background = new Sprite();
//        File backgroundFile = new File("C:\\Users\\Алена\\IdeaProjects\\GameFish\\src\\sample\\pictures\\background.png");
//        background.setImage(new Image(backgroundFile.toURI().toString(), 800, 600, false, false));
//        background.setPosition(0, 0);
//
//        Sprite fish = new Sprite();
//        File fishFile = new File("C:\\Users\\Алена\\IdeaProjects\\GameFish\\src\\sample\\pictures\\fish2_new.png");
//        fish.setImage(new Image(fishFile.toURI().toString(), 30, 30, false, false));
//        fish.setPosition(400, 300);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update(currentNanoTime);
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
