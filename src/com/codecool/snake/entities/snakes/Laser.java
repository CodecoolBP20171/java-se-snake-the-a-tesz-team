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
    private double direction;

    public Laser(Pane pane, double xc, double yc, double direction) {
        super(pane);
        setX(xc);
        setY(yc);
        this.direction = direction;
        setImage(Globals.redLaser);
        int speed = 15;
        heading = Utils.directionToVector(this.direction, speed);
        setRotate(direction);
        pane.getChildren().add(this);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());

        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Interactable) {
                    Interactable interactable = (Interactable) entity;
                    interactable.shoot(this);
                    destroy();
                }
            }
        }
    }


}
