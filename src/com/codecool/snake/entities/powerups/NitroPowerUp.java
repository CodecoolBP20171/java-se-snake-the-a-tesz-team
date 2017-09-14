package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class NitroPowerUp extends GameEntity implements Interactable {

    public NitroPowerUp(Pane pane) {
        super(pane);
        setImage(Globals.nitro);
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
        Globals.numOfNitros++;
        destroy();
    }

    @Override
    public String getMessage() {
        return "Got power-up :)";
    }
}
