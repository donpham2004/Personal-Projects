package com;

public class Player extends Polygon {
	private Vector2D acceleration;
	private Vector2D velocity;
	private double maxSpeed = 3.0;
	private boolean accelerating;
	private boolean bottomTouched = false;
	public Player() {
		accelerating = false;
		acceleration = new Vector2D(0,0);
		velocity = new Vector2D(0,0);
	}
	
	public void tick(int ticks) {
		
		velocity = velocity.add(acceleration.scale(1.0/ticks));
		if(accelerating) {
			velocity = velocity.add(acceleration.scale(1.0/ticks));
			double dot = velocity.dotProduct(new Vector2D(1,0));
			Vector2D horizontal = new Vector2D(1,0).scale(dot);
			if(Math.abs(dot)>maxSpeed) {
				Vector2D diff = horizontal.sub(horizontal.scale(Math.abs(maxSpeed/dot)));
				velocity = velocity.sub(diff);
			}
			
		}else if(velocity.magnitude()!=0.0){
			double dot = velocity.dotProduct(acceleration);
			if(dot>0) {

				velocity = new Vector2D(0,0);
				acceleration = new Vector2D(0,0);
			}
		}
		setLinearVelocity(getLinearVelocity().add(velocity));
		super.tick(ticks);
		setLinearVelocity(getLinearVelocity().sub(velocity));
		
		
		
	}

	public boolean isBottomTouched() {
		return bottomTouched;
	}

	public void setBottomTouched(boolean bottomTouched) {
		this.bottomTouched = bottomTouched;
	}

	public boolean isAccelerating() {
		return accelerating;
	}

	public void setAccelerating(boolean accelerating) {
		this.accelerating = accelerating;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	
}
