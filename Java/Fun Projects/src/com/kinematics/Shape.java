package com.kinematics;

import java.awt.Graphics;

public abstract class Shape {
	protected int width, height;
	protected float xPos,yPos,xVel,yVel,xAcc,yAcc,friction,mass,radian,radVel,radAcc;
	protected Vector posVector,velVector,accVector;
	protected ID id;
	public Shape(int width, int height, float radian, float xPos,float yPos, float xVel,float yVel,float radVel,ID id) {
		this.width=width;
		this.height=height;
		this.radian=radian;
		this.radVel=radVel;
		this.id=id;
		this.accVector=new Vector(0,(float) (9.8/60));
		this.posVector=new Vector(xPos,yPos);
		this.velVector=new Vector(xVel,yVel);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
