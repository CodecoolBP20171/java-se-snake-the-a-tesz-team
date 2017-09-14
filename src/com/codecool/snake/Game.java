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


    private static ActionListener spawnPowerUp = evt -> ableToSpawn = true;
    private static Timer timer = new Timer(powerUpDelay, spawnPowerUp);


    public Game() {
        newGame();
    }

    public void newGame() {
        new SnakeHead(this, 500, 500);
        thisGame = this;

        new SnakeTracker(this);
        new Vary(this);
        new SimpleEnemy(this);

        randomizePowerSpawn(7000, 2000);
        startTimers();
        Globals.gameOver = false;
    }

    private static void startTimers() {
        timer.setRepeats(true);
        timer.start();
    }

    public static void endTimers() {
        timer.stop();
    }

    public static int chooseRandomPowerUp() {
        Random powerUpRandomizer = new Random();
        return powerUpRandomizer.nextInt(7 - 1 + 1) +1;
    }


    public static boolean getAbleToSpawn(){
        return ableToSpawn;
    }
    public static void setAbleToSpawn(boolean value){
        ableToSpawn=value;
    }

    public static void randomizePowerSpawn(int max, int min) {
        Random randomGenerator = new Random();
        powerUpDelay = randomGenerator.nextInt(max - min +1) + min;
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
                case SHIFT: Globals.nitro = true; break;
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
