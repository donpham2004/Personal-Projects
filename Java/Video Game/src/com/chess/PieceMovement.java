package com.chess;
import java.util.LinkedList;



public class PieceMovement {
	private MovementVector movementVector;
	private int lowerBound;
	private int upperBound;
	private LinkedList<ChessCondition> conditions;
	private LinkedList<RotationMatrix> rotationMatrices;
	private LinkedList<ChessCondition> preConditions;
	private LinkedList<ChessCondition> specialAttacks;
	
	private CheckReturnResponse checkResponse;
	
	public PieceMovement() {
		checkResponse = new CheckReturnResponse();
		specialAttacks = new LinkedList<ChessCondition>();
		conditions = new LinkedList<ChessCondition>();
		preConditions = new LinkedList<ChessCondition>();
		rotationMatrices = new LinkedList<RotationMatrix>();
	}
	public CheckReturnResponse getCheckResponse() {
		return checkResponse;
	}




	public void setCheckResponse(CheckReturnResponse checkResponse) {
		this.checkResponse = checkResponse;
	}




	public LinkedList<ChessCondition> getSpecialAttacks() {
		return specialAttacks;
	}

	public void setSpecialAttacks(LinkedList<ChessCondition> specialAttacks) {
		this.specialAttacks = specialAttacks;
	}

	public LinkedList<ChessCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(LinkedList<ChessCondition> preConditions) {
		this.preConditions = preConditions;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public LinkedList<RotationMatrix> getRotationMatrices() {
		return rotationMatrices;
	}

	public LinkedList<ChessCondition> getChessConditions() {
		return conditions;
	}

	public MovementVector getMovementVector() {
		return movementVector;
	}
	
	public boolean checkReaction(VectorCoordinate vector,  ChessPieces piece) {
		return true;
	}
	
	public void setMovementVector(int lowerBound, int upperBound, MovementVector movementVector, RotationMatrix... rotationMatrices ) {
		this.movementVector = movementVector;
		setLowerBound(lowerBound);
		setUpperBound(upperBound);
		for (RotationMatrix rotationMatrix : rotationMatrices) {
			getRotationMatrices().add(rotationMatrix);
		}
	}
}