package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

// class for holding all static stuff
public class Globals {

    public static final double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    public static Stage window;
    public static Scene startScene;

    public static Image snakeHead = new Image("snake_head.png");
    public static Image unhealthyHead = new Image("unhealthy_head.png");
    public static Image almostDestroyedHead = new Image("almost_destroyed.png");
    public static Image snakeBody = new Image("snake_body.png");
    public static Image simpleEnemy = new Image("simple_enemy.png");
    public static Image powerupBerry = new Image("powerup_berry.png");
    public static Image slow = new Image("slow.png");
    public static Image turn = new Image("turn.png");
    public static Image fast = new Image("fast.png");
    public static Image powerupBerry = new Image("powerup_egg.png");

    //.. put here the other images you want to use

    public static Image redLaser = new Image("red_laser.png");

    public static Label healthCounter = new Label();

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
