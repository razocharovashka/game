package sample.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import sample.gameclass.LongValue;
import sample.gameclass.Sprite;

import java.io.File;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage theStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        theStage.setTitle("Fish eating small fish");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                if ( !input.contains(code) )
                    input.add( code );
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                input.remove( code );
            }
        });

        Sprite background = new Sprite();
        File backgroundFile = new File("C:\\Users\\Алена\\IdeaProjects\\GameFish\\src\\sample\\pictures\\background.png");
        background.setImage(new Image(backgroundFile.toURI().toString(), 800, 600, false, false));
        background.setPosition(0, 0);

        Sprite fish = new Sprite();
        File fishFile = new File("C:\\Users\\Алена\\IdeaProjects\\GameFish\\src\\sample\\pictures\\fish2.png");
        fish.setImage(new Image(fishFile.toURI().toString(), 30, 30, false, false));
        fish.setPosition(400, 300);

        LongValue lastNanoTime = new LongValue( System.nanoTime() );

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // background image clears canvas
                background.setVelocity(0,0);
                background.update(t);
                background.render(gc);

                fish.setVelocity(0, 0);

                if (input.contains("LEFT"))
                    fish.addVelocity(-100,0);
                if (input.contains("RIGHT"))
                    fish.addVelocity(100,0);
                if (input.contains("UP"))
                    fish.addVelocity(0,-100);
                if (input.contains("DOWN"))
                    fish.addVelocity(0,100);

                fish.update(t);
                fish.render(gc);
            }
        }.start();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
