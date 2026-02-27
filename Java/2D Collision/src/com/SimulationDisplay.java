package com;

import java.awt.Color;
import java.util.LinkedList;

public class SimulationDisplay {
	private Vector2D position;
	private double orientation;
	private double width;
	private double height;
	private Color color;
	
	public SimulationDisplay(Vector2D position, double orientation, double width, double height) {
		this.color = Color.BLACK;
		this.position = position;
		this.orientation = orientation;
		this.width = width;
		this.height = height;
	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
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

	public Matrix2D getInvDisplayMatrix() {
		return new Matrix2D(new double[][] { { 1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } })
				.scale(height/2)
				.rotate(-orientation)
				.translate(new Vector2D(width / 2, height / 2))
				.translate(position);
	}

	public Matrix2D getDisplayMatrix() {
		return new Matrix2D()
				.translate(position.scale(-1.0))
				.translate(new Vector2D(width/2,height/2).scale(-1.0))
				.rotate(orientation)
				.scale(2.0/height)
				.multiply(new Matrix2D(new double[][] { { 1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }));
	}

	public boolean clip(Vector2D vertex1, Vector2D vertex2) {
		LinkedList<Vector2D> vertices = new LinkedList<Vector2D>();
		LinkedList<Vector2D> normals = new LinkedList<Vector2D>();
		vertices.add(new Vector2D(position));
		vertices.add(new Vector2D(position.getX() + width, position.getY()));
		vertices.add(new Vector2D(position.getY() + width, position.getY() + height));
		vertices.add(new Vector2D(position.getX(), position.getY() + height));

		normals.add(new Vector2D(0, -1));
		normals.add(new Vector2D(1, 0));
		normals.add(new Vector2D(0, 1));
		normals.add(new Vector2D(-1, 0));

		Vector2D l0 = vertex1;
		Vector2D lf = vertex2;
		
		for (int i = 0; i < vertices.size(); i++) {
			Vector2D l = lf.sub(l0);
			Vector2D p0 = vertices.get(i);
			Vector2D n = normals.get(i);
			double ln = l.dotProduct(n);
			double pln = p0.sub(l0).dotProduct(n);
			if(ln!=0) {
				double d = pln/ln;
				if(d<=1 && d >= 0) {
					if(ln<0) {
						l0 = l0.add(l.scale(d));
					}else {
						lf = l0.add(l.scale(d));
					}
					continue;
				}
				
			}
			
			if(pln<=0)
				return false;
		}
		vertex1.copy(l0);
		vertex2.copy(lf);
		
		return true;
	}

}
