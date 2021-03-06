package com.chess.chessandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;



@SuppressLint("ViewConstructor")
@SuppressWarnings("unused")
public class Plansza extends ImageView implements OnTouchListener{
    
	private Context mContext;
    private int backgroundColor;
    
    private final int x;
    private final int y;
    
    private final BoardGameChess board;
    
    public boolean select = false;
    public boolean empty;
   
    public Plansza(int y, int x, BoardGameChess b, Context c) {
        super(c);
               
        this.x = x;
        this.y = y;
        mContext = c;
        board = b;
       
        this.setAdjustViewBounds(true);
        this.setMaxHeight(35);
        this.setMaxWidth(25);
        this.setScaleType(ImageView.ScaleType.FIT_XY);
       
        if ((x + y) % 2 == 0) {                
                this.setBackgroundResource(R.drawable.white);
                this.setImageResource(R.drawable.white);
                this.backgroundColor = 0;
        } else {                
                this.setBackgroundResource(R.drawable.black);
                this.setImageResource(R.drawable.black);
                this.backgroundColor = 1;
        }  
        this.setId(x*10+y);
        this.setOnTouchListener(this);
       
        empty = true;
    }
    public Drawable getIcon() {
        return this.getDrawable();
    }
    @Override
    protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
    }
    public void ustawSzachownice(boolean select) {
        if (select) {                  
                if (backgroundColor == 0)
                        this.setBackgroundResource(R.drawable.white_h);
                else
                        this.setBackgroundResource(R.drawable.black_h);                
        } else {                        
                if (backgroundColor == 0)
                        this.setBackgroundResource(R.drawable.white);
                else
                        this.setBackgroundResource(R.drawable.black);
        }                              
}
    public void ustawImg(Drawable d) {  
        if (d != null)
                this.setImageDrawable(d);
        else {
                this.setImageDrawable(this.getBackground());                    
           }
    }
   
    public boolean onTouch(View v, MotionEvent event) {            
                switch(event.getAction()) {
                case MotionEvent.ACTION_UP:                    
                        break;
                case MotionEvent.ACTION_DOWN:                                          
                        board.wybierzPozycje(y, x, empty);
                        break;
                case MotionEvent.ACTION_MOVE:                  
                        break;
                default:
                        break;
                }
                return false;
        }
       
        
       
        
}

