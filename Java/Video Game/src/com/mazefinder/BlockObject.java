package com.mazefinder;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class BlockObject implements RenderTickable{
	private static Handler handler;
	protected Color color;
	protected int xPosition;
	protected int yPosition;
	protected boolean tickable;
	
	public boolean isTickable() {
		return tickable;
	}

	public void setTickable(boolean tickable) {
		this.tickable = tickable;
	}

	public static void setHandler(Handler newHandler) {
		handler = newHandler;
	}
	
	public BlockObject(Color color,boolean tickable) {
		this.color = color;
		this.tickable=tickable;
	}
	
	
	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void render(Graphics g) {}
	
}

class Goal extends BlockObject {

	public Goal() {
		super(Color.YELLOW,false);
	}

	@Override
	public void tick() {
		
	}
	
}

class Wall extends BlockObject {

	public Wall() {
		super(Color.BLACK,false);
	}
	@Override
	public void tick() {
	}
}

class Finder extends BlockObject {
	private LinkedList<Direction> directionList;
	private Finder parentFinder;
	private static boolean pathFound=false;
	public Finder(int xPosition, int yPosition,Finder parentFinder) {
		super(Color.BLUE,true);
		this.parentFinder=parentFinder;
		this.xPosition=xPosition;
		this.yPosition=yPosition;
		directionList = new LinkedList<Direction>();
		directionList.add(Directions.ONE);
		directionList.add(Directions.TWO);
		directionList.add(Directions.THREE);
		directionList.add(Directions.FOUR);
	}

	public void backTrace(Finder currentFinder) {
		currentFinder.setTickable(false);
		currentFinder.setColor(Color.GREEN);
		if(currentFinder.parentFinder!=null) {
			currentFinder.parentFinder.backTrace(parentFinder);
		}else {
			currentFinder.setColor(Color.ORANGE);
		}
	}
	
	public static void setPathFound(boolean newPathFound) {
		pathFound = newPathFound;
	}
	
	public static boolean getPathFound() {
		return pathFound;
	}
	
	@Override
	public void tick() {
		if(directionList.size()==0) {
			setColor(Color.RED);
			setTickable(false);
			return;
		}else if(pathFound) {
			setTickable(false);
			return;
		}
		Direction direction = directionList.remove(new Random().nextInt(directionList.size()));
		int xPosition = getxPosition()+direction.moveXDirection();
		int yPosition = getyPosition()+direction.moveYDirection();
		Tile tile = Tile.getTileByPosition(xPosition, yPosition);
		if(tile!=null) {
			if(tile.getBlockObject() instanceof Goal) {
				pathFound=true;		
				backTrace(this);
				return;
			}
			tile.setBlockObject(new Finder(xPosition,yPosition,this));
		}
	}
	
}
