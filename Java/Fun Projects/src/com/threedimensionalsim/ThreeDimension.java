package com.threedimensionalsim;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JPanel;
 
public class ThreeDimension extends Canvas implements Runnable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 1080;
	public static final int HEIGHT = 720;
	public static double POV = Math.PI/2;
	public static final double ZFAR = 100;
	public static final double ZNEAR = 0.1;
	private int tickSpeed = 60;
	private boolean isRunning;
	private Thread thread;
	private Window window;
	private Camera camera;
	private CameraInputHandler cameraInput;
	private Handler handler;
	private Renderer renderer;
	private ConcurrentLinkedQueue<InputEvent> inputEvents;
	private ThreeDimension() {
		
		inputEvents = new ConcurrentLinkedQueue<>();
		
		
		setSize(WIDTH, HEIGHT);
		camera = new Camera(new Vector4D(0,0,-10),new Vector4D(1,0,0), new Vector4D(0,1,0),new Vector4D(0,0,1));
		cameraInput = new CameraInputHandler(camera, inputEvents);
		handler = new Handler();
		handler.addObject(new Teapot(0,0,0));
		renderer = new Renderer(camera);
		this.addKeyListener(cameraInput);
		this.addMouseMotionListener(cameraInput);
		this.addMouseWheelListener(cameraInput);
		this.addMouseListener(cameraInput);
		this.setSize(new Dimension(WIDTH,HEIGHT));
		
		window = new Window(this);
		
		this.requestFocus();
		init();
	}
	
	public synchronized void init() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000/tickSpeed;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int seconds = 0;
		int old = 0;
		while(isRunning) {
			ns = 1000000000/tickSpeed;
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
				System.out.println("Second: "+seconds+"\tFPS: " + frames+"\tCreated #"+(Matrix4D.counter-old)+" matrices");	
				old = Matrix4D.counter;
				frames = 0;
				seconds++;
			}
		
			try {
				Thread.sleep(1);
			}catch(Exception e) {}
		}
		stop();
		
	}
	
	public synchronized void stop() {
		try {
			isRunning = false;
			thread.join();
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tick() {
		InputEvent event=null;
		while((event = inputEvents.poll())!=null) {
			event.doEvent(this);
		}
		handler.tick();
		cameraInput.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		handler.render(renderer, g);
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		new ThreeDimension();
	}
}

