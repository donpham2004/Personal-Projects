package com.fluid;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Particle {
	public static final int LENGTH = 8;
	protected int xPos, yPos;
	protected Color color;
	protected float xVel;
	protected float yVel = LENGTH;
	protected ID id;
	protected int length;
	protected Handler handler;
	protected int density;
	
	public Particle(int xPos, int yPos, ID id, Handler handler,int density) {
		this.handler = handler;
		this.length = LENGTH;
		this.xPos = xPos;
		this.yPos = yPos;
		this.id = id;
		this.density=density;
	}

	public abstract void tick();

	public abstract void render(Graphics g);
	
	public ID getId() {
		return id;
	}
	
	public boolean clamp(int xPos, int yPos) {
		if(xPos>=0&&xPos<=Fluid.getWindowWidth()-length) {
			if(yPos<=Fluid.getWindowHeight()-length&&yPos>=0) {
				int oldX = this.xPos;
				int oldY = this.yPos;
				this.yPos = yPos;
				this.xPos = xPos;
				handler.updateMap(this,oldX,oldY);
				return true;
			}
		}
		return false;
	}
	
	public int getLength() {
		return length;
	}
	public void swap(Particle p1, Particle p2) {
		int temp = p1.xPos;
		p1.xPos  = p2.xPos;
		p2.xPos=temp;
		
		temp = p1.yPos;
		p1.yPos  = p2.yPos;
		p2.yPos=temp;
	}
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
}
