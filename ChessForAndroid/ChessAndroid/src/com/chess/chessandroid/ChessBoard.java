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

public class ChessBoard {
	private static final int EMPTY = 6;
    private static final int[] START_COLOR = { 
		  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final int[] START_PIECE = { 
		  3, 1, 2, 4, 5, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		  0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 4, 5, 2, 1, 3 };
	private static final int[][] imageFilename = {
	      { R.drawable.pawn_white, 	 R.drawable.hoper_white,
	        R.drawable.bishop_white, R.drawable.rook_white,
	        R.drawable.queen_withe,  R.drawable.king_white },
	      { R.drawable.pawn_black,   R.drawable.hoper_black,
	        R.drawable.bishop_black, R.drawable.rook_black,
	        R.drawable.queen_black,  R.drawable.king_black } };
	public static final int[] columnLabel = { 
		  R.string.a, R.string.b, R.string.c, R.string.d, 
		  R.string.e, R.string.f, R.string.g, R.string.h };
	public static final int[] rowLabel = { 
		  R.string.n8, R.string.n7, R.string.n6, R.string.n5, 
		  R.string.n4, R.string.n3, R.string.n2, R.string.n1 };
	private static Drawable[][] pieceImage = new Drawable[2][6];
	private final Square[][] square = new Square[8][8];
    public boolean first = true;
    private int startCol;
    private int startRow;
    private boolean moving = false;
    private final Move move = null;
    public boolean needTo = true;
    private final Activity mActivity;
    private final LinearLayout root;
    private final TableLayout table;
    public TextView status, profile;
    public TextView whiteMarker, blackMarker, whiteMove, blackMove;

        @SuppressWarnings("deprecation")
		public ChessBoard(Activity a) {
                mActivity = a;
                loadImages();
                root = (LinearLayout) mActivity.findViewById(R.id.rootLayout);
                root.removeAllViews();
                table = new TableLayout(mActivity);
                root.addView(table);
                table.setStretchAllColumns(true);
                table.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                table.setOrientation(LinearLayout.VERTICAL);
                letterColumn();
                for (int i = 0; i < 8; i++) {
                    TableRow row = new TableRow(mActivity);
                    table.addView(row);
                    row.setLayoutParams(new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.FILL_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));

                    // wiersze
                    TextView tv = new TextView(mActivity);
                    row.addView(tv);
                    tv.setText(rowLabel[i]);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setHeight(35);
                    tv.setGravity(Gravity.CENTER);

                    // plansza
                    for (int j = 0; j < 8; j++) {
                            square[i][j] = new Square(i, j, this, mActivity);
                            row.addView(square[i][j]);
                    }
            }
            setupBoard();
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

            whiteMarker = new TextView(mActivity);
            row1.addView(whiteMarker);
            whiteMarker.setBackgroundColor(0xffffffff);
            whiteMarker.setTextColor(0xff000000);
            whiteMarker.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            whiteMarker.setText("Przesun Bia�y Pionek");
            whiteMarker.setGravity(Gravity.CENTER);

            blackMarker = new TextView(mActivity);
            row1.addView(blackMarker);
            blackMarker.setBackgroundColor(0xff000000);
            blackMarker.setTextColor(0xff000000);
            blackMarker.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            blackMarker.setText("Przesun Czarny Pionek");
            blackMarker.setGravity(Gravity.CENTER);

            // moves
            TableRow row2 = new TableRow(mActivity);
            tb.addView(row2);
            row2.setLayoutParams(new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            whiteMove = new TextView(mActivity);
            row2.addView(whiteMove);
            whiteMove.setBackgroundColor(0xff000000);
            whiteMove.setTextColor(0xff00ff00);
            whiteMove.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            whiteMove.setText(" ");
            whiteMove.setGravity(Gravity.CENTER);

            blackMove = new TextView(mActivity);
            row2.addView(blackMove);
            blackMove.setBackgroundColor(0xff000000);
            blackMove.setTextColor(0xff00ff00);
            blackMove.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.FILL_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            blackMove.setText(" ");
            blackMove.setGravity(Gravity.CENTER);

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
        private void loadImages() {
            for (int i = 0; i < 2; i++) {
            	for (int j = 0; j < 6; j++)
            		pieceImage[i][j] = mActivity.getResources().getDrawable(imageFilename[i][j]);
            }
        }
        private void letterColumn() {
            TableRow row_1 = new TableRow(mActivity);
            table.addView(row_1);
            for (int i = 0; i < 8; i++) {
                    TextView tv = new TextView(mActivity);
                    tv.setText(columnLabel[i]);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setGravity(Gravity.CENTER);
                    if (i == 0) {
                            tv.setLayoutParams(new TableRow.LayoutParams(1));
                    }
                    row_1.addView(tv);
            }
        }
        public void setupBoard() {
        	for (int i = 0; i < 8; i++) {
        		for (int j = 0; j < 8; j++) {
        			int c = START_COLOR[j + 8 * i];
                    	if (c != 6) {
                    		int p = START_PIECE[j + 8 * i];
                             square[i][j].setIcon(pieceImage[c][p]);
                             square[i][j].empty = false;
                         } else
                             square[i][j].setIcon(null);
                    }
            }
            first = true;
        }
        
        public void selected(int row, int col, boolean empty) {
    	if (!moving) {
    		if (first) {
               if (!empty) {
                  startCol = col;
                  startRow = row;
                  first = false;
                  setHighlight(row, col, true);
               	}
             } else {
                 int from = (startRow << 3) + startCol;
                 int to = (row << 3) + col;
                 MoveBase newMove = new MoveBase(from, to);
                 moving = true;
                 try {
                     this.fireVetoableChange("move", null, newMove);
                     ((MainActivity) mActivity).pieceChange(newMove);
                                    // move = (Move) newMove;
                                    // this.firePropertyChange("move", null, move);
                 } catch (Exception e) {
                       moving = false;
                 }
                 first = true;
              }
            }
        }
        private void fireVetoableChange(String string, Object object,
				MoveBase newMove) {
			// TODO Auto-generated method stub
			
		}
		public void setHighlight(int row, int col, boolean highlight) {
            square[row][col].setBackgroundDrawable(null);
            square[row][col].select = highlight;
            square[row][col].setSelect(highlight);
            square[row][col].postInvalidate();
        }    
		public int promotionDialog(boolean whiteMoving) {
	            int index = whiteMoving ? 0 : 1;
	            int choice = 3;
	            return choice + 1;
	    }
		 public void setMoving(boolean m) {
	            moving = m;
	    }
		public void clear(int row, int col) {
	            square[row][col].setIcon(null);
	    }
		public void makeMove() {
	            makeMove(move);
	    }
		public void makeMove(MoveBase move) {
            int fromCol = move.getFromCol();
            int fromRow = move.getFromRow();
            square[move.getToRow()][move.getToCol()]
                            .setIcon(square[fromRow][fromCol].getIcon());
            square[move.getToRow()][move.getToCol()].empty = false;

            setHighlight(fromRow, fromCol, false);
            square[fromRow][fromCol].empty = true;
            square[fromRow][fromCol].setIcon(null);
		}
		 public void makeMoveWithPromote(Move move, int promote, boolean whiteToMove) {
	            square[move.getToRow()][move.getToCol()]
	                            .setIcon(pieceImage[whiteToMove ? 0 : 1][promote]);
	            square[move.getFromRow()][move.getFromCol()].setIcon(null);
	    }
		 public void showMove(String move, boolean whiteMoved) {
	            if (whiteMoved) {
	                    whiteMove.setText(move);
	                    blackMove.setText(null);
	            } else {
	                    whiteMove.setText(null);
	                    blackMove.setText(move);
	            }
	    }
		public void switchMoveMarkers(boolean whiteToMove) {
	            if (whiteToMove) {
	                    whiteMarker.setTextColor(0xff000000);
	                    whiteMarker.setText("Przesun Bia�y Pionek");
	                    blackMarker.setText("Wykonan ruch czarnym");
	            } else {
	                    whiteMarker.setText("Wykonano ruch bia�ym");
	                    blackMarker.setTextColor(0xffffffff);
	                    blackMarker.setText("Przesun Czarny Pionek");
	            }
	    }
     /*    
   public boolean isMoving() {
            return moving;
    }

   

   public Move getMove() {
            return move;
    }

    
    */
}