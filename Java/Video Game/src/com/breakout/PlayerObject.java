package com.breakout;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerObject extends BreakObject {
	private Handler handler;
	private float velL, velR;
	public PlayerObject(int x, int y, float velX, float velY, int width, int height,Handler handler) {
		super(x,y,velX,velY,width,height,null,new PlayerResponse());
		this.handler=handler;
		this.xMax=BreakOut.WIDTH-width;
		this.yMax=BreakOut.HEIGHT-height-20;
	}
	
	
	
	@Override
	public void tick() {
		velX=velR-velL;
		x+=velX;
		
		
		if(x<0) {
			x=0;
			velL=0;
		}else if(x>getxMax()) {
			x=getxMax();
			velR=0;
		}
	}

	
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
	}
	
	
	public float getVelL() {
		return velL;
	}
	public void setVelL(float velL) {
		this.velL = velL;
	}
	public float getVelR() {
		return velR;
	}
	public void setVelR(float velR) {
		this.velR = velR;
	}
	
	

}
