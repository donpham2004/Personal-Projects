package com.chess;

import java.awt.Graphics;
public class Board {
	
	public Board() {
		createBoard();
	}
	
	public void render(Graphics g) {
		for(Tile tile:Tile.tiles) {
			tile.render(g);
		}
	}
	
	public void tick() {
		
	}
	
	public void createBoard() {
		BoardDimension horizontal =BoardDimension.HORIZONTAL;
		BoardDimension vertical =BoardDimension.VERTICAL;
		for(int i=horizontal.getLowerBound();i<=horizontal.getUpperBound();i++) {
			for(int j=vertical.getLowerBound();j<=vertical.getUpperBound();j++) {
			new Tile(i,j);
			}
		}
	}
	
}
