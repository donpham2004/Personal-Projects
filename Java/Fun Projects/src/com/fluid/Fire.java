package com.fluid;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Fire extends Particle {
	private Handler handler;
	private int life = 24;
	private static LinkedList<Fire> compressedWater = new LinkedList<Fire>();
	public Fire(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, ID.FIRE, handler,4);
		this.handler = handler;
		yVel = length;
		dir = randomDir();
	}

	private int dir;

	@Override
	public void tick() {
		
		if((--life)<0) {
			handler.removeParticle(this);
			return;
		}
		burn();
		if(handler.getParticle(xPos, (int)(yPos+yVel))==null) {
			clamp(xPos, (int)(yPos+yVel));
		}else if(handler.getParticle(xPos+length, (int)(yPos+yVel))==null&&clamp(xPos+length, (int)(yPos+yVel))) {
		}else if(handler.getParticle(xPos-length, (int)(yPos+yVel))==null&&clamp(xPos-length, (int)(yPos+yVel))) {
		}else if(handler.getParticle(xPos+length*dir, yPos)==null&&clamp(xPos+length*dir, yPos)) {
		}else {
			dir=-dir;
		}
		
	}
	
	public void burn() {
		int arr[][] = {{-1,-1},{0,-1},{1,-1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
		for(int i=0;i<arr.length;i++) {
			Particle temp;
			if((temp=handler.getParticle(xPos+length*arr[i][0], yPos+length*arr[i][1]))!=null) {
				if(temp.getId()==ID.OIL) {
					int xPos = temp.getXPos();
					int yPos = temp.getYPos();
					handler.removeParticle(temp);
					handler.addParticle(new Fire(xPos,yPos,handler));
				}else if(temp.getId()==ID.WATER) {
					handler.removeParticle(this);
					return;
				}else if(temp.getId()==ID.WOOD) {
						int xPos = temp.getXPos();
						int yPos = temp.getYPos();
						handler.removeParticle(temp);
						handler.addParticle(new Fire(xPos,yPos,handler));
				}else if(temp.getId()==ID.TNT) {
					((TNT)temp).setIgnited();
				}else if(temp.getId()==ID.FISH) {
					handler.removeParticle(temp);
				}
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
