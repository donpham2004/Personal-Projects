package com.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class TetrisPiece implements Cloneable {

	protected LinkedList<Block> blocks = new LinkedList<>();
	private static Handler handler;
	private static PieceSpawner spawner;
	public static TetrisPiece currentPiece;
	public Rotation rotate;
	
	public TetrisPiece makeCopy() {
		TetrisPiece pieceObject = null;
		try {
			pieceObject = (TetrisPiece) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return pieceObject;
	}
	
	public TetrisPiece(Color color, int block1, int block2, int block3, int block4, boolean isControllable) {
		blocks = new LinkedList<Block>();
		if(isControllable) {
			currentPiece = this;
		}
		held = false;
		collide = false;
		createPiece(color, block1, block2, block3, block4,isControllable,this);
		counter = 0;
	}

	public static void setHandler(Handler handObj) {
		if (handler == null)
			handler = handObj;
	}
	
	public static void setSpawner(PieceSpawner newSpawner) {
		if (spawner == null)
			spawner = newSpawner;
	}

	public int getBlockX(int x) {

		if (x == 13 | x == 9 || x == 5 || x == 1) {
			return -Tetris.BLOCK_LENGTH;
		} else if (x == 2 | x == 6 || x == 10 || x == 14) {
			return 0;
		} else if (x == 3 | x == 7 | x == 1 | x == 15) {
			return Tetris.BLOCK_LENGTH;
		}
		return 2 * Tetris.BLOCK_LENGTH;

	}

	public int getBlockY(int y) {
		if (y < 5) {
			return 0;
		} else if (y < 9) {
			return Tetris.BLOCK_LENGTH;
		} else if (y < 13) {
			return 2 * Tetris.BLOCK_LENGTH;
		}
		return 3 * Tetris.BLOCK_LENGTH;
	}

	public void createPiece(Color color, int block1, int block2, int block3, int block4, boolean isControllable, TetrisPiece piece) {
		
		if(!isControllable&&piece instanceof LinePiece) {
			blocks.add(0, new Block(color, getBlockX(5), getBlockY(5)));
			blocks.get(0).setBlock(0, 5);
			blocks.add(1, new Block(color, getBlockX(6), getBlockY(6)));
			blocks.get(1).setBlock(1, 6);
			blocks.add(2, new Block(color, getBlockX(7), getBlockY(7)));
			blocks.get(2).setBlock(2, 7);
			blocks.add(3, new Block(color, getBlockX(8), getBlockY(8)));
			blocks.get(3).setBlock(3, 8);
			return;
		}
		blocks.add(0, new Block(color, getBlockX(block1), getBlockY(block1)));
		blocks.get(0).setBlock(0, block1);
		blocks.add(1, new Block(color, getBlockX(block2), getBlockY(block2)));
		blocks.get(1).setBlock(1, block2);
		blocks.add(2, new Block(color, getBlockX(block3), getBlockY(block3)));
		blocks.get(2).setBlock(2, block3);
		blocks.add(3, new Block(color, getBlockX(block4), getBlockY(block4)));
		blocks.get(3).setBlock(3, block4);
		for (Block block : blocks) {
			block.setVelY(Tetris.BLOCK_LENGTH);
		}
	}

	public void render(Graphics g) {
		for (Block block : blocks) {
			block.render(g);
		}
	}

	public boolean outOfHorizontalBounds() {
		for (Block block : blocks) {
			if (block.getX() < Tetris.widthCenter - 5 * Tetris.BLOCK_LENGTH
					| block.getX() > Tetris.widthCenter + 4 * Tetris.BLOCK_LENGTH) {
				return true;
			}
		}
		return false;
	}

	public boolean outOfVerticalBounds() {
		for (Block block : blocks) {
			if (block.getY() > Tetris.BLOCK_LENGTH * 18) {

				return (collide = true);

			}
		}
		return false;
	}

	public void moveHorizontally(int x) {
		for (Block block : blocks) {
			block.setX(x);
		}
		if (outOfHorizontalBounds() | intersects()) {
			for (Block block : blocks) {
				block.setX(-x);
			}
		}
	}

	public int getXDelta(int xpos1, int xpos2) {

		int[] x = { 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4 };
		return x[xpos2 - 1] - x[xpos1 - 1];
	}

	public int getYDelta(int ypos1, int ypos2) {

		int[] y = { 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4 };

		return y[ypos2 - 1] - y[ypos1 - 1];
	}

	public void rotate() {
		intersects();
		int oldBlock[] = new int[4];
		for (int i = 0; i < blocks.size(); i++) {
			oldBlock[i] = blocks.get(i).getBlock(i);
		}
		if (collide)
			return;
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);

			int newPos = rotate.rotate(block.getBlock(i));
			if (newPos == 0)
				continue;
			block.setX(getXDelta(block.getBlock(i), newPos) * Tetris.BLOCK_LENGTH);
			block.setY(getYDelta(block.getBlock(i), newPos) * Tetris.BLOCK_LENGTH);
			block.setBlock(i, newPos);

		}
		if (intersects()) {
			for (int i = 0; i < blocks.size(); i++) {
				Block block = blocks.get(i);
				block.setX(getXDelta(block.getBlock(i), oldBlock[i]) * Tetris.BLOCK_LENGTH);
				block.setY(getYDelta(block.getBlock(i), oldBlock[i]) * Tetris.BLOCK_LENGTH);
				block.setBlock(i, oldBlock[i]);
			}
			return;
		}
		if (outOfHorizontalBounds()) {
			String side = "right";
			for (Block block : blocks) {
				if (block.getX() < Tetris.widthCenter - 5 * Tetris.BLOCK_LENGTH) {
					side = "left";
					break;
				}
			}

			switch (side) {
			case "left":

				for (Block block : blocks) {
					block.setX(Tetris.BLOCK_LENGTH);
				}

				break;
			case "right":
				while (outOfHorizontalBounds()) {
					for (Block block : blocks) {
						block.setX(-Tetris.BLOCK_LENGTH);
					}
				}

				break;
			}
		} else if (outOfVerticalBounds()) {
			while (outOfVerticalBounds()) {
				for (Block block : blocks) {
					block.setY(-Tetris.BLOCK_LENGTH);
				}
			}
		}

	}

	protected boolean collide=false;
	protected volatile boolean spawn = false;
	public void setFast(boolean fast) {
		if(!spawn) {
			Block.fast=fast;
		}else {
			Block.fast=false;
		}
	}
	public boolean intersects() {

		for (TetrisPiece piece : handler.getList()) {
			for (int i = 0; i < piece.blocks.size(); i++) {
				for (int j = 0; j < blocks.size(); j++) {
					if (piece == this)
						break;
					if (piece.blocks.get(i).getBounds().intersects(blocks.get(j).getBounds())) {
						return (collide = true);

					}
				}
			}
		}
		return false;
	}

	protected int counter;
	protected boolean held;
	
	public void setHeld(boolean held) {
		this.held = held;
	}
	
	public boolean getHeld() {
		return held;
	}
	
	public void hold() {
		boolean first=false;
		if(spawner.isHoldPieceNull()) {
			first=true;
		}
		if(!held&this ==currentPiece) {
			boolean limit=false;
			do {
				for(Block block:this.blocks) {
					block.setY(-Tetris.BLOCK_LENGTH);
				}
				for(Block block:this.blocks) {
					if(block.getY()==0) {
						limit=true;
						break;
					}
				}
				
				
				
			}while(!limit);
			limit=false;
			for(Block block:this.blocks) {
				block.setX(-20*Tetris.BLOCK_LENGTH);
			}
			do {
				for(Block block:this.blocks) {
					block.setX(Tetris.BLOCK_LENGTH);
				}
				for(Block block:this.blocks) {
					if(block.getX()==Tetris.widthCenter-7*Tetris.BLOCK_LENGTH) {
						limit=true;
						break;
					}
				}
			}while(!limit);
			for(Block block:this.blocks) {
				block.setY(Tetris.BLOCK_LENGTH);
			}
			currentPiece=spawner.getHoldPiece();
			if(!first) {
				for(Block block:currentPiece.blocks) {
					block.setX(7*Tetris.BLOCK_LENGTH);
					block.setY(-Tetris.BLOCK_LENGTH);
				}
			}
			
			handler.addObject(currentPiece);
			spawner.setHoldPiece(this);
			handler.removeObject(this);
		}
	}

	public void tick() {
		
		if (this != currentPiece)
			return;
		collide = false;
		for (Block block : blocks) {
			if (outOfVerticalBounds()) {
				break;
			}
		}

		if (!collide) {
			for (Block block : blocks) {
				block.tick();

			}

			if (intersects()) {
				for (Block block : blocks) {
					block.setY(-Tetris.BLOCK_LENGTH);

				}

			}

			for (Block block : blocks) {
				block.setY(1);

			}
			intersects();

			for (Block block : blocks) {
				block.setY(-1);
			}
		}

		if (collide) {
			counter++;
		}else {
			counter=0;
		}
		if (counter == 10 && !spawn) {
			spawn = true;
			handler.addRow(this);
			
		}
	}
}
