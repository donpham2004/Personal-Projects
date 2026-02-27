package com.mazefinder;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler implements RenderTickable{
	private LinkedList<RenderTickable> objectList = new LinkedList<RenderTickable>();
	@Override
	public void render(Graphics g) {
		for(int i=0;i<objectList.size();i++) {	
			objectList.get(i).render(g);
		}
	}

	@Override
	public void tick() {
		for(int i=0;i<objectList.size();i++) {
			objectList.get(i).tick();
		}
	}
	
	public void addObject(RenderTickable object) {
		objectList.add(object);
	}
	
	public void removeObject(RenderTickable object) {
		objectList.remove(object);
	}
	
}
