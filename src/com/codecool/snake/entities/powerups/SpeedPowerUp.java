package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SpeedPowerUp extends GameEntity implements Interactable {

    public SpeedPowerUp(Pane pane) {
        super(pane);
        setImage(Globals.speed);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.slowMotion();
        destroy();
    }

    @Override
    public String getMessage() {
        return "Im fast as fck now!!444!!44!";
    }
}
