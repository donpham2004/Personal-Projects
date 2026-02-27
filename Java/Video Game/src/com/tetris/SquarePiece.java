package com.tetris;

import java.awt.Color;

public class SquarePiece extends TetrisPiece {
	public SquarePiece(boolean isControllable) {
		super(Color.YELLOW, 2, 6, 3, 7,isControllable);
		this.rotate=Rotations.NONE;
	}
}

class TPiece extends TetrisPiece {
	public TPiece(boolean isControllable) {
		super(Color.MAGENTA, 2, 5, 7, 6,isControllable);
		this.rotate=Rotations.THREE;
	}
}

class LinePiece extends TetrisPiece {
	public LinePiece(boolean isControllable) {
		super(Color.CYAN, 2, 6, 10, 14,isControllable);
		this.rotate=Rotations.FOUR;
	}
}

class LPiece extends TetrisPiece {
	public LPiece(boolean isControllable) {
		super(Color.ORANGE, 5, 6,7, 3,isControllable);
		this.rotate=Rotations.THREE;
	}
}

class RLPiece extends TetrisPiece {
	public RLPiece(boolean isControllable) {
		super(Color.BLUE, 1, 5, 6, 7,isControllable);
		this.rotate=Rotations.THREE;
	}
}

class SkewPiece extends TetrisPiece {
	public SkewPiece(boolean isControllable) {
		super(Color.GREEN, 5, 2, 6, 3,isControllable);
		this.rotate=Rotations.THREE;
	}	
}

class RSkewPiece extends TetrisPiece {
	public RSkewPiece(boolean isControllable) {
		super(Color.RED, 1, 2, 6, 7,isControllable);
		this.rotate=Rotations.THREE;
	}
}
