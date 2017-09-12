package com.codecool.snake;


import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game extends Pane {

    public static int numOfPowerUps;
    private static boolean ableToSpawn = false;
    public static Game thisGame;
    private static int powerUpDelay;

    public Game() {
        new SnakeHead(this, 500, 500);
        thisGame = this;
        String musicFile = "resources/Angry Birds Theme Song.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        randomPowerSpawn();
        generatePowerUp();
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
        System.out.println(powerUpDelay);
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
}
