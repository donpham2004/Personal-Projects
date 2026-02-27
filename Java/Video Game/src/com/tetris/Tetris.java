package com.tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Tetris extends Canvas implements Runnable{
	public static final int WIDTH= 1600;
	public static final int HEIGHT =(int)(WIDTH*9/16);
	public static final int GRID_HEIGHT = HEIGHT-30;
	public static final int BLOCK_LENGTH = (int)(GRID_HEIGHT/20);
	public static int widthCenter = (int)(Tetris.WIDTH/2);
	private static final long serialVersionUID = 7976670280176368440L;
	private static final String TITLE = "Tetris";
	private Thread thread;
	private AbstractBackGround background;
	private BackGroundHandler backgroundHandler;
	private PieceSpawner spawner;
	private Handler handler;
	private boolean isRunning = false;
	private static int TICKS = 60;
	
	 
	
	private Tetris() {
		
		spawner = new PieceSpawner();
		background = new GameBackGround(spawner);
		background.update();
		spawner.setBackground(background);
		handler = new Handler(spawner,background);
		backgroundHandler = new BackGroundHandler();
		TetrisPiece.setHandler(handler);
		TetrisPiece.setSpawner(spawner);
		
		handler.addObject(spawner.createPiece());
		backgroundHandler.addObject(background);
		backgroundHandler.addObject(new RowEffect());
		this.addKeyListener(new KeyInput(handler));
		new Window(TITLE, WIDTH, HEIGHT, this);
		start();
		
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000/TICKS;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
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
	int counter=0;
	public void tick() {
		if(counter<4) {
			counter++;
			return;
		}
		
		backgroundHandler.tick();
		handler.tick();
		counter=0;
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		handler.render(g);
		backgroundHandler.render(g);
		g.dispose();
		bs.show();
	}

	public static <E extends Number, A extends Number> int center(E e, A a) {
		return (int)(e.doubleValue()-(a.doubleValue()/2));
	}
	
	public synchronized void stop() {
		if(isRunning)  isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Tetris();
		
	}
}
