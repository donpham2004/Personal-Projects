package com;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class PenetrationPhysics {
	private Polygon polygon1;
	private Polygon polygon2;
	private Polygon vertexPolygon;
	private Polygon facePolygon;
	private Vector2D witness;
	private Vector2D minVertex;
	
	private double depthTolerance = 0.01;
	private double currentTime;
	private double maxTime;
	public PenetrationPhysics(Polygon polygon1, Polygon polygon2, double maxTime) {
		this.polygon1 = polygon1;
		this.polygon2 = polygon2;
		this.maxTime = maxTime;
		this.currentTime = maxTime;
	}
	
	public Vector2D getLinearPosition(Polygon polygon) {
		return polygon.getMesh().getLinearPosition().add(polygon.getLinearVelocity().scale(currentTime));
	}
	
	public Matrix2D getAngularMatrix(Polygon polygon) {
		return Matrix2D.rotation(polygon.getMesh().getAngularPosition().add(polygon.getAngularVelocity().scale(currentTime)));
	}
	
	public Matrix2D getMatrix(Polygon polygon) {
		return getAngularMatrix(polygon).translate(getLinearPosition(polygon));
	}
	
	public int findWitness(Polygon polygon1, Polygon polygon2) {
		Matrix2D matrix1= getMatrix(polygon1);
		Matrix2D norm1Matrix = getAngularMatrix(polygon1);
		
		Matrix2D matrix2= getMatrix(polygon2);
		Matrix2D norm2Matrix = getAngularMatrix(polygon2);
		Iterator<Vector2D> vertices1 = polygon1.getMesh().getVertices().iterator();
		Iterator<Vector2D> normals1 = polygon1.getMesh().getNormals().iterator();		
		Iterator<Vector2D> vertices2 = polygon2.getMesh().getVertices().iterator();
		Iterator<Vector2D> normals2 = polygon2.getMesh().getNormals().iterator();
		
		
		double max = Double.NEGATIVE_INFINITY;
		while(vertices1.hasNext()) {
			Vector2D vertex = vertices1.next();
			Vector2D normal = normals1.next();
			double min = Double.POSITIVE_INFINITY;
			while(vertices2.hasNext()) {
				Vector2D testVertex = vertices2.next().multiply(matrix2);
				double dot = testVertex.sub(vertex.multiply(matrix1)).dotProduct(normal.multiply(norm1Matrix));
				if(dot<min) {
					min = dot;
				}
			}
			if(min>depthTolerance) {
				witness = normal;
				minVertex = vertex;
				facePolygon = polygon1;
				vertexPolygon = polygon2;
				return 1;
			}else if(min>max) {
				facePolygon = polygon1;
				vertexPolygon = polygon2;
				witness = normal;
				minVertex = vertex;
				max = min;
			}
			vertices2 =  polygon2.getMesh().getVertices().iterator();			
		}
		
		vertices1 = polygon1.getMesh().getVertices().iterator();
		while(vertices2.hasNext()) {
			Vector2D vertex = vertices2.next();
			Vector2D normal = normals2.next();
			double min = Double.POSITIVE_INFINITY;
			while(vertices1.hasNext()) {
				Vector2D testVertex = vertices1.next().multiply(matrix1);
				double dot = testVertex.sub(vertex.multiply(matrix2)).dotProduct(normal.multiply(norm2Matrix));
				if(dot<min) {
					min = dot;
				}
			}
			if(min>depthTolerance) {
				facePolygon = polygon2;
				vertexPolygon = polygon1;
				witness = normal;
				minVertex = vertex;
				return 1;
			}else if(min>max) {
				facePolygon = polygon2;
				vertexPolygon = polygon1;
				witness = normal;
				minVertex = vertex;
				max = min;
			}
			vertices1 =  polygon1.getMesh().getVertices().iterator();			
		}

		if(Math.abs(max)<depthTolerance)
			return 0;
		return -1;
	}

	public int testWitness(int direction) {
		
		Matrix2D vertexMatrix= getMatrix(vertexPolygon);
		Matrix2D normalMatrix = getAngularMatrix(facePolygon);
		Matrix2D faceMatrix = getMatrix(facePolygon);
		Iterator<Vector2D> vertices = vertexPolygon.getMesh().getVertices().iterator();
		double min = Double.POSITIVE_INFINITY;
		while(vertices.hasNext()) {
			Vector2D testVertex = vertices.next().multiply(vertexMatrix);
			Vector2D normal = witness.multiply(normalMatrix);
			Vector2D vertex = minVertex.multiply(faceMatrix);
			double dot = testVertex.sub(vertex).dotProduct(normal);
			if(dot<min)
				min = dot;
		}
		
		if(Math.abs(min)<depthTolerance)
			return 0;
		if(direction*min>0)
			return 1;
		return -1;
		
	}
	
	public double getTimeOfCollision() {
		int direction = findWitness(polygon1, polygon2);
		if(direction!=-1)
			return currentTime;
		int i = 0;
		double dtime = maxTime;
		while(direction!=0) {
			int test = testWitness(direction);

			if(test==0)
				return currentTime;
			if(test==1) {
				dtime/=2;
				currentTime = currentTime + direction*dtime;
			}else {
				direction = findWitness(polygon1, polygon2);
			}

		} 
		
		return currentTime;
	}
	
	public LinkedList<ContactPoint> getContact() {
		LinkedList<ContactPoint> contacts = new LinkedList<ContactPoint>();
		int i=findWitness(polygon1, polygon2);
		if(i==0) {
			LinkedList<Vector2D> list = new LinkedList<>();
			Matrix2D vertexMatrix= getMatrix(vertexPolygon);
			Matrix2D normalMatrix = getAngularMatrix(facePolygon);
			Matrix2D faceMatrix = getMatrix(facePolygon);
			
			LinkedList<Vector2D> tmp = facePolygon.getMesh().getVertices();
			int index = tmp.indexOf(minVertex);
			minVertex = minVertex.multiply(faceMatrix);
			witness = witness.multiply(normalMatrix);
			Vector2D second = facePolygon.getMesh().getVertices()
					.get((index+1)%tmp.size()).multiply(faceMatrix);
			Vector2D parallel = second
					.sub(minVertex);
			
			list.add(minVertex);
			list.add(second);
			Iterator<Vector2D> vertices = vertexPolygon.getMesh().getVertices().iterator();
			while(vertices.hasNext()) {
				Vector2D testVertex = vertices.next().multiply(vertexMatrix);
				Vector2D normal = witness;
				Vector2D vertex = minVertex;
				double dot = testVertex.sub(vertex).dotProduct(normal);
				if(Math.abs(dot)<depthTolerance) {
					list.add(testVertex);
				}
			}
			list.sort( new Comparator<Vector2D>() {

				@Override
				public int compare(Vector2D o1, Vector2D o2) {
					
					return ( o1.dotProduct(parallel)<o2.dotProduct(parallel))?-1:1;
				}
				
			});
			if(!list.get(0).equals(minVertex)) {
				while(!list.get(0).equals(minVertex)) {
					list.remove(0);
				}
				
			}else {
				list.remove(0);
			}
			
			if(!list.getLast().equals(second)) {
				while(!list.getLast().equals(second)) {
					list.removeLast();
				}
				
			}else {
				list.removeLast();
			}
			contacts.add(new ContactPoint(vertexPolygon, facePolygon, witness,list.getFirst()));
			if(!contacts.isEmpty())
				contacts.add(new ContactPoint(vertexPolygon, facePolygon, witness,list.getLast()));
		}
		return contacts;
	}
}
