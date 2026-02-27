package com.threedimensionalsim;

import java.util.LinkedList;
import java.util.Queue;

enum MatrixType {
	MATRIX4D,
	VECTOR4D
}
public class Matrix4DFactory {
	private static Queue<Matrix4D> matrices;
	private static Queue<Vector4D> vectors;
	static {
		matrices = new LinkedList<>();
		vectors = new LinkedList<>();
	}
	
	public static Matrix4D getMatrix() {
	
		if(matrices.size()==0) {
			return new Matrix4D();
		}
		Matrix4D matrix = matrices.remove();
		matrix.reset();
		return matrix;
	}
	
	public static Vector4D getVector() {
		if(vectors.size()==0) {
			return new Vector4D();
		}
		Vector4D vector = vectors.remove();
		vector.reset();
		return vector;
	}
	
	public static void add(Matrix4D matrix) {
		
		switch(matrix.getMatrixType()) {
		case MATRIX4D:
			
			matrices.add(matrix);
			break;
		case VECTOR4D:
			vectors.add((Vector4D)matrix);
			break;
		}
	}

}
