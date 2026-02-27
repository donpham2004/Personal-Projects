package com.fluid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Fish extends Particle {
	private Handler handler;
	private boolean safe=false;
	private int warning=0;
	public Fish(int xPos, int yPos, Handler handler) {
		super(xPos, yPos,ID.FISH,handler,8);
		this.handler=handler;
		yVel = length;
	}
	
	@Override
	public void tick() {
		if(warning>120) {
			int oldX = xPos;
			int oldY = yPos;
			handler.removeParticle(this);
			handler.addParticle(new Oil(oldX,oldY,handler));
			return;
		}
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null&&	clamp(xPos,(int)(yPos+yVel))) {
		}else {
			swim();
		}
			
		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(id.getColor());
		g.fillRect(xPos, yPos, length, length);
		g.setColor(Color.WHITE);
		g.fillRect(xPos+1, yPos, 1, LENGTH);
		g.setColor(Color.BLACK);
		g.fillRect(xPos+6, yPos, 1, LENGTH);
	}
	
	public void swim() {
		Random rand = new Random();
		int radius = 1;
		int newX=xPos;
		int newY=yPos;
		safe=false;
		for (int i = -radius; i <= radius; i++) {
			for(int j=-radius;j<=radius;j++) {
				Particle temp;
				if((temp=handler.getParticle(xPos+length*i, yPos+length*j))!=null) {
					if(temp.getId()==ID.WATER&&!temp.equals(this)) {
						safe=true;
						warning=0;
						if(rand.nextInt(30)==0)  {
							newX=temp.getXPos();
							newY=temp.getYPos();
						}
					}
				}
			}
		}
		if(!safe) {
			warning++;
		}else {
			Particle part;
			if((part=handler.getParticle(newX,newY)).getId()==ID.WATER) {
				handler.removeParticle(part);
				int oldX = xPos;
				int oldY = yPos;
				clamp(newX, newY);
				handler.addParticle(new Water(oldX,oldY,handler));
			}
		}
		
	}
}
