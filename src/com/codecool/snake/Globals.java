package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.stage.Screen;
import javafx.stage.Stage;

// class for holding all static stuff
public class Globals {

    public static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    public static Stage window;
    public static Scene startScene;
    public static Scene endScene;
    public static Scene infoScene;
    public static boolean paused;
    public static boolean gameOver;
    public static boolean isNitro = false;
    public static int numOfNitros = 0;

    //images
    public static Image snakeHead = new Image("snake_head.png");
    public static Image unhealthyHead = new Image("kingcry.gif");
    public static Image almostDestroyedHead = new Image("almost_destroyed.png");
    public static Image snakeBody = new Image("snake_body.png");
    public static Image simpleEnemy = new Image("simple_enemy.gif");
    public static Image snakeTracker = new Image("snake_tracker.gif");
    public static Image vary = new Image("vary.gif");
    public static int shootCounter = 5;
    public static Image slow = new Image("slow.png");
    public static Image turn = new Image("turn.png");
    public static Image fast = new Image("fast.png");
    public static Image health = new Image("health.png");
    public static Image nitro = new Image("nitro.gif");
    public static Image powerupEgg = new Image("powerup_egg.png");

    public static Image redLaser = new Image("red_laser.png");

    //sound
    public static Media sound = new Media(new File("resources/Angry Birds Theme Song.mp3").toURI().toString());
    public static boolean isMusicOn = true;

    //counter
    public static Label healthCounter = new Label();
    public static Label ammoCounter = new Label();

    
    public static boolean leftKeyDown;
    public static boolean rightKeyDown;
    public static boolean spaceDown;
    public static List<GameEntity> gameObjects;
    public static List<GameEntity> newGameObjects; // Holds game objects crated in this frame.
    public static List<GameEntity> oldGameObjects; // Holds game objects that will be destroyed this frame.
    public static GameLoop gameLoop;


    static {
        gameObjects = new LinkedList<>();
        newGameObjects = new LinkedList<>();
        oldGameObjects = new LinkedList<>();
    }

    public static void addGameObject(GameEntity toAdd) {
        newGameObjects.add(toAdd);
    }

    public static void removeGameObject(GameEntity toRemove) {
        oldGameObjects.add(toRemove);
    }

    public static List<GameEntity> getGameObjects() {
        return Collections.unmodifiableList(gameObjects);
    }
}
