package com.kinematics;


import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends Shape{
	
	int points[][] = new int[3][2];
	
	public Triangle(int width, int height, float radian, float xPos,float yPos,float xVel,float yVel, float radVel,ID id) {
		super(width, height,radian, xPos, yPos, xVel,yVel,radVel, id);
		
	}

	public float roundDown(float x) {
		return Math.abs(x)>1?x:0;
	}
	
	@Override
	public void tick() {
		for(int i=0;i<points.length;i++) {
			if(points[i][0]>Kinematic.getWindowWidth()||points[i][0]<0) {
				velVector.setXComponent(velVector.getXComponent()*-1);
				radVel=-radVel;
				break;
			}
			if(points[i][1]>Kinematic.getWindowHeight()||points[i][1]<0) {
				posVector.setYComponent(Kinematic.getWindowHeight()-height);
				velVector.setYComponent((float) (roundDown(velVector.getYComponent())*-0.5));
				radVel=-radVel;
				break;
			}
		}
		
		radian+=radVel;
		this.posVector.addVector(velVector);
		this.velVector.addVector(accVector);
		float magnitude = (float) Math.sqrt(Math.pow(width/2, 2)+Math.pow(height/2, 2));
		for(int i=0;i<points.length;i++) {
			points[i][0] = (int)((posVector.getXComponent()+width/2)+Math.cos(radian+i*Math.PI/2)*magnitude);
			points[i][1] = (int)((posVector.getYComponent()+height/2)+Math.sin(radian+i*Math.PI/2)*magnitude);
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i=0;i<points.length;i++) {
			g.drawLine(points[i][0], points[i][1], points[(i+1)%4][0], points[(i+1)%4][1]);
		}
	}
}

