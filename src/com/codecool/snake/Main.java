package com.codecool.snake;

import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import javax.xml.bind.annotation.XmlElementDecl;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static MediaPlayer mediaPlayer = new MediaPlayer(Globals.sound);
    public static Stage window;

	@Override
	public void start(Stage primaryStage) {

		// creating a Pane
		primaryStage.setTitle("Snake Game");
		Globals.window = primaryStage;
		Pane menuScene = new Pane();
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setFullScreen(true); //maxsize
		primaryStage.setMaximized(true);


		// start button
		Button startButton = new Button("START GAME");
		startButton.setLayoutX(Globals.WINDOW_WIDTH*0.05);
		startButton.setLayoutY(Globals.WINDOW_HEIGHT-250);
		startButton.setScaleX(0.8);
		startButton.setScaleY(0.8);

		//exit button
		Button exitButton = new Button("Exit Game");
		exitButton.setLayoutX(Globals.WINDOW_WIDTH*0.05);
		exitButton.setLayoutY(Globals.WINDOW_HEIGHT-120);
		exitButton.setScaleX(0.8);
		exitButton.setScaleY(0.8);
		exitButton.setOnAction(e -> {
			primaryStage.close();
				}
		);


		// info button
		Button infoButton = new Button("Information");
		infoButton.setLayoutX(Globals.WINDOW_WIDTH*0.05);
		infoButton.setLayoutY(Globals.WINDOW_HEIGHT-185);
		infoButton.setScaleX(0.8);
		infoButton.setScaleY(0.8);

		// new text shadow
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));

		// new text keybindings
		javafx.scene.text.Text keybindings = new Text();
		keybindings.setText("Snake by The A(tesz) team");
		keybindings.setEffect(shadow);
		keybindings.setCache(true);
		keybindings.setX(350);
		keybindings.setY(80);
		keybindings.setFill(Color.BLACK);
		keybindings.setFont(Font.font(null, FontWeight.BOLD, 32));


		// start button click handler
		startButton.setOnAction(e -> {
					Game game = new Game();
					Globals.ammoCounter.setTranslateY(40);
					game.getChildren().addAll(Globals.healthCounter, Globals.ammoCounter);
					Scene gameScene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
					gameScene.getStylesheets().add("css/snake.css");
					primaryStage.setScene(gameScene);
					Globals.window = primaryStage;
					SnakeHead.setScore(0);
					if (Globals.isMusicOn){
					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //music plays until game ends
					mediaPlayer.play();
					}
					Globals.window.setFullScreen(true);
					game.start();
				}
		);
		// info button click handler
		infoButton.setOnAction(e -> {
			showInfoScreen();
		}
		);

		// adding button and texts to start screen
		menuScene.getChildren().addAll(startButton, keybindings, infoButton, exitButton);
		menuScene.getStylesheets().add("css/menu.css");

		// crating & show scene
		Globals.startScene = new Scene(menuScene, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		primaryStage.setScene(Globals.startScene);
		Globals.window.setFullScreen(true);
		primaryStage.show();
	}
  
	public static void showGameOver(){
		Pane gameOverScene = new Pane();
		Globals.endScene = new Scene(gameOverScene, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

		// menu button
		Button menuButton = new Button("Back to Main menu");
		menuButton.setLayoutX(450);
		menuButton.setLayoutY(500);
		menuButton.setScaleX(0.5);
		menuButton.setScaleY(0.5);
		menuButton.setOnAction(e -> {
			Globals.window.setScene(Globals.startScene);
			Globals.window.setFullScreen(true);
			Globals.window.show();
		});
		//shadow for text
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		//GameOver message
		javafx.scene.text.Text info = new Text();
		info.setText("Meh, you got snaked!");
		info.setEffect(shadow);
		info.setCache(true);
		info.setX(450);
		info.setY(100);
		info.setFill(Color.BLACK);
		info.setFont(Font.font(null, FontWeight.BOLD, 32));
		//results text
		javafx.scene.text.Text results = new Text();
		results.setText("Your score: " + SnakeHead.getScore());
		results.setEffect(shadow);
		results.setCache(true);
		results.setX(450);
		results.setY(250);
		results.setFill(Color.BLACK);
		results.setFont(Font.font(null, FontWeight.BOLD, 30));
		//add buttons and style to scene
		gameOverScene.getChildren().addAll(menuButton,info,results);
		gameOverScene.getStylesheets().add("css/gameOver.css");
		Globals.window.setScene(Globals.endScene);
		Globals.window.setFullScreen(true);
		Globals.window.show();
	}
	public static void showInfoScreen(){
		Pane infoScreen = new Pane();
		Globals.infoScene = new Scene(infoScreen, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		// menu button
		Button menuButton = new Button("Back to Main menu");
		menuButton.setLayoutX(Globals.WINDOW_WIDTH*0.33);
		menuButton.setLayoutY(Globals.WINDOW_HEIGHT*0.88);
		menuButton.setScaleX(0.5);
		menuButton.setScaleY(0.5);
		menuButton.setOnAction(e -> {
			Globals.window.setScene(Globals.startScene);
			Globals.window.setFullScreen(true);
			Globals.window.show();
		});
		// music button
		Button musicButton = new Button();
		if(Globals.isMusicOn) {
			musicButton.setText("Music On");
		}else if(!Globals.isMusicOn){
			musicButton.setText("Music Off");
		}
		musicButton.setLayoutX(Globals.WINDOW_WIDTH*0.36);
		musicButton.setLayoutY(Globals.WINDOW_HEIGHT*0.95);
		musicButton.setScaleX(0.4);
		musicButton.setScaleY(0.4);
		musicButton.setId("musicBTN");
		musicButton.setOnAction((ActionEvent e) -> {
			if(Globals.isMusicOn) {
				Globals.isMusicOn = false;
				musicButton.setText("Music OFF");
			}else if(!Globals.isMusicOn){
				Globals.isMusicOn=true;
				musicButton.setText("Music On");
			}
		});
		//shadow for text
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		//info message
		javafx.scene.text.Text info = new Text();
		info.setText("Controls: Use left or right arrows to turn \n\nEnemies: Simple,Vary,SnakeTracker \n\nCredits: Gina, Tamás, Barna, Márk\n\n");
		info.setEffect(shadow);
		info.setCache(true);
		info.setX(250);
		info.setY(100);
		info.setFill(Color.BLACK);
		info.setFont(Font.font(null, FontWeight.BOLD, 24));

		//images
		ImageView enemies = new ImageView(new Image("enemies2.png"));
		enemies.setFitWidth(enemies.getImage().getWidth());
		enemies.setFitHeight(enemies.getImage().getHeight());
		enemies.setX(Globals.WINDOW_WIDTH*0.040);
		enemies.setY(Globals.WINDOW_HEIGHT-300);

		ImageView powerups = new ImageView(new Image("powerups2.png"));
		powerups.setFitWidth(powerups.getImage().getWidth());
		powerups.setFitHeight(powerups.getImage().getHeight());
		powerups.setX(Globals.WINDOW_WIDTH*0.6);
		powerups.setY(Globals.WINDOW_HEIGHT-500);

		//add buttons and style to scene
		infoScreen.getChildren().addAll(menuButton,info,musicButton,enemies,powerups);
		infoScreen.getStylesheets().add("css/info.css");
		Globals.window.setScene(Globals.infoScene);
		Globals.window.setFullScreen(true);
		Globals.window.show();
	}

}
