package com.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class GameBackGround extends AbstractBackGround{
	private PieceSpawner spawner;
	LinkedList<TetrisPiece> queuePiece = new LinkedList<TetrisPiece>();
	public GameBackGround(PieceSpawner spawner) {
		this.spawner=spawner;
	}
	@Override
	public void tick() {
		queuePiece.clear();
		for(String piece:PieceSpawner.queue) {
			
			switch(piece) {
			case "Square":
				queuePiece.add(new SquarePiece(false));
				break;
			case "TPiece":
				queuePiece.add(new TPiece(false));
				break;
			case "LinePiece":
				queuePiece.add(new LinePiece(false));
				break;
			case "LPiece":
				queuePiece.add(new LPiece(false));
				break;
			case "RLPiece":
				queuePiece.add(new RLPiece(false));
				break;
			case "SkewPiece":
				queuePiece.add(new SkewPiece(false));
				break;
			default:
				queuePiece.add(new RSkewPiece(false));
				break;
			}
		}
		
		for(int i=0;i<queuePiece.size();i++) {
			LinkedList<Block> blocks = queuePiece.get(i).blocks;
			for(Block block: blocks) {
				block.setX(8*Tetris.BLOCK_LENGTH);
				block.setY((1+i*3)*Tetris.BLOCK_LENGTH);
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.GRAY);
		int x1 = Tetris.WIDTH;
		int y1= Tetris.BLOCK_LENGTH*20;
		g.drawRect(Tetris.center(x1/2, y1/2), 0, y1/2, y1);
		g.drawRect(Tetris.widthCenter+5*Tetris.BLOCK_LENGTH, 0, (int)(y1/3), y1);
		g.drawRect(Tetris.widthCenter-10*Tetris.BLOCK_LENGTH, 0, (int)(y1/4), (int)(y1/4));
		
		if(!spawner.isHoldPieceNull()) {
			for(Block block:spawner.getHoldPiece().blocks) {
				block.render(g);
			}
		}
		
		for(int i=0;i<queuePiece.size();i++) {
			
			try {
				LinkedList<Block> blocks = queuePiece.get(i).blocks;
				for(Block block: blocks) {
					
					block.render(g);
					
					
				}
			}catch(NullPointerException e) {
				return;
			}
			
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
