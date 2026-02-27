package com.threedimensionalsim;

/**
 * 
 * @author donpham
 *
 * 4D Homogeneous vector of 3D vector
 * Automatically divides by w values after matrix multiplication
 * Treated as 4D vector only through matrix multiplication and scaling
 */

public class Vector4D extends Matrix4D {
	
	
	// ---- Constructors ---- //
	public Vector4D(double xVal, double yVal, double zVal, double wVal) {
		super(new double[][] {{xVal},{yVal},{zVal},{wVal}});
		
		setMatrixType(MatrixType.VECTOR4D);
	}
	
	public Vector4D(double xVal, double yVal, double zVal) {
		this(xVal,yVal,zVal, 1);
	}
	
	public Vector4D() {
		this(0,0,0,1);
	}
	
	
	// ---- Setters and getters ---- //
	
	public void velocityVector(double xVal) {
		setElem(0,0,xVal);
	}
	
	public void setXVal(double xVal) {
		setElem(0,0,xVal);
	}
	
	public double getXVal() {
		return getElem(0,0);
	}
	
	public void setYVal(double yVal) {
		setElem(1,0,yVal);
	}
	
	public double getYVal() {
		return getElem(1,0);
	}
	
	public void setZVal(double zVal) {
		setElem(2,0,zVal);
	}
	
	public void setWVal(double wVal) {
		setElem(3,0,wVal);
	}
	
	public double getWVal() {
		return getElem(3,0);
	}
	
	public double getZVal() {
		return getElem(2,0);
	}
	
	public double getElem(int index) {
		return getElem(index,0);
	}
	
	public void setElem(int index, double elem) {
		setElem(index,0,elem);
	}
	
	public int getLen() {
		return this.getNumberOfRows();
	}
	
	public double getMagnitude() {
		return Math.sqrt(getXVal()*getXVal()+getYVal()*getYVal()+getZVal()*getZVal());
	}
	
	// ---- Operations ---- //
	public void reset() {
		setXVal(0);
		setYVal(0);
		setZVal(0);
		setWVal(1);
	}
	
	public void normalize() {
		scale(1/this.getMagnitude());
	}
	
	public void add(Vector4D vector) {
		this.setXVal(getXVal()+vector.getXVal());
		this.setYVal(getYVal()+vector.getYVal());
		this.setZVal(getZVal()+vector.getZVal());
	}
	
	public void sub(Vector4D vector) {
		this.setXVal(getXVal()-vector.getXVal());
		this.setYVal(getYVal()-vector.getYVal());
		this.setZVal(getZVal()-vector.getZVal());
	}
	
	public void multiply(Matrix4D matrix) {
		super.multiply(matrix);
		scale(1/getWVal());
	}
	
	public double dotProduct(Vector4D vector) {
		
		return getXVal()*vector.getXVal()+getYVal()*vector.getYVal()+getZVal()*vector.getZVal();
	}
	
	public void crossProduct(Vector4D vector) {
		double nx = getElem(1)*vector.getElem(2) - getElem(2) *vector.getElem(1);
		double ny = getElem(2)*vector.getElem(0) - getElem(0) * vector.getElem(2);
		double nz = getElem(0) * vector.getElem(1) - getElem(1)*vector.getElem(0);
		
		setElem(0,nx);
		setElem(1,ny);
		setElem(2,nz);
	}
	
	public Vector4D copy() {
		Vector4D vector = Matrix4DFactory.getVector();
		vector.copy(this);
		return vector;
	}
	
	public void copy(Vector4D vector) {
		setXVal(vector.getXVal());
		setYVal(vector.getYVal());
		setZVal(vector.getZVal());
		setWVal(vector.getWVal());
	}
	
}
