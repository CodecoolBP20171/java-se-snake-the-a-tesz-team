package com.codecool.snake;

import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.powerups.SlowMotionPowerUp;
import com.codecool.snake.entities.powerups.SpeedingPowerUp;
import com.codecool.snake.entities.powerups.TurnRatePowerUp;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Game extends Pane {

    public static int numOfPowerUps;

    public Game() {
        new SnakeHead(this, 500, 500);

        generatePowerUp(this);


    }

    public void generatePowerUp(Game game) {
        while (numOfPowerUps <= 5) {
            Random rnd = new Random();
            int powerUpRandom = rnd.nextInt(4 - 1) + 1;
            if (powerUpRandom == 1) {
                new SlowMotionPowerUp(game);
            } else if (powerUpRandom == 2) {
                new SimplePowerup(game);
            } else if(powerUpRandom == 3) {
                new SpeedingPowerUp(game);
            } else if (powerUpRandom == 4) {
                new TurnRatePowerUp(game);
            }
            numOfPowerUps++;
        }
    }



    public void start() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = true; break;
                case RIGHT: Globals.rightKeyDown  = true; break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = false; break;
                case RIGHT: Globals.rightKeyDown  = false; break;
            }
        });
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }
}
