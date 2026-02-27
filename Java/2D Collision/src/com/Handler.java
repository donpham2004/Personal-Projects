package com;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;
public class Handler {
	private LinkedList<Polygon> polygons;
	private LinkedList<ContactPoint> contacts;
	private Box box;
	private Camera camera;
	private SimulationDisplay simDisplay;
	public Handler(Box box, Camera camera, SimulationDisplay simDisplay) {
		
		contacts = new LinkedList<>();
		this.camera = camera;
		this.box = box;
		this.simDisplay = simDisplay;
		polygons = new LinkedList<>();
	}
	
	public void addPolygon(Polygon polygon) {
		polygons.add(polygon);
	}
	public void tick(int ticks) {
		contacts.clear();
		box.setMomentumHit(0);
		for(int i=0;i<polygons.size();i++) {
			polygons.get(i).setMomentumHit(0);
		}
		box.tick(ticks);
	
		for(int i=0;i<polygons.size();i++) {
			for(int j=i+1;j<polygons.size();j++) {
				new RectPhysics().handleCollision(polygons.get(i), polygons.get(j));
			}
			new BoxPhysics(box).handleBox(polygons.get(i), ticks);
			polygons.get(i).tick(ticks);
			
		}
			
		
			

		
		
		
	}
	
	public void render(Renderer renderer, Graphics g) {
		renderer.render(box, g, camera, simDisplay);
		for(int i=0;i<polygons.size();i++) {
			
			renderer.render(polygons.get(i), g, camera,simDisplay);
		}
		g.setColor(simDisplay.getColor());
		g.drawRect(	(int)Math.round(simDisplay.getPosition().getX()), 
				(int)Math.round(simDisplay.getPosition().getY()), 
				(int)Math.round(simDisplay.getWidth()),
				(int)Math.round(simDisplay.getHeight()));
		
		for(int i=0;i<contacts.size();i++) {
			renderer.renderContact(contacts.get(i), g, camera, simDisplay);
		}
	}
	
	public void split(Vector2D normal, Vector2D point1, Vector2D point2) {
		
		SplitPolygon splitter = new SplitPolygon();
		for(int i=0;i<polygons.size();i++) {
			splitter.split(polygons.get(i), normal, point1, point2);
			
		}
		
		polygons = splitter.getPolygons();
	}
}
