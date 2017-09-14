package com.codecool.snake;


import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.enemies.SnakeTracker;
import com.codecool.snake.entities.enemies.Vary;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;


public class Game extends Pane {

    public static int numOfPowerUps;
    private static boolean ableToSpawn = false;
    public static Game thisGame;
    private static int powerUpDelay;

    public Game() {
        newGame();
    }

    public void newGame() {
        new SnakeHead(this, 500, 500);
        thisGame = this;

        new SnakeTracker(this);
        new Vary(this);
        new SimpleEnemy(this);

        randomPowerSpawn();
        generatePowerUp();
        Globals.gameOver = false;
    }

    public static void generatePowerUp() {
        ActionListener spawnPowerUp = evt -> ableToSpawn = true;
        ActionListener randomDelay = evt -> randomPowerSpawn();

        Timer timer = new Timer(powerUpDelay, spawnPowerUp);
        Timer delayTimer = new Timer(powerUpDelay, randomDelay);

        delayTimer.setRepeats(true);
        delayTimer.start();

        timer.setRepeats(true);
        timer.start();
    }

    public static int randomizePowerUp() {
        Random powerUpRandomizer = new Random();
        return powerUpRandomizer.nextInt(4 - 1 + 1) +1;
    }


    public static boolean getAbleToSpawn(){
        return ableToSpawn;
    }
    public static void setAbleToSpawn(boolean value){
        ableToSpawn=value;
    }

    public static void randomPowerSpawn () {
        Random randomGenerator = new Random();
        powerUpDelay = randomGenerator.nextInt(10000 - 2000 +1) + 2000;
    }

    public Game getThis() {
        return this;
    }

    public void start() {

        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = true; break;
                case RIGHT: Globals.rightKeyDown  = true; break;
                case SPACE: Globals.spaceDown = false; break;
                case P:     Globals.paused = !Globals.paused; break;
                case R:     restart(); break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = false; break;
                case RIGHT: Globals.rightKeyDown  = false; break;
                case SPACE: Globals.spaceDown = true; break;
            }
        });
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }

    private void restart() {
        for (GameEntity gameEntity : Globals.gameObjects) {
            gameEntity.destroy();
        }
        newGame();
    }
}
