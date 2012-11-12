package com.example.chessforandroid.pieces;

import com.example.chessforandroid.Board;
import com.example.chessforandroid.Square;

public class Rook extends AbstractPiece {

	public Rook(Color color) {
		super(color);
	}
	
	@Override
	public boolean canAttack(Board board, Square currentSquare, Square occupiedSquare) {
		return (validSetupForAttack(board, currentSquare, occupiedSquare) &&
				board.clearPathBetween(currentSquare, occupiedSquare) &&
				differentSquareInSameRankOrFile(currentSquare, occupiedSquare));
	}

	@Override
	public boolean canMove(Board board, Square currentSquare, Square emptySquare) {
		return (validSetupForMove(board, currentSquare, emptySquare) &&
				board.clearPathBetween(currentSquare, emptySquare) &&
				differentSquareInSameRankOrFile(currentSquare, emptySquare));
	}
	
	private boolean differentSquareInSameRankOrFile(Square square1, Square square2) {
		return !square1.equals(square2) && (square1.inSameRankAs(square2) || square1.inSameFileAs(square2));
	}

	@Override
	public PieceType pieceType() {
		return PieceType.ROOK;
	}
}
