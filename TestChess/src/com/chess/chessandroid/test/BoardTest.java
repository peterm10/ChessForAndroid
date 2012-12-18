package com.chess.chessandroid.test;

import android.test.ActivityInstrumentationTestCase2;

import com.chess.chessandroid.Board;
import com.chess.chessandroid.ChessBoard;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.Search;
import com.chess.chessandroid.Square;
import com.jayway.android.robotium.solo.Solo;

import junit.framework.*;


public class BoardTest extends ActivityInstrumentationTestCase2<MainActivity>{
	private ChessBoard chessViewTes;
	private MainActivity mActivity;
	private Board board;
	private Search searcher;
	private Solo solo;
	   
	public BoardTest(){
		super("com.chess.chessandroid",MainActivity.class);
	}
	protected void setUp() throws Exception{
		super.setUp();
		mActivity = getActivity();
		solo = new Solo(getInstrumentation(),getActivity());
		//searcher = new Search();
        //board = new Board();
        
        
	}
	public void testApp(){
		solo.assertCurrentActivity("Sprawdzanie czy dobrz aplikacja jest wlaczona", MainActivity.class);
	}
	public void testBo(){
		assertNotNull(getActivity());
		chessViewTes = new ChessBoard(mActivity);
	}
}
