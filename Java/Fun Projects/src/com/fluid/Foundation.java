package com.fluid;

import java.awt.Color;
import java.awt.Graphics;

public class Foundation extends Particle {
	private static Color color = Color.MAGENTA;
	private Handler handler;
	public Foundation(int xPos, int yPos, Handler handler) {
		super(xPos, yPos,ID.FOUNDATION,handler,0);
		this.handler=handler;
		yVel = length;
	}
	
	@Override
	public void tick() {	
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor( color);
		g.fillRect(xPos, yPos, length, length);
	}
}