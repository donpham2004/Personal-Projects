package com;

import java.awt.Color;
import java.awt.Graphics;

public class Effects {
	private Vector2D p1;
	private Vector2D p2;
	private Color color;
	
	public Effects(Vector2D p1, Vector2D p2, Color color) {
		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
	}
	
	public Vector2D getP1() {
		return p1;
	}

	public void setP1(Vector2D p1) {
		this.p1 = p1;
	}

	public Vector2D getP2() {
		return p2;
	}

	public void setP2(Vector2D p2) {
		this.p2 = p2;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.drawLine((int)Math.round(p1.getX()),(int)Math.round( p1.getY()),(int)Math.round( p2.getX()),(int)Math.round( p2.getY()));
	}
}
