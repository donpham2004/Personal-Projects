package com.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block {
	public static boolean fast=false;
	private Color color;
	private int x,y;
	private int velX, velY;
	private int[] block = new int[4];
	public Block(Color color,int x,int y) {
		this.color = color;
		this.x=x+Tetris.widthCenter-Tetris.BLOCK_LENGTH;
		this.y=y+0;
	}
	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(), getY(), Tetris.BLOCK_LENGTH, Tetris.BLOCK_LENGTH);
		g.setColor(getColor());
		g.fillRect(getX()+2, getY()+2, Tetris.BLOCK_LENGTH-4, Tetris.BLOCK_LENGTH-4);
	}
	private int counter=0;
	public void tick() {
		if(fast) {
			y+=getVelY();
			return;
		}else if(counter<4){
			counter++;
			return;
		}else if(counter>=4) {
			counter=0;
			y+=getVelY();
		}
		
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x += x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y += y;
	}
	
	public int getVelX() {
		return velX;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVelY() {
		return velY;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public void setColor(Color color) {
		this.color= color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setBlock(int x, int y) {
		this.block[x] = y;
	}
	
	public int getBlock(int x) {
		return block[x];
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),Tetris.BLOCK_LENGTH,Tetris.BLOCK_LENGTH);
	}
}
