package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class HealthPowerUp extends GameEntity implements Interactable {

    public HealthPowerUp(Pane pane) {
        super(pane);
        setImage(Globals.health);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public void shoot(Laser laser) {
        System.out.println("fire");
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        if(snakeHead.getHealth() <= 90) {
            snakeHead.changeHealth(10);
        }
        destroy();
    }

    @Override
    public String getMessage() {
        return "Got power-up :)";
    }
}
