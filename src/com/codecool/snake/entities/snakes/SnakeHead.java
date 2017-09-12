package com.codecool.snake.entities.snakes;

import com.codecool.snake.Game;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.powerups.SlowMotionPowerUp;
import com.codecool.snake.entities.powerups.SpeedingPowerUp;
import com.codecool.snake.entities.powerups.TurnRatePowerUp;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SnakeHead extends GameEntity implements Animatable {

    private static float speed = 2;
    private static float turnRate = 2;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int health;
    private static double coordX;
    private static double coordY;
    private static Point2D SnakeHeadHeading;

    public static double getCoordX() {
        return coordX;
    }
    public static double getCoordY() {
        return coordY;
    }
    public static Point2D getHeading(){
        return SnakeHeadHeading;
    }



    public SnakeHead(Pane pane, int xc, int yc) {
        super(pane);
        health = 100;
        if (health == 100) {
            Globals.healthCounter.setText("Health: " + health);
        }
        setX(xc);
        setY(yc);
        tail = this;
        setImage(Globals.snakeHead);
        pane.getChildren().add(this);
        addPart(4);

        coordX=getX();
        coordY=getY();

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
            new Laser(pane, getX(), getY(), dir);
            MediaPlayer laserEffect = new MediaPlayer(Globals.laserSound);
            laserEffect.play();
            Globals.spaceDown = false;
        }
        // set rotation and position
        setRotate(dir);

        Point2D heading = Utils.directionToVector(dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
        coordX=getX();
        coordY=getY();
        SnakeHeadHeading=heading;



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
        if(Game.getAbleToSpawn() && Game.numOfPowerUps <= 5){
            switch(Game.randomizePowerUp()) {
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
            }

            Game.setAbleToSpawn(false);
            Game.numOfPowerUps++;
        }

        // check for game over condition
        if (isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            Globals.gameLoop.stop();
        }
    }

    public void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
        }
    }

    public void changeSpeed(float speedChange) {
        if (speed > 0.5 && speed < 5) {
            speed += speedChange;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            speed -= speedChange;
                        }
                    },
                    7000
            );
        }

    }

    public void changeTurnRate(float turnRateChange) {
        if (turnRate < 5   ) {
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

    public void changeHealth(int diff) {
        health += diff;
        Globals.healthCounter.setText("Health: " + health);
        if (health <= 80 && health > 40 ) {
            setImage(Globals.unhealthyHead);
        } else if (health <= 40) {
            setImage(Globals.almostDestroyedHead);
        }
    }
}
