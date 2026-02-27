package com.chess;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

public class TakenPieces {
	private static LinkedList<TakenPieces> takenWhitePieces = new LinkedList<>();
	private static LinkedList<TakenPieces> takenBlackPieces = new LinkedList<>();
	
	public static void resetTakenPieces( ) {
		takenWhitePieces.clear();
		takenBlackPieces.clear();
	}
	
	private Team team;
	private Image image;
	private int ID;
	public static void addPiece(ChessPieces piece) {
		TakenPieces takenPiece = new TakenPieces();
		takenPiece.setTeam(piece.getTeam());
		takenPiece.setImage(piece.getPieceType().getImage(piece.getTeam()));
		takenPiece.setID(piece.getPieceType().getID());
		if(piece.getTeam()==Team.WHITE) {
			takenWhitePieces.add(takenPiece);
			sortList(takenWhitePieces);
		}else {
			takenBlackPieces.add(takenPiece);
			sortList(takenBlackPieces);
		}
		
		
	}
	
	public static void sortList(LinkedList<TakenPieces> takenPiecesList) {
		for(int i=0;i<takenPiecesList.size()-1;i++) {
			for(int j=0;j<takenPiecesList.size()-1-i;j++) {
				if(takenPiecesList.get(j).getID()>takenPiecesList.get(j+1).getID()) {
					TakenPieces a=takenPiecesList.get(j);
					TakenPieces b=takenPiecesList.get(j+1);
					takenPiecesList.set(j, b);
					takenPiecesList.set(j+1, a);
				}
			}
		}
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void render(Graphics g) {
		
		
		for(int i=0;i<takenWhitePieces.size();i++) {
			g.drawImage(takenWhitePieces.get(i).getImage(), 1350+(i%5)*70, 20+((int)(i/5))*120, null);
		}
		for(int i=0;i<takenBlackPieces.size();i++) {
			g.drawImage(takenBlackPieces.get(i).getImage(), 1350+(i%5)*70, 600+((int)(i/5))*120, null);
		}
		
	}
}
