package com.fluid;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;


public class Handler {
	private LinkedList<Particle> list = new LinkedList<>();
	private HashMap<Integer,HashMap<Integer,Particle>> map = new HashMap<>();
	private static final int MAX_SIZE = 2000;
	public static int counter = 0;
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

	public synchronized void addParticle(Particle particle) {
		HashMap<Integer,Particle> row;
		if((row=map.get(particle.getXPos()))!=null) {
		}else {
			row = new HashMap<Integer,Particle>();
			map.put(particle.getXPos(), row);
		}
		row.put(particle.getYPos(), particle);
		list.add(particle);
		counter++;
	}

	public synchronized void removeParticle(Particle particle) {
		list.remove(particle);
		(map.get(particle.getXPos())).remove(particle.getYPos());
		counter--;
	}

	public synchronized void removeAll(ID id) {
		if(id==null) {
			counter=0;
			list.clear();
			map.clear();
		}else {
			LinkedList<Particle> removeList = new LinkedList<Particle>();
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).id.equals(id)) {
					removeList.add(list.get(i));
				}
			}
			while(removeList.size()>0) {
				removeParticle(removeList.poll());
			}
		}
	}
	
	public synchronized void updateMap(Particle particle, int oldx, int oldY) {
		(map.get(oldx)).remove(oldY);
		HashMap<Integer,Particle> row;
		if((row=map.get(particle.getXPos()))!=null) {
		}else {
			row = new HashMap<Integer,Particle>();
			map.put(particle.getXPos(), row);
		}
		row.put(particle.getYPos(), particle);

	}
	
	public synchronized Particle getParticle(int xPos, int yPos) {
		HashMap<Integer,Particle> row;
		if((row=map.get(xPos))!=null)
			return row.get(yPos);
		return null;
	}
}
