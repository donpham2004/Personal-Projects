package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MouseHandler extends InputHandler implements MouseWheelListener, MouseMotionListener, MouseListener {

	public MouseHandler(ConcurrentLinkedQueue<SimulationEvent> eventList) {
		super(eventList);
	}
	private Effects effect;
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		eventList.add(new SimulationEvent() {

			@Override
			public void doEvent(Simulation simulation) {
				Camera camera = simulation.getCamera();
				if(e.getPreciseWheelRotation()>0)
					camera.setScale(simulation.getCamera().getScale()*1.1);
				else
					camera.setScale(simulation.getCamera().getScale()*0.9);
			}
			
		});
	}
	private Vector2D oldCamera;
	private int firstDragX, firstDragY;
	private int secondDragX, secondDragY;
	private boolean leftClicked = false;
	private boolean rightClicked = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			eventList.add(new SimulationEvent() {

				@Override
				public void doEvent(Simulation simulation) {
					if(!leftClicked)
						return;
					Camera camera = simulation.getCamera();
					SimulationDisplay simDisplay = simulation.getSimDisplay();
					Vector2D newCamera = new Vector2D(e.getX(),e.getY())
							.multiply(simDisplay.getDisplayMatrix())
							.multiply(camera.getCameraMatrix())
							.sub(camera.getPosition());
							
					camera.setPosition(camera.getPosition().sub(newCamera.sub(oldCamera)));
					oldCamera = newCamera;
				}
				
			});
			break;
		case MouseEvent.BUTTON3:
			eventList.add(new SimulationEvent() {

				@Override
				public void doEvent(Simulation simulation) {
					if(!rightClicked)
						return;
					secondDragX = e.getX();
					secondDragY = e.getY();
					effect.getP2().setX(secondDragX);
					effect.getP2().setY(secondDragY);
				}
				
				
			});
			
		}
			
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		eventList.add(new SimulationEvent() {

			@Override
			public void doEvent(Simulation simulation) {
				switch(e.getButton()) {
				case MouseEvent.BUTTON1:
					leftClicked = true;
					Camera camera = simulation.getCamera();
					SimulationDisplay simDisplay = simulation.getSimDisplay();
					oldCamera = new Vector2D(e.getX(),e.getY())
							.multiply(simDisplay.getDisplayMatrix())
							.multiply(camera.getCameraMatrix())
							.sub(camera.getPosition());
					break;
				case MouseEvent.BUTTON3:
					rightClicked = true;
					firstDragX = e.getX();
					firstDragY = e.getY();
					secondDragX = e.getX();
					secondDragY = e.getY();
					simulation.getEffectList().add(effect=new Effects(
							new Vector2D(firstDragX,firstDragY),
							new Vector2D(secondDragX,secondDragY),
							Color.RED
							));
				}
				
			}
			
		});
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		eventList.add(new SimulationEvent() {

			@Override
			public void doEvent(Simulation simulation) {
				switch(e.getButton()) {
				case MouseEvent.BUTTON1:
					leftClicked = false;
					break;
				case MouseEvent.BUTTON3:
					rightClicked = false;
					simulation.getEffectList().remove(effect);
					Camera camera = simulation.getCamera();
					SimulationDisplay simDisplay = simulation.getSimDisplay();
					
					Vector2D point1 = effect.getP1()
							.multiply(simDisplay.getDisplayMatrix())
							.multiply(camera.getCameraMatrix());
						
					Vector2D point2 = effect.getP2()
							.multiply(simDisplay.getDisplayMatrix())
							.multiply(camera.getCameraMatrix());
					Vector2D len = point2.sub(point1);
					
					Vector2D normal = new Vector2D(-len.getY(),len.getX());
					if(normal.magnitude()!=0) {
						simulation.getHandler().split(normal.normalize(), point1, point2);
					}
					
					
					
				}
				
				
			}
			
		});
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
