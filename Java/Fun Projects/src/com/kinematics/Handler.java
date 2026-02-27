package com.kinematics;


import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;


public class Handler {
	private LinkedList<Shape> list = new LinkedList<>();
	public synchronized void tick() {
		for(int i=0;i<list.size();i++) {
			list.get(i).tick();
		}
	}

	public synchronized void render(Graphics g) {
		for(int i=0;i<list.size();i++) {
			list.get(i).render(g);
		}
	}

	public LinkedList<Shape> getList() {
		return list;
	}
	
	public synchronized void addShape(Shape particle) {
		list.add(particle);
	}

	public synchronized void removeShape(Shape particle) {
		list.remove(particle);
	}
}

