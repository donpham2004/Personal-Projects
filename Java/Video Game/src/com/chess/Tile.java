package com.chess;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Tile {
	public static LinkedList<Tile> tiles = new LinkedList<Tile>();
	private VectorCoordinate vector;
	private Color color;
	private ChessPieces piece;
	public volatile static boolean renderable;
	private boolean hasLegalMove;
	public void setHasLegalMove(boolean hasLegalMove) {
		renderable=true;
		this.hasLegalMove=hasLegalMove;
	}
	
	public boolean getHasLegalMove() {
		return hasLegalMove;
	}
	
	public static void clearTiles() {
		renderable=true;
		for(Tile tile:tiles) {
			tile.setPiece(null);
		}
	}
	
	public Tile(int x, int y) {
	
		tiles.add(this);
		color= getColor();
		hasLegalMove=false;
		vector = new VectorCoordinate(x,y);
	}
	
	public void setPiece(ChessPieces piece) {
		this.piece= piece;
	}

	public ChessPieces getPiece() {
		return piece;
	}
	
	public VectorCoordinate getVector() {
		return vector;
	}
	
	public static Tile getTile(int x, int y) {
		for(Tile tile:tiles) {
			if(tile.getVector().getXPosition()==x&&
					tile.getVector().getYPosition()==y) {
				return tile;
			}
		}
		
		return null;
	}
	
	public void setColor(Color color) {
		this.color=color;
	}
	public static Tile getTile(VectorCoordinate vector) {
		
		return getTile(vector.getXPosition(),vector.getYPosition());
	}
	
	public static Tile getTile(ChessPieces piece) {
		return getTile(piece.getVector().getXPosition(), piece.getVector().getYPosition());
	}
	
	public static Tile getCoordinateTile(int x, int y) {
		for(Tile tile:tiles) {
			if(Chess.currentStartVector.getXPosition()-tile.getVector().getXPosition()*Chess.TILE_WIDTH<=x&&Chess.currentStartVector.getXPosition()+(tile.getVector().getXPosition()+1)*Chess.TILE_WIDTH>=x) {
				if(Chess.currentStartVector.getYPosition()-tile.getVector().getYPosition()*Chess.TILE_WIDTH<=y&&Chess.currentStartVector.getYPosition()+(tile.getVector().getYPosition()+1)*Chess.TILE_WIDTH>=y) {
					return tile;
				}
			}
		}
		return null;
	}
	
	public void renderTile(Graphics g) {
		g.fillRect(Chess.currentStartVector.getXPosition()+Chess.multiplier*getVector().getXPosition()*Chess.TILE_WIDTH,
				Chess.currentStartVector.getYPosition()+Chess.multiplier*getVector().getYPosition()*Chess.TILE_WIDTH,
				Chess.TILE_WIDTH,Chess.TILE_WIDTH);
	}
	
	public void render(Graphics g) {
		
		if(this.getPiece() instanceof KingPiece&&((KingPiece)this.getPiece()).isChecked()) {
			g.setColor(Color.RED);
	
		}	else if(this.getPiece() instanceof PawnPiece) {
			if(this.getPiece().getTeam()==Team.WHITE&&this.getVector().getYPosition()==1) {
				g.setColor(Color.YELLOW);
			}else if(this.getPiece().getTeam()==Team.BLACK&&this.getVector().getYPosition()==8) {
				g.setColor(Color.YELLOW);
			}else {
				g.setColor(color);
			}
		}else {
			g.setColor(color);
		}
		
			
		renderTile(g);
			
	
		
		if(this.getHasLegalMove()&&renderable) {
			
			g.setColor(new Color(0,255,0,125));

			renderTile(g);
		}else if(KeyInput.clickedPiece!=null&&getPiece()==KeyInput.clickedPiece) {
			g.setColor(new Color(0,0,255));

			renderTile(g);
		}
		
	}
	private static int x=0;
	private static int y=0;
	public Color getColor() {
		if(y==BoardDimension.HORIZONTAL.getUpperBound()) {
			y=0;
			if(x==0) {
				x++;
			}else {
				x--;
			}
		}
		if(x==0) {
			y++;
			x++;
			return new Color(220,220,220);
		}else {
			y++;
			x--;
			return new Color(153,173,217);
			
		}
	}
	
}
