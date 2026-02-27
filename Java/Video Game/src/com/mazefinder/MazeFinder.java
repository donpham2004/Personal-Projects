package com.mazefinder;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class MazeFinder extends Canvas implements Runnable {
	private static final int WIDTH = 1600;
	private static final int HEIGHT = (int)(WIDTH*9/16);
	private static final long serialVersionUID = 1L;
	private static final String TITLE= "Maze Finder";
	private static final int TICKS = 10;
	private Thread thread;
	private KeyInput input;
	private Handler handler;
	private boolean isRunning=false;
	
	private MazeFinder() {
		handler = new Handler();
		BlockObject.setHandler(handler);
		this.addMouseMotionListener(input=new KeyInput(new EventHandler()));
		this.addMouseListener(input);
		this.addKeyListener(input);
		thread = new Thread(this);
		new Window(WIDTH,HEIGHT,TITLE,this);
		init();
		start();
	}
	
	public synchronized void init() {
		for(int i=0;i<(int)(HEIGHT/Tile.getTileWidth());i++) {
			for(int j=0;j<(int)(WIDTH/Tile.getTileWidth());j++) {
				handler.addObject(new Tile(j,i));
			}
		}
		
	}
	
	public synchronized void start() {
		isRunning=true;
		thread.start();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000/TICKS;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(isRunning) {
			ns = 1000000000/TICKS;
			long now = System.nanoTime();
			delta +=(now -lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(isRunning)
				render();
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
			try {
				Thread.sleep(1);
			}catch(Exception e) {}
		}
		stop();
	}
	
	public void tick() {
		handler.tick();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0, WIDTH, HEIGHT);
		handler.render(g);
		g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,20));
		g.setColor(Color.BLACK);
		g.drawString(EventHandler.getKeyEventType().toString(), 10, 20);
		g.dispose();
		bs.show();
	}
	
	public synchronized void stop() {
		isRunning=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MazeFinder();
	}

	
}
