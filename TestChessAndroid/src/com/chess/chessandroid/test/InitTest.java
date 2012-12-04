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
import android.widget.ImageButton;
//import com.jayway.android.robotium.solo.Solo;
import android.widget.TextView;

public class InitTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private TextView status;
	private ChessBoard chessView;
	@SuppressWarnings("deprecation")
	public InitTest(Class<?> activityClass) {
		super("com.chess.chessandroid", MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	protected void setUp() throws Exception {  
	       super.setUp(); 
	       MainActivity mainActivity = getActivity();  
	       chessView = new ChessBoard(mainActivity);
	       status = chessView.status;
	    }
	public void testStatus(){
		String mathResult = status.getText().toString();  
		assertEquals("Sprawdzanie czy status został wyświetlony", mathResult);
	}
	public void testBoard(){
		
		assertEquals("Sprawdzanie czy plansza została uruchomiona", MainActivity.class);
	}

}

