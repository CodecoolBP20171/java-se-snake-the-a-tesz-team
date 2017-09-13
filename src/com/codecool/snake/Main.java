package com.codecool.snake;

import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {



	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// creating a Pane
		primaryStage.setTitle("Snake Game");
		Globals.window = primaryStage;
		Pane menuScene = new Pane();

		// start button
		Button startButton = new Button("START GAME");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setFullScreen(true); //maxsize
		startButton.setLayoutX(300);
		startButton.setLayoutY(150);

		// new text shadow
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));

		// new text keybindings
		javafx.scene.text.Text keybindings = new Text();
		keybindings.setText("Press 'P' to Pause the game or 'R' to Restart");
		keybindings.setEffect(shadow);
		keybindings.setCache(true);
		keybindings.setX(Globals.WINDOW_WIDTH * 0.3);
		keybindings.setY(40);
		keybindings.setFill(Color.BLACK);
		keybindings.setFont(Font.font(null, FontWeight.BOLD, 32));

		// new text player info
		javafx.scene.text.Text info = new Text();
		info.setText("Health: " + "health_here" + " " + "Score: " + "length_here");
		info.setEffect(shadow);
		info.setCache(true);
		info.setX(Globals.WINDOW_WIDTH * 0.3);
		info.setY(Globals.WINDOW_HEIGHT);
		info.setFill(Color.BLACK);
		info.setFont(Font.font(null, FontWeight.BOLD, 32));

		// start button click handler
		startButton.setOnAction(e -> {
					Game game = new Game();
					game.getChildren().add(Globals.healthCounter);
					Scene gameScene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
					gameScene.getStylesheets().add("css/snake.css");
					primaryStage.setScene(gameScene);
					Globals.window = primaryStage;
					MediaPlayer mediaPlayer = new MediaPlayer(Globals.sound);
					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //music plays until game ends
					mediaPlayer.play();
					game.start();
				}
		);

		// adding button and texts to start screen
		menuScene.getChildren().addAll(startButton, keybindings, info);
		menuScene.getStylesheets().add("css/menu.css");

		// crating & show scene
		Globals.startScene = new Scene(menuScene, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		primaryStage.setScene(Globals.startScene);
		primaryStage.show();
	}

}
