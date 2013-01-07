package com.chess.chessandroid.test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.chess.chessandroid.Board;
import com.chess.chessandroid.ChessBoard;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.Move;
import com.chess.chessandroid.MoveTest;
import com.chess.chessandroid.Search;
import com.chess.chessandroid.Square;
import com.jayway.android.robotium.solo.Solo;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.widget.TextView;

public class PiecesTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private Board board;
	private ChessBoard chessViewTest;
	private Move mov;
	private Solo solo;
	private static Drawable[][] pieceImage = new Drawable[2][6];
	private Search searcher = new Search();
	private TextView status;
	
	protected Board board1 = new Board();
	   
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
	@SuppressWarnings("static-access")
	public void testROW(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(0, board.ROW(0));
		assertEquals(0, board.ROW(1));
		assertEquals(0, board.ROW(2));
		assertEquals(0, board.ROW(3));
		assertEquals(0, board.ROW(4));
		assertEquals(0, board.ROW(5));
		assertEquals(0, board.ROW(6));
		assertEquals(0, board.ROW(7));
		
		assertEquals(1, board.ROW(8));
		assertEquals(1, board.ROW(9));
		assertEquals(1, board.ROW(10));
		assertEquals(1, board.ROW(11));
		assertEquals(1, board.ROW(12));
		assertEquals(1, board.ROW(13));
		assertEquals(1, board.ROW(14));
		assertEquals(1, board.ROW(15));
		
		assertEquals(2, board.ROW(16));
		assertEquals(2, board.ROW(17));
		assertEquals(2, board.ROW(18));
		assertEquals(2, board.ROW(19));
		assertEquals(2, board.ROW(20));
		assertEquals(2, board.ROW(21));
		assertEquals(2, board.ROW(22));
		assertEquals(2, board.ROW(23));
		
		assertEquals(3, board.ROW(24));
		assertEquals(3, board.ROW(25));
		assertEquals(3, board.ROW(26));
		assertEquals(3, board.ROW(27));
		assertEquals(3, board.ROW(28));
		assertEquals(3, board.ROW(29));
		assertEquals(3, board.ROW(30));
		assertEquals(3, board.ROW(31));
		
		assertEquals(4, board.ROW(32));
		assertEquals(4, board.ROW(33));
		assertEquals(4, board.ROW(34));
		assertEquals(4, board.ROW(35));
		assertEquals(4, board.ROW(36));
		assertEquals(4, board.ROW(37));
		assertEquals(4, board.ROW(38));
		assertEquals(4, board.ROW(39));
		
		assertEquals(5, board.ROW(40));
		assertEquals(5, board.ROW(41));
		assertEquals(5, board.ROW(42));
		assertEquals(5, board.ROW(43));
		assertEquals(5, board.ROW(44));
		assertEquals(5, board.ROW(45));
		assertEquals(5, board.ROW(46));
		assertEquals(5, board.ROW(47));
		
		assertEquals(6, board.ROW(48));
		assertEquals(6, board.ROW(49));
		assertEquals(6, board.ROW(50));
		assertEquals(6, board.ROW(51));
		assertEquals(6, board.ROW(52));
		assertEquals(6, board.ROW(53));
		assertEquals(6, board.ROW(54));
		assertEquals(6, board.ROW(55));
		
		assertEquals(7, board.ROW(56));
		assertEquals(7, board.ROW(57));
		assertEquals(7, board.ROW(58));
		assertEquals(7, board.ROW(59));
		assertEquals(7, board.ROW(60));
		assertEquals(7, board.ROW(61));
		assertEquals(7, board.ROW(62));
		assertEquals(7, board.ROW(63));
		
	}
	@SuppressWarnings("static-access")
	public void testCol(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new Board();
		assertEquals(1, board.COL(1));
		assertEquals(1, board.COL(9));
		
		assertEquals(2, board.COL(2));
		assertEquals(2, board.COL(10));
		
		assertEquals(3, board.COL(3));
		assertEquals(3, board.COL(11));
		
		assertEquals(4, board.COL(4));
		assertEquals(4, board.COL(12));
		
		assertEquals(5, board.COL(5));
		assertEquals(5, board.COL(13));
		
		assertEquals(6, board.COL(6));
		assertEquals(6, board.COL(14));
		
		assertEquals(7, board.COL(7));
		assertEquals(7, board.COL(15));
		
	}
	public void testPawn(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(-10, board.evalLightPawn(9));
		assertEquals(-5, board.evalLightPawn(10));
		assertEquals(0, board.evalLightPawn(11));
		
		
	}
	
	public void testSprawdzaniaNIEwSzachu(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new Board();
		assertEquals(false, board.inCheck(0));
	}
	public void testSprawdzaniaJestwSzachu(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		int side = 1;
		board = new Board();
		//assertEquals(true, board.inCheck(side));
	}
	public void testAttack(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		int side = 1;
		board = new Board();
		//atakowany na czarnym polu F1
		assertEquals(false, board.attack(61, 1));
	}
	

}
