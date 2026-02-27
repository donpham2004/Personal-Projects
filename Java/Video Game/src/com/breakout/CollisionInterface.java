package com.breakout;

enum Direction {
	X(),Y();
}

public abstract class CollisionInterface {
	public abstract void collideX(Handler handler,BreakObject object);
	public abstract void collideY(Handler handler,BreakObject object);
	
	public void reflect(BreakObject obj, BreakObject tempObj, Direction direction) {
		switch(direction) {
		case X:
			if(tempObj instanceof PlayerObject) break;
			do {
				obj.setX((int) (obj.getX()-Math.signum(obj.getVelX())));
			}while(tempObj.getBounds().intersects(obj.getBounds()));
			obj.setVelX(-obj.getVelX());
			break;
		case Y:
			if(tempObj instanceof PlayerObject) {
				do {
					obj.setY((int) (obj.getY()-1));
					}while(tempObj.getBounds().intersects(obj.getBounds()));
			}else {
				do {
					obj.setY((int) (obj.getY()-Math.signum(obj.getVelY())));
					}while(tempObj.getBounds().intersects(obj.getBounds()));
					obj.setVelY(-obj.getVelY());
					break;
			}
		
		}
		tempObj.response.response(obj,tempObj);
	}
}

class BallCollision extends CollisionInterface {
	@Override
	public void collideX(Handler handler,BreakObject obj) {
		for(BreakObject tempObj:handler.obj) {
			if(tempObj instanceof BallObject) continue;
			if(tempObj.getBounds().intersects(obj.getBounds())) {
				reflect(obj,tempObj,Direction.X);
			}
		}
	}
	public void collideY(Handler handler,BreakObject obj) {
		for(BreakObject tempObj:handler.obj) {
			if(tempObj instanceof BallObject) continue;
			if(tempObj.getBounds().intersects(obj.getBounds())) {
				reflect(obj,tempObj,Direction.Y);
			}
		}
	}
}
