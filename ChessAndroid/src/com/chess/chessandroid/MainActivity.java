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
	
	private PrzeszukajPlansze szukaj = new PrzeszukajPlansze();
    private BoardGame board = new BoardGame();
    private BoardGameChess chessBoardView;
    private Thread czekajWatek = null;
    @SuppressWarnings("rawtypes")
	private Ruchy ruchyG = null;
    private TextView status;
    private String statusStr = "";
    private String moveStr;
    @SuppressWarnings("rawtypes")
	private Ruchy prawidlowyRuch = null;
    
    private int maxCzasCzekania = 5000;
    private int czarnyPC = CZARNE;
    
    private boolean bialyPrzesunienty = true;
    private boolean bialyRuch = true;
    
    private final int ruchyPionka = 0;//moves
    private final Handler handler = new Handler();

    static final private int MENUNEW = Menu.FIRST;
    static final private int MENUNEXT = MENUNEW + 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        chessGameStart();
        newGame();
    }
    @SuppressWarnings("static-access")
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuItem gameNew = menu.add(0, MENUNEW, Menu.NONE, "Nowa Gra");
    	MenuItem gameNext = menu.add(0, this.MENUNEXT, Menu.NONE, "Nastêpny ruch");
    	gameNew.setIcon(null);
      return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case MENUNEW:
            	chessGameStart();
            	newGame();
                    return true;
            case MENUNEXT:
               //     nextMove();
                    return true;
            }
            return false;
    }

    public void chessGameStart() {
    	chessBoardView = new BoardGameChess(this);
        status = chessBoardView.status;
    }
    public void newGame() {
            stop();
            czarnyPC = CZARNE;
            ruchyG = null;
            szukaj = new PrzeszukajPlansze();
            board = new BoardGame();
            chessBoardView.ladujPlansze();
    }
    public void stop() {
    	szukaj.koniecOczekiwania();
    }
    //private void nextMove() {}
    
    @SuppressWarnings("rawtypes")
	public void zmienPozycje(RuchyPodstawowe mo) {
        RuchyPodstawowe move = mo;
        if (move == null)
                return;
        int wyrozniaj = 0;
        int ruchDo = move.getDo();
        int rychOd = move.getOd();
        if ((((ruchDo < 8) && (board.bialy == BIALE)) || ((ruchDo > 55) && (board.bialy == CZARNE)))
                        && (board.getPionek(rychOd) == PIONEK)) {
        	wyrozniaj = chessBoardView.wyroznijOkno(board.bialy == BIALE);
        }
        boolean znajdz = false;
        Collection validMoves = board.generujRuch();
        Iterator i = validMoves.iterator();
        Ruchy m = null;
        while (i.hasNext()) {
                m = (Ruchy) i.next();
                if (m.getOd() == rychOd && m.getDo() == ruchDo && m.wyroznij == wyrozniaj) {
                	znajdz = true;
                        break;
                }
        }
        if (!znajdz || !board.wykonajRuch(m)) {
        	pokazStatus("Nie mo¿na wykonaæ ruchu!");
                chessBoardView.setPodswietlenie(mo.getOdWier(), mo.getOdKolumn(), false);
                chessBoardView.setPrzesun(false);
        } else {
        	setRuchy(m);
        	pokazRuchy(m.toString(), board.bialy == CZARNE);
        	zmienZnacznikRuchu(board.bialy == BIALE);

                if (wynikRuchow())
                        return;

                if (board.bialy == czarnyPC) {
                        if (ruchyG != null) {
                                if (!m.equals(ruchyG)) {
                                	szukaj.ponownieOczekuj();
                                        try {
                                        	czekajWatek.join();
                                        } catch (InterruptedException ignore) {

                                        }
                                        szukaj.ponownieOczekuj();
                                        szukaj.board.cofnijRuch();
                                        szukaj.board.wykonajRuch(m);
                                        szukaj.wyczyscRuchyPV();
                                        czekajWatek = new SzukajRozwiazania();
                                        czekajWatek.start();
                                }
                        } else {
                        	szukaj.wyczyscRuchyPV();
                        	szukaj.board.wykonajRuch(m);
                        	czekajWatek = new SzukajRozwiazania();
                            czekajWatek.start();
                        }
                        szukaj.setZaczymajOdliczanie(System.currentTimeMillis() + maxCzasCzekania);
                }
        }
    }
    public void pokazStatus(String s) {
        statusStr = "Status: " + s;
        handler.post(pokazStatusRunnable);
    }
    @SuppressWarnings("rawtypes")
	private void setRuchy(Ruchy m) {
    	prawidlowyRuch = m;
        handler.post(wykonajRuchRunnable);
    }
    private void pokazRuchy(String move, boolean b) {
        moveStr = move;
        bialyRuch = b;
        handler.post(pokazRuchyRunnable);
    }
    private void zmienZnacznikRuchu(boolean b) {
    	bialyPrzesunienty = b;
        handler.post(wybierzZnacznikRuchuRunnable);
        pokazStatus("");
    }
    @SuppressWarnings("rawtypes")
	private boolean wynikRuchow() {
        Collection validMoves = board.generujRuch();

        Iterator i = validMoves.iterator();
        boolean znajdz = false;
        while (i.hasNext()) {
                if (board.wykonajRuch((Ruchy) i.next())) {
                        board.cofnijRuch();
                        znajdz = true;
                        break;
                }
        }
        String message = null;
        if (!znajdz) {
                if (board.sprawdzAtak(board.bialy)) {
                        if (board.bialy == BIALE)
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
        	pokazStatus(message);
                // TODO option for start a new game
                szukaj.ponownieOczekuj();
                wybierz2NewGame();
                return true;
        }
        if (board.sprawdzAtak(board.bialy))
        	pokazStatus("Sprawdzam!");
        return false;
    }
    private void oczekuj() {
    	szukaj.oczekujKlasaSzukaj(this);
    }
    final class SzukajRozwiazania extends Thread {
        @SuppressWarnings("rawtypes")
		@Override
        public void run() {
        	oczekuj();
                if (szukaj.zatrzymajOczekiwanie())
                        return;
                final Ruchy best = szukaj.getNajlepszyRuch();
                if (best == null) {
                	pokazStatus("Z³y ruch");
                        czarnyPC = PUSTE;
                        return;
                }
                board.wykonajRuch(best);
                szukaj.board.wykonajRuch(best);
                pokazRuchy(best.toString(), board.bialy == CZARNE);
                setRuchy(best);
                zmienZnacznikRuchu(board.bialy == BIALE);
                wynikRuchow();
                chessBoardView.setPrzesun(false);
                
        }
	}
    private void wybierzNewGame() {
        AlertDialog.Builder alert1 = new AlertDialog.Builder(this).setTitle(
                        "Koniec Gry").setMessage("Chcesz rozpocz¹æ now¹ gre?")
                        .setPositiveButton("Tak",
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                int whichButton) {
                                                	newGame();
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
	private void wykonajRuch(Ruchy m) {
        int rychOd = m.getOd();
        int ruchDo = m.getDo();
        if (m.wyroznij != 0) {
        	chessBoardView.wykinajRuchBialymWyroznij(m, m.wyroznij, board.bialy != BIALE);
        } else {
                if ((m.bits & 2) != 0) {
                        if (rychOd == E1 && ruchDo == G1)
                        	chessBoardView.wykinajRuch(new RuchyPodstawowe(H1, F1));
                        else if (rychOd == E1 && ruchDo == C1)
                        	chessBoardView.wykinajRuch(new RuchyPodstawowe(A1, D1));
                        else if (rychOd == E8 && ruchDo == G8)
                        	chessBoardView.wykinajRuch(new RuchyPodstawowe(H8, F8));
                        else if (rychOd == E8 && ruchDo == C8)
                        	chessBoardView.wykinajRuch(new RuchyPodstawowe(A8, D8));
                } else if ((m.bits & 4) != 0) {
                        if (board.czarny == BIALE)
                        	chessBoardView.wyczysc(m.getDoWierszy() + 1, m.getDoKolumn());
                        else
                        	chessBoardView.wyczysc(m.getDoWierszy() - 1, m.getDoKolumn());
                }
                chessBoardView.wykinajRuch(m);
        }
    }
	private final Runnable pokazRuchyRunnable = new Runnable() {
            public void run() {
            	chessBoardView.pokazRuch(moveStr, bialyRuch);
            }
	};
	private void wybierz2NewGame() {
        handler.post(wybierzNewGameRunnable);
	}
	private final Runnable pokazStatusRunnable = new Runnable() {
        public void run() {
                status.setText(statusStr);
        }
	};
	private final Runnable wykonajRuchRunnable = new Runnable() {
        public void run() {
        	wykonajRuch(prawidlowyRuch);
        }
	};
	private final Runnable wybierzZnacznikRuchuRunnable = new Runnable() {
        public void run() {
        	chessBoardView.zmienZnacznikRuchu(bialyPrzesunienty);
        }
	};
	private final Runnable wybierzNewGameRunnable = new Runnable() {
        public void run() {
        	wybierzNewGame();
        }
	};
    }
