package com.tictactoe;

public class ComputerPlayer extends AbstractPlayer {
	private int bestPosition;
	
	public ComputerPlayer(String piece, boolean turn, String name) {
		super(piece,turn,name);
	}
	
	private int minimax(Grid newPosition,int depth, boolean turn) {
		Grid position =newPosition.getClone();
		
		int[] emptySpace=position.getEmptySpace();
		if(depth==0|position.calcPoint(getPiece())!=0) {
			return 1*position.calcPoint(getPiece());
		}
		else if(turn) {
			int maxEval=-1000;
			int bestPosition=0;
			for(int child:emptySpace) {
				position.putPiece(child, PlayerPieces.getPlayerPiece(isTurn()).getPiece());
				int eval=minimax(position,depth-1,false);
				if(eval>maxEval) {
					bestPosition = child;
					maxEval=eval;
				}
				position.removePiece(child);
			}
			this.bestPosition=bestPosition;
			return maxEval;
		}else {
			int minEval=1000;
			for(int child:emptySpace) {
				position.putPiece(child, PlayerPieces.getPlayerPiece(!isTurn()).getPiece());
				int eval=minimax(position,depth-1,true);
				if(eval<minEval) {
					minEval=eval;
				}
				position.removePiece(child);
			}
			return minEval;
		}
	}
	
	@Override
	protected void makeMove() {
		minimax(gridObj,gridObj.getEmptyLength(),true);
		System.out.println(bestPosition);
		putPiece(bestPosition);
	}
}
