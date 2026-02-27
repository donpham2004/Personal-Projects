package com.kinematics;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Kinematic extends Canvas implements Runnable {
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;
	private int ticks=30;
	private Thread thread;
	private String title;
	private boolean isRunning;
	private Handler handler;
	
	
	public static int getWindowWidth() {
		return WIDTH;
	}
	
	public static int getWindowHeight() {
		return HEIGHT;
	}
	
	public Kinematic() {
		
		new Window(WIDTH,HEIGHT, title, this);	
		handler = new Handler();
		handler.addShape(new Square(100,100,(float) (Math.PI/4),200,420, 4f,0f,0.0f, ID.SQUARE,handler));
		handler.addShape(new Square(100,100,(float) (Math.PI/4),400,620, 0f,0f,0.0f, ID.SQUARE,handler));
		handler.addShape(new Square(100,100,(float) (Math.PI/4),300,620, 0f,0f,0.0f, ID.SQUARE,handler));
		handler.addShape(new Square(100,100,(float) (Math.PI/4),200,620, 0f,0f,0.0f, ID.SQUARE,handler));
		start();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000/ticks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			ns = 1000000000/ticks;
			long now = System.nanoTime();
			delta +=(now -lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(isRunning)
				render();
				frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
			
			try {
				Thread.sleep(1);
			}catch(Exception e) {}
		}
		stop();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		handler.render(g);
		bs.show();
		g.dispose();
	}
	
	public void tick() {
		handler.tick();
	}
	
	public synchronized void stop() {
		try {
			isRunning = false;
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		new Kinematic();
	}
}