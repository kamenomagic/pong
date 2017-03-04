package pong.entities;

import pong.core.Bitmap;

public class Ball extends Entity {

	public Ball() {
		sprite = new Bitmap(16, 16);
		for(int i = 0; i < sprite.pixels.length; i++) {
			sprite.pixels[i] = 0xffffff;
		}
	}
}
