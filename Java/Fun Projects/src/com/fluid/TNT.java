package com.fluid;

import java.awt.Graphics;

public class TNT extends Particle {
	private Handler handler;
	private boolean ignited=false;
	public TNT(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, ID.TNT, handler, 6);
		this.handler = handler;
		yVel = length;
	}

	@Override
	public void tick() {
		if(ignited) {
			ignite();
			return;
		}
		if (handler.getParticle(xPos, (int) (yPos + yVel)) == null) {
			clamp(xPos, (int) (yPos + yVel));
		} else if (handler.getParticle(xPos + length, (int) (yPos + yVel)) == null) {
			clamp(xPos + length, (int) (yPos + yVel));
		} else if (handler.getParticle(xPos - length, (int) (yPos + yVel)) == null) {
			clamp(xPos - length, (int) (yPos + yVel));
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(id.getColor());
		g.fillRect(xPos, yPos, length, length);
	}

	public void setIgnited() {
		this.ignited=true;
	}
	
	public void ignite() {
		final int radius = 5;
		for (int i = -radius; i <= radius; i++) {
			for(int j=-radius;j<=radius;j++) {
				Particle temp;
				if((temp=handler.getParticle(xPos+length*i, yPos+length*j))!=null) {
					if(temp.getId()==ID.TNT) {
						if(!temp.equals(this)) {
							((TNT)temp).ignited=true;
							
						}
							
					}else {
						handler.removeParticle(temp);
					}
				}
			}
			
		}
		handler.removeParticle(this);
	}
}
