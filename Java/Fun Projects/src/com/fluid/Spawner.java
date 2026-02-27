package com.fluid;

public class Spawner {

	private Handler handler;

	public Spawner(Handler handler) {
		this.handler = handler;
	}

	public Particle spawnParticle(int xPos, int yPos, ID id) {
		switch(id) {
		case SAND:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Sand(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case WATER:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Water(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case STONE:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Stone(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case SALT:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Salt(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
		case OIL:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Oil(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		
		case FOUNDATION:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Foundation(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case TNT:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new TNT(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case FIRE:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Fire(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case WOOD:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Wood(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case ACID:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Acid(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;
		case FISH:
			if(handler.getParticle(xPos-xPos%Particle.LENGTH, yPos-yPos%Particle.LENGTH)==null) {
				return new Fish(xPos-xPos%Particle.LENGTH,yPos-yPos%Particle.LENGTH, handler);
			}
			break;

	}return null;
}}
