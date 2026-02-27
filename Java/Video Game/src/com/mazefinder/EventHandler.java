package com.mazefinder;

import java.awt.event.KeyEvent;

enum EventType {
	ONE ,TWO,THREE,FOUR
}

public class EventHandler {
	private static EventType keyEventType=EventType.ONE;
	
	public void mouseEvent(int xCoordinate, int yCoordinate,EventType eventType) {
		Tile tile =Tile.getTileByCoordinate(xCoordinate, yCoordinate);
		if(tile!=null) {
			switch(eventType) {
			case ONE:
				if(keyEventType==EventType.ONE) {
					tile.setBlockObject(new Wall());
				}else if(keyEventType==EventType.TWO){
					tile.setBlockObject(null);
				}
				break;
			case TWO:
				if(keyEventType==EventType.ONE) {
					tile.setBlockObject(new Goal());
				}else if(keyEventType==EventType.TWO){
					tile.setBlockObject(null);
				}
				break;
			case THREE:
				tile.setBlockObject(new Finder(xCoordinate,yCoordinate,null));
				break;
			default:
				break;
			}
			
		}
	}
	
	public static EventType getKeyEventType() {
		return keyEventType;
	}
	
	public void keyEvent(int i) {
		switch(i) {
			case KeyEvent.VK_1:
				keyEventType = EventType.ONE;
				break;
			case KeyEvent.VK_2:
				keyEventType = EventType.TWO;
				break;
			case KeyEvent.VK_3:
				keyEventType = EventType.THREE;
				Finder.setPathFound(false);
				Tile.resetFinderTile();
				break;
			case KeyEvent.VK_4:
				keyEventType = EventType.FOUR;
				Finder.setPathFound(false);
				Tile.resetTile();
				break;
			}
		
			
	}
}
