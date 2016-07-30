package com.sjacobpowell.core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ArtTools {
	public static Bitmap getSubBitmap(Bitmap bitmap, int x, int y, int width, int height) {
		BufferedImage image = new BufferedImage(bitmap.width, bitmap.height, BufferedImage.TYPE_INT_RGB);
		final int[] a = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(bitmap.pixels, 0, a, 0, bitmap.pixels.length);
		x = x < 0 ? 0 : x;
		x = x + width > image.getWidth() ? image.getWidth() - width : x;
		y = y < 0 ? 0 : y;
		y = y + height > image.getHeight() ? image.getHeight() - height : y;
		return convertToBitmap(image.getSubimage(x, y, width, height));
	}

	public static Bitmap getBitmap(String filename) {
		return convertToBitmap(getImage(filename));
	}

	public static Bitmap getScaledBitmap(String filename, int width, int height) {
		return convertToBitmap(getScaledRGBImage(filename, width, height));
	}

	public static BufferedImage getImage(String filename) {
		try {
			return ImageIO.read(Screen.class.getResource(filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static BufferedImage getRGBImage(String filename) {
		try {
			return getRGBImage(ImageIO.read(Screen.class.getResource(filename)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static BufferedImage getScaledRGBImage(String filename, int width, int height) {
		return getScaledRGBImage(getRGBImage(filename), width, height);
	}

	public static BufferedImage getRGBImage(BufferedImage image) {
		BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		rgbImage.getGraphics().drawImage(image, 0, 0, null);
		return rgbImage;
	}

	public static BufferedImage getScaledRGBImage(BufferedImage rawImage, int width, int height) {
		BufferedImage rgbImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		rgbImage.getGraphics().drawImage(rawImage, 0, 0, width, height, null);
		return rgbImage;
	}

	public static Bitmap convertToBitmap(BufferedImage image) {
		image = getRGBImage(image);
		Bitmap bitmap = new Bitmap(image.getWidth(), image.getHeight());
		int[] pixels = ((DataBufferInt) getRGBImage(image).getRaster().getDataBuffer()).getData();
		for (int i = 0; i < bitmap.pixels.length; i++) {
			bitmap.pixels[i] = pixels[i];
		}
		return bitmap;
	}
	
	public static Bitmap circlefy(Bitmap bitmap, double radius) {
		for(int y = 0; y < bitmap.height; y++) {
			for(int x = 0; x < bitmap.width; x++) {
				double dx = x - bitmap.width / 2;
				double dy = y - bitmap.height / 2;
				double distanceSquared = dx * dx + dy * dy;
				if(distanceSquared > Math.pow(radius, 2)) {
					bitmap.pixels[x + y * bitmap.width] = 0;
				}
			}
		}
		return bitmap;
	}
}