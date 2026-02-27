package com.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class RowEffect extends AbstractBackGround {
	static LinkedList<Integer> lines = new LinkedList<Integer>();
	
	
	public static void addLines(LinkedList<Integer> newLines) {
		lines.clear();
		lines.addAll(newLines);
		
	}
	private boolean white= false;
	public void render(Graphics g) {
		if(!white) {
			g.setColor(Color.WHITE);
			white = true;
		}else {
			g.setColor(Color.BLACK);
			white=false;
		}
		
		
		for(Integer line:lines) {
			g.fillRect(Tetris.widthCenter-5*Tetris.BLOCK_LENGTH, 
					line*Tetris.BLOCK_LENGTH, 10*Tetris.BLOCK_LENGTH, Tetris.BLOCK_LENGTH);
		}
	}
	
	public void update() {
		
	}

 	public void tick() {
		if(lines.size()>0) {
			lines.clear();
		}
	}
}
