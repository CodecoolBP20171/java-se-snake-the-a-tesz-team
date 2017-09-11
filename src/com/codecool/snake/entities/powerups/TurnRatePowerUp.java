package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class TurnRatePowerUp extends GameEntity implements Interactable {

    public TurnRatePowerUp(Pane pane) {
        super(pane);
        setImage(Globals.turn);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.changeTurnRate((float) 0.5);
        destroy();
    }

    @Override
    public String getMessage() {
        return "I can turn fast as fck now yaaay!4!!";
    }

}
