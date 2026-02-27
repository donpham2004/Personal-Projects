package com.breakout;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class BreakOut extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = WIDTH * 9 / 16;
	public static final int TILES_COUNT=20;
	public static final int SPACE_HEIGHT=150;
	public static BreakStates state;
	private static final String title = "Atari Breakout";
	private Thread thread;
	private Handler handler;
	private BreakFactory factory;
	private BreakObject player;
	private boolean isRunning = false;
	
	public enum BreakStates {
		PLAYING(), GAMEOVER();
	}
	
	public BreakOut() {
		handler = new Handler();
		factory = new BreakFactory(handler);
		state=BreakStates.PLAYING;
		handler.addObject(factory.createBall());
		handler.addObject(player=factory.createPlayer());
		handler.addList(factory.createBlocks());
		new Window(this, title);
		this.addKeyListener(new KeyInput(player));
		start();
	}
	
	public synchronized void start() {
			isRunning=true;
			thread = new Thread(this);
			thread.start();
	}
	
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double ns = 1000000000/60;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			ns = 1000000000/60;
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
	public void tick() {
		if(state==BreakStates.PLAYING)
		handler.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		handler.render(g);
		g.dispose();
		bs.show();
	}
	
	public synchronized void stop() {
		if(isRunning) {
			isRunning = false;
			try {
				thread.join();
			}catch(InterruptedException e) {}
		}
	
	}
	
	public static void main(String args[]) {
		new BreakOut();
	}

	

	
}
