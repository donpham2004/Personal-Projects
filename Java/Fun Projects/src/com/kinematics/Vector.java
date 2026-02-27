package com.kinematics;

public class Vector {
	private float xComponent, yComponent;
	public Vector(float xComponent, float yComponent) {
		this.xComponent=xComponent;
		this.yComponent=yComponent;
	}
	
	public void scaleVector(float magnitude) {
		this.xComponent*=magnitude;
		this.yComponent*=magnitude;
	}
	
	public void addVector(Vector vector) {
		this.xComponent+=vector.xComponent;
		this.yComponent+=vector.yComponent;
	}
	
	public void subtractVector(Vector vector) {
		this.xComponent-=vector.xComponent;
		this.yComponent-=vector.yComponent;
	}	
	
	public void setXComponent(float xComponent) {
		this.xComponent=xComponent;
	}
	
	public void setYComponent(float yComponent) {
		this.yComponent=yComponent;
	}
	
	public float getXComponent() {
		return this.xComponent;
	}
	
	public float getYComponent() {
		return this.yComponent;
	}
}
