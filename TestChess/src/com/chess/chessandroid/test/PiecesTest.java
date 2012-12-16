package com.chess.chessandroid.test;

import com.chess.chessandroid.Board;
import com.chess.chessandroid.ChessBoard;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.Search;
import com.chess.chessandroid.Square;
import com.jayway.android.robotium.solo.Solo;



import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;

public class PiecesTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity = new MainActivity();
	private Board board;
	private ChessBoard chessView;
	private Solo solo;
	private static Drawable[][] pieceImage = new Drawable[2][6];
	private Search searcher = new Search();
	
	   
	@SuppressWarnings("deprecation")
	public PiecesTest(){
		super("com.chess.chessandroid",MainActivity.class);
	}
	protected void setUp() throws Exception{
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
		searcher = new Search();
        board = new Board();
        
        
	}
	public void testApp(){
		solo.assertCurrentActivity("Sprawdzanie czy dobrz aplikacja jest wlaczona", MainActivity.class);
	}
	public void testInit(){
		//chessView = new ChessBoard(mActivity);
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new Board();
		assertEquals(1, board.getColor(1));
		assertEquals(6, board.getColor(17));
		assertEquals(0, board.getColor(49));
		 
		
	}
	public void testPieces(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new Board();
		//sprawdzanie czy na pozycji A8 jest ROOK
		assertEquals(3, board.getPiece(0));
		//sprawdzanie czy na pozycji B8 jest KNIGHT
		assertEquals(1, board.getPiece(1));
		//sprawdzanie czy na pozycji C8 jest BISHOP
		assertEquals(2, board.getPiece(2));
		//sprawdzanie czy na pozycji D8 jest QUEEN
		assertEquals(4, board.getPiece(3));
		//sprawdzanie czy na pozycji E8 jest KING
		assertEquals(5, board.getPiece(4));
		//sprawdzanie czy sa wystawione pionki
		for(int i=9;i<16;i++){
		assertEquals(0, board.getPiece(i));
		}
		//sprawdzanie czy pola srodkowe s¹ puste
		for(int i=17;i<48;i++){
		assertEquals(6, board.getPiece(i));
		}				 
		
	}

}
