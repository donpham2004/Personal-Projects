package com.mazefinder;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Tile implements RenderTickable{
	private static final int TILE_WIDTH=12;
	private static LinkedList<Tile> tileList= new LinkedList<Tile>();;
	private int xPosition;
	private int yPosition;
	
	private BlockObject blockObject;
	
	public static void resetTile() {
		for(Tile tile:tileList) {
			tile.setBlockObject(null);
		}
	}
	public static void resetFinderTile() {
		for(Tile tile:tileList) {
			if(tile.getBlockObject() instanceof Finder) {
				tile.setBlockObject(null);
			}
		}
	}
	
	public static Tile getTileByPosition(int xPosition, int yPosition) {
		for(Tile tile:tileList) {
			if(tile.getxPosition()==xPosition&&tile.getyPosition()==yPosition) {
				return tile;
			}
		}
		return null;
	}
	
	public static Tile getTileByCoordinate(int xCoordinate, int yCoordinate) {
		int xPosition = (int) Math.floor(xCoordinate/TILE_WIDTH);
		int yPosition = (int) Math.floor(yCoordinate/TILE_WIDTH);
		return getTileByPosition(xPosition,yPosition);
	}
	
	public static int getTileWidth() {
		return TILE_WIDTH;
	}
	
	public Tile(int xPosition,int yPosition) {
		this.xPosition=xPosition;
		this.yPosition=yPosition;
		tileList.add(this);
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
	
	public BlockObject getBlockObject() {
		return blockObject;
	}

	public void setBlockObject(BlockObject blockObject) {
		if(this.blockObject==null&&blockObject!=null) {
			this.blockObject = blockObject;
			this.blockObject.setxPosition(getxPosition());
			this.blockObject.setyPosition(getyPosition());
		}else if(blockObject==null){
			this.blockObject=null;
		}
	}

	@Override
	public void render(Graphics g) {
		if(blockObject!=null) {
			g.setColor(blockObject.getColor());
		}else {
			g.setColor(Color.WHITE);
		}
		g.fillRect(xPosition*TILE_WIDTH, yPosition*TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
	}

	@Override
	public void tick() {
		if(blockObject!=null&&blockObject.isTickable()) {
			blockObject.tick();
		}
	}
}
