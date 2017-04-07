package pong.core;

import java.awt.event.KeyEvent;

import neuralnet.NeuralNet;
import pong.entities.Ball;
import pong.entities.Paddle;
import pong.entities.Wall;

public class Game {
	public int time;
	public int width;
	public int height;
	public int bounces;
	private NeuralNet net;
	public Paddle player1;
	public Paddle player2;
	public Ball ball;
	private static final double PADDLE_SPEED = 10;
	private static final double BALL_SPEED = 2.5;
	private static final double MAX_BALL_SPEED = 45;

	private double[] weights;
	private boolean useWall = false;
	private boolean useAI = false;
	public boolean over;

	public Game(int width, int height, double[] weights) {
		this.width = width;
		this.height = height;
		this.weights = weights;
		bounces = 0;
	}

	public Game setUseWall(boolean useWall) {
		this.useWall = useWall;
		return this;
	}

	public Game setUseAI(boolean useAI) {
		this.useAI = useAI;
		return this;
	}

	public Game build() {
		net = new NeuralNet(weights);
		player1 = new Paddle();
		if(useWall) {
			player2 = new Wall(height);
		} else {
			player2 = new Paddle();
		}
		ball = new Ball();
		ball.xSpeed = BALL_SPEED;
		reset();
		over = false;
		return this;
	}

	public void tick(boolean[] keys) {
		if (keys[KeyEvent.VK_ESCAPE])
			System.exit(0);
		boolean up1 = keys[KeyEvent.VK_W];
		boolean down1 = keys[KeyEvent.VK_S];
		boolean up2 = keys[KeyEvent.VK_UP];
		boolean down2 = keys[KeyEvent.VK_DOWN];

		double player1y = player1.y - (height/2);
		double ballx = ball.x - (width/2);
		double bally = ball.y - (height/2);
		boolean[] outputs = net.play(new double[]{player1y, ballx, bally, ball.xSpeed, ball.ySpeed});
		up1 = outputs[0];
		down1 = outputs[1];
		if (up1 || down1) {
			player1.ySpeed = up1 ? -PADDLE_SPEED : PADDLE_SPEED;
		} else {
			player1.ySpeed = 0;
		}

		if (useAI) {
			if (ball.y < player2.y) {
				player2.ySpeed = -PADDLE_SPEED;
			} else if (ball.y > player2.y) {
				player2.ySpeed = PADDLE_SPEED;
			} else {
				player2.ySpeed = 0;
			}
		} else if(!useWall) {
			if (up2 || down2) {
				player2.ySpeed = up2 ? -PADDLE_SPEED : PADDLE_SPEED;
			} else {
				player2.ySpeed = 0;
			}
		}

		player1.y = player1.topEdge() < 0 ? player1.sprite.height / 2 : player1.y;
		player2.y = player2.topEdge() < 0 ? player2.sprite.height / 2 : player2.y;
		player1.y = player1.bottomEdge() >= height ? height - player1.sprite.height / 2 : player1.y;
		player2.y = player2.bottomEdge() >= height ? height - player2.sprite.height / 2 : player2.y;
		if (ball.topEdge() < 0 || ball.bottomEdge() > height) {
			ball.ySpeed = -ball.ySpeed;
		}
		boolean player1hit = ball.isColliding(player1);
		boolean player2hit = ball.isColliding(player2);
		if(player2hit) {
			bounces++;
		}
		if (player1hit || player2hit) {
			ball.xSpeed = -ball.xSpeed * 1.1;
			ball.xSpeed = Math.min(ball.xSpeed, MAX_BALL_SPEED);
			ball.xSpeed = Math.max(ball.xSpeed, -MAX_BALL_SPEED);

			if(player1hit) {
				ball.ySpeed *= ball.y < player1.y ? .9 : 1.1;
			} else {
				ball.ySpeed *= ball.y < player2.y ? .9 : 1.1;
			}
		}
		if (ball.rightEdge() > player2.rightEdge() && !player2hit) {
			player1.points++;
			reset();
		}
		if (ball.leftEdge() < player1.leftEdge() && !player1hit) {
			player2.points++;
			reset();
		}

		player1.move();
		player2.move();
		ball.move();

		time++;
	}

	private void reset() {
		over = true;
//		player1.y = player2.y = height / 2;
		player1.y = player1.sprite.height + 25;
		player1.x = player1.sprite.width / 2;
		player2.x = width - player2.sprite.width / 2;
//		ball.xSpeed = ball.xSpeed > 0 ? -BALL_SPEED : BALL_SPEED;
		ball.xSpeed = BALL_SPEED;
		ball.ySpeed = BALL_SPEED;
		ball.x = width / 2;
		ball.y = height / 2;
	}
}