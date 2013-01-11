package com.chess.chessandroid;


import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("unused")
public class BoardGameChess {
	
	private static final int EMPTY = 6;
	private static Drawable[][] IMAGE = new Drawable[2][6];
	
	public boolean first = true;
    private boolean przesowaj = false;
    
    private int start_Col;
    private int start_Row;
    
    private final Plansza[][] szachownica = new Plansza[8][8];
	@SuppressWarnings("rawtypes")
	private final Ruchy move = null;
    private final Activity mActivity;
    private final LinearLayout root;
    private final TableLayout table;
    
    public TextView status, profile;
    public TextView white_Marker, black_Marker, white_Move, black_Move;
    public boolean needTo = true;
    
	private static final int[] STARTCOLOR = { 
		  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final int[] STARTPIECE = { 
		  3, 1, 2, 4, 5, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 4, 5, 2, 1, 3 };
	public static final int[] COLUMN_LABEL = { 
		  R.string.a, R.string.b, R.string.c, R.string.d, 
		  R.string.e, R.string.f, R.string.g, R.string.h };
	public static final int[] ROW_LABEL = { 
		  R.string.n8, R.string.n7, R.string.n6, R.string.n5, 
		  R.string.n4, R.string.n3, R.string.n2, R.string.n1 };
	private static final int[][] IMG_FILE_NAME = {
	      { R.drawable.pawn_white, 	 R.drawable.hoper_white,
	        R.drawable.bishop_white, R.drawable.rook_white,
	        R.drawable.queen_withe,  R.drawable.king_white },
	      { R.drawable.pawn_black,   R.drawable.hoper_black,
	        R.drawable.bishop_black, R.drawable.rook_black,
	        R.drawable.queen_black,  R.drawable.king_black } };
	
	
        @SuppressWarnings("deprecation")
		public BoardGameChess(Activity a) {
                mActivity = a;
                loadImg();
                root = (LinearLayout) mActivity.findViewById(R.id.rootLayout);
                root.removeAllViews();
                table = new TableLayout(mActivity);
                root.addView(table);
                table.setStretchAllColumns(true);
                table.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                table.setOrientation(LinearLayout.VERTICAL);
                listaKolumn();
                for (int i = 0; i < 8; i++) {
                    TableRow row = new TableRow(mActivity);
                    table.addView(row);
                    row.setLayoutParams(new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.FILL_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));

                    // wiersze
                    TextView tv = new TextView(mActivity);
                    row.addView(tv);
                    tv.setText(ROW_LABEL[i]);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setHeight(35);
                    tv.setGravity(Gravity.CENTER);

                    // plansza
                    for (int j = 0; j < 8; j++) {
                    	szachownica[i][j] = new Plansza(i, j, this, mActivity);
                            row.addView(szachownica[i][j]);
                    }
            }
                ladujPlansze();
         // profil
            profile = new TextView(mActivity);
            root.addView(profile);
            profile.setTextColor(0xff00ff00);
            profile.setBackgroundColor(0xff000000);

            // ruch
            TableLayout tb = new TableLayout(mActivity);
            root.addView(tb);
            table.setStretchAllColumns(true);
            table.setLayoutParams(new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            TableRow row1 = new TableRow(mActivity);
            tb.addView(row1);
            row1.setLayoutParams(new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            white_Marker = new TextView(mActivity);
            row1.addView(white_Marker);
            white_Marker.setBackgroundColor(0xffffffff);
            white_Marker.setTextColor(0xff000000);
            white_Marker.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            white_Marker.setText("Przesun Bia³y Pionek");
            white_Marker.setGravity(Gravity.CENTER);

            black_Marker = new TextView(mActivity);
            row1.addView(black_Marker);
            black_Marker.setBackgroundColor(0xff000000);
            black_Marker.setTextColor(0xff000000);
            black_Marker.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            black_Marker.setText("Przesun Czarny Pionek");
            black_Marker.setGravity(Gravity.CENTER);

            // moves
            TableRow row2 = new TableRow(mActivity);
            tb.addView(row2);
            row2.setLayoutParams(new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            white_Move = new TextView(mActivity);
            row2.addView(white_Move);
            white_Move.setBackgroundColor(0xff000000);
            white_Move.setTextColor(0xff00ff00);
            white_Move.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            white_Move.setText(" ");
            white_Move.setGravity(Gravity.CENTER);

            black_Move = new TextView(mActivity);
            row2.addView(black_Move);
            black_Move.setBackgroundColor(0xff000000);
            black_Move.setTextColor(0xff00ff00);
            black_Move.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            black_Move.setText(" ");
            black_Move.setGravity(Gravity.CENTER);

            // status
            status = new TextView(mActivity);
            root.addView(status);
            status.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.FILL_PARENT, 1));
            status.setText("\n");
            status.setTextColor(0xff00ff00);
            status.setBackgroundColor(0xff000000);
        }
        
        public void loadImg() {
            for (int i = 0; i < 2; i++) {
            	for (int j = 0; j < 6; j++)
            		IMAGE[i][j] = mActivity.getResources().getDrawable(IMG_FILE_NAME[i][j]);
            }
        }
        private void listaKolumn() {
            TableRow row_1 = new TableRow(mActivity);
            table.addView(row_1);
            for (int i = 0; i < 8; i++) {
                    TextView tv = new TextView(mActivity);
                    tv.setText(COLUMN_LABEL[i]);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setGravity(Gravity.CENTER);
                    if (i == 0) {
                            tv.setLayoutParams(new TableRow.LayoutParams(1));
                    }
                    row_1.addView(tv);
            }
        }
        public void ladujPlansze() {
        	for (int i = 0; i < 8; i++) {
        		for (int j = 0; j < 8; j++) {
        			int c = STARTCOLOR[j + 8 * i];
                    	if (c != 6) {
                    		int p = STARTPIECE[j + 8 * i];
                    		szachownica[i][j].ustawImg(IMAGE[c][p]);
                    		szachownica[i][j].empty = false;
                         } else
                        	 szachownica[i][j].ustawImg(null);
                    }
            }
            first = true;
        }
        
        public void wybierzPozycje(int row, int col, boolean empty) {
    	if (!przesowaj) {
    		if (first) {
               if (!empty) {
            	   start_Col = col;
            	   start_Row = row;
                  first = false;
                  setPodswietlenie(row, col, true);
               	}
             } else {
                 int from = (start_Row << 3) + start_Col;
                 int to = (row << 3) + col;
                 RuchyPodstawowe newMove = new RuchyPodstawowe(from, to);
                 przesowaj = true;
                 try {
                     this.szybkaZamianaPionkow("move", null, newMove);
                     ((MainActivity) mActivity).zmienPozycje(newMove);
                                    // move = (Move) newMove;
                                    // this.firePropertyChange("move", null, move);
                 } catch (Exception e) {
                	 przesowaj = false;
                 }
                 first = true;
              }
            }
        }
        private void szybkaZamianaPionkow(String string, Object object,
				RuchyPodstawowe newMove) {
			// TODO Auto-generated method stub
			
		}
		@SuppressWarnings("deprecation")
		public void setPodswietlenie(int row, int col, boolean highlight) {
			szachownica[row][col].setBackgroundDrawable(null);
			szachownica[row][col].select = highlight;
			szachownica[row][col].ustawSzachownice(highlight);
			szachownica[row][col].postInvalidate();
        }    
		public int wyroznijOkno(boolean whiteMoving) {
	            int index = whiteMoving ? 0 : 1;
	            int choice = 3;
	            return choice + 1;
	    }
		 public void setPrzesun(boolean m) {
			 przesowaj = m;
	    }
		public void wyczysc(int row, int col) {
			szachownica[row][col].ustawImg(null);
	    }
		public void wykinajRuch() {
			wykinajRuch(move);
	    }
		public void wykinajRuch(RuchyPodstawowe move) {
            int fromCol = move.getOdKolumn();
            int fromRow = move.getOdWier();
            szachownica[move.getDoWierszy()][move.getDoKolumn()]
                            .ustawImg(szachownica[fromRow][fromCol].getIcon());
            szachownica[move.getDoWierszy()][move.getDoKolumn()].empty = false;

            setPodswietlenie(fromRow, fromCol, false);
            szachownica[fromRow][fromCol].empty = true;
            szachownica[fromRow][fromCol].ustawImg(null);
		}
		 @SuppressWarnings("rawtypes")
		public void wykinajRuchBialymWyroznij(Ruchy move, int promote, boolean whiteToMove) {
			 szachownica[move.getDoWierszy()][move.getDoKolumn()]
	                            .ustawImg(IMAGE[whiteToMove ? 0 : 1][promote]);
			 szachownica[move.getOdWier()][move.getOdKolumn()].ustawImg(null);
	    }
		 public void pokazRuch(String move, boolean whiteMoved) {
	            if (whiteMoved) {
	            	white_Move.setText(move);
	            	black_Move.setText(null);
	            } else {
	            	white_Move.setText(null);
	            	black_Move.setText(move);
	            }
	    }
		public void zmienZnacznikRuchu(boolean whiteToMove) {
	            if (whiteToMove) {
	            	white_Marker.setTextColor(0xff000000);
	            	white_Marker.setText("Przesun Bia³y Pionek");
	            	black_Marker.setText("Wykonan ruch czarnym");
	            } else {
	            	white_Marker.setText("Wykonano ruch bia³ym");
	            	black_Marker.setTextColor(0xffffffff);
	            	black_Marker.setText("Przesun Czarny Pionek");
	            }
	    }
 }
