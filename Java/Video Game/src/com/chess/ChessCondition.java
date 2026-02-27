package com.chess;


public interface ChessCondition {
	public boolean isLegal(VectorCoordinate vector);
}

class LeftCastleKing implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		ChessPieces king =Tile.getTile(vector).getPiece();
		ChessPieces rook = Tile.getTile(1, vector.getYPosition()).getPiece();
		if(king.getCounter()==1) {
			if(rook!=null) {
				if(rook instanceof RookPiece &&rook.getCounter()==0) {
					if(vector.getXPosition()==3) {
						rook.moveToTile(Tile.getTile(vector.getXPosition()+1, vector.getYPosition()));
						return true;
					}
				}
			}
		}
		return false;
	}
	
}

class KingInCheck implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		ChessPieces king =Tile.getTile(vector).getPiece();
		if(king!=null&&king instanceof KingPiece) {
			if(((KingPiece)king).isChecked) {
				return false;
			}
		}
		return true;
	}
	
}

class RightCastleKing implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		ChessPieces king =Tile.getTile(vector).getPiece();
		ChessPieces rook = Tile.getTile(8, vector.getYPosition()).getPiece();
		if(king.getCounter()==1) {
			if(rook!=null) {
				if(rook instanceof RookPiece &&rook.getCounter()==0) {
					if(vector.getXPosition()==7) {
						rook.moveToTile(Tile.getTile(vector.getXPosition()-1, vector.getYPosition()));
						return true;
					}
				}
			}
		}
		return false;
	}
	
}

class NewLeftRook implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		boolean legal=false;
		for(ChessPieces piece:Handler.getChessPieces()) {
			if(piece.getTeam()==ChessPieces.currentTeam) {
				if(piece instanceof RookPiece) {
					if(piece.getCounter()==0) {
						if(piece.getVector().getXPosition()==1) {
							legal=true;
							break;
						}
					}
				}
				
			}
		}
		for(int i=2;i<vector.getXPosition();i++) {
			if(Tile.getTile(i, vector.getYPosition()).getPiece()!=null) {
				legal=false;
				break;
			}
		}
		
		return legal;
	}
	
}

class NewRightRook implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		boolean legal=false;
		for(ChessPieces piece:Handler.getChessPieces()) {
			if(piece.getTeam()==ChessPieces.currentTeam) {
				if(piece instanceof RookPiece) {
					if(piece.getCounter()==0) {
						if(piece.getVector().getXPosition()==8) {
							legal=true;
							break;
						}
					}
				}
			}
		}
		for(int i=7;i>vector.getXPosition();i--) {
			if(Tile.getTile(i, vector.getYPosition()).getPiece()!=null) {
				legal=false;
				break;
			}
		}
		
		return legal;
	}
	
}


class FirstMove implements ChessCondition {
	
	@Override
	public boolean isLegal(VectorCoordinate vector) {
		ChessPieces piece =Tile.getTile(vector).getPiece();
		
		if(piece instanceof PawnPiece&&ChessPieces.currentTeam==piece.getTeam()) {
			((PawnPiece)piece).setEnPassantable(false);
		}
		
		if(piece==null) return false;
		if(piece.getCounter()>0) {	
			return false;
		}
		return true;
	}
}

class EnPassant implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		ChessPieces piece =Tile.getTile(vector).getPiece();
		switch(piece.getTeam()) {
		case WHITE:
			if(vector.getYPosition()==5&&piece.getCounter()==1) {
				((PawnPiece)piece).setEnPassantable(true);
			}
			break;
		default:
			if(vector.getYPosition()==4&&piece.getCounter()==1) {
				((PawnPiece)piece).setEnPassantable(true);
			}
		}
		
		if(!ChessPieces.attack) {
			if(piece!=null) {
				switch(piece.getTeam()) {
				case WHITE:
					Tile tile = Tile.getTile(vector.getXPosition(), vector.getYPosition()+1);
					if(tile!=null) {
						piece =tile.getPiece();
						if(piece!=null) {
							if(piece instanceof PawnPiece) {
								Handler.removePiece(piece);
								tile.setPiece(null);
								return true;
							}
						}
					}
					break;
				case BLACK:
					tile =Tile.getTile(vector.getXPosition(), vector.getYPosition()-1);
					if(tile!=null) {
						piece =tile.getPiece();
						if(piece!=null) {
							if(piece instanceof PawnPiece) {
								Handler.removePiece(piece);
								tile.setPiece(null);
								return true;
							}
						}
						
					}
					break;
				}
			}
		}
		return false;
	}
	
}

class Promotion implements ChessCondition {
	
	
	@Override
	public boolean isLegal(VectorCoordinate vector) {
		
		ChessPieces piece = Tile.getTile(vector).getPiece();
		if(piece instanceof PawnPiece) {
			switch(piece.getTeam()) {
			case WHITE:
				if(vector.getYPosition()==1) {
					SelectPiece.vector=vector;
					
					KeyInput.promoted=true;
					return true;
				}
				
			case BLACK:
				if(vector.getYPosition()==8) {
					SelectPiece.vector=vector;
					KeyInput.promoted=true;
					return true;
				}
				
			}
		}
		return false;
	}
	
}

class WillAttack implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		
		ChessPieces piece = Tile.getTile(vector).getPiece();
		
		if(piece!=null) {
			if(piece.getTeam()!=ChessPieces.currentTeam) {
				return true;
			}
		}
		return false;
	}
	
}

class InFront implements ChessCondition {

	@Override
	public boolean isLegal(VectorCoordinate vector) {
		ChessPieces piece = Tile.getTile(vector).getPiece();
		if(piece==null) {
			return true;
		}
		return false;
	}
	
}

class PawnOnLeftSide implements ChessCondition {
	@Override
	public boolean isLegal(VectorCoordinate vector) {
		int x;
		int y;
		ChessPieces piece=Tile.getTile(vector).getPiece();
		switch(piece.getTeam()) {
		case WHITE:
			x=-1;
			y=4;
			break;
		default:
			x=1;
			y=5;
		}
			Tile tile = Tile.getTile(vector.getXPosition()+x, vector.getYPosition());
			piece = tile.getPiece();
			if(piece!=null&&piece instanceof PawnPiece) {
				if(piece.getTeam()!=ChessPieces.currentTeam&&piece.getCounter()==1) {
					if(tile.getVector().getYPosition()==y) {
						if(((PawnPiece)piece).isEnPassantable()) {
							return true;
						}
					}
				}
			}
		
		return false;
	}
}

class PawnOnRightSide implements ChessCondition {
	@Override
	public boolean isLegal(VectorCoordinate vector) {
		int x;
		int y;
		ChessPieces piece=Tile.getTile(vector).getPiece();
		switch(piece.getTeam()) {
		case WHITE:
			x=1;
			y=4;
			break;
		default:
			x=-1;
			y=5;
		}
	
			Tile tile = Tile.getTile(vector.getXPosition()+x, vector.getYPosition());
			piece=tile.getPiece();
			if(piece!=null&&piece instanceof PawnPiece) {
				if(piece.getTeam()!=ChessPieces.currentTeam&&piece.getCounter()==1) {
					if(tile.getVector().getYPosition()==y) {
						if(((PawnPiece)piece).isEnPassantable()) {
							return true;
						}
					}
				}
		}
		
		return false;
	}
}

enum ChessConditions implements ChessCondition{
	TEAM_NOT_IN_WAY {

		@Override
		public boolean isLegal(VectorCoordinate vector) {
			ChessPieces piece=Tile.getTile(vector).getPiece();
			if(piece!=null) {
				if(piece.getTeam()==ChessPieces.currentPiece.getTeam()) {
					return false;
				}
			}
			
			return true;
		}
		
	}, NO_ENEMIES {

		@Override
		public boolean isLegal(VectorCoordinate vector) {
			ChessPieces piece=Tile.getTile(vector).getPiece();

			if(piece!=null) {
				if(piece.getTeam()!=ChessPieces.currentPiece.getTeam()) {
					return false;
				}
			}
			return true;
		}
		
	};
}