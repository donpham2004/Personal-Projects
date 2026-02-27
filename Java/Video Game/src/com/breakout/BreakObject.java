package com.breakout;

import java.awt.Graphics;
import java.awt.Rectangle;
public abstract class BreakObject {
	public static final int SPEED=8;
	protected int x, y, width, height;
	protected float  velX, velY;
	protected CollisionInterface collide;
	protected ResponseInterface response;
	protected int xMax,yMax;
	public BreakObject(int x, int y, float velX, float velY, int width, int height, CollisionInterface collide, ResponseInterface response) {
		this.x=x;
		this.y=y;
		this.velX=velX;
		this.velY=velY;
		this.width=width;
		this.height=height;
		this.collide=collide;
		this.response=response;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getxMax() {
		return xMax;
	}

	public void setxMax(int xMax) {
		this.xMax = xMax;
	}

	public int getyMax() {
		return yMax;
	}

	public void setyMax(int yMax) {
		this.yMax = yMax;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}
	
	public boolean isAtBorder(int x, int max) {
		return x<0?true:x>max?true:false;
	}
}
