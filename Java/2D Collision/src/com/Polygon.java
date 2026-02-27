package com;
import java.awt.Color;
import java.util.LinkedList;
public class Polygon {
	private Mesh mesh;
	private Vector2D angularVelocity;
	private Vector2D linearVelocity;
	private Vector2D netForce;
	private Vector2D netTorque;
	private double density=1;
	private double mass;
	private double inertia;
	private Color color;
	public double momentumHit;
	private double slowFactor = 0.9;

	public Polygon() {
		this.setColor(Color.BLACK);
		this.netForce = new Vector2D();
		this.netTorque = new Vector2D();
	}
	
	public void tick(double ticks) {
		linearVelocity = linearVelocity.add(netForce.scale(mass/ticks));
		angularVelocity = angularVelocity.add(netTorque.scale(inertia/ticks));
		mesh.setLinearPosition(mesh.getLinearPosition().add(linearVelocity.scale(1.0/ticks)));
		mesh.setAngularPosition(mesh.getAngularPosition().add(angularVelocity.scale(1.0/ticks)));
		mesh.getAngularPosition().setW(mesh.getAngularPosition().getW()%(2*Math.PI));
		this.netForce = new Vector2D();
		this.netTorque  = new Vector2D();
	}
	
	public double getMomentumHit() {
		return momentumHit;
	}

	public void setMomentumHit(double momentumHit) {
		this.momentumHit = momentumHit;
	}

	public Vector2D getNetTorque() {
		return netTorque;
	}

	public void setNetTorque(Vector2D netTorque) {
		this.netTorque = netTorque;
	}

	public Vector2D getNetForce() {
		return netForce;
	}

	public void setNetForce(Vector2D netForce) {
		this.netForce = netForce;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public Vector2D getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(Vector2D orientation) {
		this.angularVelocity = orientation;
	}
	
	
	public double getMass() {
		return mass;
	}

	private void setMass(double mass) {
		this.mass = mass;
	}
	
	public double getDensity() {
		return density;
	}
	
	public void setDensity(double density) {
		this.density = density;
		setMass(density*getArea());
	}
	
	public double getInertia() {
		return inertia;
	}

	public void setInertia(double inertia) {
		this.inertia = inertia;
	}

	public Vector2D getLinearVelocity() {
		return linearVelocity;
	}

	public void setLinearVelocity(Vector2D linearVelocity) {
		this.linearVelocity = linearVelocity;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public double getArea() {
		double area = 0;
		LinkedList<Vector2D> vertices = mesh.getVertices();
		for(int i=1;i<vertices.size()-1;i++) {
			Vector2D v1 = vertices.get(i).sub(vertices.get(0));
			Vector2D v2 = vertices.get(i+1).sub(vertices.get(0));
			area += v1.crossProduct(v2).magnitude()/2;
		}
		return area;
	}
	
	public void calcInertia() {
		double inertia = 0;
		
		LinkedList<Vector2D> vertices = mesh.getVertices();
		for(int i=0;i<vertices.size();i++) {
			Vector2D v1 = vertices.get(i);
			Vector2D v2 = vertices.get((i+1)%vertices.size());
			Vector2D r = v1.add(v2).scale(1.0/3.0);
			double b = v1.magnitude();
			double h = Math.abs(new Vector2D(-v1.getY(),v1.getX()).normalize().dotProduct(v2));
			inertia += density*b*h*((b*b+h*h)/36.0 + r.dotProduct(r)/2.0);
		}
		setInertia(inertia);
		
	}
	
	public Matrix2D getInvPolygonMatrix() {
		return new Matrix2D().rotate(mesh.getAngularPosition()).translate(mesh.getLinearPosition());
	}
	
}
