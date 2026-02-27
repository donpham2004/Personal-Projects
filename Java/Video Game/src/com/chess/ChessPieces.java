package com.chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

enum Team {
	WHITE, BLACK;
}

enum CheckReturn {
	Legal_Continue, Legal_Break, Illegal;
}

class CheckReturnResponse {
	public boolean willContinue(CheckReturn checkReturn) {
		
		return true;
	}
}

class CheckBreak extends CheckReturnResponse {

	@Override
	public boolean willContinue(CheckReturn checkReturn) {
		
		switch (checkReturn) {
		case Illegal:
			return false;
		default:
			return true;
		}
	}

}

public abstract class ChessPieces {
	protected int counter = 0;
	protected static final RotationMatrix[] STANARD_ROTATION_MATRIX = { RotationMatrix.ROTATION1,
			RotationMatrix.ROTATION2, RotationMatrix.ROTATION3, RotationMatrix.ROTATION4 };
	public static Team currentTeam;
	public static ChessPieces currentPiece;
	protected Tile tile;
	protected VectorCoordinate vector;
	protected Team team;
	protected Piece pieceType;
	protected Image image;
	protected LinkedList<PieceMovement> pieceMovements = new LinkedList<>();

	public Piece getPieceType() {
		return pieceType;
	}

	public void setPieceType(Piece pieceType) {
		this.pieceType = pieceType;
	}

	public ChessPieces(LinkedList<PieceMovement> pieceMovements, Piece pieceType, Team team, int x, int y) {
		this.counter = 0;
		this.pieceType = pieceType;
		this.team = team;
		this.pieceMovements = pieceMovements;
		this.image=pieceType.getImage(team);
		vector = new VectorCoordinate(x, y);
		Tile.getTile(x, y).setPiece(this);
		setPieceMovements();
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public static Team getCurrentTeam() {
		return currentTeam;
	}

	public static void setCurrentTeam(Team currentTeam) {
		ChessPieces.currentTeam = currentTeam;
	}

	public VectorCoordinate getVector() {
		return vector;
	}

	public void setVector(VectorCoordinate vector) {
		this.vector = vector;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public Tile getTile() {
		return tile;
	}

	public static boolean attack;
	
	public void changeTurn() {
		Tile.renderable=false;
		for (ChessPieces piece : Handler.getChessPieces()) {
			if (piece.getTeam() == currentTeam&& piece instanceof KingPiece) {
				((KingPiece)piece).setChecked(false);
			}
		}
		
		switch (currentTeam) {
		case WHITE:
			currentTeam = Team.BLACK;
			break;
		default:
			currentTeam = Team.WHITE;
			break;
		}
		boolean inCheck=false;
		for (ChessPieces piece : Handler.getChessPieces()) {
			if (piece.getTeam() == currentTeam) {
				piece.tick();
			}
			if (piece.getTeam() == currentTeam&& piece instanceof KingPiece) {
				switch(isChecked(piece.vector,piece)) {
				case Illegal:
					((KingPiece)piece).setChecked(true);
					inCheck=true;
					break;
				default:
					break;
				}
			}
		}
		
		Chess.changeStartVector();
		for (Tile t : Tile.tiles) {
			if (t.getHasLegalMove()) {
				Tile.renderable=true;
				clear();
				return;
			}
		}
		Chess.clickToRestart="Click To Restart";
		if(inCheck) {
			Chess.state="CheckMate!";
			switch(currentTeam) {
			case WHITE:
				Chess.stateColor=Color.BLACK;
				Chess.winningTeam="Black Wins!";
				
				break;
			default:
				Chess.stateColor= Color.WHITE;
				Chess.winningTeam="White Wins!";
				break;
			}
		}else {
			Chess.state="StaleMate!";
		}
		
	}
	
	public void moveToTile(Tile tile) {
		
		attack = false;
		counter++;
		if (tile.getPiece() != null) {
			attack = true;
			Handler.removePiece(tile.getPiece());
		}
		Tile.getTile(this).setPiece(null);
		VectorCoordinate newVector = new VectorCoordinate(tile.getVector());
		this.setVector(newVector);
		Tile.getTile(newVector).setPiece(this);
		for (PieceMovement pieceMovement : this.pieceMovements) {
			for (ChessCondition specialAttack : pieceMovement.getSpecialAttacks()) {
				specialAttack.isLegal(newVector);
			}
		}
		
	}

	public abstract void setPieceMovements();

	public void render(Graphics g) {
		g.drawImage(image,
				Chess.currentStartVector.getXPosition() + Chess.multiplier*getVector().getXPosition() * Chess.TILE_WIDTH - 133 + Chess.TILE_WIDTH,
				Chess.currentStartVector.getYPosition() + Chess.multiplier*getVector().getYPosition() * Chess.TILE_WIDTH - 133 + Chess.TILE_WIDTH, null);
	}

	public static CheckReturn isChecked(VectorCoordinate optionVector, ChessPieces isKing) {
		boolean breaking = false;
		for (ChessPieces piece : Handler.getChessPieces()) {
			if (piece.getTeam() == currentTeam)
				continue;
			if (piece.getVector().getXPosition() == optionVector.getXPosition()
					&& piece.getVector().getYPosition() == optionVector.getYPosition()) {
				breaking = true;
				continue;
			}

			currentPiece = piece;
			for (PieceMovement pieceMovement : piece.pieceMovements) {
				for (RotationMatrix rotationMatrix : pieceMovement.getRotationMatrices()) {
					VectorCoordinate tempVector = new VectorCoordinate(piece.getVector().getXPosition(),
							piece.getVector().getYPosition());

					for (int i = pieceMovement.getLowerBound(); i <= pieceMovement.getUpperBound(); i++) {
						if(piece instanceof PawnPiece && pieceMovement.getMovementVector()==MovementVector.STRAIGHT) continue;
						try {
							boolean legal=true;
							for (ChessCondition condition : pieceMovement.getPreConditions()) {
								
								if (!condition.isLegal(piece.getVector())) {
									legal = false;
									break;
								}
							}
							if (!legal)
								break;
							tempVector.addVector(rotationMatrix
									.getRotatedVector(pieceMovement.getMovementVector().getVector(), rotationMatrix));
							for (ChessCondition condition : pieceMovement.getChessConditions()) {
								if(condition instanceof WillAttack) {
									continue;
								}
								if (!condition.isLegal(tempVector)) {
									legal = false;
									break;
								}
							}
							if (legal == false) {

								break;
							}
							
							if (tempVector.getXPosition() == optionVector.getXPosition()
									&& tempVector.getYPosition() == optionVector.getYPosition()) {
								if (isKing instanceof KingPiece) {

									return CheckReturn.Illegal;
								}
								break;
							}

							if (!ChessConditions.TEAM_NOT_IN_WAY.isLegal(tempVector)) {
								ChessPieces pawn = Tile.getTile(tempVector)
										.getPiece();
								if (pawn instanceof PawnPiece && ((PawnPiece) pawn).isEnPassantable()) {
									if (pawn.getVector().getXPosition() == optionVector.getXPosition()) {
										boolean willBreak = false;
										switch (pawn.getTeam()) {
										case WHITE:
											if (optionVector.getYPosition() != 6) {

												willBreak = true;
												break;
											}
										default:
											if (optionVector.getYPosition() != 3) {
												willBreak = true;
												break;
											}
										}
										if (!willBreak) {
											continue;
										}else {
											break;
										}
									} else {
										break;
									}
								} else {
									break;
								}

							}
							Tile tile = Tile.getTile(tempVector);
							if (tile.getPiece() == null)
								continue;
							if (isKing instanceof KingPiece) {
							} else {
								if (tile.getPiece() instanceof KingPiece) {
									return CheckReturn.Illegal;
								}
							}
							if (!ChessConditions.NO_ENEMIES.isLegal(tempVector)) {
								if (Tile.getTile(tempVector)
										.getPiece() == isKing) {
									continue;
								}
								break;
							}
						} catch (NullPointerException npe) {
							break;
						}
					}

				}
			}

		}
		if (breaking)
			return CheckReturn.Legal_Break;

		return CheckReturn.Legal_Continue;
	}

	ChessPieces piece;

	public void clear() {
		for (Tile tile : Tile.tiles) {
			tile.setHasLegalMove(false);
		}

	}

	public void tick() {
		currentPiece = this;
		piece = this;
		for (PieceMovement pieceMovement : pieceMovements) {
			for (RotationMatrix rotationMatrix : pieceMovement.getRotationMatrices()) {
				VectorCoordinate tempVector = new VectorCoordinate(getVector().getXPosition(),
						getVector().getYPosition());
				for (int i = pieceMovement.getLowerBound(); i <= pieceMovement.getUpperBound(); i++) {
					boolean legal = true;
					try {
						for (ChessCondition condition : pieceMovement.getPreConditions()) {
							if (!condition.isLegal(piece.getVector())) {
								legal = false;
								break;
							}
						}
						if (!legal)
							break;
						tempVector.addVector(rotationMatrix
								.getRotatedVector(pieceMovement.getMovementVector().getVector(), rotationMatrix));

						for (ChessCondition condition : pieceMovement.getChessConditions()) {
							if (!condition.isLegal(tempVector)) {
								legal = false;
								break;
							}
						}
						if (!legal||!ChessConditions.TEAM_NOT_IN_WAY.isLegal(tempVector)) 
							break;
						CheckReturn checkReturn = isChecked(tempVector, this);
						currentPiece = this;
						if (checkReturn == CheckReturn.Illegal) {
							if (pieceMovement.getCheckResponse().willContinue(checkReturn)) {
								continue;
							}
							break;
						} else if (checkReturn == CheckReturn.Legal_Break) {
							Tile.getTile(tempVector).setHasLegalMove(true);
							break;
						}
						if (!ChessConditions.NO_ENEMIES.isLegal(tempVector)) {
							Tile.getTile(tempVector).setHasLegalMove(true);
							break;
						}

						Tile.getTile(tempVector).setHasLegalMove(true);
					} catch (NullPointerException e) {
						break;
					}
				}
			}
		}
	}
}