package com.fluid;

import java.awt.Color;

public enum ID {
	SAND(Color.YELLOW),WATER(Color.BLUE),
	STONE(Color.GRAY),SALT(Color.WHITE),
	OIL(new Color(102, 102, 51)),
	FOUNDATION(Color.MAGENTA),TNT(Color.RED),
	FIRE(Color.ORANGE),WOOD(new Color(165,42,42)),
	ACID(Color.GREEN),FISH(new Color(255, 204, 0));
	private Color color;
	
	private ID(Color color) {
		this.color=color;
	}
	
	public Color getColor() {
		return color;
	}
}
