package com.chess;


public class VectorCoordinate {
	private int xPosition;
	private int yPosition;
	public static VectorCoordinate currentVector;
	public VectorCoordinate(int x, int y) {
		xPosition = x;
		yPosition = y;
	}
	
	public VectorCoordinate() {
		
	}
	
	public VectorCoordinate(VectorCoordinate vector) {
		this.xPosition=vector.getXPosition();
		this.yPosition=vector.getYPosition();
	}
	
	public void addVector(VectorCoordinate vector)  {
		currentVector=this;
		this.xPosition+=vector.xPosition;
		this.yPosition+=vector.yPosition;
	}
	
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
	public void setVectorCoordinate(int xPosition, int yPosition) {
		setXPosition(xPosition);
		setYPosition(yPosition);
	}
	
	public void setXPosition(int xPosition) {
		this.xPosition=xPosition;
	}
	
	public void setYPosition(int yPosition) {
		this.yPosition=yPosition;
	}
	
	public void copyVector(VectorCoordinate vector1, VectorCoordinate vector2) {
		vector1.setVectorCoordinate(vector2.getXPosition(), vector2.getYPosition());
	}
	
	
}