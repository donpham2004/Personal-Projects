package com.tetris;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
public class BackGroundHandler {
	private LinkedList<AbstractBackGround> list = new LinkedList<AbstractBackGround>();
	

	
	public void render(Graphics g) {
		
		for(int i=0;i<list.size();i++) {
			list.get(i).render(g);
		}
	}
	
	public void tick() {
		for(int i =0; i<list.size();i++) {
			list.get(i).tick();
		}
	}
	
	public void addObject(AbstractBackGround piece) {
		list.add(piece);
	}
	
	public void removeObject(AbstractBackGround piece) {
		list.remove(piece);
	}
	
	
}