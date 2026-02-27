package com.chess;

import java.awt.Image;

enum Piece {
	KING(0,5),
	QUEEN(133,4),
	BISHOP(266,2),
	KNIGHT(399,1),
	ROOK(532,3),
	PAWN(665,0);
	private int x;
	private int ID;
	private Piece(int x,int ID) {
		this.x=x;
		this.ID=ID;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Image getImage(Team team) {
		switch(team) {
		case WHITE:
			return Chess.sheet.crop(x, 0, 133, 133);
		default:

			return Chess.sheet.crop(x, 128, 133, 133);
		}
	}
	
	
	
}

