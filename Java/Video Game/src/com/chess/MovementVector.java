package com.chess;

public enum MovementVector {
	STRAIGHT(new VectorCoordinate(0,1)),
	DIAGONAL(new VectorCoordinate(1,1)),
	LMOVE(new VectorCoordinate(2,1)),
	RLMOVE(new VectorCoordinate(1,2));
	private VectorCoordinate vector;
	private MovementVector(VectorCoordinate vector) {
		this.vector=vector;
	}
	public VectorCoordinate getVector() {
		return vector;
	}
}
