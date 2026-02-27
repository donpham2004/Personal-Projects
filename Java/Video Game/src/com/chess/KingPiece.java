package com.chess;
import java.util.LinkedList;

public class KingPiece extends ChessPieces {
	boolean isChecked;
	public KingPiece(int x, int y, Team team) {
		super(new LinkedList<PieceMovement>(), Piece.KING,team, x,  y);
		isChecked= false;
		 
	}
	

	public boolean isChecked() {
		return isChecked;
	}


	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	@Override
	public void setPieceMovements() {
		PieceMovement movement1 = new PieceMovement();
		movement1.setMovementVector(1, 7,MovementVector.STRAIGHT,STANARD_ROTATION_MATRIX);
		PieceMovement movement2 = new PieceMovement();
		movement2.setMovementVector(1, 7,MovementVector.DIAGONAL,STANARD_ROTATION_MATRIX);
		PieceMovement movement3 = new PieceMovement();
		movement3.setMovementVector(1, 2, MovementVector.STRAIGHT, RotationMatrix.ROTATION2);
		PieceMovement movement4 = new PieceMovement();
		movement4.setMovementVector(1, 2, MovementVector.STRAIGHT, RotationMatrix.ROTATION4);
		
		movement3.getPreConditions().add(new FirstMove());
		movement3.setCheckResponse(new CheckBreak());
		movement3.getPreConditions().add(new NewLeftRook());
		movement3.getSpecialAttacks().add(new LeftCastleKing());
		movement3.getPreConditions().add(new KingInCheck());
		
		movement4.getPreConditions().add(new KingInCheck());
		movement4.getPreConditions().add(new FirstMove());
		movement4.setCheckResponse(new CheckBreak());
		movement4.getPreConditions().add(new NewRightRook());
		movement4.getSpecialAttacks().add(new RightCastleKing());
		this.pieceMovements.add(movement1);
		this.pieceMovements.add(movement2);
		this.pieceMovements.add(movement3);
		this.pieceMovements.add(movement4);
		
	}
}

class QueenPiece extends ChessPieces {

	public QueenPiece(int x, int y, Team team) {
		super(new LinkedList<PieceMovement>(), Piece.QUEEN,team, x,  y);
		
	}

	@Override
	public void setPieceMovements() {
		PieceMovement movement1 = new PieceMovement();
		movement1.setMovementVector(1, 7,MovementVector.STRAIGHT,STANARD_ROTATION_MATRIX);
		PieceMovement movement2 = new PieceMovement();
		movement2.setMovementVector(1, 7,MovementVector.DIAGONAL,STANARD_ROTATION_MATRIX);
		this.pieceMovements.add(movement1);
		this.pieceMovements.add(movement2);
	}
}

class KnightPiece extends ChessPieces {
		
	
	public KnightPiece(int x, int y, Team team) {
		super(new LinkedList<PieceMovement>(), Piece.KNIGHT,team, x,  y);
		
	}

	@Override
	public void setPieceMovements() {
		PieceMovement movement1 = new PieceMovement();
		movement1.setMovementVector(1, 1,MovementVector.LMOVE,STANARD_ROTATION_MATRIX);
		PieceMovement movement2 = new PieceMovement();
		movement2.setMovementVector(1, 1,MovementVector.RLMOVE,STANARD_ROTATION_MATRIX);
		this.pieceMovements.add(movement1);
		this.pieceMovements.add(movement2);
	}
	
}

class BishopPiece extends ChessPieces {
	public BishopPiece(int x, int y, Team team) {
		super(new LinkedList<PieceMovement>(), Piece.BISHOP,team, x,  y);
		
	}

	@Override
	public void setPieceMovements() {
		PieceMovement movement1 = new PieceMovement();
		movement1.setMovementVector(1, 7,MovementVector.DIAGONAL,STANARD_ROTATION_MATRIX);
		this.pieceMovements.add(movement1);
		
	}
}

class RookPiece extends ChessPieces {
	public RookPiece(int x, int y, Team team) {
		super(new LinkedList<PieceMovement>(), Piece.ROOK,team, x,  y);
		
	}
	@Override
	public void setPieceMovements() {
		PieceMovement movement1 = new PieceMovement();
		movement1.setMovementVector(1, 7,MovementVector.STRAIGHT,STANARD_ROTATION_MATRIX);
		this.pieceMovements.add(movement1);
		
	}
}

class PawnPiece extends ChessPieces {
	private boolean enPassantable;
	public PawnPiece(int x, int y, Team team) {
		super(new LinkedList<PieceMovement>(), Piece.PAWN,team, x,  y);
		enPassantable=false;
		
	}
	
	public boolean isEnPassantable() {
		return enPassantable;
	}
	
	public void setEnPassantable(boolean enPassantable) {
		this.enPassantable=enPassantable;
	}
	@Override
	public void setPieceMovements() {
		PieceMovement single = new PieceMovement();
		PieceMovement twice = new PieceMovement();
		PieceMovement attack = new PieceMovement();
		PieceMovement enPassanteLeft = new PieceMovement();
		PieceMovement enPassanteRight = new PieceMovement();
		if(getTeam()==Team.WHITE) {
			enPassanteLeft.setMovementVector(1, 1, MovementVector.DIAGONAL, RotationMatrix.ROTATION3);
			enPassanteRight.setMovementVector(1, 1, MovementVector.DIAGONAL, RotationMatrix.ROTATION4);
			single.setMovementVector(1, 1, MovementVector.STRAIGHT,RotationMatrix.ROTATION3);
			twice.setMovementVector(1, 2, MovementVector.STRAIGHT,RotationMatrix.ROTATION3);
			attack.setMovementVector(1, 1, MovementVector.DIAGONAL, RotationMatrix.ROTATION3,RotationMatrix.ROTATION4);
		}else {
			single.setMovementVector(1, 1, MovementVector.STRAIGHT,RotationMatrix.ROTATION1);
			twice.setMovementVector(1, 2, MovementVector.STRAIGHT,RotationMatrix.ROTATION1);
			enPassanteRight.setMovementVector(1, 1, MovementVector.DIAGONAL, RotationMatrix.ROTATION2);
			enPassanteLeft.setMovementVector(1, 1, MovementVector.DIAGONAL, RotationMatrix.ROTATION1);
			attack.setMovementVector(1, 1, MovementVector.DIAGONAL, RotationMatrix.ROTATION1,RotationMatrix.ROTATION2);
		}
		
		single.getChessConditions().add(new InFront());
		single.getSpecialAttacks().add(new Promotion());
		
		twice.getPreConditions().add(new FirstMove());
		twice.getChessConditions().add(new InFront());
		twice.getSpecialAttacks().add(new Promotion());
		
		enPassanteLeft.getPreConditions().add(new PawnOnLeftSide());
		enPassanteLeft.getSpecialAttacks().add(new EnPassant());
		enPassanteLeft.getSpecialAttacks().add(new Promotion());
		
		enPassanteRight.getPreConditions().add(new PawnOnRightSide());
		enPassanteRight.getSpecialAttacks().add(new EnPassant());
		enPassanteRight.getSpecialAttacks().add(new Promotion());
		
		attack.getChessConditions().add(new WillAttack());
		attack.getSpecialAttacks().add(new Promotion());
		this.pieceMovements.add(single);
		this.pieceMovements.add(twice);
		this.pieceMovements.add(attack);
		this.pieceMovements.add(enPassanteRight);
		this.pieceMovements.add(enPassanteLeft);
	}
}