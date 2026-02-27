package com;

public class Box extends Polygon{
	
	public Box() {
		this.setNetForce(new Vector2D(0,0));
		this.setNetTorque(new Vector2D(0));
	}
	
	public void tick(int ticks) {
		
		getMesh().setLinearPosition(getMesh().getLinearPosition().add(getLinearVelocity().scale(1.0/ticks)));
		getMesh().setAngularPosition(getMesh().getAngularPosition().add(getAngularVelocity().scale(1.0/ticks)));
	}
}
