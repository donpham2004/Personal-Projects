package com.breakout;

import java.util.LinkedList;
import java.util.Random;

public class BreakFactory {
	
	private Handler handler;
	
	public BreakFactory(Handler handler) {
		this.handler=handler;
	}
	
	public float randomize() {
		if(new Random().nextInt(2) > 0 ) {
			return (float)(BreakObject.SPEED*Math.sqrt(2)/2);
		}else 
			return (float)(-BreakObject.SPEED*Math.sqrt(2)/2);
	}
	
	public <E extends Number> int center(E e, int length) {
		return (int)(e.doubleValue()-length/2);
	}
	
	
	
	public BreakObject createBall() {
		return new BallObject(center(BreakOut.WIDTH/2,30),center(BreakOut.HEIGHT/2,30),randomize(),randomize(),30,30,handler);
	}
	
	public BreakObject createPlayer() {
		return new PlayerObject(center(BreakOut.WIDTH/2,200),(int)(BreakOut.HEIGHT*0.8),0,0,200,20,handler);
	}
	
	public LinkedList<BreakObject> createBlocks() {
		LinkedList<BreakObject> blocks = new LinkedList<BreakObject>();
		for(int i=0;i<5;i++) {
			for(int j=0;j<20;j++) {
				blocks.add(new BlockObject((int)(j*BreakOut.WIDTH/20),(int)(i*BreakOut.WIDTH/40)+BreakOut.SPACE_HEIGHT,0,0,(int)(BreakOut.WIDTH/20),(int)(BreakOut.WIDTH/40)-1,handler));
			}
		}
		return blocks;
	}
	
}
