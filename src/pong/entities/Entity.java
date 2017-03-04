package pong.entities;

import pong.core.Bitmap;

public class Entity {
	public double x = 0;
	public double y = 0;
	public double xSpeed = 0;
	public double ySpeed = 0;
	public Bitmap sprite = new Bitmap(0, 0);
	public String ID;
	public boolean alive = true;

	public int getXInt() {
		return (int) x;
	}

	public int getYInt() {
		return (int) y;
	}

	public int getXSpeedInt() {
		return (int) xSpeed;
	}

	public int getYSpeedInt() {
		return (int) ySpeed;
	}

	public double topEdge() {
		return y - sprite.height / 2;
	}

	public double leftEdge() {
		return x - sprite.width / 2;
	}

	public double rightEdge() {
		return x + sprite.width / 2;
	}

	public double bottomEdge() {
		return y + sprite.height / 2;
	}

	public void move() {
		x += xSpeed;
		y += ySpeed;
	}

	public boolean isColliding(Entity other) {
		return collide(other) || other.collide(this);
	}

	private boolean collide(Entity other) {
		 return normalCollision(other) || sweptCollision(other);
	}

	private boolean normalCollision(Entity other) {
		return verticalNormalCollision(other) && horizontalNormalCollision(other);
	}

	private boolean verticalNormalCollision(Entity other) {
		return topEdge() <= other.bottomEdge() && bottomEdge() >= other.topEdge();
	}

	private boolean horizontalNormalCollision(Entity other) {
		return leftEdge() <= other.rightEdge() && rightEdge() >= other.leftEdge();
	}

	private boolean sweptCollision(Entity other) {
		return verticalSweptCollision(other) && horizontalSweptCollision(other);
	}

	private boolean verticalSweptCollision(Entity other) {
		if (ySpeed == 0) {
			return verticalNormalCollision(other);
		} else {
			return (topEdge() <= other.bottomEdge() && topEdge() - ySpeed >= other.bottomEdge())
				|| (bottomEdge() >= other.topEdge() && bottomEdge() - ySpeed <= other.topEdge());
		}
	}

	private boolean horizontalSweptCollision(Entity other) {
		if (xSpeed == 0) {
			return horizontalNormalCollision(other);
		} else {
			return (leftEdge() <= other.rightEdge() && leftEdge() - xSpeed >= other.rightEdge())
					|| (rightEdge() >= other.leftEdge() && rightEdge() - xSpeed <= other.leftEdge());
		}
	}

}
