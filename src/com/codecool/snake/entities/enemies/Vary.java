package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Vary extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private static final int damage = 10;
    private double direction = 360;
    private int speed = 1;
    private int frameCounter = 0;
    private String turnDirection;

    public Vary(Pane pane) {
        super(pane);
        setImage(Globals.vary);
        pane.getChildren().add(this);
        Random rnd = new Random();
        double SnakeHeadX=SnakeHead.getCoordX();
        double SnakeHeadY=SnakeHead.getCoordY();
        double spawnPositionX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
        double spawnPositionY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
        while (isSafeToSpawn(spawnPositionX,SnakeHeadX,spawnPositionY,SnakeHeadY)){
            spawnPositionX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
            spawnPositionY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
            System.out.println("Enemy spawn prohibited on SnakeHead");
        }
        setX(spawnPositionX);
        setY(spawnPositionY);
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    /**Checks if enemy spawn coordinates collide, or within the safe radius of the snake's head*/
    private boolean isSafeToSpawn(double spawnPositionX, double SnakeHeadX, double spawnPositionY, double SnakeHeadY){
        int safeRadius = 100;
        return (spawnPositionX>SnakeHeadX-safeRadius && spawnPositionX<SnakeHeadX+safeRadius) && (spawnPositionY>SnakeHeadY-safeRadius && spawnPositionY<SnakeHeadY+safeRadius);
        }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            direction=+90;
        }

        Random rnd = new Random();
        if(frameCounter==60){
            frameCounter=0;
            if(rnd.nextInt(100)<50){
                turnDirection="left";
            }else{
                turnDirection="right";
            }
        }
        if(turnDirection=="left") {
            double random = rnd.nextDouble();
            double start = 0;
            double end = 2;
            double result = start + (random * (end - start));
            direction=direction+result;
        }else if(turnDirection=="right"){
            double start = -2;
            double end = 0;
            double random = rnd.nextDouble();
            double result = start + (random * (end - start));
            direction=direction+result;
        }
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
        frameCounter++;


    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
    }

    @Override
    public void shoot(Laser laser) {
        destroy();
        new Vary(pane);
    }

    @Override
    public String getMessage() {
        return damage + " damage";
    }}
