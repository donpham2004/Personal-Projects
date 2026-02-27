package com.fluid;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseMotionListener, MouseListener, Runnable {

	private Handler handler;
	private volatile boolean pressed;
	private Thread thread;
	private MouseEvent event;
	private Spawner spawn;
	private SelectMenu menu;
	private ID selectedID;
	public Input(Handler handler, Spawner spawn,SelectMenu menu) {
		thread = new Thread(this);
		this.menu=menu;
		this.handler = handler;
		this.spawn = spawn;
		thread.start();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Item item;
		if((item=menu.getItem(e.getX(), e.getY()))!=null) {
			selectedID = item.getId();
		}else {
			event = e;
			pressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		event = e;
		pressed = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void haltThread(int mili) {
		try {

			Thread.sleep(mili);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			while (pressed&&selectedID!=null) {
				Particle part1 = null;
//				Particle part2 = null;
//				Particle part3 = null;
//				Particle part4 = null;
				switch (event.getButton()) {
				case MouseEvent.BUTTON1:
					part1 = spawn.spawnParticle(event.getX(), event.getY(), selectedID);
//					part2 = spawn.spawnParticle(event.getX()-Particle.LENGTH, event.getY(), selectedID);
//					part3 = spawn.spawnParticle(event.getX()+Particle.LENGTH, event.getY(), selectedID);
//					part4 = spawn.spawnParticle(event.getX(), event.getY()+Particle.LENGTH, selectedID);
					break;
				default:
				}

				if (part1 != null)
					handler.addParticle(part1);
//				if(part2!=null)
//					handler.addParticle(part2);
//				if(part3!=null)
//					handler.addParticle(part3);
//				if(part4!=null)
//					handler.addParticle(part4);
				this.haltThread(10);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		event = e;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
