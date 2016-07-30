package com.sjacobpowell.core;

public class Screen extends Bitmap {
	public Screen(int width, int height) {
		super(width, height);
	}

	public void render(Game game) {
		clear();
		draw(game.player1.sprite, game.player1.leftEdge(), game.player1.topEdge());
		draw(game.player2.sprite, game.player2.leftEdge(), game.player2.topEdge());
		draw(game.ball.sprite, game.ball.leftEdge(), game.ball.topEdge());
	}
}
