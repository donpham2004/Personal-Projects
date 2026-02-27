package com;

import java.util.Comparator;
import java.util.LinkedList;

public class RectPhysics {
	private LinkedList<Vector2D> shape1Vertices;
	private LinkedList<Vector2D> shape2Vertices;
	private LinkedList<Vector2D> shape1Normals;
	private LinkedList<Vector2D> shape2Normals;
	private LinkedList<Vector2D> contactPoints;
	private Vector2D collidePoint;
	private double overlap;
	private Vector2D minNormal1;
	private Vector2D minVertex1;
	private Vector2D minNormal2;
	private Vector2D minVertex2;
	private Vector2D minNormal;
	private int direction;
	private final double EPSILON = 0.0000000000001;

	private void initiateLists(Polygon shape1, Polygon shape2) {
		shape1Vertices = new LinkedList<>();
		shape2Vertices = new LinkedList<>();
		shape1Normals= new LinkedList<>();
		shape2Normals= new LinkedList<>();
		contactPoints = new LinkedList<Vector2D>();
		for(Vector2D vertex: shape1.getMesh().getVertices()) {
			shape1Vertices.add(
					vertex.multiply(
					Matrix2D.rotation(
					shape1.getMesh().getAngularPosition().getW()))
					.add(shape1.getMesh().getLinearPosition())
					);
		}
		for(Vector2D vertex: shape2.getMesh().getVertices()) {
			shape2Vertices.add(vertex.multiply(Matrix2D.rotation(shape2.getMesh().getAngularPosition().getW()))
					.add(shape2.getMesh().getLinearPosition())
					);
		}
		for(Vector2D normal: shape1.getMesh().getNormals()) {
			shape1Normals.add(normal.multiply(Matrix2D.rotation(shape1.getMesh().getAngularPosition().getW())));
		}
		for(Vector2D normal: shape2.getMesh().getNormals()) {
			shape2Normals.add(normal.multiply(Matrix2D.rotation(shape2.getMesh().getAngularPosition().getW())));
		}
	}
	
	
	private void init(Polygon shape1, Polygon shape2) {
		initiateLists(shape1,shape2);
		collidePoint = null;
		overlap = Double.NEGATIVE_INFINITY;
		minNormal1 = null;
		minVertex1 = null;
		minNormal2 = null;
		minVertex2 = null;
		minNormal = null;
		direction = 0;
	}
	
	private boolean setOverlap(Polygon shape1, Polygon shape2, LinkedList<Vector2D> shape1Vertices,LinkedList<Vector2D> shape1Normals,LinkedList<Vector2D> shape2Vertices, int direction) {
		for(int k=0;k<shape1Normals.size();k++) {	
			double offset = shape1Vertices.get(k).dotProduct(shape1Normals.get(k));
			double min = Double.POSITIVE_INFINITY;
			for(int l=0;l<shape2Vertices.size();l++) {
				double dot = shape2Vertices.get(l).dotProduct(shape1Normals.get(k))-offset;
				if(dot<=min||Math.abs(dot-min)<EPSILON) {	
					min = dot;
				}
			}
			if(min>=0) {
				return true;
			}else if(min>=overlap||Math.abs(min-overlap)<EPSILON) {
				this.direction = direction;
				overlap = min;
				if(direction ==1) {
					minNormal1 = shape1Normals.get(k);
					minNormal = minNormal1;
					minVertex1 = shape1Vertices.get(k);
				}else {
					minNormal2 = shape1Normals.get(k);
					minNormal = minNormal2;
					minVertex2 = shape1Vertices.get(k);
				}
				
			}
		}
		return false;
	}
	
	private void addContactPoints(Vector2D minVertex1, Vector2D minNormal1, LinkedList<Vector2D> shapeVertices) {
		
		minNormal = minNormal.normalize().scale(overlap/2*direction);
		if(minVertex1!=null && minNormal1!=null) {
			double offset = minVertex1.dotProduct(minNormal1);
			for(int l=0;l<shapeVertices.size();l++) {
				double dot = shapeVertices.get(l).dotProduct(minNormal1)-offset;
				if(dot==overlap||Math.abs(dot-overlap)<EPSILON) {	
					contactPoints.add(shapeVertices.get(l).sub(minNormal));
				}
			}
		}
	}
	
	
	
	public boolean handleCollision(Polygon shape1, Polygon shape2) {
		init(shape1,shape2);
		if(setOverlap(shape1,shape2,shape1Vertices,shape1Normals,shape2Vertices,1)|| setOverlap(shape2,shape1,shape2Vertices,shape2Normals,shape1Vertices,-1)) 
			return false;
		
		Vector2D parallel = new Vector2D(-minNormal.getY(),minNormal.getX());
		minNormal = minNormal.normalize().scale(overlap/2*direction);
		addContactPoints(minVertex1, minNormal1, shape2Vertices);
		addContactPoints(minVertex2,minNormal2,shape1Vertices);	
	
		shape1.getMesh().setLinearPosition(shape1.getMesh().getLinearPosition().add(minNormal));
		shape2.getMesh().setLinearPosition(shape2.getMesh().getLinearPosition().sub(minNormal));
		
		contactPoints.sort( new Comparator<Vector2D>() {

			@Override
			public int compare(Vector2D o1, Vector2D o2) {
				
				return ( o1.dotProduct(parallel)<o2.dotProduct(parallel))?1:-1;
			}
			
		});
		minNormal = minNormal.normalize();
		if(contactPoints.size()%2==1) {
			collidePoint = contactPoints.get(contactPoints.size()/2);
		}
		else if(contactPoints.size()%2==0) {
			collidePoint = contactPoints.get(contactPoints.size()/2-1).add(contactPoints.get(contactPoints.size()/2)).scale(0.5);
		}
		
		Vector2D n21 = minNormal;
		Vector2D n12 = n21.scale(-1.0);
		Vector2D n = n12;
		double m1 = shape1.getMass();
		Vector2D v1 = shape1.getLinearVelocity();
		Vector2D w1 = shape1.getAngularVelocity();
		double I1 = shape1.getInertia();
		Vector2D r1 = collidePoint.sub(shape1.getMesh().getLinearPosition());
		
		double m2 = shape2.getMass();
		Vector2D v2 = shape2.getLinearVelocity();
		Vector2D w2 = shape2.getAngularVelocity();
		double I2 = shape2.getInertia();
		Vector2D r2 = collidePoint.sub(shape2.getMesh().getLinearPosition());
		
		double e = 1;
		
		Vector2D vr = v2.add(w2.crossProduct(r2)).sub(v1).sub(w1.crossProduct(r1));
		
		double J = -(e+1)*vr.dotProduct(n)/
				(
					1/m2 + 1/m1 + r2.crossProduct(n).crossProduct(r2).scale(1.0/I2).add(
								r1.crossProduct(n).crossProduct(r1).scale(1.0/I1)
							).dotProduct(n)
				);
//		Vector2D left = 
//		n21.scale(1.0/m1)
//		.add( r1.crossProduct(n21).crossProduct(r1).scale(1.0/I1) )
//		.sub(n12.scale(1.0/m2))
//		.sub( r2.crossProduct(n12).crossProduct(r1).scale(1.0/I2));
//		
//		Vector2D right = v2.sub(v1);
//		double J = right.magnitude()/left.magnitude();
	
		

		
	
//		double J = -2*(v1.dotProduct(n21) + w1.dotProduct(r1.crossProduct(n21)) + v2.dotProduct(n12) + w2.dotProduct(r2.crossProduct(n12)) )/
//					(1.0/m1
//							+ Math.pow(r1.crossProduct(n21).magnitude(), 2.0)/I1 
//							+ 1.0/m2 
//							+ Math.pow(r2.crossProduct(n12).magnitude(), 2.0)/I2
//					
//					);

//		double E = .9;
//		double a = 0.5*(1.0/m1
//				+ Math.pow(r1.crossProduct(n21).magnitude(), 2.0)/I1 
//				+ 1.0/m2 
//				+ Math.pow(r2.crossProduct(n12).magnitude(), 2.0)/I2
//		
//		);
//		
//		double b = v1.dotProduct(n21) + w1.dotProduct(r1.crossProduct(n21)) + v2.dotProduct(n12) + w2.dotProduct(r2.crossProduct(n12));
//		double c = (0.5*m1*v1.dotProduct(v1) + 0.5*I1*w1.dotProduct(w1)+
//				0.5*m2*v2.dotProduct(v2) + 0.5*I2*w2.dotProduct(w2))*(1-E);
//		
//		double J = (-b+Math.sqrt(Math.abs(b*b-4*a*c)))/(2*a);
		
		
		
		shape1.setLinearVelocity(
				v1.scale(m1).sub(n.scale(J)).scale(1.0/m1)
				);
		shape2.setLinearVelocity(
				v2.scale(m2).add(n.scale(J)).scale(1.0/m2)
				);
		shape1.setAngularVelocity(
			w1.scale(I1).sub(r1.crossProduct(n).scale(J)).scale(1.0/I1)
		);
		shape2.setAngularVelocity(
			w2.scale(I2).add(r2.crossProduct(n).scale(J)).scale(1.0/I2)
		);
		shape1.setMomentumHit(shape1.getMomentumHit()+Math.abs(J));
		shape2.setMomentumHit(shape2.getMomentumHit()+Math.abs(J));
		if(vr.dotProduct(n)!=0) {
			Vector2D t = vr.sub(n.scale(vr.dotProduct(n)));
			if(t.magnitude()!=0) {
				
				t = t.normalize();
						double dCoeff = 0.3;
				if(vr.dotProduct(t)!=0) {
					
					Vector2D friction = t.scale(-J*dCoeff);
				//	shape1.setLinearVelocity(shape1.getLinearVelocity().sub(friction.scale(1.0/shape1.getMass())));
				//	shape1.setAngularVelocity(shape1.getAngularVelocity().sub(r1.crossProduct(friction).scale(1.0/shape1.getInertia())));
				//	shape2.setLinearVelocity(shape2.getLinearVelocity().add(friction.scale(1.0/shape2.getMass())));
				//	shape2.setAngularVelocity(shape2.getAngularVelocity().add(r2.crossProduct(friction).scale(1.0/shape2.getInertia())));
				}
				
			}
			
			
		}
		return true;
		
	}
	
}
