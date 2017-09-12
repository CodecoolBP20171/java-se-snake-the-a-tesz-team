package com.codecool.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Text;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage window;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Snake Game");
        window = primaryStage;

        Pane menuScene = new Pane();

        Button startButton = new Button("START GAME");
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setFullScreen(true); //maxsize
        startButton.setLayoutX(300);
        startButton.setLayoutY(150);


        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        javafx.scene.text.Text info = new Text();
        info.setEffect(ds);
        info.setCache(true);
        info.setX(Globals.WINDOW_WIDTH*0.3);
        info.setY(40);
        info.setFill(Color.BLACK);
        info.setText("Press 'P' to Pause the game or 'R' to Restart");
        info.setFont(Font.font(null, FontWeight.BOLD, 32));


        startButton.setOnAction(e -> {
                    Game game = new Game();
                    game.getChildren().add(Globals.healthCounter);
                    Scene gameScene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
                    gameScene.getStylesheets().add("css/snake.css");
                    primaryStage.setScene(gameScene);
                    Globals.window = primaryStage;
                    game.start();
                }
        );

        menuScene.getChildren().addAll(startButton, info);
        menuScene.getStylesheets().add("css/menu.css");

        Globals.startScene = new Scene(menuScene, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

        primaryStage.setScene(Globals.startScene);
        primaryStage.show();
    }

}
