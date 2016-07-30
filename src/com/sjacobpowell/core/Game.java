package com.sjacobpowell.core;

import java.awt.event.KeyEvent;

import com.sjacobpowell.entities.Ball;
import com.sjacobpowell.entities.Paddle;

public class Game {
	public int time;
	public int width;
	public int height;
	public Paddle player1;
	public Paddle player2;
	public Ball ball;
	private static final double PADDLE_SPEED = 10;
	private static final double BALL_SPEED = 2.5;

	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		player1 = new Paddle();
		player2 = new Paddle();
		ball = new Ball();
		reset();
	}

	public void tick(boolean[] keys) {
		if (keys[KeyEvent.VK_ESCAPE])
			System.exit(0);
		boolean up1 = keys[KeyEvent.VK_W];
		boolean down1 = keys[KeyEvent.VK_S];
		boolean up2 = keys[KeyEvent.VK_UP];
		boolean down2 = keys[KeyEvent.VK_DOWN];
		
		if(up1 || down1) {
			player1.ySpeed = up1 ? -PADDLE_SPEED : PADDLE_SPEED;
		} else {
			player1.ySpeed = 0;
		}

		if(up2 || down2) {
			player2.ySpeed = up2 ? -PADDLE_SPEED : PADDLE_SPEED;
		} else {
			player2.ySpeed = 0;
		}
		
		player1.y = player1.topEdge() < 0 ? player1.sprite.height / 2 : player1.y;
		player2.y = player2.topEdge() < 0 ? player2.sprite.height / 2 : player2.y;
		player1.y = player1.bottomEdge() >= height ? height - player1.sprite.height / 2 : player1.y;
		player2.y = player2.bottomEdge() >= height ? height - player2.sprite.height / 2 : player2.y;
		if(ball.topEdge() < 0 || ball.bottomEdge() > height) ball.ySpeed = -ball.ySpeed;
		if(ball.isColliding(player1) || ball.isColliding(player2)) ball.xSpeed = -ball.xSpeed;
		if(ball.rightEdge() > player2.rightEdge() || ball.leftEdge() < player1.leftEdge()) reset();
		
		player1.move();
		player2.move();
		ball.move();

		time++;
	}

	private void reset() {
		player1.y = player2.y = height / 2;
		player1.x = player1.sprite.width / 2;
		player2.x = width - player2.sprite.width / 2;
		ball.xSpeed = -BALL_SPEED;
		ball.ySpeed = BALL_SPEED;
		ball.x = width / 2;
		ball.y = height / 2;
	}
}
