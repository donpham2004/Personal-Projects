package com;

public class ContactPoint {
	private Polygon vertexPolygon;
	private Polygon facePolygon;
	private Vector2D normal;
	private Vector2D contactPoint;
	private double threshold = 0.01;
	public ContactPoint(Polygon vertexPolygon, Polygon facePolygon, Vector2D normal, Vector2D contactPoint) {
		this.vertexPolygon = vertexPolygon;
		this.facePolygon = facePolygon;
		this.normal = normal;
		this.contactPoint = contactPoint;
	}
	
	public Polygon getVertexPolygon() {
		return vertexPolygon;
	}
	
	public void setVertexPolygon(Polygon vertexPolygon) {
		this.vertexPolygon = vertexPolygon;
	}

	public Polygon getFacePolygon() {
		return facePolygon;
	}

	public void setFacePolygon(Polygon facePolygon) {
		this.facePolygon = facePolygon;
	}

	public Vector2D getNormal() {
		return normal;
	}

	public void setNormal(Vector2D normal) {
		this.normal = normal;
	}

	public Vector2D getContactPoint() {
		return contactPoint;
	}

	public void setContactPoint(Vector2D contactPoint) {
		this.contactPoint = contactPoint;
	}
	
	public Vector2D getRelVel() {
		Vector2D faceVel = facePolygon.getLinearVelocity().add(facePolygon.getAngularVelocity().crossProduct(contactPoint.sub(facePolygon.getMesh().getLinearPosition())));
		Vector2D vertVel = vertexPolygon.getLinearVelocity().add(vertexPolygon.getAngularVelocity().crossProduct(contactPoint.sub(vertexPolygon.getMesh().getLinearPosition())));
		return vertVel.sub(faceVel);
	}
	
	public boolean isColliding() {
		
		double vr = getRelVel().dotProduct(normal);
		if(vr>threshold)
			return false;
		if(vr>-threshold)
			return false;
		return true;
	}
	
}
