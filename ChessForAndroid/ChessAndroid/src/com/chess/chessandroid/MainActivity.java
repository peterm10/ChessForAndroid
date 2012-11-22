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


@SuppressWarnings("unused")
public class MainActivity extends Activity implements Constants {
	private Board board = new Board();
    private Search searcher = new Search();
    private ChessBoard chessView;
    private int computerSide = DARK;
    private final int[] playTime = { 3000, 5000, 10000, 20000, 30000, 60000 };
    private final int moves = 0;
    private int maxTime = 5000;
    private Thread thinkThread = null;
    @SuppressWarnings("rawtypes")
	private Move guessedMove = null;

    private TextView status;
    private String statusStr = "";
    @SuppressWarnings("rawtypes")
	private Move curMove = null;
    private boolean whiteToMove = true;
    private boolean whiteMoved = true;
    private String moveStr;

    private final Handler handler = new Handler();

    static final private int MENU_NEW = Menu.FIRST;
    static final private int MENU_SKILL = MENU_NEW + 1;
    static final private int MENU_SKILL_BEGINNER = MENU_SKILL + 1;
    static final private int MENU_SKILL_INTERMEDIATE = MENU_SKILL_BEGINNER + 1;
    static final private int MENU_SKILL_ADVANCED = MENU_SKILL_INTERMEDIATE + 1;
    static final private int MENU_SKILL_MASTER = MENU_SKILL_ADVANCED + 1;
    static final private int MENU_NEXT = MENU_SKILL_MASTER + 1;
    static final private int MENU_GROUP_SKILL = 1;
    String idun = "a149d4582f745a5";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        playNewGame();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuItem itemNew = menu.add(0, MENU_NEW, Menu.NONE, "Nowa Gra");
      /*  SubMenu itemSkill = menu.addSubMenu(MENU_GROUP_SKILL, MENU_SKILL,Menu.NONE, "Poziom");
            	itemSkill.add(MENU_GROUP_SKILL, this.MENU_SKILL_BEGINNER, Menu.NONE,"Pocz�tkuj�cy");
            	itemSkill.add(MENU_GROUP_SKILL, this.MENU_SKILL_INTERMEDIATE,Menu.NONE, "�rednio Zaawansowany");
                itemSkill.add(MENU_GROUP_SKILL, this.MENU_SKILL_ADVANCED, Menu.NONE,"Zaawansowany");
                itemSkill.add(MENU_GROUP_SKILL, this.MENU_SKILL_MASTER, Menu.NONE,"Gracz zawodowy");
                itemSkill.setGroupCheckable(MENU_GROUP_SKILL, true, true);*/
       MenuItem itemNext = menu.add(0, this.MENU_NEXT, Menu.NONE, "Nast�pny ruch");
       itemNew.setIcon(null);
      // itemSkill.setIcon(null);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case MENU_NEW:
                    init();
                    playNewGame();
                    return true;
            case MENU_SKILL_BEGINNER:
                    setMaxTime(5000);
                    return true;
            case MENU_SKILL_INTERMEDIATE:
                    setMaxTime(10000);
                    return true;
            case MENU_SKILL_ADVANCED:
                    setMaxTime(30000);
                    return true;
            case MENU_SKILL_MASTER:
                    setMaxTime(60000);
                    return true;
            case MENU_NEXT:
                    nextMove();
                    return true;
            }
            return false;
    }

    private void init() {
            chessView = new ChessBoard(this);
            status = chessView.status;
           // setMaxTime(5000);
    }
    public void playNewGame() {
            stop();
            computerSide = DARK;
            guessedMove = null;
            searcher = new Search();
            board = new Board();
            chessView.setupBoard();
    }
    public void stop() {
            searcher.stopThinking();
    }
    public void setMaxTime(int millis) {
            maxTime = millis;
            switch (maxTime) {
            case 5000:
                    chessView.profile.setText("Poziom: Pocz�tkuj�cy");
                    break;
            case 10000:
                    chessView.profile.setText("Poziom: Srednio Zaawansowany");
                    break;
            case 30000:
                    chessView.profile.setText("Poziom: Zaawansowany");
                    break;
            case 60000:
                    chessView.profile.setText("Poziom: Gracz Zawosowy ");
                    break;
            }
    }
    private void nextMove() {
    }
    public void pieceChange(MoveBase mo) {
        MoveBase move = mo;
        if (move == null)
                return;
        int promote = 0;
        int to = move.getTo();
        int from = move.getFrom();
        if ((((to < 8) && (board.side == LIGHT)) || ((to > 55) && (board.side == DARK)))
                        && (board.getPiece(from) == PAWN)) {
                promote = chessView.promotionDialog(board.side == LIGHT);
        }
        boolean found = false;
        Collection validMoves = board.gen();
        Iterator i = validMoves.iterator();
        Move m = null;
        while (i.hasNext()) {
                m = (Move) i.next();
                if (m.getFrom() == from && m.getTo() == to && m.promote == promote) {
                        found = true;
                        break;
                }
        }
        if (!found || !board.makeMove(m)) {
                showStatus("Nie mo�na wykona� ruchu!");
                chessView.setHighlight(mo.getFromRow(), mo.getFromCol(), false);
                chessView.setMoving(false);
        } else {
                setMove(m);
                showMove(m.toString(), board.side == DARK);
                switchMoveMarkers(board.side == LIGHT);

                if (isResult())
                        return;

                if (board.side == computerSide) {
                        if (guessedMove != null) {
                                if (!m.equals(guessedMove)) {
                                        searcher.stopThinking();
                                        try {
                                                thinkThread.join();
                                        } catch (InterruptedException ignore) {

                                        }
                                        searcher.restartThinking();
                                        searcher.board.takeBack();
                                        searcher.board.makeMove(m);
                                        searcher.clearPV();
                                        thinkThread = new Thinker();
                                        thinkThread.start();
                                }
                        } else {
                                searcher.clearPV();
                                searcher.board.makeMove(m);
                                thinkThread = new Thinker();
                                thinkThread.start();
                        }
                        searcher.setStopTime(System.currentTimeMillis() + maxTime);
                }
        }
    }
    public void showStatus(String s) {
        statusStr = "Status: " + s;
        handler.post(doShowStatus);
    }
    private void setMove(Move m) {
        curMove = m;
        handler.post(doMakeMove);
    }
    private void showMove(String move, boolean b) {
        moveStr = move;
        whiteMoved = b;
        handler.post(doShowMove);
    }
    private void switchMoveMarkers(boolean b) {
        whiteToMove = b;
        handler.post(doSwitchMoveMarkers);
        showStatus("");
    }
    private boolean isResult() {
        Collection validMoves = board.gen();

        Iterator i = validMoves.iterator();
        boolean found = false;
        while (i.hasNext()) {
                if (board.makeMove((Move) i.next())) {
                        board.takeBack();
                        found = true;
                        break;
                }
        }
        String message = null;
        if (!found) {
                if (board.inCheck(board.side)) {
                        if (board.side == LIGHT)
                                message = "0 - 1 Czarny Mat";
                        else
                                message = "1 - 0 Bia�y Mat";
                } else
                        message = "0 - 0 Remis";
        } else if (board.reps() == 3)
                message = "1/2 - 1/2 Remis Powt�rka";
        else if (board.fifty >= 100)
                message = "1/2 - 1/2 Remis po 50 ruchach";
        if (message != null) {
                showStatus(message);
                // TODO option for start a new game
                searcher.stopThinking();
                setChoice4NewGame();
                return true;
        }
        if (board.inCheck(board.side))
                showStatus("Sprawdzam!");
        return false;
    }
    private void think() {
        searcher.think(this);
    }
    final class Thinker extends Thread {
        @Override
        public void run() {
                think();
                if (searcher.isStopped())
                        return;
                final Move best = searcher.getBest();
                if (best == null) {
                        showStatus("Z�y ruch");
                        computerSide = EMPTY;
                        return;
                }
                board.makeMove(best);
                searcher.board.makeMove(best);
                showMove(best.toString(), board.side == DARK);
                setMove(best);
                switchMoveMarkers(board.side == LIGHT);
                isResult();
                chessView.setMoving(false);
                /*
                 * guessedMove = searcher.getBestNext(); if (guessedMove != null) {
                 * searcher.board.makeMove(guessedMove);
                 * searcher.setStopTime(Long.MAX_VALUE); searcher.shiftPV();
                 * thinkThread = new Thinker(); thinkThread.start(); }
               */
        }
	}
    private void choice4NewGame() {
        AlertDialog.Builder alert1 = new AlertDialog.Builder(this).setTitle(
                        "Koniec Gry").setMessage("Chcesz rozpocz�� now� gre?")
                        .setPositiveButton("Tak",
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                int whichButton) {
                                                        playNewGame();
                                                }
                                        }).setNegativeButton("Nie",
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                int whichButton) {
                                                        // Put your code in here for a negative response
                                                }
                                        });
        alert1.show();
    }
    private void makeMove(Move m) {
        int from = m.getFrom();
        int to = m.getTo();
        if (m.promote != 0) {
                chessView.makeMoveWithPromote(m, m.promote, board.side != LIGHT);
        } else {
                if ((m.bits & 2) != 0) {
                        if (from == E1 && to == G1)
                                chessView.makeMove(new MoveBase(H1, F1));
                        else if (from == E1 && to == C1)
                                chessView.makeMove(new MoveBase(A1, D1));
                        else if (from == E8 && to == G8)
                                chessView.makeMove(new MoveBase(H8, F8));
                        else if (from == E8 && to == C8)
                                chessView.makeMove(new MoveBase(A8, D8));
                } else if ((m.bits & 4) != 0) {
                        if (board.xside == LIGHT)
                                chessView.clear(m.getToRow() + 1, m.getToCol());
                        else
                                chessView.clear(m.getToRow() - 1, m.getToCol());
                }
                chessView.makeMove(m);
        }
    }
	private final Runnable doShowMove = new Runnable() {
            public void run() {
                    chessView.showMove(moveStr, whiteMoved);
            }
	};
	private void setChoice4NewGame() {
        handler.post(doChoice4NewGame);
	}
	private final Runnable doShowStatus = new Runnable() {
        public void run() {
                status.setText(statusStr);
        }
	};
	private final Runnable doMakeMove = new Runnable() {
        public void run() {
                makeMove(curMove);
        }
	};
	private final Runnable doSwitchMoveMarkers = new Runnable() {
        public void run() {
                chessView.switchMoveMarkers(whiteToMove);
        }
	};
	private final Runnable doChoice4NewGame = new Runnable() {
        public void run() {
                choice4NewGame();
        }
	};
    
/*	@Override
    protected void onStart() {
            super.onStart();
    }

    @Override
    protected void onPause() {
            super.onStop();
            stop();
    } 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                            || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
                            || newConfig.orientation == Configuration.ORIENTATION_SQUARE
                            || newConfig.orientation == Configuration.ORIENTATION_UNDEFINED) {

            }
    }

    private void computerMove() {
            searcher.stopThinking();
            try {
                    if (thinkThread != null)
                            thinkThread.join();
            } catch (InterruptedException ignore) {
            }
            searcher.restartThinking();
            searcher.clearPV();
            thinkThread = new Thinker();
            thinkThread.start();
            searcher.setStopTime(System.currentTimeMillis() + maxTime);
    }

    

    

    

    
    */
}