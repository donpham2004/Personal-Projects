package com.kinematics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;


public class Square extends Shape{
	private Handler handler;
	public int points[][] = new int[4][2];
	
	public Square(int width, int height, float radian, float xPos,float yPos,float xVel,float yVel, float radVel,ID id,Handler handler) {
		super(width, height,radian, xPos, yPos, xVel,yVel,radVel, id);
		this.handler=handler;
	}

	public float roundDown(float x) {
		return Math.abs(x)>1?x:0;
	}
	
	@Override
	public void tick() {
		for(int i=0;i<points.length;i++) {
			if(points[i][0]>Kinematic.getWindowWidth()||points[i][0]<0) {
				velVector.setXComponent(0);
				radVel=-radVel;
				break;
			}
			if(points[i][1]>Kinematic.getWindowHeight()||points[i][1]<0) {
				velVector.setYComponent(0);
				radVel=-radVel;
				break;
			}
		}
		boolean exit=false;
		for(Shape shape:handler.getList()) {
			Square square = (Square)shape;
			if(shape.equals(this)) continue;
			if(exit) break;
			for(int i=0;i<4&&!exit;i++) {
				for(int j=0;j<4&&!exit;j++) {
					
					if(Line2D.linesIntersect(square.points[i][0], square.points[i][1], 
							square.points[(i+1)%4][0], square.points[(i+1)%4][1],
							this.points[j][0], this.points[j][1], 
							this.points[(j+1)%4][0], this.points[(j+1)%4][1])) {
						velVector.setYComponent(0);
						exit=true;
					}
				}
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
