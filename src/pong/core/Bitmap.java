package pong.core;

public class Bitmap {
	public int width;
	public int height;
	public int[] pixels;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Bitmap bitmap, double xOffset, double yOffset) {
		draw(bitmap, (int)xOffset, (int)yOffset);
	}
	
	public void draw(Bitmap bitmap, int xOffset, int yOffset) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPixel = y + yOffset;
			if (yPixel < 0 || yPixel >= height)
				continue;
			for (int x = 0; x < bitmap.width; x++) {
				int xPixel = x + xOffset;
				if (xPixel < 0 || xPixel >= width)
					continue;
				int source = bitmap.pixels[y * bitmap.width + x];
				if (source > 0) {
					pixels[yPixel * width + xPixel] = source;
				}
			}
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}

}
