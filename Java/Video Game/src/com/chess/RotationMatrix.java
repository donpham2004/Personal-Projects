package com.chess;

public enum RotationMatrix {
	ROTATION1(new VectorCoordinate(1,0), new VectorCoordinate(0,1)),
	ROTATION2(new VectorCoordinate(0,1), new VectorCoordinate(-1,0)),
	ROTATION3(new VectorCoordinate(-1,0), new VectorCoordinate(0,-1)),
	ROTATION4(new VectorCoordinate(0,-1), new VectorCoordinate(1,0));
	
	private VectorCoordinate column1;
	private VectorCoordinate column2;
	
	private RotationMatrix(VectorCoordinate column1, VectorCoordinate column2) {
		this.column1=column1;
		this.column2=column2;
	}
	
	public VectorCoordinate getColumn1() {
		return column1;
	}
	
	public VectorCoordinate getColumn2() {
		return column2;
	}
	
	public VectorCoordinate getRotatedVector(VectorCoordinate vector, RotationMatrix rotationMatrix) {
	
		int x=vector.getXPosition()*rotationMatrix.getColumn1().getXPosition()+
		vector.getYPosition()*rotationMatrix.getColumn2().getXPosition();
		
		int y=vector.getXPosition()*rotationMatrix.getColumn1().getYPosition()+
				vector.getYPosition()*rotationMatrix.getColumn2().getYPosition();
		return new VectorCoordinate(x,y);
	}
	
}


