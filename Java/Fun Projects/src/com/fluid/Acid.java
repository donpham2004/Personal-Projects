package com.fluid;


import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Acid extends Particle {
	private Handler handler;
	private static LinkedList<Acid> compressedWater = new LinkedList<Acid>();
	public Acid(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, ID.ACID, handler,4);
		this.handler = handler;
		yVel = length;
		dir = randomDir();
	}
	private int dir;
	@Override
	public void tick() {
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null) {
			clamp(xPos, (int)(yPos+yVel));
		}else if(handler.getParticle(xPos+length, (int)(yPos+yVel))==null&&clamp(xPos+length, (int)(yPos+yVel))) {
		}else if(handler.getParticle(xPos-length, (int)(yPos+yVel))==null&&clamp(xPos-length, (int)(yPos+yVel))) {
		}else if(handler.getParticle(xPos+length*dir, yPos)==null&&clamp(xPos+length*dir, yPos)) {
		}else {
			dir=-dir;
		}
		corrode();
	}
	
	public void corrode() {
		int arr[][] = {{-1,-1},{0,-1},{1,-1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
		for(int i=0;i<arr.length;i++) {
			Particle temp;
			if((temp=handler.getParticle(xPos+length*arr[i][0], yPos+length*arr[i][1]))!=null) {
				if(temp.getId()!=ID.FOUNDATION&&temp.getId()!=ID.ACID)
					handler.removeParticle(temp);
			}
		}
	}
	
	public int randomDir() {
		return new Random().nextInt(2) == 1 ? 1 : -1;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(id.getColor());
		g.fillRect(xPos, yPos, length, length);
	}
}

