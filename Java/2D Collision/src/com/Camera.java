package com;

public class Camera {
	private Vector2D position;
	private double orientation;
	private double scale;
	
	public Camera(Vector2D position, double orientation, double scale) {
		this.position = position;
		this.orientation = orientation;
		this.scale = scale;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public Matrix2D getCameraMatrix() {
		return new Matrix2D().scale(scale).rotate(orientation).translate(position);
	}
	
	public Matrix2D getInvCameraMatrix() {
		return new Matrix2D().translate(position.scale(-1.0)).rotate(-orientation).scale(1.0/scale);
	}
}
