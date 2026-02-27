package com.breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BlockObject extends BreakObject{
	private Handler handler;
	private Color[] colorArray= {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.BLACK};
	private int colorID;
	private List<Color> colorList = Arrays.asList(colorArray);	
	public BlockObject(int x, int y, float velX, float velY, int width, int height, Handler handler) {
		super(x, y, velX, velY, width, height, null, new BlockResponse());
		this.handler=handler;
		if(y<height+BreakOut.SPACE_HEIGHT) {
			colorID=0;
		}else if(y<height*2+BreakOut.SPACE_HEIGHT) {
			colorID=1;
		}else if(y<height*3+BreakOut.SPACE_HEIGHT) {
			colorID=2;
		}else if(y<height*4+BreakOut.SPACE_HEIGHT) {
			colorID=3;
		}else if(y<height*5+BreakOut.SPACE_HEIGHT) {
			colorID=4;
		}
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	public void setColorID(int colorID) {
		this.colorID=colorID;
	}
	
	public int getColorID() {
		return colorID;
	}
	
	public Color getColor() {
		if(colorList.get(colorID)==Color.BLACK) {
			handler.block.add(this);
		}
		return colorList.get(colorID);
	}
	
	public Handler getHandler() {
		return this.handler;
	}
}
