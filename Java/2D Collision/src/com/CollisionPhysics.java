package com;

import java.util.Iterator;
import java.util.LinkedList;

public class CollisionPhysics {

	public void collision(ContactPoint contact, double e) {
		Polygon shape1 = contact.getFacePolygon();
		Polygon shape2 = contact.getVertexPolygon();
		Vector2D n = contact.getNormal();
		double m1 = shape1.getMass();
		Vector2D v1 = shape1.getLinearVelocity();
		Vector2D w1 = shape1.getAngularVelocity();
		double I1 = shape1.getInertia();
		Vector2D r1 = contact.getContactPoint().sub(shape1.getMesh().getLinearPosition());
		
		double m2 = shape2.getMass();
		Vector2D v2 = shape2.getLinearVelocity();
		Vector2D w2 = shape2.getAngularVelocity();
		double I2 = shape2.getInertia();
		Vector2D r2 = contact.getContactPoint().sub(shape2.getMesh().getLinearPosition());
		Vector2D vr = v2.add(w2.crossProduct(r2)).sub(v1).sub(w1.crossProduct(r1));
		
		double J = -(e+1)*vr.dotProduct(n)/
				(
					1/m2 + 1/m1 + r2.crossProduct(n).crossProduct(r2).scale(1.0/I2).add(
								r1.crossProduct(n).crossProduct(r1).scale(1.0/I1)
							).dotProduct(n)
				);
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
	}
	public void handleCollision(LinkedList<ContactPoint> contacts) {
		boolean collided = false;
		do {
			collided = false;
			Iterator<ContactPoint> itr = contacts.iterator();
			while(itr.hasNext()) {
				ContactPoint contact = itr.next();
				if(contact.isColliding()) {
					collision(contact,0);
					collided = true;
					
				}
			}
		}while(collided);
	}
}
