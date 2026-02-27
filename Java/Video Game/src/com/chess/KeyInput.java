package com.chess;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

enum SelectPieces {
	KNIGHT(100, 100), BISHOP(100, 250), ROOK(100, 400), QUEEN(100, 550),DEFAULT(0,0);

	private int x;
	private int y;

	private SelectPieces(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static SelectPieces getSelectPiece(int x, int y) {
		if (x >= 100 && x <= 233) {
			if (y >= 100 && y <= 233) {

				
				return KNIGHT;
			}
			if (y >= 250 && y <= 383) {

				
				return BISHOP;
			}
			if (y >= 400 && y <= 533) {

				
				return ROOK;
			}
			if (y >= 550 && y <= 683) {

				return QUEEN;
			}

		}
		return DEFAULT;
	}

}

class SelectPiece {
	public static Team team;

	public static VectorCoordinate vector;
	public void render(Graphics g) {
		team =ChessPieces.currentTeam;
		if(KeyInput.promoted) {
			g.drawImage(Piece.KNIGHT.getImage(ChessPieces.currentTeam), 100, 100, null);
			g.drawImage(Piece.BISHOP.getImage(ChessPieces.currentTeam), 100, 250, null);
			g.drawImage(Piece.ROOK.getImage(ChessPieces.currentTeam), 100, 400, null);
			g.drawImage(Piece.QUEEN.getImage(ChessPieces.currentTeam), 100, 550, null);
		}
	}
}

public class KeyInput extends MouseAdapter {
	public static boolean clicked = false;
	public static ChessPieces clickedPiece;
	public static boolean promoted = false;
	private Chess chess;
	public KeyInput(Chess chess) {
		this.chess=chess;
	}
	

	
	public void mousePressed(MouseEvent e) {
		
		Tile tile=null;
		
			tile = Tile.getCoordinateTile(e.getX(), e.getY());
		
		
		if (tile == null && !promoted) {
			if (clickedPiece != null) {
				clickedPiece.clear();
				clickedPiece = null;
				clicked = false;
			}
			return;
		}

		if (Chess.state.equals("")) {
			
			if (promoted) {

				switch (SelectPieces.getSelectPiece(e.getX(), e.getY())) {
				case KNIGHT:
					Handler.removePiece(Tile.getTile(SelectPiece.vector.getXPosition(), SelectPiece.vector.getYPosition()).getPiece());
					Handler.addPiece(clickedPiece=new KnightPiece(SelectPiece.vector.getXPosition(),
							SelectPiece.vector.getYPosition(), SelectPiece.team));
					promoted=false;
					clickedPiece.changeTurn();
					clickedPiece = null;
					clicked = false;
					break;
				case BISHOP:
					Handler.removePiece(Tile.getTile(SelectPiece.vector.getXPosition(), SelectPiece.vector.getYPosition()).getPiece());
					Handler.addPiece(clickedPiece=new BishopPiece(SelectPiece.vector.getXPosition(),
							SelectPiece.vector.getYPosition(), SelectPiece.team));
					promoted=false;
					clickedPiece.changeTurn();
					clickedPiece = null;
					clicked = false;
					break;
				case ROOK:
					Handler.removePiece(Tile.getTile(SelectPiece.vector.getXPosition(), SelectPiece.vector.getYPosition()).getPiece());
					Handler.addPiece(clickedPiece=new RookPiece(SelectPiece.vector.getXPosition(), SelectPiece.vector.getYPosition(),
							
							SelectPiece.team));
					promoted=false;
					clickedPiece.changeTurn();
					clickedPiece = null;
					clicked = false;
					break;
				case QUEEN:
					Handler.removePiece(Tile.getTile(SelectPiece.vector.getXPosition(), SelectPiece.vector.getYPosition()).getPiece());
					Handler.addPiece(clickedPiece=new QueenPiece(SelectPiece.vector.getXPosition(),
							SelectPiece.vector.getYPosition(), SelectPiece.team));
					promoted=false;
					clickedPiece.changeTurn();
					clickedPiece = null;
					clicked = false;
					break;
				default:
					break;
				}
			}
			if (!promoted && tile != null) {
				if (clicked) {
					if (tile.getPiece() == clickedPiece) {
						clickedPiece.clear();
						clickedPiece = null;
						clicked = false;
					} else if (tile.getHasLegalMove()) {
						clickedPiece.clear();
						clickedPiece.moveToTile(tile);
						if(!promoted) {
							clickedPiece.changeTurn();
							clickedPiece = null;
							clicked = false;
						}
					} else {
						clickedPiece.clear();
						clickedPiece = null;
						clicked = false;
					}
				} else {
					if (tile.getPiece() != null) {
						if (tile.getPiece().getTeam() == ChessPieces.currentTeam) {
							tile.getPiece().clear();
							tile.getPiece().tick();
							clickedPiece = tile.getPiece();
							clicked = true;
						}

					}
				}
			}

		}else {
			if(e.getX()>=550&&e.getX()<=1190) {
				if(e.getY()>=310&&e.getY()<=490) {
					chess.init();
				}
			}
			
		}

	}
}
