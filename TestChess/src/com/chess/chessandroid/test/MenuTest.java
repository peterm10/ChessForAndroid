package com.chess.chessandroid.test;

import java.util.Random;

import com.chess.chessandroid.Board;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.R;
import com.chess.chessandroid.Search;
import com.chess.chessandroid.Square;
import com.jayway.android.robotium.solo.Solo;



import android.test.ActivityInstrumentationTestCase2;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import junit.framework.Assert;
import junit.framework.TestCase;


public class MenuTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private Board board;
	private Search searcher = new Search();
	private Solo solo;
	   
	public MenuTest(){
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
	@SuppressWarnings("deprecation")
	public void testMenu1(){
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Nowa Gra");
		Assert.assertTrue(solo.searchText(""));
		
	}
	@SuppressWarnings("deprecation")
	public void testMenu2(){
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Nastêpny ruch");
		Assert.assertTrue(solo.searchText(""));
		
	}
	@Override
	  public void tearDown() throws Exception {
	    try {
	      solo.finalize();
	    } catch (Throwable e) {

	      e.printStackTrace();
	    }
	    getActivity().finish();
	    super.tearDown();
	  }
}
