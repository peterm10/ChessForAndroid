package com.chess.chessandroid.test;

import java.util.Random;

import com.chess.chessandroid.BoardGame;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.R;
import com.chess.chessandroid.PrzeszukajPlansze;
import com.chess.chessandroid.Plansza;
import com.jayway.android.robotium.solo.Solo;



import android.test.ActivityInstrumentationTestCase2;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import junit.framework.Assert;
import junit.framework.TestCase;


public class MenuTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private BoardGame board;
	private PrzeszukajPlansze searcher = new PrzeszukajPlansze();
	private Solo solo;
	   
	public MenuTest(){
		super("com.chess.chessandroid",MainActivity.class);
	}
	protected void setUp() throws Exception{
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
		searcher = new PrzeszukajPlansze();
        board = new BoardGame();
        
        
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
