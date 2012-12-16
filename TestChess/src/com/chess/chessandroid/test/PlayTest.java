package com.chess.chessandroid.test;

import junit.framework.Assert;

import com.chess.chessandroid.Board;
import com.chess.chessandroid.R;
import com.chess.chessandroid.ChessBoard;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.Search;
import com.jayway.android.robotium.solo.Solo;

import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class PlayTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity = new MainActivity();
	private Board board;
	private ChessBoard chessView;
	private Solo solo;
	private static Drawable[][] pieceImage = new Drawable[2][6];
	private Search searcher = new Search();
	
	public PlayTest(){
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
	
}
