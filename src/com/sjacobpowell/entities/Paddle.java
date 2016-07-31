package com.sjacobpowell.entities;

import com.sjacobpowell.core.Bitmap;

public class Paddle extends Entity {
	public int points;

	public Paddle() {
		sprite = new Bitmap(16, 64);
		for(int i = 0; i < sprite.pixels.length; i++) {
			sprite.pixels[i] = 0xffffff;
		}
		y += sprite.height / 2;
		points = 0;
	}

}
