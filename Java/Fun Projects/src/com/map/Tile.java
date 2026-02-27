package com.map;

import java.awt.Color;

public enum Tile {
	
	GRASS(Color.GREEN);
	
	private Color color;
	
	private Tile(Color color) {
		this.color=color;
	}
	public Color getColor() {
		return color;
	}
}
