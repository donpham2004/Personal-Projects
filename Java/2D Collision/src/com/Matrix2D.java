package com;

public class Matrix2D {
	
	private double elem[][];
	
	public Matrix2D(double elem[][]) {
		this.elem = elem;
	}
	
	public Matrix2D() {
		this(new double[][] {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}});
	}
	
	public Matrix2D copy() {
		return new Matrix2D(elem.clone());
	}
		
	public static Matrix2D translation(Vector2D vector) {
		
		return new Matrix2D(new double[][] {
			{1,0,0,vector.getX()},
			{0,1,0,vector.getY()},
			{0,0,1,vector.getW()},
			{0,0,0,1}});
	}
	
	
	
	public Matrix2D scale(double scalar) {
		Matrix2D matrix = new Matrix2D();
		for(int i=0;i<3;i++) {
			for(int j=0;j<4;j++) {
				matrix.elem[i][j] = elem[i][j]*scalar;
			}
		}
		return matrix;
	}
	
	public Matrix2D multiply(Matrix2D matrix) {
		Matrix2D newMatrix = new Matrix2D();
		for(int i=0;i<4;i++) { 
			for(int j=0;j<4;j++) {
				double dotProduct = 0;
				for(int k=0;k<4;k++) {
					dotProduct += getElem(k,i) * matrix.getElem(j, k);
				}
				newMatrix.setElem(j, i, dotProduct);
			}
		}
		return newMatrix;
	}
	
	public Matrix2D rotate(double rad) {
		return multiply(rotation(rad));
	}
	
	public Matrix2D rotate(Vector2D vector) {
		return multiply(rotation(vector));
	}
	
	public Matrix2D translate(Vector2D vector) {
		return multiply(Matrix2D.translation(vector));
	}
	
	public double getElem(int row, int col) {
		return elem[row][col];
	}
	
	public void setElem(int row, int col, double val) {
		elem[row][col] = val;
	}
	
	
	
	public static Matrix2D rotation(double rad) {
		Matrix2D matrix = new Matrix2D();
		matrix.setElem(0, 0, Math.cos(rad));
		matrix.setElem(0, 1, -Math.sin(rad));
		matrix.setElem(1, 0, Math.sin(rad));
		matrix.setElem(1, 1, Math.cos(rad));
		return matrix;
	}
	
	public static Matrix2D rotation(Vector2D vector) {
		return rotation(vector.getW());
	}
}
