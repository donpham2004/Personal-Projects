package com;

import java.util.Comparator;
import java.util.LinkedList;

public class BoxPhysics {
	private Box box;
	private final double EPSILON = 0.0000000001;

	public BoxPhysics(Box box) {
		this.box = box;
	}

	public boolean handleBox(Polygon shape, int ticks) {
		LinkedList<Vector2D> shapeVertices = new LinkedList<>();
		LinkedList<Vector2D> shapeNormals = new LinkedList<>();
		LinkedList<Vector2D> boxVertices = new LinkedList<>();
		LinkedList<Vector2D> boxNormals = new LinkedList<>();
		for (Vector2D vertex : shape.getMesh().getVertices()) {
			shapeVertices.add(vertex.multiply(Matrix2D.rotation(shape.getMesh().getAngularPosition().getW()))
					.add(shape.getMesh().getLinearPosition()));
		}
		for (Vector2D vertex : box.getMesh().getVertices()) {
			boxVertices.add(
					vertex.multiply(Matrix2D.rotation(box.getMesh().getAngularPosition().getW())).add(box.getMesh().getLinearPosition()));
		}
		for (Vector2D normal : shape.getMesh().getNormals()) {
			shapeNormals.add(normal.multiply(Matrix2D.rotation(shape.getMesh().getAngularPosition().getW())));
		}
		for (Vector2D normal : box.getMesh().getNormals()) {
			boxNormals.add(normal.multiply(Matrix2D.rotation(box.getMesh().getAngularPosition().getW())));
		}

		Vector2D minNormal = null;
		Vector2D minVertex = null;
		double overlap = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < boxNormals.size(); i++) {
			double offset = boxVertices.get(i).dotProduct(boxNormals.get(i));
			double min = Double.POSITIVE_INFINITY;
			for (int j = 0; j < shapeVertices.size(); j++) {
				double dot = shapeVertices.get(j).dotProduct(boxNormals.get(i)) - offset;
				if (dot < min) {
					min = dot;
				}
			}

			if (min < 0) {
				overlap = min;
				minNormal = boxNormals.get(i);
				minVertex = boxVertices.get(i);
				shape.getMesh().setLinearPosition(shape.getMesh().getLinearPosition().sub(boxNormals.get(i).scale(min)));
			}
		}
		
		if (minNormal == null || minVertex == null)
			return false;
		LinkedList<Vector2D> contactPoints = new LinkedList<Vector2D>();
		for (int i = 0; i < shapeVertices.size(); i++) {
			double offset = minVertex.dotProduct(minNormal);
			double dot = shapeVertices.get(i).dotProduct(minNormal) - offset;
			if (dot == overlap || Math.abs(dot - overlap) < EPSILON) {
				contactPoints.add(shapeVertices.get(i));
			}
		}
		Vector2D parallel = new Vector2D(-minNormal.getY(), minNormal.getX());
		contactPoints.sort(new Comparator<Vector2D>() {

			@Override
			public int compare(Vector2D o1, Vector2D o2) {
				// TODO Auto-generated method stub
				return (o1.dotProduct(parallel) < o2.dotProduct(parallel)) ? 1 : -1;
			}

		});
		Vector2D collidePoint = null;
		if(contactPoints.size()%2==1) {
			collidePoint = contactPoints.get(contactPoints.size()/2);
		}
		else if(contactPoints.size()%2==0) {
			collidePoint = contactPoints.get(contactPoints.size()/2-1).add(contactPoints.get(contactPoints.size()/2)).scale(0.5);
		}

		Vector2D n21 = minNormal.scale(-1.0);
		Vector2D n12 = minNormal;
		Vector2D n = n12;
		Vector2D v1 = box.getLinearVelocity();
		Vector2D w1 = box.getAngularVelocity();
		Vector2D r1 = collidePoint.sub(box.getMesh().getLinearPosition());
		
		
		double m2 = shape.getMass();
		Vector2D v2 = shape.getLinearVelocity();
		Vector2D w2 = shape.getAngularVelocity();
		double I2 = shape.getInertia();
		Vector2D r2 = collidePoint.sub(shape.getMesh().getLinearPosition());
		
		
		double e = 0;
		Vector2D vr = v2.add(w2.crossProduct(r2)).sub(v1).sub(w1.crossProduct(r1));
	
		double J = -(e+1)*vr.dotProduct(n)/
				(
				1/m2 + (r2.crossProduct(n).crossProduct(r2).scale(1.0/I2).dotProduct(n))
				);
		
		
		/**
		 * First Inelastic
		  	Vector2D left = n12.scale(1.0 / m2).add(r2.crossProduct(n12).crossProduct(r1).scale(1.0 / I2));
		  	Vector2D right = v2.sub(v1);
		  	double J = right.magnitude()/left.magnitude();
		 */
		/**
		 * Second Inelastic
		  		double vf = v1.dotProduct(n21);
				double J = m2*v2.dotProduct(n21)-m2*vf;

				
		 * 
		 */
		/**
		 *First Elastic
		 double J = -2*(v1.dotProduct(n21)+v2.dotProduct(n12) + w1.dotProduct(r1.crossProduct(n21))+w2.dotProduct(r2.crossProduct(n12)) )/
					( 
							1.0/m2 
							+ Math.pow(r2.crossProduct(n12).magnitude(), 2.0)/I2
					
					);
		 */
		/**
				Second Elastic
				double E = 0.9;
				double a = 0.5*(1.0/m2 
				+ Math.pow(r2.crossProduct(n12).magnitude(), 2.0)/I2);
		
				double b = v1.dotProduct(n21)+v2.dotProduct(n12) + w1.dotProduct(r1.crossProduct(n21))+w2.dotProduct(r2.crossProduct(n12));
				double c = (0.5*m2*v2.dotProduct(v2) + 0.5*I2*w2.dotProduct(w2))*(1-E);
		
				double J = (-b+Math.sqrt(Math.abs(b*b-4*a*c)))/(2*a);
	
		 */
		shape.setLinearVelocity(v2.add(n.scale(J/m2)));
		shape.setAngularVelocity(w2.add(r2.crossProduct(n).scale(J/I2)));
		shape.setMomentumHit(shape.getMomentumHit()+Math.abs(J));
		if(vr.dotProduct(n)!=0) {
			Vector2D t = vr.sub(n.scale(vr.dotProduct(n)));
			if(t.magnitude()!=0) {
				
				t = t.normalize();
				double dCoeff = 0.3;
					
					Vector2D friction = t.scale(-J*dCoeff);
					if(t.dotProduct(vr)+friction.dotProduct(t)<0) {
						friction = t.scale(t.dotProduct(vr));
					}
					//shape.setLinearVelocity(shape.getLinearVelocity().add(friction.scale(1.0/shape.getMass())));
				//	shape.setAngularVelocity(shape.getAngularVelocity().add(r2.crossProduct(friction).scale(1.0/shape.getInertia())));
				
				
			}
			
			
		}
		
		
//		Vector2D normalForce = n.scale(-shape.getNetForce().dotProduct(n));
//		Vector2D t = shape.getNetForce().sub(n.scale(shape.getNetForce().dotProduct(n)));
//		if(t.magnitude()!=0) {
//			t = t.normalize();
//			
//			double dCoeff = .8;
//			Vector2D friction = t.scale(-normalForce.magnitude());
//			if(friction.magnitude()> t.scale(shape.getNetForce().dotProduct(t)).magnitude() ) {
//				friction = t.scale(-shape.getNetForce().dotProduct(t));
//			}
//			shape.setNetForce(shape.getNetForce().add(friction));
//		}
//		
//		double normalForceMagnitude = shape.getNetForce().scale(-1.0).dotProduct(n12);
//		if(normalForceMagnitude>0) {
//			Vector2D force = shape.getNetForce().add(n12.scale(normalForceMagnitude));
//			//shape.setNetForce(force);
//		}
//		Vector2D force = n12.scale(J).scale(ticks);
//		shape.setNetForce(force);
//		shape.setNetTorque(r2.crossProduct(force));
 		return true;
	}
}
