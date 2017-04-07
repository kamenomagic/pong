package pong.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pong.entities.Paddle;

public class PongComponent extends Canvas {
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static final int SCALE = 1;
	private boolean running = false;
	private boolean render = true;
	private Thread thread;
	private Game game;
	private Screen screen;
	private InputManager inputManager;
	private BufferedImage image;
	private int[] pixels;
	private static final String OS = System.getProperty("os.name").toLowerCase();

	public PongComponent() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		if (OS.indexOf("win") >= 0) {
			size = new Dimension(WIDTH * SCALE - 10, HEIGHT * SCALE - 10);
		}
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(WIDTH, HEIGHT);

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		inputManager = new InputManager();
		addKeyListener(inputManager);
	}
	
	public PongComponent render(boolean render) {
		this.render = render;
		return this;
	}

	private void run() {
		if(render) {
			int frames = 0;
			int tickCount = 0;
			double FPS = 60;
			double unprocessedSeconds = 0;
			double secondsPerTick = 1 / FPS;
			double end = System.nanoTime();

			requestFocus();

			while (running) {
				double start = System.nanoTime();
				double passedTime = start - end;
				end = start;
				if (passedTime < 0)
					passedTime = 0;
				if (passedTime > 100000000)
					passedTime = 100000000;

				unprocessedSeconds += passedTime / 1000000000;

				boolean ticked = false;
				while (unprocessedSeconds > secondsPerTick) {
					tick();
					unprocessedSeconds -= secondsPerTick;
					ticked = true;

					tickCount++;
					if (tickCount % FPS == 0) {
						System.out.println(frames + " fps");
						end += 1000;
						frames = 0;
					}
				}

				if (ticked) {
					render();
					frames++;
				} else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			while(running && game.bounces < 100) {
				tick();
			}
		}
	}

	private void tick() {
		game.tick(inputManager.keys);
		if(game.over) {
			running = false;
		}
	}

	private void render() {
		BufferStrategy strategy = getBufferStrategy();
		if (strategy == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(game);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = strategy.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(game.player1.points), WIDTH * SCALE / 2 - 50, 10);
		g.drawString(String.valueOf(game.player2.points), WIDTH * SCALE / 2 + 50, 10);
		g.dispose();
		strategy.show();
	}
	
	public double run(double[] weights) {
		game = new Game(WIDTH, HEIGHT, weights).setUseWall(true).build();
//		game = new Game(WIDTH, HEIGHT, weights).setUseAI(true).build();
		JFrame frame = null;
		if(render) {
			frame = new JFrame("Pong");
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(this, BorderLayout.CENTER);
			frame.setContentPane(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
		} 
		running = true;
		run();
		if(frame != null) {
			frame.dispose();
		}
		return (double) game.bounces;
	}

	public void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PongComponent component = new PongComponent().render(true);
		double[] weights = new double[82];
		for(int i = 0; i < weights.length; i++) {
			weights[i] = new Random().nextDouble();
		}
		System.out.println(component.run(weights));
	}
}