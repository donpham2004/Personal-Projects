package com.map;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Map extends Canvas implements Runnable {
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;
	private int ticks=30;
	private Thread thread;
	private String title="Map";
	private boolean isRunning;
	private Handler handler;
	
	public Map() {
		handler=new Handler();
		new Window(WIDTH,HEIGHT, title, this);	
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
		new Map();
	}
}

