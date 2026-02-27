package com.threedimensionalsim;

import java.awt.Graphics;
import java.util.LinkedList;
public class Handler {
	LinkedList<RenderableObject4D> objects4D;
	
	public Handler() {
		objects4D = new LinkedList<>();
	}
	
	public void tick() {
		for(int i=0;i<objects4D.size();i++) {
			objects4D.get(i).tick();
		}
	}
	
	public void render(Renderer renderer, Graphics g) {
		renderer.render(objects4D, g);
	}
	
	public void addObject(RenderableObject4D object) {
		objects4D.add(object);
	}
	
	public void removeObject(Object4D object) {
		objects4D.remove(object);
	}
}
