package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

// a simple enemy TODO make better ones.
public class SimpleEnemy extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private static final int damage = 10;

    public SimpleEnemy(Pane pane) {
        super(pane);
        setImage(Globals.simpleEnemy);
        pane.getChildren().add(this);
        int speed = 1;
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
        double direction = rnd.nextDouble() * 360;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    /**Checks if enemy spawn coordinates collide, or within the safe radius of the snake's head*/
    private boolean isSafeToSpawn(double spawnPositionX, double SnakeHeadX, double spawnPositionY, double SnakeHeadY){
        int safeRadius = 50;
        return (spawnPositionX>SnakeHeadX-safeRadius && spawnPositionX<SnakeHeadX+safeRadius) && (spawnPositionY>SnakeHeadY-safeRadius && spawnPositionY<SnakeHeadY+safeRadius);
        }


    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());


    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
    }

    @Override
    public void shoot(Laser laser) {
        destroy();
        new SimpleEnemy(pane);
    }

    @Override
    public String getMessage() {
        return "10 damage";
    }
}
