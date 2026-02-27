package com.tetris;

import java.awt.Graphics;

public abstract class AbstractBackGround {
	
	public abstract void render(Graphics g);
	public abstract void tick();
	public abstract void update();
}
