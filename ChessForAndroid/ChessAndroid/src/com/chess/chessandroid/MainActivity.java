package com.chess.chessandroid;

import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;


import com.google.ads.AdView;


public class MainActivity extends Activity implements Constants {
	private ChessBoard chessView;
    private TextView status;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_c);
        //init();
       // playNewGame();

       // (new AdView(this)).startSnack();


    }

    private void init() {
            chessView = new ChessBoard(this);
            status = chessView.status;
            //setMaxTime(5000);
    }

}


