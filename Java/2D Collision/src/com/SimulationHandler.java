package com;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimulationHandler extends InputHandler implements ComponentListener{
	
	
	public SimulationHandler(ConcurrentLinkedQueue<SimulationEvent> eventList) {
		super(eventList);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		eventList.add(new SimulationEvent() {

			@Override
			public void doEvent(Simulation simulation) {
				simulation.getDimension().setSize(e.getComponent().getWidth(),e.getComponent().getHeight());
				
			}
			
		});
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
