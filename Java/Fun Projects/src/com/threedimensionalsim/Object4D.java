package com.threedimensionalsim;

public abstract class Object4D   {
	private Vector4D positionVector;
	private Vector4D velocityVector;
	private Vector4D xVector;
	private Vector4D yVector;
	private Vector4D zVector;
	
	
	public Object4D(Vector4D positionVector, Vector4D xVector, Vector4D yVector, Vector4D zVector) {
		this.positionVector = positionVector;
		this.xVector = xVector;
		this.yVector = yVector;
		this.zVector = zVector;
		velocityVector = new Vector4D();
	}
	
	public boolean renderable() {
		return false;
	}
	
	public void tick() {
		
	}
	
	public Vector4D getPositionVector() {
		return positionVector;
	}
	
	
	public void setPositionVector(Vector4D positionVector) {
		this.positionVector = positionVector;
	}

	public void setXVector(Vector4D xVector) {
		this.xVector = xVector;
	}

	public void setYVector(Vector4D yVector) {
		this.yVector = yVector;
	}

	public void setZVector(Vector4D zVector) {
		this.zVector = zVector;
	}

	public Vector4D getXVector() {
		return xVector;
	}
	
	public Vector4D getYVector() {
		return yVector;
	}
	
	public Vector4D getZVector() {
		return zVector;
	}

	public Vector4D getVelocityVector() {
		return velocityVector;
	}

	public void setVelocityVector(Vector4D vectorVelocity) {
		this.velocityVector = vectorVelocity;
	}
	
	public void rotate(Matrix4D matrix) {
		xVector.multiply(matrix);
		yVector.multiply(matrix);
		zVector.multiply(matrix);
	}
	
	public void setXYZ(Matrix4D matrix) {
		xVector.setXVal(matrix.getElem(0, 0));
		xVector.setYVal(matrix.getElem(1, 0));
		xVector.setZVal(matrix.getElem(2, 0));
		xVector.setWVal(matrix.getElem(3, 0));
		
		yVector.setXVal(matrix.getElem(0, 1));
		yVector.setYVal(matrix.getElem(1, 1));
		yVector.setZVal(matrix.getElem(2, 1));
		yVector.setWVal(matrix.getElem(3, 1));
		
		zVector.setXVal(matrix.getElem(0, 2));
		zVector.setYVal(matrix.getElem(1, 2));
		zVector.setZVal(matrix.getElem(2, 2));
		zVector.setWVal(matrix.getElem(3, 2));
	}
} 
