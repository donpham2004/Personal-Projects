package com.breakout;

import java.awt.Color;
import java.awt.Graphics;

import com.breakout.BreakOut.BreakStates;

public class BallObject extends BreakObject{
	private Handler handler;
	public BallObject(int x, int y, float velX, float velY, int width, int height,Handler handler) {
		super(x,y,velX,velY,width,height,new BallCollision(),null);
		this.handler=handler;
		this.xMax=BreakOut.WIDTH-width;
		this.yMax=BreakOut.HEIGHT-height-20;
	}
	
	@Override
	public void tick() {
		x+=velX;
		collide.collideX(handler, this);
		y+=velY;
		collide.collideY(handler, this);
		velX= x<0?-getVelX():x>getxMax()?-getVelX():getVelX();
		velY= y<0?-getVelY():y>getyMax()?0:getVelY();
		if(velY==0) {
			BreakOut.state=BreakStates.GAMEOVER;
			return;
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)x, (int)y, width, height);
	}
	
	
}
