package com;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyHandler extends InputHandler implements KeyListener {
	private Player player;
	public KeyHandler(ConcurrentLinkedQueue<SimulationEvent> eventList, Player player) {
		super(eventList);
		this.player = player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
private boolean pressed = false;
	@Override
	public void keyPressed(KeyEvent e) {
		eventList.add(new SimulationEvent() {

			@Override
			public void doEvent(Simulation simulation) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_P:
					simulation.setPaused(!simulation.isPaused());
					break;
				case KeyEvent.VK_RIGHT:
					if(simulation.isPaused()) {
						simulation.setPaused(false);
						simulation.tick();
						simulation.setPaused(true);
						player.setAccelerating(true);
					}
					
					break;
				case KeyEvent.VK_D:
					if(!simulation.isPaused()) {
						player.setAcceleration(new Vector2D(10,0));
						player.setAccelerating(true);
					}
					break;
				case KeyEvent.VK_A:
					if(!simulation.isPaused()) {
						player.setAcceleration(new Vector2D(-10,0));
						player.setAccelerating(true);
					}
					break;
				
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_W:
					if(!simulation.isPaused() && !pressed) {
						player.setLinearVelocity(player.getLinearVelocity().add(new Vector2D(0,8)));
						pressed = true;
					}
				}
				
			}
			
		});
	}

	@Override
	public void keyReleased(KeyEvent e) {
		eventList.add(new SimulationEvent() {
			@Override
			public void doEvent(Simulation simulation) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_D:
				case KeyEvent.VK_A:					
					player.setAcceleration(player.getAcceleration().scale(-1.0));
					player.setAccelerating(false);
					break;
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_W:
					pressed = false;
					break;
				}
			}
		});
	}
}
