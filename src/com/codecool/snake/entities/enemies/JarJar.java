package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Laser;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

public class JarJar extends GameEntity implements Animatable, Interactable {

	private static final int damage = 20;
	private Point2D heading;
	private int speed = 2;
	private double direction;

	public JarJar(Pane pane) {
		super(pane);
		setImage(Globals.jarJar);
		pane.getChildren().add(this);
		Random rnd = new Random();
		double SnakeHeadX = SnakeHead.getCoordX();
		double SnakeHeadY = SnakeHead.getCoordY();
		double spawnPositionX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
		double spawnPositionY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
		while (isSafeToSpawn(spawnPositionX, SnakeHeadX, spawnPositionY, SnakeHeadY)) {
			spawnPositionX = rnd.nextDouble() * Globals.WINDOW_WIDTH;
			spawnPositionY = rnd.nextDouble() * Globals.WINDOW_HEIGHT;
			System.out.println("Enemy spawn prohibited on SnakeHead");
		}
		setX(spawnPositionX);
		setY(spawnPositionY);
		direction = rnd.nextDouble() * 360;
		setRotate(direction);
		heading = Utils.directionToVector(direction, speed);
	}

	/**
	 * Checks if enemy spawn coordinates collide, or within the safe radius of the snake's head
	 */
	private boolean isSafeToSpawn(double spawnPositionX, double SnakeHeadX, double spawnPositionY, double SnakeHeadY) {
		int safeRadius = 100;
		return (spawnPositionX > SnakeHeadX - safeRadius && spawnPositionX < SnakeHeadX + safeRadius) && (spawnPositionY > SnakeHeadY - safeRadius && spawnPositionY < SnakeHeadY + safeRadius);
	}

	@Override
	public void step() {
		if (isOutOfBounds()) {
			direction = +180;
		}
		setX(getX() + heading.getX());
		setY(getY() + heading.getY());
	}

	@Override
	public void apply(SnakeHead player) {
		player.changeHealth(-damage);
		destroy();
	}

	@Override
	public void shoot(Laser laser) {
		destroy();
		new JarJar(pane);
		new JarJar(pane);
	}

	@Override
	public String getMessage() {
		return damage + " damage";
	}
}
