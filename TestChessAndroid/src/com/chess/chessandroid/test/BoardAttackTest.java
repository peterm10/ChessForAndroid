package com.chess.chessandroid.test;

import static org.junit.Assert.*;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.chess.chessandroid.Board;
import com.chess.chessandroid.ChessBoard;
import com.chess.chessandroid.MainActivity;

public class BoardAttackTest extends ActivityInstrumentationTestCase2<MainActivity>{

	private TextView status;
	private ChessBoard chessView;
	private Board board;
	int LIGHT = 0;
    int DARK = 1;
    int PAWN = 0;
    int KNIGHT = 1;
    int BISHOP = 2;
    int ROOK = 3;
    int QUEEN = 4;
    int KING = 5;
    int EMPTY = 6;

    char A1 = 56;
    char B1 = 57;
    char C1 = 58;
    char D1 = 59;
    char E1 = 60;
    char F1 = 61;
    char G1 = 62;
    char H1 = 63;
    char A8 = 0;
    char B8 = 1;
    char C8 = 2;
    char D8 = 3;
    char E8 = 4;
    char F8 = 5;
    char G8 = 6;
    char H8 = 7;
	public BoardAttackTest(Class<MainActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {  
	       super.setUp(); 
	       MainActivity mainActivity = getActivity();  
	       board = new Board();
	       
	    }
	public void testColor(){
		assertEquals("Sprawdzanie czy wyœwietla siê kolor planszy", board.getColor(1));
	}
	
	public void testPiece(){
		assertEquals("Sprawdzanie czy wyœwietla siê szachownica", board.getPiece(1));
	}
	public void testIsCheck(){
		assertTrue("Sprawdzanie czy pionek jest w szachu", board.inCheck(1));
	}
	public void testAttack(){
		assertTrue("Sprawdzanie ruchu komputera pionek zbija pionka z bia³ego pola", board.attack(3,1));
	}
}
