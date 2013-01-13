package com.chess.chessandroid.test;

import android.test.ActivityInstrumentationTestCase2;

import com.chess.chessandroid.BoardGame;
import com.chess.chessandroid.BoardGameChess;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.PrzeszukajPlansze;
import com.chess.chessandroid.Plansza;
import com.jayway.android.robotium.solo.Solo;

import junit.framework.*;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BoardTest extends ActivityInstrumentationTestCase2<MainActivity>{
	private BoardGameChess chessViewTes;
	private MainActivity mActivity;
	private BoardGame board;
	private PrzeszukajPlansze searcher;
	private Solo solo;
	private EditText etValue1;
    private EditText etValue2;
    private TextView tvResult;
    private Button btnPlus;
    private Button btnMinus;
      
	public BoardTest(){
		super("com.chess.chessandroid",MainActivity.class);
	}
	protected void setUp() throws Exception{
		super.setUp();
		mActivity = getActivity();
		solo = new Solo(getInstrumentation(),getActivity());
		//searcher = new Search();
        board = new BoardGame();
        
        
	}
	public void testApp(){
		solo.assertCurrentActivity("Sprawdzanie czy dobrz aplikacja jest wlaczona", MainActivity.class);
	}
	public void testBo(){
		assertNotNull(getActivity());
		chessViewTes = new BoardGameChess(mActivity);
	}
	
	
	
	
	/*   private OnClickListener operationListener = new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            int value1 = Integer.parseInt(etValue1.getText().toString());
	            int value2 = Integer.parseInt(etValue2.getText().toString());
	            Integer result = null;
	            switch (v.getId()) {
	            case R.id.a1:
	                result = value1 + value2;
	                break;
	            case R.id.btnMinus:
	                result = value1 * value2;
	                break;
	            default:
	                break;
	            }
	            tvResult.setText(result.toString());
	        }
	    };*/
}
