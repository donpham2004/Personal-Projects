package com;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class InputHandler {
	protected ConcurrentLinkedQueue<SimulationEvent> eventList;
	public InputHandler(ConcurrentLinkedQueue<SimulationEvent> eventList) {
		this.eventList = eventList;
	}
}
