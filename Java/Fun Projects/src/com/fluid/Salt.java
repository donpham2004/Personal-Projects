package com.fluid;

import java.awt.Color;
import java.awt.Graphics;

public class Salt extends Particle {
	
	private Handler handler;
	public Salt(int xPos, int yPos, Handler handler) {
		super(xPos, yPos,ID.SALT,handler,6);
		this.handler=handler;
		yVel = length;
	}
	
	@Override
	public void tick() {	
		if(dryWater()) return;
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null) {
			clamp(xPos,(int)(yPos+yVel));
		}else if(handler.getParticle(xPos+length, (int)(yPos+yVel))==null) {
			clamp(xPos+length,(int)(yPos+yVel));
		}else if(handler.getParticle(xPos-length, (int)(yPos+yVel))==null) {
			clamp(xPos-length,(int)(yPos+yVel));
		}
	}
	
	public boolean dryWater() {
		int arr[][] = {{-1,-1},{0,-1},{1,-1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
		for(int i=0;i<arr.length;i++) {
			Particle particle;
			if((particle = handler.getParticle(xPos+length*arr[i][0], yPos+length*arr[i][1]))!=null) {
				
				if(particle.id==ID.WATER) {
					handler.removeParticle(particle);
					handler.removeParticle(this);
					return true;
				}
				
				
			}
		}
		return false;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xPos, yPos, length, length);
	}
}