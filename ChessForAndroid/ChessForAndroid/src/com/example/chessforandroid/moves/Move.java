package com.brasee.chess.moves;

import java.util.List;
import java.util.Map;

import com.brasee.chess.Board;
import com.brasee.chess.Square;
import com.brasee.chess.pieces.Piece;

public interface Move {

	public enum MoveType {
		INVALID, NORMAL, CAPTURE, CASTLING, EN_PASSANT, PROMOTION
	}
	
	public Piece piece();
	public Square startSquare();
	public Square endSquare();
	public MoveType moveType();
	public List<Square> clearedSquares();
	public Map<Square, Piece> updatedSquares();
	public void undo(Board board);
	public Piece capturedPiece();
	public String notation();

}


