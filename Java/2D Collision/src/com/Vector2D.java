package com;

public class Vector2D {
	private double x;
	private double y;
	private double w;
	private double t;
	
	public Vector2D(double x, double y, double w, double t) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.t = t;
	}
	
	public Vector2D(double x, double y, double w) {
		this(x,y,w,1.0);
	}
	
	public Vector2D(double x, double y) {
		this(x,y,0.0);
	}
	
	public Vector2D(double w) {
		this(0.0,0.0,w);
	}
	
	public Vector2D() {
		this(0.0,0.0,0.0);
	}
	 
	public Vector2D(Vector2D vector) {
		this(vector.x,vector.y,vector.w,vector.t);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public Vector2D add(Vector2D vector) {
		return new Vector2D(x+vector.x,y+vector.y, w+vector.w);
	}
	
	public Vector2D sub(Vector2D vector) {
		return new Vector2D(x-vector.x,y-vector.y,w-vector.w);
	}
	
	public Vector2D scale(double scale) {
		return new Vector2D(x*scale, y*scale, w*scale);
	}
	
	public Vector2D normalize() {
		return scale(1.0/magnitude());
	}
	
	public Vector2D multiply(Matrix2D matrix) {
		double newX = x*matrix.getElem(0, 0) + y*matrix.getElem(0, 1)+ w*matrix.getElem(0, 2)+t*matrix.getElem(0, 3);
		
		double newY = x*matrix.getElem(1, 0) + y*matrix.getElem(1, 1)+ w*matrix.getElem(1, 2)+t*matrix.getElem(1, 3);
		
		double newW = x*matrix.getElem(2, 0) + y*matrix.getElem(2, 1)+ w*matrix.getElem(2, 2)+t*matrix.getElem(2, 3);
		
		double newT = x*matrix.getElem(3, 0) + y*matrix.getElem(3, 1)+ w*matrix.getElem(3, 2)+t*matrix.getElem(3, 3);
		return new Vector2D(newX,newY,newW, newT);
		
	}
	
	public Vector2D crossProduct(Vector2D vector) {
		return new Vector2D(y*vector.w-w*vector.y,w*vector.x-x*vector.w,x*vector.y-y*vector.x);
	}
	
	public double magnitude() {
		return Math.sqrt(x*x +y*y + w*w);
	}
	
	public double dotProduct(Vector2D vector) {
		return x*vector.x + y*vector.y + w*vector.w;
	}
	
	public void copy(Vector2D vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.w = vector.w;
		this.t = vector.t;
	}
}
