package com.tictactoe;

import java.util.Enumeration;

enum PlayerPieces {
	XPIECE("X",true),
	OPIECE("O",false);
	
	private String piece;
	private boolean turn;
	
	private PlayerPieces(String piece, boolean turn) {
		this.piece=piece;
		this.turn=turn;
	}
	
	public String getPiece() {
		return piece;
	}
	
	public boolean isTurn() {
		return turn;
	}
	
	public static PlayerPieces getPlayerPiece(boolean turn) {
		for(PlayerPieces playerPiece:PlayerPieces.values()) {
			if(playerPiece.isTurn()==turn) return playerPiece;
		}
		return null;
	}
}
 
public abstract class AbstractPlayer {
	public static boolean inGame=true;
	public static int rounds;
	protected String name;
	protected boolean turn;
	protected static Grid gridObj=new Grid();
	protected String piece;
	protected static boolean currentTurn=true;
	protected int score=0;
	
	public static int counter=1;
	public static void startRound() {
		inGame=true;
		gridObj.printGrid();
		System.out.println("Round "+counter);
	}
	
	public void play() {
		if(inGame&&currentTurn==isTurn()) {
			System.out.printf("%s's turn\n",name);
			makeMove();
		}
	}
	
	protected AbstractPlayer(String piece, boolean turn,String name) {
		setName(name);
		setPiece(piece);
		setTurn(turn);
	}

	protected abstract void makeMove();
	
	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getPiece() {
		return piece;
	}

	protected void setPiece(String piece) {
		this.piece = piece;
	}

	protected boolean isTurn() {
		return turn;
	}

	protected void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	protected void switchTurn() {
		currentTurn=!currentTurn;
	}
	
	protected void setScore(int score) {
		this.score=score;
	}
	
	protected void incrementScore() {
		this.score++;
	}
	
	protected int getScore() {
		return score;
	}
	
	protected void putPiece(int position) {
		
		if(gridObj.putPiece(position, getPiece())) {
			if(gridObj.calcPoint(getPiece())!=0) {
				reset();
				System.out.println(getName()+" Wins!!!");
			}else if(gridObj.getEmptyLength()==0){				
				reset();
				System.out.println("Tie!!!");
			}else {
				switchTurn();
			}
		}
		if(inGame)gridObj.printGrid();
	}
	
	protected void reset() {
		inGame=false;
		currentTurn = true;
		gridObj.printGrid();
		counter++;
		gridObj=new Grid();
	}
	
}