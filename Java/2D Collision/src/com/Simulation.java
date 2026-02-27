package com;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulation extends Canvas implements Runnable {
	private boolean isRunning;
	private int ticks;
	private Dimension dimension;
	private Window window;
	private Thread thread;
	private Polygon square;
	private Renderer renderer;
	private Handler handler;
	private Camera camera;
	private SimulationDisplay simDisplay;
	private ConcurrentLinkedQueue<SimulationEvent> eventList;
	private Player player;
	private Queue<Effects> effectList;
	private boolean paused = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public Simulation(int width, int height) {
		final double size = 1;
		
		dimension = new Dimension(width, height);
		setPreferredSize(dimension);
		ticks = 60;
		window = new Window(this);
		thread = new Thread(this);
		Box box = new Box();
		
		Mesh mesh = new Mesh();
		mesh.addVertices(new Vector2D(-10*size, -10*size));
		mesh.addVertices(new Vector2D(-10*size, 10*size));
		mesh.addVertices(new Vector2D(10*size, 10*size));
		mesh.addVertices(new Vector2D(10*size, -10*size));
		mesh.addNormals(new Vector2D(1, 0));
		mesh.addNormals(new Vector2D(0, -1));
		mesh.addNormals(new Vector2D(-1, 0));
		mesh.addNormals(new Vector2D(0, 1));
		
		mesh.setLinearPosition(new Vector2D(0,0));
		mesh.setAngularPosition(new Vector2D(0));
		
		box.setMesh(mesh);
		box.setLinearVelocity(new Vector2D(0,0));
		box.setAngularVelocity(new Vector2D(0));
		
		
		handler = new Handler(box, camera=new Camera(new Vector2D(),0,size*10), simDisplay=new SimulationDisplay(new Vector2D(0,0),0, width,height));
		player = new Player();
		mesh = new Mesh();
		player.setMesh(mesh);
		player.setColor(Color.GREEN);
		
		mesh.setLinearPosition(new Vector2D(0,-8*size));
		mesh.setAngularPosition(new Vector2D(0));
		mesh.addVertices(new Vector2D(-.5*size, -size));
		mesh.addVertices(new Vector2D(-.5*size, size));
		mesh.addVertices(new Vector2D(.5*size, size));
		mesh.addVertices(new Vector2D(.5*size, -size));
		mesh.addNormals(new Vector2D(-1, 0));
		mesh.addNormals(new Vector2D(0, 1));
		mesh.addNormals(new Vector2D(1, 0));
		mesh.addNormals(new Vector2D(0, -1));
		player.setAngularVelocity(new Vector2D(0));
		player.setLinearVelocity(new Vector2D(0,0));
		
		player.setDensity(0.3);
		player.calcInertia();
	//	handler.addPolygon(player);
		int len = 0;
		for(int i=0;i<len;i++) {
			for(int j=0;j<len;j++) {
				square = new Polygon();
				mesh = new Mesh();
				square.setMesh(mesh);
				mesh.setLinearPosition(new Vector2D((-len/2+j*2.5)*size,(-len/2+i*2.5)*size));
				mesh.setAngularPosition(new Vector2D(Math.PI/3));
				mesh.addVertices(new Vector2D(-size, -size));
				mesh.addVertices(new Vector2D(-size, size));
				mesh.addVertices(new Vector2D(size, size));
				mesh.addVertices(new Vector2D(size, -size));
				mesh.addNormals(new Vector2D(-1, 0));
				mesh.addNormals(new Vector2D(0, 1));
				mesh.addNormals(new Vector2D(1, 0));
				mesh.addNormals(new Vector2D(0, -1));
				square.setAngularVelocity(new Vector2D(0));
				square.setLinearVelocity(new Vector2D(0,0));
				
				square.setDensity(1);
				square.calcInertia();
			
				handler.addPolygon(square);
			}
			
		}
		
		square = new Polygon();
		mesh = new Mesh();
		square.setMesh(mesh);
		mesh.setLinearPosition(new Vector2D(-3*size,0));
		mesh.setAngularPosition(new Vector2D(0));
		mesh.addVertices(new Vector2D(-size, -size));
		mesh.addVertices(new Vector2D(-size, size));
		mesh.addVertices(new Vector2D(size, size));
		mesh.addVertices(new Vector2D(size, -size));
		mesh.addNormals(new Vector2D(-1, 0));
		mesh.addNormals(new Vector2D(0, 1));
		mesh.addNormals(new Vector2D(1, 0));
		mesh.addNormals(new Vector2D(0, -1));
		square.setAngularVelocity(new Vector2D(Math.PI));
		square.setLinearVelocity(new Vector2D(size,0));
		
		square.setDensity(1);
		square.calcInertia();
	
		handler.addPolygon(square);
		
		square = new Polygon();
		mesh = new Mesh();
		square.setMesh(mesh);
		mesh.setLinearPosition(new Vector2D(3*size,0));
		mesh.setAngularPosition(new Vector2D(0));
		mesh.addVertices(new Vector2D(-size, -size));
		mesh.addVertices(new Vector2D(-size, size));
		mesh.addVertices(new Vector2D(size, size));
		mesh.addVertices(new Vector2D(size, -size));
		mesh.addNormals(new Vector2D(-1, 0));
		mesh.addNormals(new Vector2D(0, 1));
		mesh.addNormals(new Vector2D(1, 0));
		mesh.addNormals(new Vector2D(0, -1));
		square.setAngularVelocity(new Vector2D(0));
		square.setLinearVelocity(new Vector2D(-size,0));
		
		square.setDensity(1);
		square.calcInertia();
	
		handler.addPolygon(square);
		
		eventList = new ConcurrentLinkedQueue<>();
		effectList = new LinkedList<>();
		renderer = new Renderer(dimension);
		this.addKeyListener(new KeyHandler(eventList,player));
		MouseHandler m = new MouseHandler(eventList);
		this.addComponentListener(new SimulationHandler(eventList));
		this.addMouseWheelListener(m);
		this.addMouseMotionListener(m);
		this.addMouseListener(m);
		this.requestFocus();
		init();
	}

	
	
	public SimulationDisplay getSimDisplay() {
		return simDisplay;
	}



	public void setSimDisplay(SimulationDisplay simDisplay) {
		this.simDisplay = simDisplay;
	}



	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public synchronized void init() {
		isRunning = true;
		thread.start();
	}

	public synchronized void exit() {
		isRunning = false;
	}
	
	public Queue<Effects> getEffectList() {
		return effectList;
	}

	public void setEffectList(Queue<Effects> effectList) {
		this.effectList = effectList;
	}

	public int getTicks() {
		return ticks;
	}



	public void setTicks(int ticks) {
		this.ticks = ticks;
	}



	@Override
	public void run() {
		long last = System.nanoTime();
		double ns = 1000000000.0 /ticks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - last) / ns;
			last = now;
			while (delta >= 1) {
				tick();

				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis()-timer>1000) {
				timer +=1000;
				System.out.println("Frames: "+frames);
				frames = 0;
			}
			try {
				Thread.sleep(1);
			} catch (Exception e) {

			}
		}
		exit();
	}

	public void tick() {
		if(!paused)
		handler.tick(ticks);

	}

	public boolean isPaused() {
		return paused;
	}



	public void setPaused(boolean paused) {
		this.paused = paused;
	}



	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		SimulationEvent event;
		while ((event = eventList.poll()) != null)
			event.doEvent(this);
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());
		handler.render(renderer, g);
		Effects effect;
		Iterator<Effects> itr= effectList.iterator();
		while(itr.hasNext()) {
			itr.next().render(g);
		}
		g.dispose();
		bs.show();
	}

	public static void main(String args[]) {
		new Simulation(1080, 720);
	}

}
