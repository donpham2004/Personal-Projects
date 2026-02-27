package com.fluid;

import java.awt.Color;
import java.awt.Graphics;

public class Stone extends Particle {
	private Handler handler;
	public Stone(int xPos, int yPos, Handler handler) {
		super(xPos, yPos,ID.STONE,handler,8);
		this.handler=handler;
		yVel = length;
	}
	
	@Override
	public void tick() {	
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null) {
			clamp(xPos,(int)(yPos+yVel));
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(id.getColor());
		g.fillRect(xPos, yPos, length, length);
	}
}
