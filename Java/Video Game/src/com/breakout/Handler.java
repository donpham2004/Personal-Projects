package com.breakout;

import java.awt.Graphics;
import java.util.LinkedList;

import com.breakout.BreakOut.BreakStates;

public class Handler {
	LinkedList<BreakObject> obj= new LinkedList<BreakObject>();
	LinkedList<BreakObject> block= new LinkedList<BreakObject>();
	public void tick() {
		if(BreakOut.state==BreakStates.PLAYING) {
			for(BreakObject tempObj:obj) {
				tempObj.tick();
			}
			for(BreakObject b:block) {
				removeObject(b);
			}
			block.clear();
		}
		for(BreakObject tempObj:obj) {
			if(tempObj instanceof BlockObject) {
				return;
			}
		}
		BreakOut.state=BreakStates.GAMEOVER;
	}
	
	public void render(Graphics g) {
		for(BreakObject tempObj:obj) {
			tempObj.render(g);
			
		}
	}
	
	public void addList(LinkedList<BreakObject> list) {
		this.obj.addAll(list);
	}
	
	public void addObject(BreakObject obj) {
		this.obj.add(obj);
	}
	
	public void removeObject(BreakObject obj) {
		this.obj.remove(obj);
	}
}
