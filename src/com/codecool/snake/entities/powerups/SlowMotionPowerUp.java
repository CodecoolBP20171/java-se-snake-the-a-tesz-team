package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SlowMotionPowerUp extends GameEntity implements Interactable {

    public SlowMotionPowerUp(Pane pane) {
        super(pane);
        setImage(Globals.slow);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.changeSpeed((float) -0.5, 7000);

        destroy();
    }

    @Override
    public void shoot(Laser laser) {
        System.out.println("shoot");
    }

    @Override
    public String getMessage() {
        return "Im NEO BITCHES";
    }
}
