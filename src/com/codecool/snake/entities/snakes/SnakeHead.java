package com.codecool.snake.entities.snakes;

import com.codecool.snake.Game;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.enemies.SnakeTracker;
import com.codecool.snake.entities.enemies.Vary;
import com.codecool.snake.entities.powerups.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SnakeHead extends GameEntity implements Animatable {

    private static float speed = 2;
    private static float turnRate = 2;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int health;
    private static double coordX;
    private static double coordY;
    private static int score=0;

    private static Point2D SnakeHeadHeading;

    public static double getCoordX() {
        return coordX;
    }

    public static double getCoordY() {
        return coordY;
    }

    public int getShootCounter() {
        return Globals.shootCounter;
    }

    public void setShootCounter(int newValue) {
        Globals.shootCounter = newValue;
    }

    public static Point2D getHeading() {
        return SnakeHeadHeading;
    }


    public SnakeHead(Pane pane, int xc, int yc) {
        super(pane);
        if (Globals.shootCounter == 5) {
            Globals.ammoCounter.setText("Ammo: " + Globals.shootCounter);
        }
        health = 100;
        Globals.healthCounter.setText("Health: " + health);
        setX(xc);
        setY(yc);
        tail = this;
        setImage(Globals.snakeHead);
        pane.getChildren().add(this);
        addPart(4);

        coordX = getX();
        coordY = getY();

    }

    public void step() {

        double dir = getRotate();
        if (Globals.leftKeyDown) {
            dir = dir - turnRate;
        }
        if (Globals.rightKeyDown) {
            dir = dir + turnRate;
        }
        if (Globals.spaceDown) {
            changeAmmo(dir);
        }
        // set rotation and position
        setRotate(dir);

        Point2D heading = Utils.directionToVector(dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());

        coordX=getX();
        coordY=getY();

        // check if collided with an enemy or a powerup
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Interactable) {
                    Interactable interactable = (Interactable) entity;
                    interactable.apply(this);
                    System.out.println(interactable.getMessage());
                }
            }
        }
        if (Game.getAbleToSpawnPowerup() && Game.numOfPowerUps <= 5) {
            switch (Game.randomizePowerUp()) {
                case 1:
                    new SlowMotionPowerUp(Game.thisGame);
                    break;
                case 2:
                    new SpeedingPowerUp(Game.thisGame);
                    break;
                case 3:
                    new TurnRatePowerUp(Game.thisGame);
                    break;
                case 4:
                    new NitroPowerUp(Game.thisGame);
                case 5:
                    new HealthPowerUp(Game.thisGame);
                    break;
            }

            Game.setAbleToSpawnPowerup(false);
            Game.randomizePowerSpawn();
            Game.numOfPowerUps++;
        }

        if(Globals.isNitro) {
            changeSpeed(2, 3000);
            Globals.numOfNitros--;
            Globals.isNitro = false;
        }

        if (Game.isAbleToSpawnEnemy()){
            new SimpleEnemy(pane);
            new Vary(pane);
            new SnakeTracker(pane);
            Game.setAbleToSpawnEnemy(false);
        }

        // check for game over condition
        if (isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            for (GameEntity gameEntity : Globals.gameObjects) {
                gameEntity.destroy();
            }
            Globals.gameOver = true;
        }
    }

    public void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
        }
        score++;
    }

    public void changeSpeed(float speedChange, int time) {
        if (speed > 0.5 && speed < 5) {
            speed += speedChange;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            speed -= speedChange;
                        }
                    },
                    time
            );
        }


    }

    public void changeTurnRate(float turnRateChange) {
        if (turnRate < 5) {
            turnRate += turnRateChange;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            turnRate -= turnRateChange;
                        }
                    },
                    7000
            );
        }
    }

    private void changeAmmo(double dir) {
        Laser shoot = new Laser(pane, getX(), getY(), dir);
        Globals.shootCounter--;
        Globals.ammoCounter.setText("Ammo: " + Globals.shootCounter);
        if (Globals.shootCounter < 0) {
            shoot.destroy();
            Globals.ammoCounter.setText("Ammo: 0");
        }
        Globals.spaceDown = false;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int diff) {
        health += diff;
        Globals.healthCounter.setText("Health: " + health);
    }

    public static int getScore() {
        return score;
    }
    public static void setScore(int score) {
        SnakeHead.score = score;
    }
}
