package com.tetris;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
public class Handler {
	private PieceSpawner spawner;
	private AbstractBackGround background;
	private LinkedList<TetrisPiece> list = new LinkedList<TetrisPiece>();
	private HashMap<Integer, LinkedList<Block>> row = new HashMap<Integer,LinkedList<Block>>();
	public Handler(PieceSpawner spawner,AbstractBackGround background) {
		this.spawner=spawner;
		for(int i=0;i<20;i++) {
			row.put(i, new LinkedList<Block>());
			
		}
		this.background=background;
		
	}
	
	

	public void render(Graphics g) {
		
		for(int i=0;i<list.size();i++) {
			list.get(i).render(g);
			
		}
	}
	
	public void tick() {
		for(int i =0; i<list.size();i++) {
			
			list.get(i).tick();
		}
	}
	
	public void addObject(TetrisPiece piece) {
		list.add(piece);
	
	}
	
	public void removeObject(TetrisPiece piece) {
		list.remove(piece);
		
	}
	
	public LinkedList<TetrisPiece> getList() {
		return list;
	}

	public void addRow(TetrisPiece piece) {
		LinkedList<Integer> lines = new LinkedList<Integer>();
		int counter=0;
		for(Block block:piece.blocks) {
			row.get(block.getY()/Tetris.BLOCK_LENGTH).add(block);
			if(row.get(block.getY()/Tetris.BLOCK_LENGTH).size()==10) {
				lines.add(block.getY()/Tetris.BLOCK_LENGTH);
				counter++;
			}
		}
		RowEffect.addLines(lines);
		for(int i=19;i>=0;i--) {
			if(row.get(i).size()==10) {
				for(Block block:row.get(i)) {
					for(TetrisPiece tetrisPiece: list) {
						if(tetrisPiece.blocks.contains(block)) {
							tetrisPiece.blocks.remove(block);
						}
					}
					
				}	
				row.get(i).clear();
			}
		}
		for(int h=0;h<counter;h++) {
			for(int i=19;i>=0;i--) {
				if(row.get(i).size()==0) {
					for(int j=i-1;j>=0;j--) {
						LinkedList<Block> tempList = row.get(j);
						for(Block tempBlock:tempList) {
							tempBlock.setY(Tetris.BLOCK_LENGTH);
						}
					}
					break;
				}
			}
		}
		for(int i=0;i<20;i++) {
			row.get(i).clear();
		}
		for(TetrisPiece tetrisPiece: list) {
			for(Block block:tetrisPiece.blocks) {
				if(row.get(block.getY()/Tetris.BLOCK_LENGTH)==null) {
					row.put(block.getY()/Tetris.BLOCK_LENGTH, new LinkedList<Block>());
				}
				row.get(block.getY()/Tetris.BLOCK_LENGTH).add(block);
			}
		}
		addObject(spawner.createPiece());
		
	}
}
