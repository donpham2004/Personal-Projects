package com;

import java.util.LinkedList;

public class Mesh {
	private LinkedList<Vector2D> vertices;
	private LinkedList<Vector2D> normals;
	private Vector2D linearPosition;
	private Vector2D angularPosition;
	
	public Mesh() {
		vertices = new LinkedList<Vector2D>();
		normals = new LinkedList<Vector2D>();
	}
	
	public void addVertices(Vector2D vertex) {
		vertices.add(vertex);
	}
	
	public void addNormals(Vector2D normal) {
		normals.add(normal);
	}
	
	public void setLinearPosition(Vector2D linearPosition) {
		this.linearPosition = linearPosition;
		
	}
	
	public void setAngularPosition(Vector2D angularPosition) {
		this.angularPosition = angularPosition;
	}
	
	public Vector2D getLinearPosition() {
		return linearPosition;
	}

	public Vector2D getAngularPosition() {
		return angularPosition;
	}

	public Matrix2D getMeshMatrix() {
		return new Matrix2D().rotate(angularPosition).translate(linearPosition);
	}

	public LinkedList<Vector2D> getVertices() {
		return vertices;
	}

	public void setVertices(LinkedList<Vector2D> vertices) {
		this.vertices = vertices;
	}

	public LinkedList<Vector2D> getNormals() {
		return normals;
	}

	public void setNormals(LinkedList<Vector2D> normals) {
		this.normals = normals;
	}
	
	
}
