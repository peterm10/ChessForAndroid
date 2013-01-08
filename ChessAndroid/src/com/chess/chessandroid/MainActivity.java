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
public class MainActivity extends Activity implements Sta³eConst {
	private BoardGame board = new BoardGame();
    private PrzeszukajPlansze searcher = new PrzeszukajPlansze();
    private BoardGameChess chessView;
    private int computerSide = DARK;
    private final int moves = 0;
    private int maxTime = 5000;
    private Thread thinkThread = null;
    @SuppressWarnings("rawtypes")
	private Ruchy guessedMove = null;

    private TextView status;
    private String statusStr = "";
    @SuppressWarnings("rawtypes")
	private Ruchy curMove = null;
    private boolean whiteToMove = true;
    private boolean whiteMoved = true;
    private String moveStr;

    private final Handler handler = new Handler();

    static final private int MENU_NEW = Menu.FIRST;
    static final private int MENU_NEXT = MENU_NEW + 1;
    static final private int MENU_GROUP_SKILL = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        playNewGame();
    }
    @SuppressWarnings("static-access")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuItem itemNew = menu.add(0, MENU_NEW, Menu.NONE, "Nowa Gra");
       MenuItem itemNext = menu.add(0, this.MENU_NEXT, Menu.NONE, "Nastêpny ruch");
       itemNew.setIcon(null);
      return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case MENU_NEW:
                    init();
                    playNewGame();
                    return true;
            case MENU_NEXT:
                    nextMove();
                    return true;
            }
            return false;
    }

    public void init() {
            chessView = new BoardGameChess(this);
            status = chessView.status;
    }
    public void playNewGame() {
            stop();
            computerSide = DARK;
            guessedMove = null;
            searcher = new PrzeszukajPlansze();
            board = new BoardGame();
            chessView.setupBoard();
    }
    public void stop() {
            searcher.stopThinking();
    }
    private void nextMove() {
    }
    @SuppressWarnings("rawtypes")
	public void pieceChange(RuchyPodstawowe mo) {
        RuchyPodstawowe move = mo;
        if (move == null)
                return;
        int promote = 0;
        int to = move.getTo();
        int from = move.getFrom();
        if ((((to < 8) && (board.side == LIGHT)) || ((to > 55) && (board.side == DARK)))
                        && (board.getPionek(from) == PAWN)) {
                promote = chessView.promotionDialog(board.side == LIGHT);
        }
        boolean found = false;
        Collection validMoves = board.generujRuch();
        Iterator i = validMoves.iterator();
        Ruchy m = null;
        while (i.hasNext()) {
                m = (Ruchy) i.next();
                if (m.getFrom() == from && m.getTo() == to && m.promote == promote) {
                        found = true;
                        break;
                }
        }
        if (!found || !board.wykonajRuch(m)) {
                showStatus("Nie mo¿na wykonaæ ruchu!");
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
                                        searcher.board.cofnijRuch();
                                        searcher.board.wykonajRuch(m);
                                        searcher.clearPV();
                                        thinkThread = new Thinker();
                                        thinkThread.start();
                                }
                        } else {
                                searcher.clearPV();
                                searcher.board.wykonajRuch(m);
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
    @SuppressWarnings("rawtypes")
	private void setMove(Ruchy m) {
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
    @SuppressWarnings("rawtypes")
	private boolean isResult() {
        Collection validMoves = board.generujRuch();

        Iterator i = validMoves.iterator();
        boolean found = false;
        while (i.hasNext()) {
                if (board.wykonajRuch((Ruchy) i.next())) {
                        board.cofnijRuch();
                        found = true;
                        break;
                }
        }
        String message = null;
        if (!found) {
                if (board.sprawdzAtak(board.side)) {
                        if (board.side == LIGHT)
                                message = "0 - 1 Czarny Mat";
                        else
                                message = "1 - 0 Bia³y Mat";
                } else
                        message = "0 - 0 Remis";
        } else if (board.liczbaPowtPoz() == 3)
                message = "1/2 - 1/2 Remis Powtórka";
        else if (board.fifty >= 100)
                message = "1/2 - 1/2 Remis po 50 ruchach";
        if (message != null) {
                showStatus(message);
                // TODO option for start a new game
                searcher.stopThinking();
                setChoice4NewGame();
                return true;
        }
        if (board.sprawdzAtak(board.side))
                showStatus("Sprawdzam!");
        return false;
    }
    private void think() {
        searcher.think(this);
    }
    final class Thinker extends Thread {
        @SuppressWarnings("rawtypes")
		@Override
        public void run() {
                think();
                if (searcher.isStopped())
                        return;
                final Ruchy best = searcher.getBest();
                if (best == null) {
                        showStatus("Z³y ruch");
                        computerSide = EMPTY;
                        return;
                }
                board.wykonajRuch(best);
                searcher.board.wykonajRuch(best);
                showMove(best.toString(), board.side == DARK);
                setMove(best);
                switchMoveMarkers(board.side == LIGHT);
                isResult();
                chessView.setMoving(false);
                
        }
	}
    private void choice4NewGame() {
        AlertDialog.Builder alert1 = new AlertDialog.Builder(this).setTitle(
                        "Koniec Gry").setMessage("Chcesz rozpocz¹æ now¹ gre?")
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
    @SuppressWarnings("rawtypes")
	private void makeMove(Ruchy m) {
        int from = m.getFrom();
        int to = m.getTo();
        if (m.promote != 0) {
                chessView.makeMoveWithPromote(m, m.promote, board.side != LIGHT);
        } else {
                if ((m.bits & 2) != 0) {
                        if (from == E1 && to == G1)
                                chessView.makeMove(new RuchyPodstawowe(H1, F1));
                        else if (from == E1 && to == C1)
                                chessView.makeMove(new RuchyPodstawowe(A1, D1));
                        else if (from == E8 && to == G8)
                                chessView.makeMove(new RuchyPodstawowe(H8, F8));
                        else if (from == E8 && to == C8)
                                chessView.makeMove(new RuchyPodstawowe(A8, D8));
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
    }
