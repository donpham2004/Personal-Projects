package com.threedimensionalsim;

/**
 * 
 * @author donpham
 *
 * 4D Matrix for transforming and translating 4D VectorMatrix
 *
 */

public class Matrix4D {
	
	private double elem[][];
	private MatrixType type;
	static int counter = 0;
	public Matrix4D(double elem[][]) {
		type = MatrixType.MATRIX4D;
		counter ++;
		this.elem = elem;
	}
	
	public void setMatrixType(MatrixType type) {
		this.type =type;
	}
	
	public MatrixType getMatrixType() {
		return type;
	}
	
	public Matrix4D() {
		this(new double[][] {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}});
	}
	
	public void reset() {
		for(int i=0;i<getNumberOfRows();i++) {
			for(int j=0;j<getNumberOfColumns();j++) {
				elem[i][j] = (i==j)?1:0;
			}
		}
	}

	public int getNumberOfRows() {
		return elem.length;
	}
	
	public int getNumberOfColumns() {
		return elem[0].length;
	}
	   
	public void setElem(int row, int col, double value) {
		elem[row][col] = value;
	}
	
	public double getElem(int row, int col) {
		return elem[row][col];
	}
	
	public void scale(double scalar) {
		for(int i=0;i<getNumberOfRows();i++) {
			for(int j=0;j<getNumberOfColumns();j++) {
				elem[i][j] *= scalar;
			}
		}
	}
	
	public void add(Matrix4D matrix) {
		for(int i=0;i<getNumberOfRows();i++) {
			for(int j=0;j<getNumberOfColumns();j++) {
				elem[i][j] += matrix.elem[i][j];
			}
		}
	}
	
	public void sub(Matrix4D matrix) {
		for(int i=0;i<getNumberOfRows();i++) {
			for(int j=0;j<getNumberOfColumns();j++) {
				elem[i][j] -= matrix.elem[i][j];
			}
		}
	}
	
	public void multiply(Matrix4D matrix) {
		Matrix4D product =  Matrix4DFactory.getMatrix();
		
		
		for(int i=0;i<getNumberOfColumns();i++) { 
			for(int j=0;j<matrix.getNumberOfRows();j++) {
				double dotProduct = 0;
				for(int k=0;k<getNumberOfRows();k++) {
					dotProduct += getElem(k,i) * matrix.getElem(j, k);
				}
				product.setElem(j, i, dotProduct);
			}
		}
		copy(product);
		Matrix4DFactory.add(product);
	}
	
	public Matrix4D copy() {
		Matrix4D matrix = Matrix4DFactory.getMatrix();
		matrix.copy(this);
		return matrix;
	}
	
	public void copy(Matrix4D matrix) {
		for(int i=0;i<getNumberOfRows();i++) {
			for(int j=0;j<getNumberOfColumns();j++) {
				elem[i][j] = matrix.elem[i][j];
			}
		}
	}
}
  