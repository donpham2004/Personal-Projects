package com.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	private Handler handler;
	private TetrisPiece piece;
	
	private boolean pressed;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
		pressed=false;
		
	}

	public void keyPressed(KeyEvent e) {
		piece = TetrisPiece.currentPiece;
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			if (piece != null&&!piece.spawn) {
				piece.rotate();

			}
			break;
		case KeyEvent.VK_S:
			if (piece != null&&!piece.spawn) {
			
			piece.setFast(true);
			
			}
			
			
			break;
		case KeyEvent.VK_A:

			if (piece != null&&!piece.spawn) {

				piece.moveHorizontally(-Tetris.BLOCK_LENGTH);
			}

			break;
		case KeyEvent.VK_D:

			if (piece != null&&!piece.spawn) {

				piece.moveHorizontally(+Tetris.BLOCK_LENGTH);
			}

			break;
		case KeyEvent.VK_C:

			if (piece != null&&!piece.spawn&&!pressed) {

				piece.hold();
				pressed=true;
			}

			break;
		default:
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_S:
			piece.setFast(false);
			break;
		case KeyEvent.VK_C:

			
				pressed=false;
				break;
			}

			
		}
 	
}