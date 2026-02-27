package com;

import java.util.LinkedList;

public class PhysicsTemplate {
	public void initList(LinkedList<Vector2D> vertices, LinkedList<Vector2D> normals, Polygon polygon) {
		for(Vector2D vertex: polygon.getMesh().getVertices()) {
			vertices.add(
					vertex.multiply(
					Matrix2D.rotation(
							polygon.getMesh().getAngularPosition().getW()))
					.add(polygon.getMesh().getLinearPosition())
					);
		}
		for(Vector2D normal: polygon.getMesh().getNormals()) {
			normals.add(normal.multiply(Matrix2D.rotation(polygon.getMesh().getAngularPosition().getW())));
		}
	}
	
	public void handleDot(double dot) {
		
	}
	
	public void handleMin(double min) {
		
	}
	
}
