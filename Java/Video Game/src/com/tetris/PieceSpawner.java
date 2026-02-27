package com.tetris;

import java.util.ArrayDeque;
import java.util.Random;

public class PieceSpawner {
	static ArrayDeque<String> queue = new ArrayDeque<String>();
	private TetrisPiece holdPiece;
	private AbstractBackGround background;
	public PieceSpawner() {
		while(queue.size()<6) {
			addToQueue();
		}
	}
	
	public void setBackground(AbstractBackGround background) {
		this.background=background;
	}
	
	boolean repeat=false;
	public boolean isHoldPieceNull() {
		return holdPiece==null?true:false;
	}
	public void setHoldPiece(TetrisPiece holdPiece) {
		this.holdPiece=holdPiece.makeCopy();
		this.holdPiece.setHeld(true);
		
	}
	
	public TetrisPiece getHoldPiece() {
		TetrisPiece piece;
		if(holdPiece ==null) {
			return createPiece();
		}
		
		return holdPiece;
	}
	
	
	
	public void addToQueue() {
		String type= "";
		switch(new Random().nextInt(7)) {
		case 0:
			type = "Square";
			break;
		case 1:
			type = "TPiece";
			break;
		case 2:
			type = "LinePiece";
			break;
		case 3:
			type = "LPiece";
			break;
		case 4:
			type = "RLPiece";
			break;
		case 5:
			type = "SkewPiece";
			break;
		default:
			type="RSkewPiece";
			break;
		}
		if(queue.contains(type)) {
			if(!repeat) {
				repeat = true;
				addToQueue();
				return;
			}
			
			
		}
		queue.add(type);
		repeat = false;
	}
	
	public TetrisPiece createPiece() {
		TetrisPiece piece;
		addToQueue();
		switch(queue.poll()) {
		case "Square":
			piece= new SquarePiece(true);
			break;
		case "TPiece":
			piece= new TPiece(true);
			break;
		case "LinePiece":
			piece=new LinePiece(true);
			break;
		case "LPiece":
			piece= new LPiece(true);
			break;
		case "RLPiece":
			piece= new RLPiece(true);
			break;
		case "SkewPiece":
			piece= new SkewPiece(true);
			break;
		default:
			piece= new RSkewPiece(true);
			break;
		}
		return piece;
	}

}
