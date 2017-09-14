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

import static java.lang.Math.toDegrees;

public class SnakeTracker extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private static final int damage = 10;
    private static final double speed = 1.2;

    public SnakeTracker(Pane pane) {
        super(pane);
        setImage(Globals.snakeTracker);
        pane.getChildren().add(this);

        Random rnd = new Random();
        double SnakeHeadX = SnakeHead.getCoordX();
        double SnakeHeadY = SnakeHead.getCoordY();
        double spawnPositionX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
        double spawnPositionY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
        while (isSafeToSpawn(spawnPositionX, SnakeHeadX, spawnPositionY, SnakeHeadY)) {
            spawnPositionX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
            spawnPositionY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
        }
        setX(spawnPositionX);
        setY(spawnPositionY);
        double direction = rnd.nextDouble() * 360;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    /**
     * Checks if enemy spawn coordinates collide, or within the safe radius of the snake's head
     */
    private boolean isSafeToSpawn(double spawnPositionX, double SnakeHeadX, double spawnPositionY, double SnakeHeadY) {
        int safeRadius = 200;
        return (spawnPositionX > SnakeHeadX - safeRadius && spawnPositionX < SnakeHeadX + safeRadius) && (spawnPositionY > SnakeHeadY - safeRadius && spawnPositionY < SnakeHeadY + safeRadius);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
            new SnakeTracker(pane);
        }
        double direction = angleToTurnTo();
        setRotate(direction);
        ;

        heading = Utils.directionToVector(direction, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    private double angleToTurnTo() {
        double currX = SnakeHead.getCoordX();
        double currY = SnakeHead.getCoordY();
        double trackerX = getX();
        double trackerY = getY();

        double deltaX = (currX - trackerX);
        double deltaY = (currY - trackerY);
        double tangentA = Math.atan2(deltaY, deltaX);
        double tangentInDegrees = toDegrees(tangentA);
        double result = (tangentInDegrees) + 90;

        return result;
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
        Random random = new Random();
        int chance = random.nextInt((1 - 0) + 1) + 0;
        if (chance == 1) {
            new SnakeTracker(pane);
        }
    }

    @Override
    public void shoot(Laser laser) {
        destroy();
        new SnakeTracker(pane);
    }

    @Override
    public String getMessage() {
        return damage + " damage";
    }
}
