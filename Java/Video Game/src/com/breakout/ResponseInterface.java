package com.breakout;

public interface ResponseInterface {
	public void response(BreakObject obj, BreakObject tempObj);
}

class PlayerResponse implements ResponseInterface {
	@Override
	public void response(BreakObject obj, BreakObject tempObj) {
		float delta = obj.getX()-tempObj.getX()+obj.getWidth()/2;
		if(obj.getVelY()>0) {
			if(delta<tempObj.getWidth()/4) {
				obj.setVelX((float) (-BreakObject.SPEED*Math.sqrt(3)/2));
				obj.setVelY(-BreakObject.SPEED/2);
			} else if (delta<tempObj.getWidth()/2) {
				obj.setVelX((float) (-BreakObject.SPEED*Math.sqrt(2)/2));
				obj.setVelY((float) (-BreakObject.SPEED*Math.sqrt(2)/2));
			} else if (delta<tempObj.getWidth()*3/4) {
				obj.setVelX((float) (BreakObject.SPEED*Math.sqrt(2)/2));
				obj.setVelY((float) (-BreakObject.SPEED*Math.sqrt(2)/2));
			} else  {
				obj.setVelX((float) (BreakObject.SPEED*Math.sqrt(3)/2));
				obj.setVelY(-BreakObject.SPEED/2);
			}
		}
		
	}
}

class BlockResponse implements ResponseInterface {

	@Override
	public void response(BreakObject obj, BreakObject tempObj) {
		
		
		((BlockObject) tempObj).setColorID(5);
	}
	
}