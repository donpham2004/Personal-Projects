package com.tictactoe;

import java.util.Scanner;

public class RealPlayer extends AbstractPlayer{
	public RealPlayer(String piece, boolean turn, String name) {
		super(piece,turn,name);
	}

	@Override
	public void makeMove() {
		Scanner scan = new Scanner(System.in);
		while(currentTurn==isTurn()&&inGame) {
			System.out.println("Enter Position");
			try {
				int position = Integer.parseInt(scan.next());
				putPiece(position);
			}catch(NumberFormatException e) {
				System.out.println("Cannot be a letter");
				gridObj.printGrid();
			}
		}
	}
}
