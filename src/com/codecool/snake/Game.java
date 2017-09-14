package com.codecool.snake;


import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.enemies.SnakeTracker;
import com.codecool.snake.entities.enemies.Vary;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;


public class Game extends Pane {
    public static int numOfPowerUps;
    private static boolean ableToSpawnPowerup = false;
    public static Game thisGame;
    private static int powerUpDelay;
    private static int enemyDelay = 20000;

    private static boolean ableToSpawnEnemy = false;

    public Game() {
        newGame();
    }

    public void newGame() {
        Random randomizer = new Random();
        int rndSimpleEnemy = randomizer.nextInt((8 - 1) + 1) + 1;
        int rndSnakeTracker = randomizer.nextInt((5 - 1) + 1) + 1;
        int rndVary = randomizer.nextInt((4 - 1) + 1) + 1;


        for (int i = 1; i < rndSimpleEnemy; i++) {
            new SimpleEnemy(this);
        }

        for (int i = 1; i < rndSnakeTracker; i++) {
            new SnakeTracker(this);
        }

        for (int i = 1; i < rndVary; i++) {
            new Vary(this);
        }


        new SnakeHead(this, 500, 500);
        thisGame = this;

        new SimplePowerup(this);

        randomizePowerSpawn();
        generatePowerUp();
        generateEnemy();
        Globals.gameOver = false;
    }


    public static void generatePowerUp() {
        ActionListener spawnPowerUp = evt -> ableToSpawnPowerup = true;
        Timer timer = new Timer(powerUpDelay, spawnPowerUp);

        timer.setRepeats(true);
        timer.start();
    }

    public static void generateEnemy() {
        ActionListener spawnEnemy = evt -> ableToSpawnEnemy = true;

        Timer timer = new Timer(enemyDelay, spawnEnemy);

        timer.setRepeats(true);
        timer.start();
    }

    public static int randomizePowerUp() {
        Random powerUpRandomizer = new Random();
        return powerUpRandomizer.nextInt(5 - 1 + 1) + 1;
    }

    public static boolean isAbleToSpawnEnemy() {
        return ableToSpawnEnemy;
    }

    public static void setAbleToSpawnEnemy(boolean ableToSpawnEnemy) {
        Game.ableToSpawnEnemy = ableToSpawnEnemy;
    }


    public static boolean getAbleToSpawnPowerup() {
        return ableToSpawnPowerup;
    }

    public static void setAbleToSpawnPowerup(boolean value) {
        ableToSpawnPowerup = value;
    }

    public static void randomizePowerSpawn() {
        Random randomGenerator = new Random();
        powerUpDelay = randomGenerator.nextInt(10000 - 2000 + 1) + 2000;
    }


    public Game getThis() {
        return this;
    }

    public void start() {

        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    Globals.leftKeyDown = true;
                    break;
                case RIGHT:
                    Globals.rightKeyDown = true;
                    break;
                case SPACE:
                    Globals.spaceDown = false;
                    break;
                case P:
                    Globals.paused = !Globals.paused;
                    break;
                case R:
                    restart();
                    break;
                case SHIFT:
                    if (Globals.numOfNitros > 0) {
                        Globals.isNitro = true;
                        break;
                    } else {
                        break;
                    }
                case Q:
                    Globals.window.close();
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    Globals.leftKeyDown = false;
                    break;
                case RIGHT:
                    Globals.rightKeyDown = false;
                    break;
                case SPACE:
                    Globals.spaceDown = true;
                    break;
            }
        });
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }

    private void restart() {
        for (GameEntity gameEntity : Globals.gameObjects) {
            gameEntity.destroy();
        }
        Globals.rightKeyDown = false;
        Globals.leftKeyDown = false;
        Globals.shootCounter = 5;
        Main.mediaPlayer.stop();
        Main.mediaPlayer.play();
        newGame();
    }

}
