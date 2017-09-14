package com.codecool.snake.entities.snakes;

import com.codecool.snake.Game;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.powerups.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

public class SnakeHead extends GameEntity implements Animatable {

    private static float speed = 2;
    private static float turnRate = 2;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int health;
    private static double coordX;
    private static double coordY;
    public static int numOfNitros = 2;

    private static Point2D SnakeHeadHeading;
    static int shootCounter = 5;

    public static double getCoordX() {
        return coordX;
    }

    public static double getCoordY() {
        return coordY;
    }

    public void setCoordX(double coordX) {
        SnakeHead.coordX = coordX;
    }

    public int getShootCounter() {
        return shootCounter;
    }

    public void setShootCounter(int newValue) {
        this.shootCounter = newValue;
    }

    public static Point2D getHeading() {
        return SnakeHeadHeading;
    }


    public SnakeHead(Pane pane, int xc, int yc) {
        super(pane);
        if (shootCounter == 5) {
            Globals.ammoCounter.setText("Ammo: " + shootCounter);
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
        if (Game.getAbleToSpawn() && Game.numOfPowerUps <= 5) {
            switch (Game.chooseRandomPowerUp()) {
                case 1:
                    new SimplePowerup(Game.thisGame);
                    break;
                case 2:
                    new SlowMotionPowerUp(Game.thisGame);
                    break;
                case 3:
                    new SpeedingPowerUp(Game.thisGame);
                    break;
                case 4:
                    new TurnRatePowerUp(Game.thisGame);
                    break;
                case 5:
                    new HealthPowerUp(Game.thisGame);
                    break;
                case 6:
                    new nitroPowerUp(Game.thisGame);
            }

            Game.setAbleToSpawn(false);
            Game.randomizePowerSpawn(7000, 2000);
            Game.numOfPowerUps++;
        }

        if(numOfNitros >= 1 && Globals.nitro) {
            this.changeSpeed(2, 2000);
            numOfNitros--;
            Globals.nitro = false;
        }

        // check for game over condition
        if (isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            for (GameEntity gameEntity : Globals.gameObjects) {
                gameEntity.destroy();
            }
            Globals.gameOver = true;
            Game.endTimers();

        }
    }

    public void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
        }
    }

    public void changeSpeed(float speedChange, int time) {
        if (speed > 0.5 && speed < 5) {
            speed += speedChange;
            new Timer().schedule(
                    new TimerTask() {
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
        shootCounter--;
        Globals.ammoCounter.setText("Ammo: " + shootCounter);
        if (shootCounter < 0) {
            shoot.destroy();
            Globals.ammoCounter.setText("Ammo: 0");
        }
        Globals.spaceDown = false;
    }



    public int getHealth() {
        return health;
    }

    public static void setNumOfNitros(int numOfNitros) {
        SnakeHead.numOfNitros = numOfNitros;
    }

    public void changeHealth(int diff) {
        health += diff;
        Globals.healthCounter.setText("Health: " + health);
        if (health <= 80 && health > 40) {
            setImage(Globals.unhealthyHead);
        } else if (health <= 40) {
            setImage(Globals.almostDestroyedHead);
        }
    }
}
