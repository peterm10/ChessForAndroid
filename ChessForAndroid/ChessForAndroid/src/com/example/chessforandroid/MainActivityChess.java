package com.example.chessforandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivityChess extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_chess);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_activity_chess, menu);
        return true;
    }
}
