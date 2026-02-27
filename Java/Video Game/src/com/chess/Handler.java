package com.chess;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	private static LinkedList<ChessPieces> chessPieces = new LinkedList<ChessPieces>();
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		for(int i=0;i<chessPieces.size();i++) {
			chessPieces.get(i).render(g);
		}
	}
	
	public static void addPiece(ChessPieces chessPiece) {
		chessPieces.add(chessPiece);
	}
	
	public static void removePiece(ChessPieces chessPiece) {
		if(!KeyInput.promoted) {
			TakenPieces.addPiece(chessPiece);
		}
	
		chessPieces.remove(chessPiece);
	}
	
	public static LinkedList<ChessPieces> getChessPieces() {
		return chessPieces;
	}
	public static void clearChessPieces() {
		chessPieces.clear();
		TakenPieces.resetTakenPieces();
	}
	
	
}	
