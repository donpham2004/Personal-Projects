package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

public class Renderer {
	private Dimension dimension;
	public Renderer(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public void render(Polygon polygon, Graphics g, Camera camera, SimulationDisplay simDisplay) {		
		g.setColor(polygon.getColor());
		LinkedList<Vector2D> vertices= polygon.getMesh().getVertices();
		Matrix2D matrix = polygon.getInvPolygonMatrix()
							.multiply(camera.getInvCameraMatrix())
							
							.multiply(simDisplay.getInvDisplayMatrix());
		
		for(int i=0;i<vertices.size();i++) {
			Vector2D vertex1 = vertices.get(i).multiply(matrix);
			Vector2D vertex2 = vertices.get((i+1)%vertices.size()).multiply(matrix);
			if(simDisplay.clip(vertex1, vertex2))
			g.drawLine((int)Math.round(vertex1.getX()),
					(int)Math.round(vertex1.getY()),
					(int)Math.round(vertex2.getX()),
					(int)Math.round(vertex2.getY()));
			
		}
	}
	
	public void renderContact(ContactPoint contact,Graphics g, Camera camera, SimulationDisplay simDisplay) {
		Matrix2D matrix =camera.getInvCameraMatrix().multiply(simDisplay.getInvDisplayMatrix());
		Vector2D p1 = contact.getContactPoint().add(contact.getNormal().scale(0.5)).multiply(matrix);
		Vector2D p2 = contact.getContactPoint().sub(contact.getNormal().scale(0.5)).multiply(matrix);
		g.setColor(Color.RED);
		g.drawLine((int)Math.round(p1.getX()),
				(int)Math.round(p1.getY()),
				(int)Math.round(p2.getX()),
				(int)Math.round(p2.getY()));
	}
}
