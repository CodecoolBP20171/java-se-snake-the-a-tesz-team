package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Laser extends GameEntity implements Animatable {

    private Point2D heading;
    private int speed = 15;
    private double direction;
    private static float turnRate = 2;

    public Laser(Pane pane, double xc, double yc, double direction) {
        super(pane);

        setX(xc);
        setY(yc);
        setImage(Globals.redLaser);
        heading = Utils.directionToVector(this.direction, this.speed);
        System.out.println("Direction: " + direction);
        setRotate(direction);
        pane.getChildren().add(this);

    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }

        double dir = getRotate();
        if (Globals.leftKeyDown) {
            dir = dir - turnRate;
        }
        if (Globals.rightKeyDown) {
            dir = dir + turnRate;
        }
        setRotate(dir);
        Point2D heading = Utils.directionToVector(dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());

    }


}
