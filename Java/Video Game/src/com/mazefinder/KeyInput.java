package com.mazefinder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class KeyInput extends MouseAdapter implements MouseMotionListener, KeyListener {
	private EventHandler eventHandler;
	public KeyInput(EventHandler eventHandler) {
		this.eventHandler=eventHandler;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			eventHandler.mouseEvent(e.getX(),e.getY(),EventType.ONE);
			break;
		case MouseEvent.BUTTON2:
			eventHandler.mouseEvent(e.getX(),e.getY(),EventType.TWO);
			break;
		}	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON3:
			eventHandler.mouseEvent(e.getX(),e.getY(),EventType.THREE);
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		eventHandler.keyEvent(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
