package pong.entities;

import pong.core.Bitmap;

public class Wall extends Paddle {
	public int score;
	public Wall(int height) {
		sprite = new Bitmap(16, height);
		for(int i = 0; i < sprite.pixels.length; i++) {
			sprite.pixels[i] = 0xffffff;
		}
		y += sprite.height / 2;
		score = 0;
	}
}
