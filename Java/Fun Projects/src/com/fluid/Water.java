package com.fluid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Water extends Particle {
	private static Color color = Color.BLUE;
	private Handler handler;
	private static LinkedList<Water> compressedWater = new LinkedList<Water>();
	private int compression = 0;
	public Water(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, ID.WATER, handler,4);
		this.handler = handler;
		yVel = length;
		dir = randomDir();
	}

	private int dir;

	@Override
	public void tick() {
		
//		if(compressedWater.size()>0&&compression>0) {
//			compressWater();
//			return;
//		}
		
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
	
//	public void compressWater() {
//		int arr[][] = {{-1,-1},{0,-1},{1,-1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
//		Particle bestParticle=null;
//		for(int i=0;i<arr.length;i++) {
//			Particle temp;
//			if((temp=handler.getParticle(xPos+length*arr[i][0], yPos+length*arr[i][1]))!=null) {
//				if(temp.getId()==ID.WATER) {
//					if(((Water)temp).compression<compression) {
//						bestParticle = temp;
//					}
//				}
//			}else {
//				Water water = compressedWater.remove();
//				water.clamp(xPos+length*arr[i][0], yPos+length*arr[i][1]);
//				handler.addParticle(water);
//				compression--;
//				return;
//				
//			}
//		}
//		if(bestParticle!=null) {
//			compression--;
//			((Water)bestParticle).compression++;
//		}
//			
//	}
	
	public int randomDir() {
		return new Random().nextInt(2) == 1 ? 1 : -1;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(xPos, yPos, length, length);
	}
}
