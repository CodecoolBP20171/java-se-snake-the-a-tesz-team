package com.codecool.snake.entities.powerups;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

// a simple powerup that makes the snake grow TODO make other powerups
public class SimplePowerup extends GameEntity implements Interactable {

    public SimplePowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupEgg);
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
        snakeHead.addPart(4);
        if (snakeHead.getShootCounter() < 0) {
            snakeHead.setShootCounter(0);
        }
        int newShootCounter = snakeHead.getShootCounter() + 5;
        snakeHead.setShootCounter(newShootCounter);
        
        Globals.ammoCounter.setText("Ammo: " + newShootCounter);
        destroy();
        new SimplePowerup(pane);
    }

    @Override
    public String getMessage() {
        return "Got power-up :)";
    }
}
