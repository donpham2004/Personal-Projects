package com.chess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Chess extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private Board board;
	private Handler handler;
	public static final int WIDTH = 1760;
	public static final int HEIGHT = (int) (WIDTH * 9 / 16);
	private static BufferedImage image;
	private SelectPiece selectPiece;
	private TakenPieces takenPieces;
	public static String state;
	public static String winningTeam;
	public static Color stateColor;
	public static String clickToRestart;
	public static final int TILE_WIDTH = 120;
	public static int multiplier;
	public static final VectorCoordinate FIRST_START_VECTOR = new VectorCoordinate((int) (WIDTH / 2 - 5 * TILE_WIDTH),
			-TILE_WIDTH);
	public static final VectorCoordinate SECOND_START_VECTOR = new VectorCoordinate((int) (WIDTH / 2 + 4 * TILE_WIDTH),
			8 * TILE_WIDTH);
	public static SpriteSheet sheet;
	public static VectorCoordinate currentStartVector;
	private static final String TITLE = "Chess";
	private static final int TICKS = 5;
	private boolean isRunning = false;

	private Chess() {
		ChessPieces.currentTeam = Team.WHITE;
		currentStartVector = new VectorCoordinate();
		takenPieces = new TakenPieces();
		selectPiece = new SelectPiece();

		stateColor = Color.GRAY;
		handler = new Handler();
		board = new Board();
		image = new ImageLoader().loadImage("/chess.jpg");
		sheet = new SpriteSheet(image);

		this.addMouseListener(new KeyInput(this));
		new Window(WIDTH, HEIGHT, TITLE, this);
		init();
		start();
	}

	public static void changeStartVector() {
		if (multiplier == 1) {
			multiplier = -1;
			currentStartVector.copyVector(currentStartVector, SECOND_START_VECTOR);
		} else {
			multiplier = 1;
			currentStartVector.copyVector(currentStartVector, FIRST_START_VECTOR);
		}
	}

	public void init() {
		currentStartVector.copyVector(currentStartVector, FIRST_START_VECTOR);
		multiplier = 1;

		KeyInput.clickedPiece = null;
		KeyInput.clicked = false;
		KeyInput.promoted = false;
		Tile.clearTiles();
		Handler.clearChessPieces();
		createChessGame();
		clickToRestart = "";
		state = "";
		winningTeam = "";
	}

	public void createChessGame() {
		for (int i = 1; i <= 8; i++) {
			Handler.addPiece(new PawnPiece(i, 2, Team.BLACK));
			Handler.addPiece(new PawnPiece(i, 7, Team.WHITE));
		}

		Handler.addPiece(new RookPiece(1, 8, Team.WHITE));
		Handler.addPiece(new RookPiece(8, 8, Team.WHITE));
		
		Handler.addPiece(new RookPiece(1, 1, Team.BLACK));
		Handler.addPiece(new RookPiece(8, 1, Team.BLACK));

		Handler.addPiece(new KnightPiece(7, 8, Team.WHITE));
		Handler.addPiece(new KnightPiece(2, 8, Team.WHITE));
		
		Handler.addPiece(new KnightPiece(7, 1, Team.BLACK));
		Handler.addPiece(new KnightPiece(2, 1, Team.BLACK));
		
		Handler.addPiece(new BishopPiece(6, 8, Team.WHITE));
		Handler.addPiece(new BishopPiece(3, 8, Team.WHITE));
		
		Handler.addPiece(new BishopPiece(6, 1, Team.BLACK));
		Handler.addPiece(new BishopPiece(3, 1, Team.BLACK));

		Handler.addPiece(new QueenPiece(4, 8, Team.WHITE));
		Handler.addPiece(new QueenPiece(4, 1, Team.BLACK));

		Handler.addPiece(new KingPiece(5, 8, Team.WHITE));
		Handler.addPiece(new KingPiece(5, 1, Team.BLACK));

	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000 / TICKS;
		double delta = 0;
		long timer = System.currentTimeMillis();

		while (isRunning) {
			ns = 1000000000 / TICKS;
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (isRunning)
				render();

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		stop();
	}

	public void tick() {

		handler.tick();
	}

	Color background = new Color(153, 173, 217);
	Color teamColor;

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		board.render(g);
		handler.render(g);
		selectPiece.render(g);
		if (state != "") {
			g.setColor(new Color(200, 200, 200, 150));
			g.fillRect(700, 525, 360, 200);
			g.setColor(new Color(240, 240, 240, 200));
			g.fillRect(550, 310, 640, 180);
		}
		g.setColor(stateColor);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g.drawString(state, (int) (WIDTH / 2) - 150, (HEIGHT / 2) + 100);
		g.drawString(winningTeam, (int) (WIDTH / 2) - 160, (HEIGHT / 2) + 180);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 60));
		g.setColor(Color.BLACK);
		g.drawString(clickToRestart, (int) (WIDTH / 2) - 300, (HEIGHT / 2) - 80);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		if (ChessPieces.currentTeam == Team.WHITE) {
			teamColor = Color.WHITE;
		} else {
			teamColor = Color.BLACK;
		}

		g.setColor(background);
		g.fillRect(1400, 400, 300, 200);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		g.setColor(teamColor);
		g.drawString(ChessPieces.currentTeam + "'s Turn", 1440, 515);
		takenPieces.render(g);
		g.dispose();
		bs.show();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Chess();
	}
}
