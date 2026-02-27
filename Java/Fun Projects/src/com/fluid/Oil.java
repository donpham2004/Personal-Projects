package com.fluid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Oil extends Particle {
	private Handler handler;
	private static LinkedList<Oil> compressedWater = new LinkedList<Oil>();
	public Oil(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, ID.OIL, handler,2);
		this.handler = handler;
		yVel = length;
		dir = randomDir();
	}

	private int dir;

	@Override
	public void tick() {
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null) {
			clamp(xPos, (int)(yPos+yVel));
//			if((particle=handler.getParticle(xPos, (int)(yPos-yVel)))!=null&&
//					particle.density>density&&particle.id!=ID.SALT) {
//				handler.removeParticle(this);
//				compressedWater.add(this);
//				compression++;
//			}
		}else if(handler.getParticle(xPos+length, (int)(yPos+yVel))==null&&clamp(xPos+length, (int)(yPos+yVel))) {
		}else if(handler.getParticle(xPos-length, (int)(yPos+yVel))==null&&clamp(xPos-length, (int)(yPos+yVel))) {
		}else if(handler.getParticle(xPos+length*dir, yPos)==null&&clamp(xPos+length*dir, yPos)) {
//		}else if((particle=handler.getParticle(xPos, (int)(yPos-yVel)))!=null&&
//				particle.density>density&&particle.id!=ID.SALT) {
//			handler.removeParticle(this);
//			compressedWater.add(this);
//			compression++;
		}else {
			dir=-dir;
		}
	}
	
	public void compressWater() {
		int arr[][] = {{-1,-1},{0,-1},{1,-1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
		for(int i=0;i<arr.length;i++) {
			if(handler.getParticle(xPos+length*arr[i][0], yPos+length*arr[i][1])==null) {
				Particle particle;
				if((particle=handler.getParticle(xPos+length*arr[i][0], yPos+length*arr[i][1]-length))!=null) {
					if(particle.density>density)
						continue;
				}
				Oil water = compressedWater.remove();
				water.xPos=xPos+length*arr[i][0];
				water.yPos=yPos+length*arr[i][1];
				if(clamp(water.xPos,water.yPos)) {
					handler.addParticle(water);
				}else {
					compressedWater.add(water);
				}
				break;
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
