package pong.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
	public boolean[] keys = new boolean[KeyEvent.CHAR_UNDEFINED];

	@Override
	public void keyPressed(KeyEvent e) {
		setKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setKey(e.getKeyCode(), false);
	}

	private void setKey(int code, boolean pressed) {
		if (code > 0 && code < keys.length) {
			keys[code] = pressed;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
