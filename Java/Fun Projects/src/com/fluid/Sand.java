package com.fluid;

import java.awt.Color;
import java.awt.Graphics;

public class Sand extends Particle {
	
	private Handler handler;
	public Sand(int xPos, int yPos, Handler handler) {
		super(xPos, yPos,ID.SAND,handler,6);
		this.handler=handler;
		yVel = length;
	}
	
	@Override
	public void tick() {	
		
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null) {
			clamp(xPos,(int)(yPos+yVel));
		}else if(handler.getParticle(xPos+length, (int)(yPos+yVel))==null) {
			clamp(xPos+length,(int)(yPos+yVel));
		}else if(handler.getParticle(xPos-length, (int)(yPos+yVel))==null) {
			clamp(xPos-length,(int)(yPos+yVel));
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(xPos, yPos, length, length);
	}
}
