package com.chess.chessandroid.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import com.chess.chessandroid.BoardGame;
import com.chess.chessandroid.BoardGameChess;
import com.chess.chessandroid.MainActivity;
import com.chess.chessandroid.Ruchy;
import com.chess.chessandroid.PrzeszukajPlansze;
import com.chess.chessandroid.RuchyPodstawowe;
import com.chess.chessandroid.Sta³eConst;
import com.jayway.android.robotium.solo.Solo;


import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class PiecesTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity,mActivity1;
	private BoardGame board;
	private BoardGameChess chessViewTest;
	private Ruchy<?> mov=null;
	private Solo solo;
	private static Drawable[][] pieceImage = new Drawable[2][6];
	private PrzeszukajPlansze searcher = new PrzeszukajPlansze();
	private TextView status;
	
	protected RuchyPodstawowe ruch;
	   
	@SuppressWarnings("deprecation")
	public PiecesTest(){
		super("com.chess.chessandroid",MainActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
		searcher = new PrzeszukajPlansze();
        board = new BoardGame();
        mActivity1 = getActivity();
		
        
        
	}
	public void testApp(){
		solo.assertCurrentActivity("Sprawdzanie czy dobrz aplikacja jest wlaczona", MainActivity.class);
	}
	public void testInit(){
		//chessView = new ChessBoard(mActivity);
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		assertEquals(1, board.getColor(1));
		assertEquals(6, board.getColor(17));
		assertEquals(0, board.getColor(49));
		 
		
	}
	public void testPieces(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		//sprawdzanie czy na pozycji A8 jest ROOK
		assertEquals(3, board.getPionek(0));
		//sprawdzanie czy na pozycji B8 jest KNIGHT
		assertEquals(1, board.getPionek(1));
		//sprawdzanie czy na pozycji C8 jest BISHOP
		assertEquals(2, board.getPionek(2));
		//sprawdzanie czy na pozycji D8 jest QUEEN
		assertEquals(4, board.getPionek(3));
		//sprawdzanie czy na pozycji E8 jest KING
		assertEquals(5, board.getPionek(4));
		//sprawdzanie czy sa wystawione pionki
		for(int i=9;i<16;i++){
		assertEquals(0, board.getPionek(i));
		}
		//sprawdzanie czy pola srodkowe s¹ puste
		for(int i=17;i<48;i++){
		assertEquals(6, board.getPionek(i));
		}				 
		
	}
	@SuppressWarnings("static-access")
	public void testROW(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(0, board.ROW(0));
		assertEquals(0, board.ROW(1));
		assertEquals(0, board.ROW(2));
		assertEquals(0, board.ROW(3));
		assertEquals(0, board.ROW(4));
		assertEquals(0, board.ROW(5));
		assertEquals(0, board.ROW(6));
		assertEquals(0, board.ROW(7));
		
		assertEquals(1, board.ROW(8));
		assertEquals(1, board.ROW(9));
		assertEquals(1, board.ROW(10));
		assertEquals(1, board.ROW(11));
		assertEquals(1, board.ROW(12));
		assertEquals(1, board.ROW(13));
		assertEquals(1, board.ROW(14));
		assertEquals(1, board.ROW(15));
		
		assertEquals(2, board.ROW(16));
		assertEquals(2, board.ROW(17));
		assertEquals(2, board.ROW(18));
		assertEquals(2, board.ROW(19));
		assertEquals(2, board.ROW(20));
		assertEquals(2, board.ROW(21));
		assertEquals(2, board.ROW(22));
		assertEquals(2, board.ROW(23));
		
		assertEquals(3, board.ROW(24));
		assertEquals(3, board.ROW(25));
		assertEquals(3, board.ROW(26));
		assertEquals(3, board.ROW(27));
		assertEquals(3, board.ROW(28));
		assertEquals(3, board.ROW(29));
		assertEquals(3, board.ROW(30));
		assertEquals(3, board.ROW(31));
		
		assertEquals(4, board.ROW(32));
		assertEquals(4, board.ROW(33));
		assertEquals(4, board.ROW(34));
		assertEquals(4, board.ROW(35));
		assertEquals(4, board.ROW(36));
		assertEquals(4, board.ROW(37));
		assertEquals(4, board.ROW(38));
		assertEquals(4, board.ROW(39));
		
		assertEquals(5, board.ROW(40));
		assertEquals(5, board.ROW(41));
		assertEquals(5, board.ROW(42));
		assertEquals(5, board.ROW(43));
		assertEquals(5, board.ROW(44));
		assertEquals(5, board.ROW(45));
		assertEquals(5, board.ROW(46));
		assertEquals(5, board.ROW(47));
		
		assertEquals(6, board.ROW(48));
		assertEquals(6, board.ROW(49));
		assertEquals(6, board.ROW(50));
		assertEquals(6, board.ROW(51));
		assertEquals(6, board.ROW(52));
		assertEquals(6, board.ROW(53));
		assertEquals(6, board.ROW(54));
		assertEquals(6, board.ROW(55));
		
		assertEquals(7, board.ROW(56));
		assertEquals(7, board.ROW(57));
		assertEquals(7, board.ROW(58));
		assertEquals(7, board.ROW(59));
		assertEquals(7, board.ROW(60));
		assertEquals(7, board.ROW(61));
		assertEquals(7, board.ROW(62));
		assertEquals(7, board.ROW(63));
		
	}
	@SuppressWarnings("static-access")
	public void testCol(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		assertEquals(1, board.COL(1));
		assertEquals(1, board.COL(9));
		
		assertEquals(2, board.COL(2));
		assertEquals(2, board.COL(10));
		
		assertEquals(3, board.COL(3));
		assertEquals(3, board.COL(11));
		
		assertEquals(4, board.COL(4));
		assertEquals(4, board.COL(12));
		
		assertEquals(5, board.COL(5));
		assertEquals(5, board.COL(13));
		
		assertEquals(6, board.COL(6));
		assertEquals(6, board.COL(14));
		
		assertEquals(7, board.COL(7));
		assertEquals(7, board.COL(15));
		
	}
	public void testPawnWhite(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(-15, board.pozPionkaBialego(8));
		assertEquals(-10, board.pozPionkaBialego(9));
		assertEquals(-5, board.pozPionkaBialego(10));
		assertEquals(0, board.pozPionkaBialego(11));
		assertEquals(0, board.pozPionkaBialego(12));
		assertEquals(-5, board.pozPionkaBialego(13));
		assertEquals(-10, board.pozPionkaBialego(14));
		assertEquals(-15, board.pozPionkaBialego(15));
	}
	public void testPawnBlack(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(10, board.pozPionkaCzarnego(8));
		assertEquals(10, board.pozPionkaCzarnego(9));
		assertEquals(10, board.pozPionkaCzarnego(10));
		assertEquals(-30, board.pozPionkaCzarnego(11));
		assertEquals(-30, board.pozPionkaCzarnego(12));
		assertEquals(10, board.pozPionkaCzarnego(13));
		assertEquals(10, board.pozPionkaCzarnego(14));
		assertEquals(10, board.pozPionkaCzarnego(15));
	}
	
	public void testPozycjaBialegoKrola(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(0, board.pozBialegoKrola(60));
	
	}
	public void testsprawdzPionkaBialegoa(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board.MiejscePionka[0][0]=6;//pionek nie wykona³ ruchu
		assertEquals(0, board.sprawdzPionkaBialego(0));
		board.MiejscePionka[0][1]=5;//pionek wykonal ruch o jedno pole
		assertEquals(-10, board.sprawdzPionkaBialego(1));
		board.MiejscePionka[0][2]=8;//pionek wykonal ruch wiencej niz o jedno pole
		assertEquals(-20, board.sprawdzPionkaBialego(2));
		
		board.MiejscePionka[1][0]=7;//pionek bia³y
		assertEquals(-15, board.sprawdzPionkaBialego(0));
		board.MiejscePionka[1][0]=5;//pionek bia³y ma 3 polu
		assertEquals(-10, board.sprawdzPionkaBialego(1));
		board.MiejscePionka[1][0]=4;//pionek bia³y
		assertEquals(-5, board.sprawdzPionkaBialego(0));
		
	}
	public void testPozCzarnegoKrola(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(0, board.pozCzarnegoKrola(4));
	
	}
	public void testSprawdzPionkaCzarnego(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board.MiejscePionka[0][1]=0;//pionek bia³y
		assertEquals(-35, board.sprawdzPionkaCzarnego(1));
		board.MiejscePionka[0][0]=2;//pionek bia³y ma 3 polu
		assertEquals(-30, board.sprawdzPionkaCzarnego(0));
		board.MiejscePionka[0][0]=3;//pionek bia³y
		assertEquals(-25, board.sprawdzPionkaCzarnego(0));
		
		board.MiejscePionka[1][0]=1;//pionek nie wykona³ ruchu
		assertEquals(-5, board.sprawdzPionkaCzarnego(0));
		board.MiejscePionka[1][0]=2;//pionek wykonal ruch o jedno pole
		assertEquals(-15, board.sprawdzPionkaCzarnego(0));
		board.MiejscePionka[1][0]=8;//pionek wykonal ruch wiencej niz o jedno pole
		assertEquals(-25, board.sprawdzPionkaCzarnego(0));
		
		
	}
	/*public void testwyroznijOkno(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(null, chessViewTest.hashCode());
		
	}*/
	
	public void testzatrzymajOczekiwanie(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertFalse(searcher.zatrzymajOczekiwanie());
	}

	@SuppressWarnings("rawtypes")
	public void testGetWynik(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		mov= new Ruchy(63, 61, 3, 32, 'n');
		assertEquals(0,mov.getWynik());
		
	}
	public void testRuchy(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		mov= new Ruchy(63, 61, 3, 2, 'r');
		assertEquals("h1-f1",mov.toString());
	}
	public void testRuchy1(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		ruch= new RuchyPodstawowe(63, 61);
		assertEquals(7,ruch.getOdWier());
	}
	public void testRuchy2(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		ruch= new RuchyPodstawowe(63, 50);
		assertEquals(7,ruch.getOdWier());
	}
	public void testRuchyWiersze(){
		
		mActivity = getActivity();
		assertNotNull(mActivity);
		ruch= new RuchyPodstawowe(0, 7);
		assertEquals(0,ruch.getDoWierszy());
		assertEquals(0,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(8, 15);
		assertEquals(1,ruch.getDoWierszy());
		assertEquals(1,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(16, 23);
		assertEquals(2,ruch.getDoWierszy());
		assertEquals(2,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(24, 31);
		assertEquals(3,ruch.getDoWierszy());
		assertEquals(3,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(32, 39);
		assertEquals(4,ruch.getDoWierszy());
		assertEquals(4,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(40, 47);
		assertEquals(5,ruch.getDoWierszy());
		assertEquals(5,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(48, 55);
		assertEquals(6,ruch.getDoWierszy());
		assertEquals(6,ruch.getOdWier());
		
		ruch= new RuchyPodstawowe(56, 63);
		assertEquals(7,ruch.getDoWierszy());
		assertEquals(7,ruch.getOdWier());
		
	}
public void testPobiezNrPola(){
		
		mActivity = getActivity();
		assertNotNull(mActivity);
		ruch= new RuchyPodstawowe(0, 7);
		assertEquals(7,ruch.getDo());
		assertEquals(0,ruch.getOd());
		
		ruch= new RuchyPodstawowe(8, 15);
		assertEquals(15,ruch.getDo());
		assertEquals(8,ruch.getOd());
		
		ruch= new RuchyPodstawowe(16, 23);
		assertEquals(23,ruch.getDo());
		assertEquals(16,ruch.getOd());
		
		
	}
public void testRuchyKolumny(){
		
		mActivity = getActivity();
		assertNotNull(mActivity);
		ruch= new RuchyPodstawowe(0, 56);
		assertEquals(0,ruch.getDoKolumn());
		assertEquals(0,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(1, 57);
		assertEquals(1,ruch.getDoKolumn());
		assertEquals(1,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(2, 58);
		assertEquals(2,ruch.getDoKolumn());
		assertEquals(2,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(3, 59);
		assertEquals(3,ruch.getDoKolumn());
		assertEquals(3,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(4, 60);
		assertEquals(4,ruch.getDoKolumn());
		assertEquals(4,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(5, 61);
		assertEquals(5,ruch.getDoKolumn());
		assertEquals(5,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(6, 62);
		assertEquals(6,ruch.getDoKolumn());
		assertEquals(6,ruch.getOdKolumn());
		
		ruch= new RuchyPodstawowe(7, 63);
		assertEquals(7,ruch.getDoKolumn());
		assertEquals(7,ruch.getOdKolumn());
		
	}
	public void testliczbaPowtPoz(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		assertEquals(0, board.liczbaPowtPoz());
		
	}
	public void testSprawdzaniaNIEwSzachu(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		assertEquals(false, board.sprawdzAtak(0));
	}
	public void testAttackWieza(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		assertEquals(false, board.attack(Sta³eConst.H8, board.czarny));
		assertEquals(false, board.attack(Sta³eConst.H1, board.bialy));
	}
	public void testAttackSkoczek(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		assertEquals(true, board.attack(Sta³eConst.G8, board.czarny));
		assertEquals(true, board.attack(Sta³eConst.G1, board.bialy));
	}
	public void testAttackGoniec(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		assertEquals(true, board.attack(Sta³eConst.F8, board.czarny));
		assertEquals(true, board.attack(Sta³eConst.F1, board.bialy));
	}
	
	public void testWykonajRuch(){
		mActivity = getActivity();
		assertNotNull(mActivity);
		board = new BoardGame();
		mov= new Ruchy(1, 1, 0, 16, board.znakPionka[board.ulozeniePionkow[1]]);
		assertEquals(true, board.wykonajRuch(mov));
		mov= new Ruchy(Sta³eConst.A2, Sta³eConst.A4, 0, 32, board.znakPionka[board.ulozeniePionkow[0]]);
		assertEquals(true, board.wykonajRuch(mov));
		
	}
	

}
