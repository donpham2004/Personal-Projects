package com.threedimensionalsim;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CameraInputHandler implements KeyListener, MouseMotionListener, MouseWheelListener, MouseListener {
	
	private Camera camera;
	private double distance = 0.06;
	private double radians = 0.03;
	private double mouseSensitivity = 0.0004;
	private Vector4D velocityVector;
	private Matrix4D xRotation;
	private Matrix4D yRotation;
	private Matrix4D zRotation;
	private Robot robot;
	private boolean[] pressedKey = new boolean[12];
	private ConcurrentLinkedQueue<InputEvent> queue;
	
	public CameraInputHandler(Camera camera,  ConcurrentLinkedQueue<InputEvent> queue) {
		this.queue = queue;
		this.camera = camera;
		velocityVector = Matrix4DFactory.getVector();
		xRotation = Matrix4DFactory.getMatrix();
		yRotation = Matrix4DFactory.getMatrix();
		zRotation = Matrix4DFactory.getMatrix();
		try {
			robot = new Robot();
			
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void tick() {
		
		
		Vector4D upVector = Matrix4DFactory.getVector(); // 3

		upVector.setYVal(1);
		
		Vector4D zVector = camera.getXVector().copy(); // 4
		
		zVector.crossProduct(upVector);
		zVector.scale(velocityVector.getZVal());
		
		
		Vector4D xVector = camera.getXVector().copy(); //5
		
		xVector.scale(velocityVector.getXVal());
		Vector4D yVector = upVector;
		yVector.scale(velocityVector.getYVal());
		
		
		camera.getPositionVector().add(zVector);
		camera.getPositionVector().add(xVector);
		camera.getPositionVector().add(yVector);
		
		
		
		Matrix4DFactory.add(upVector);
		
		Matrix4DFactory.add(zVector);
		Matrix4DFactory.add(xVector);
	}
	
	

	
	

	public void setxRot(double xRot) {
		xRotation.setElem(1, 1, Math.cos(xRot));
		xRotation.setElem(1, 2, -Math.sin(xRot));
		xRotation.setElem(2, 1, Math.sin(xRot));
		xRotation.setElem(2, 2, Math.cos(xRot));
	}
	
	public void setyRot(double yRot) {
		yRotation.setElem(0, 0, Math.cos(yRot));
		yRotation.setElem(0, 2, Math.sin(yRot));
		yRotation.setElem(2, 0, -Math.sin(yRot));
		yRotation.setElem(2, 2, Math.cos(yRot));
	}
	
	public void setzRot(double zRot) {
		zRotation.setElem(0, 0, Math.cos(zRot));
		zRotation.setElem(0, 1, -Math.sin(zRot));
		zRotation.setElem(1, 0, Math.sin(zRot));
		zRotation.setElem(1, 1, Math.cos(zRot));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		queue.add(new InputEvent() {
			
			@Override
			public void doEvent(ThreeDimension threeDimension) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					if(pressedKey[0])
						return;
					velocityVector.setXVal(-distance);
					pressedKey[0] = true;
					break;
				case KeyEvent.VK_D:
					if(pressedKey[1])
						return;
					velocityVector.setXVal(distance);
					pressedKey[1] = true;
					break;
				case KeyEvent.VK_W:
					if(pressedKey[2])
						return;
					velocityVector.setZVal(distance);
					pressedKey[2] = true;
					break;
				case KeyEvent.VK_S:
					if(pressedKey[3])
						return;
					velocityVector.setZVal(-distance);
					pressedKey[3] = true;
					break;
				case KeyEvent.VK_SHIFT:
					if(pressedKey[4])
						return;
					velocityVector.setYVal(distance);
					pressedKey[4] = true;
					break;
				case KeyEvent.VK_SPACE:
					if(pressedKey[5])
						return;
					velocityVector.setYVal(-distance);
					pressedKey[5] = true;
					break;
				case KeyEvent.VK_LEFT:
					if(pressedKey[6])
						return;
					setyRot(-radians);
					pressedKey[6] = true;
					break;
				case KeyEvent.VK_RIGHT:
					if(pressedKey[7])
						return;
					setyRot(radians);
					pressedKey[7] = true;
					break;
				case KeyEvent.VK_UP:
					if(pressedKey[8])
						return;
					setxRot(radians);
					pressedKey[8] = true;
					break;
				case KeyEvent.VK_DOWN:
					if(pressedKey[9])
						return;
					setxRot(-radians);
					pressedKey[9] = true;
					break;
				}
				
			}
			
		});
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		queue.add(new InputEvent() {

			@Override
			public void doEvent(ThreeDimension threeDimension) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					pressedKey[0] = false;
					if (!pressedKey[0] && !pressedKey[1])
						velocityVector.setXVal(0);
					break;
				case KeyEvent.VK_D:

					pressedKey[1] = false;
					if (!pressedKey[0] && !pressedKey[1])
						velocityVector.setXVal(0);
					break;
				case KeyEvent.VK_W:
					pressedKey[2] = false;
					if (!pressedKey[2] && !pressedKey[3])
						velocityVector.setZVal(0);
					break;
				case KeyEvent.VK_S:
					pressedKey[3] = false;
					if (!pressedKey[2] && !pressedKey[3])
						velocityVector.setZVal(0);
					break;
				case KeyEvent.VK_SHIFT:
					
					pressedKey[4] = false;
					if (!pressedKey[4] && !pressedKey[5])
						velocityVector.setYVal(0);
					break;
				case KeyEvent.VK_SPACE:
					pressedKey[5] = false;
					if (!pressedKey[4] && !pressedKey[5])
						velocityVector.setYVal(0);
					break;
				case KeyEvent.VK_LEFT:
					pressedKey[6] = false;
					if(!pressedKey[6] && !pressedKey[7])
						setyRot(0);
					break;
				case KeyEvent.VK_RIGHT:
					pressedKey[7] = false;
					if(!pressedKey[6] && !pressedKey[7])
						setyRot(0);
					break;
				case KeyEvent.VK_UP:
					pressedKey[8] = false;
					if(!pressedKey[8] && !pressedKey[9])
						setxRot(0);
					break;
				case KeyEvent.VK_DOWN:
					pressedKey[9] = false;
					if(!pressedKey[8] && !pressedKey[9])
						setxRot(0);
					break;
				case KeyEvent.VK_ESCAPE:
					set = false;
					break;
				}
				
				
			}
			
		});
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!set)
			return;
		queue.add(new InputEvent() {

			@Override
			public void doEvent(ThreeDimension threeDimension) {
				if(e.getX() == ThreeDimension.WIDTH/2 &&e.getY() == ThreeDimension.HEIGHT/2) 
					return;
				moveCamera(e);
				robot.mouseMove(e.getXOnScreen()-e.getX() + ThreeDimension.WIDTH/2,
				e.getYOnScreen()-e.getY() + ThreeDimension.HEIGHT/2);
			}
			
		});
		
		
	}
	
	private volatile boolean set = false;
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!set)
			return;
		queue.add(new InputEvent() {

			@Override
			public void doEvent(ThreeDimension threeDimension) {
				if(e.getX() == ThreeDimension.WIDTH/2 &&e.getY() == ThreeDimension.HEIGHT/2) 
					return;
				moveCamera(e);
				robot.mouseMove(e.getXOnScreen()-e.getX() + ThreeDimension.WIDTH/2,
				e.getYOnScreen()-e.getY() + ThreeDimension.HEIGHT/2);
			}
			
		});
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		queue.add(new InputEvent() {
			@Override
			public void doEvent(ThreeDimension threeDimension) {
				ThreeDimension.POV+=e.getWheelRotation()*0.01;
			}
			
		});
		
	}
	
	public void moveCamera(MouseEvent e) {
		
		int dx = e.getX()-ThreeDimension.WIDTH/2;
		int dy = e.getY() - ThreeDimension.HEIGHT/2;
	
		setxRot(-dy*mouseSensitivity);
		setyRot(dx*mouseSensitivity);
		
		Matrix4D xyz = xRotation.copy(); // 1
		
		Matrix4D tmp = camera.getXYZMatrixCopy(); // 2
		xyz.multiply(tmp);
		xyz.multiply(yRotation);
		camera.setXYZ(xyz);		
		Matrix4DFactory.add(tmp);
		Matrix4DFactory.add(xyz);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		queue.add(new InputEvent() {
			@Override
			public void doEvent(ThreeDimension threeDimension) {
				robot.mouseMove(e.getXOnScreen()-e.getX() + ThreeDimension.WIDTH/2,
						e.getYOnScreen()-e.getY() + ThreeDimension.HEIGHT/2);
				set = true;
			}
		});
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
