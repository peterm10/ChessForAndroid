package com.chess.chessandroid.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import android.test.ActivityInstrumentationTestCase2;
import com.chess.chessandroid.Board;
import com.chess.chessandroid.ChessBoard;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.R;
import com.chess.chessandroid.Search;

import android.widget.ImageButton;
//import com.jayway.android.robotium.solo.Solo;
import android.widget.TextView;

public class PlayGameTest extends ActivityInstrumentationTestCase2<MainActivity> {

//	private Solo solo;
	private TextView status;
	private Board board;
	private Search searcher = new Search();
    
	@SuppressWarnings("deprecation")
	public PlayGameTest(Class<?> activityClass) {
		super("com.chess.chessandroid", MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	protected void setUp() throws Exception {  
	       super.setUp(); 
	       MainActivity mainActivity = getActivity();  
	       mainActivity.stop();
	       searcher = new Search();
           board = new Board();
           
	    }
	public void testSearch(){
		assertEquals("Sprawdzanie czy plansza zosta³a uruchomiona", searcher);
	}
	public void testBoardch(){
		
		assertEquals("Sprawdzanie czy plansza zosta³a uruchomiona", board);
	}

}

