package com.chess;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	public BufferedImage loadImage(String path) {
		try {
		
			return ImageIO.read(getClass().getResourceAsStream(path));
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
