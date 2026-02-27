package com.breakout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	private BreakObject player;
	public KeyInput(BreakObject player) {
		this.player=player;
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			((PlayerObject)player).setVelL((float)(BreakObject.SPEED*1.5));
			break;
		case KeyEvent.VK_D:
			((PlayerObject)player).setVelR((float) (BreakObject.SPEED*1.5));
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			((PlayerObject)player).setVelL(0);
			break;
		case KeyEvent.VK_D:
			((PlayerObject)player).setVelR(0);
		}
	}
}
