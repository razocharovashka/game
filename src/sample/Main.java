package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import sample.gameclass.Enemy;
import sample.gameclass.MyFish;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {
    //
    public static Scene mainScene;
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private static GraphicsContext graphicsContext;
    //
    private static MyFish player;
    private static Enemy fish;
    //
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 30;
    private static final int PLAYER_X = 400;
    private static final int PLAYER_Y = 300;
    //
    private static ArrayList<String> input = new ArrayList<String>();
    private static long startNanoTime = System.nanoTime();

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

        Random rand = new Random();
//        int r = rand.nextInt();
//        int g = rand.nextInt();
//        int b = rand.nextInt();
        int size = (int)(Math.random() * 30 + 10);
        fish = new Enemy(Color.GOLD, size, 1);
        fish.setTranslateX(0);
        fish.setTranslateY(120);
        
        gameRoot.getChildren().add(fish);
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
        fish.Move(fish.getVelocity(), 0);
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
        mainScene = new Scene(sceneCreate());
        primaryStage.setScene(mainScene);

        handler();

//
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
