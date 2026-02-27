package com.map;

import java.awt.Color;
import java.awt.Graphics;

public class World {
	
	public void render(Graphics g) {
		Color color = Tile.GRASS.getColor();
		g.drawRect(0, 0, 0, 0);
	}
}
