package com.chess;

enum BoardDimension {
	VERTICAL(1,8),HORIZONTAL(1,8);
	private final int lowerBound;
	private final int upperBound;
	
	private BoardDimension(int lowerBound, int upperBound) {
		this.lowerBound=lowerBound;
		this.upperBound=upperBound;
	}
	
	public int getLowerBound() {
		return lowerBound;
	}
	
	public int getUpperBound() {
		return upperBound;
	}
}
