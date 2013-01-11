package com.chess.chessandroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class BoardGame implements Sta³eConst, Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return super.clone();
    }

    public int bialy = BIALE;
    public int czarny = CZARNE;
    private int hply = 0;
    private int wieza = 15;
    private int ep = -1;
    private int matP[] = { 3100, 3100 };
    private int matPionek[] = new int[2];
    
    final static int HISSTACK = 800;// zzn 400;
    private HistoriaRuchow histDat[] = new HistoriaRuchow[HISSTACK];
    
    final static int PAWNPENALTY10 = 10;
    final static int PAWNPENALTY20 = 20;
    final static int PAWNPENALTY8 = 8;
    final static int PAWNBONUS20 = 20;
    final static int ROOKSEMIOPENFILE10 = 10;
    final static int ROOKOPENFILE15 = 15;
    final static int ROOKONSEVENTH20 = 20;
    
    final private static char znakPionka[] = { 'P', 'N', 'B', 'R', 'Q', 'K' };
    final private static boolean przesun[] = { false, false, true, true, true, false };
    final private static int przesuniecie[] = { 0, 8, 4, 4, 8, 8 };
    final private static int wartosci[] = {100, 300, 300, 500, 900, 0};
    


    int fifty = 0;
    int history[][] = new int[64][64];

    public int MiejscePionka[][] = new int [2][10];
    
    long bitPionka[] = { 0x00ff000000000000L, 0xff00L };
    long bitulozeniePionkow[] = { 0xffff000000000000L, 0xffffL };
    long oldPawnBits = 0;
    long oldPieceBits = 0;
    int polozenieKrola[] = { 60, 4 };
    public int color[] =  {
        1, 1, 1, 1, 1, 1, 1, 1,//pionki czarne
        1, 1, 1, 1, 1, 1, 1, 1,//pionki czarne
        6, 6, 6, 6, 6, 6, 6, 6,
        6, 6, 6, 6, 6, 6, 6, 6,
        6, 6, 6, 6, 6, 6, 6, 6,//puste
        6, 6, 6, 6, 6, 6, 6, 6,
        0, 0, 0, 0, 0, 0, 0, 0,//pionki bia³e
        0, 0, 0, 0, 0, 0, 0, 0//pionki bia³e
};
       
public int ulozeniePionkow[] =  {
    3, 1, 2, 4, 5, 2, 1, 3,
    0, 0, 0, 0, 0, 0, 0, 0,
    6, 6, 6, 6, 6, 6, 6, 6,
    6, 6, 6, 6, 6, 6, 6, 6,
    6, 6, 6, 6, 6, 6, 6, 6,
    6, 6, 6, 6, 6, 6, 6, 6,
    0, 0, 0, 0, 0, 0, 0, 0,
    3, 1, 2, 4, 5, 2, 1, 3
};
       
	
       
    final private static int przesuniecieTab[][] = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { -21, -19, -12, -8, 8, 12, 19, 21 },
            { -11, -9, 9, 11, 0, 0, 0, 0 },
            { -10, -1, 1, 10, 0, 0, 0, 0 },
            { -11, -10, -9, -1, 1, 9, 10, 11 },
            { -11, -10, -9, -1, 1, 9, 10, 11 }
    };

    final private static int pudelko[] = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1,  0,  1,  2,  3,  4,  5,  6,  7, -1,
            -1,  8,  9, 10, 11, 12, 13, 14, 15, -1,
            -1, 16, 17, 18, 19, 20, 21, 22, 23, -1,
            -1, 24, 25, 26, 27, 28, 29, 30, 31, -1,
            -1, 32, 33, 34, 35, 36, 37, 38, 39, -1,
            -1, 40, 41, 42, 43, 44, 45, 46, 47, -1,
            -1, 48, 49, 50, 51, 52, 53, 54, 55, -1,
            -1, 56, 57, 58, 59, 60, 61, 62, 63, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    private final static int pudelko64[] = {
            21, 22, 23, 24, 25, 26, 27, 28,
            31, 32, 33, 34, 35, 36, 37, 38,
            41, 42, 43, 44, 45, 46, 47, 48,
            51, 52, 53, 54, 55, 56, 57, 58,
            61, 62, 63, 64, 65, 66, 67, 68,
            71, 72, 73, 74, 75, 76, 77, 78,
            81, 82, 83, 84, 85, 86, 87, 88,
            91, 92, 93, 94, 95, 96, 97, 98
    };

    private final static int ruchywiezy[] = {
            7, 15, 15, 15,  3, 15, 15, 11,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            13, 15, 15, 15, 12, 15, 15, 14
    };

        

/* po³ozeniw pionka*/

    private final static int pionkiPC[] = {
            0,   0,   0,   0,   0,   0,   0,   0,
            5,  10,  15,  20,  20,  15,  10,   5,
            4,   8,  12,  16,  16,  12,   8,   4,
            3,   6,   9,  12,  12,   9,   6,   3,
            2,   4,   6,   8,   8,   6,   4,   2,
            1,   2,   3, -10, -10,   3,   2,   1,
            0,   0,   0, -40, -40,   0,   0,   0,
            0,   0,   0,   0,   0,   0,   0,   0
    };

    private final static int krolowaPC[] = {
            -10, -10, -10, -10, -10, -10, -10, -10,
            -10,   0,   0,   0,   0,   0,   0, -10,
            -10,   0,   5,   5,   5,   5,   0, -10,
            -10,   0,   5,  10,  10,   5,   0, -10,
            -10,   0,   5,  10,  10,   5,   0, -10,
            -10,   0,   5,   5,   5,   5,   0, -10,
            -10,   0,   0,   0,   0,   0,   0, -10,
            -10, -30, -10, -10, -10, -10, -30, -10
    };

    private final static int skoczekPC[] = {
            -10, -10, -10, -10, -10, -10, -10, -10,
            -10,   0,   0,   0,   0,   0,   0, -10,
            -10,   0,   5,   5,   5,   5,   0, -10,
            -10,   0,   5,  10,  10,   5,   0, -10,
            -10,   0,   5,  10,  10,   5,   0, -10,
            -10,   0,   5,   5,   5,   5,   0, -10,
            -10,   0,   0,   0,   0,   0,   0, -10,
            -10, -10, -20, -10, -10, -20, -10, -10
    };

    private final static int krolPC[] = {
            -40, -40, -40, -40, -40, -40, -40, -40,
            -40, -40, -40, -40, -40, -40, -40, -40,
            -40, -40, -40, -40, -40, -40, -40, -40,
            -40, -40, -40, -40, -40, -40, -40, -40,
            -40, -40, -40, -40, -40, -40, -40, -40,
            -40, -40, -40, -40, -40, -40, -40, -40,
            -20, -20, -20, -20, -20, -20, -20, -20,
            0,  20,  40, -20,   0, -20,  40,  20
    };

    private final static int koncoweRuchyKrolaPC[] = {
            0,  10,  20,  30,  30,  20,  10,   0,
            10,  20,  30,  40,  40,  30,  20,  10,
            20,  30,  40,  50,  50,  40,  30,  20,
            30,  40,  50,  60,  60,  50,  40,  30,
            30,  40,  50,  60,  60,  50,  40,  30,
            20,  30,  40,  50,  50,  40,  30,  20,
            10,  20,  30,  40,  40,  30,  20,  10,
            0,  10,  20,  30,  30,  20,  10,   0
    };

   
private final static int nrSzachownicy[] = {
    56,  57,  58,  59,  60,  61,  62,  63,
    48,  49,  50,  51,  52,  53,  54,  55,
    40,  41,  42,  43,  44,  45,  46,  47,
    32,  33,  34,  35,  36,  37,  38,  39,
    24,  25,  26,  27,  28,  29,  30,  31,
    16,  17,  18,  19,  20,  21,  22,  23,
    8,   9,  10,  11,  12,  13,  14,  15,
    0,   1,   2,   3,   4,   5,   6,   7
};

public BoardGame() {            
}

public int getColor(int i, int j) {
    return color[(i << 3) + j];
}

public int getColor(int i) {
    return color[i];
}

public int getPionek(int i, int j) {
    return ulozeniePionkow[(i << 3) + j];
}

public int getPionek(int i) {
    return ulozeniePionkow[i];
}
/*
public boolean WhiteToMove() {
    return (bialy == BIALE);
}*/

/* 
 * sprawdzAtak () zwraca true, jeœli s pionek jest w szachu i fa³szywe w przeciwnym wypadku
To po prostu skanuje p³ytê by znaleŸæ oboik s król wolne miejsce
i wzywa atak (), aby sprawdziæ, czy jest atakowany.
 */

public boolean sprawdzAtak(int s) {
    return attack(polozenieKrola[s], s ^ 1);
}

/* attack() zrwaca true jesli kwadrat square sq jest atakowany z którejœ strony
s i false w przeciwnym wypadku. */

public boolean attack(int sq, int s) {
     long attackSq = (1L << sq);
     	if (s == BIALE) {
     		long moves = ((bitPionka[BIALE] & 0x00fefefefefefefeL) >> 9) & attackSq;
	        if (moves != 0)
	            return true;
	        moves = ((bitPionka[BIALE] & 0x007f7f7f7f7f7f7fL) >> 7) & attackSq;
	        if (moves != 0)
	             return true;
        } else {
             long moves = ((bitPionka[CZARNE] & 0x00fefefefefefefeL) << 7)& attackSq;
                    if (moves != 0)
                            return true;
                    moves = ((bitPionka[CZARNE] & 0x007f7f7f7f7f7f7fL) << 9) & attackSq;
                    if (moves != 0)
                            return true;
            }
            long pieces = bitulozeniePionkow[s] ^ bitPionka[s];
            while (pieces != 0) {
                    int i = getLBit(pieces);
                    int p = ulozeniePionkow[i];
                    for (int j = 0; j < przesuniecie[p]; ++j)
                            for (int n = i;;) {
                                    n = pudelko[pudelko64[n] + przesuniecieTab[p][j]];
                                    if (n == -1)
                                            break;
                                    if (n == sq)
                                            return true;
                                    if (color[n] != PUSTE)
                                            break;
                                    if (!przesun[p])
                                            break;
                            }
                    pieces &= (pieces - 1);
            }
            return false;
    }

/* gen() losuje pseudo losowe ruchy dla biezoncej pozycji
 * Przeszukuje plansze w poszukiwaniu wolnego miejsca i 
 * sprawdza czy na pozycji na której bedzie postawiony 
 * jest jakieœ zagro¿enie po umieszczeniu umieszcza 
 * pozycje na stosie ruchów
 * dodajNaStos */

@SuppressWarnings("rawtypes")
public List generujRuch() {
            List ret = new ArrayList();    
   
            long emptySlots = ~(bitulozeniePionkow[BIALE] | bitulozeniePionkow[CZARNE]);
            if (bialy == BIALE) {
                    long moves = (bitPionka[BIALE] >> 8) & emptySlots;
                    long keep = moves;
                    while (moves != 0) {
                            int theMove = getLBit(moves);                          
                            dodajNaStos(ret, theMove + 8, theMove, 16);
                            moves &= (moves - 1);
                    }
                    moves = ((keep & 0x0000ff0000000000L) >> 8) & emptySlots;
                    while (moves != 0) {
                            int theMove = getLBit(moves);                          
                            dodajNaStos(ret, theMove + 16, theMove, 24);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[BIALE] & 0x00fefefefefefefeL) >> 9)
                                    & bitulozeniePionkow[CZARNE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 9, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[BIALE] & 0x007f7f7f7f7f7f7fL) >> 7)
                                    & bitulozeniePionkow[CZARNE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 7, theMove, 17);
                            moves &= (moves - 1);
                    }
            } else {
                    long moves = (bitPionka[CZARNE] << 8) & emptySlots;
                    long keep = moves;
                    while (moves != 0) {
                            int theMove = getLBit(moves);                          
                            dodajNaStos(ret, theMove - 8, theMove, 16);
                            moves &= (moves - 1);
                    }
                    moves = ((keep & 0xff0000L) << 8) & emptySlots;
                    while (moves != 0) {
                            int theMove = getLBit(moves);                          
                            dodajNaStos(ret, theMove - 16, theMove, 24);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[CZARNE] & 0x00fefefefefefefeL) << 7)
                                    & bitulozeniePionkow[BIALE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 7, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[CZARNE] & 0x007f7f7f7f7f7f7fL) << 9)
                                    & bitulozeniePionkow[BIALE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 9, theMove, 17);
                            moves &= (moves - 1);
                    }
            }
            long pieces = bitulozeniePionkow[bialy] ^ bitPionka[bialy];
            while (pieces != 0) {
                    int i = getLBit(pieces);
                    int p = ulozeniePionkow[i];
                    for (int j = 0; j < przesuniecie[p]; ++j)
                            for (int n = i;;) {
                                    n = pudelko[pudelko64[n] + przesuniecieTab[p][j]];
                                    if (n == -1)
                                            break;
                                    if (color[n] != PUSTE) {
                                            if (color[n] == czarny)
                                            	dodajNaStos(ret, i, n, 1);
                                            break;
                                    }
                                    dodajNaStos(ret, i, n, 0);
                                    if (!przesun[p])
                                            break;
                            }
                    pieces &= (pieces - 1);
            }

            /* generuje ruch wie¿y */
            if (bialy == BIALE) {
                    if (((wieza & 1) != 0) && (ulozeniePionkow[F1] == PUSTE)
                                    && (ulozeniePionkow[G1] == PUSTE))
                    	dodajNaStos(ret, E1, G1, 2);
                    if (((wieza & 2) != 0) && (ulozeniePionkow[D1] == PUSTE)
                                    && (ulozeniePionkow[C1] == PUSTE) && (ulozeniePionkow[B1] == PUSTE))
                    	dodajNaStos(ret, E1, C1, 2);
            } else {
                    if (((wieza & 4) != 0) && (ulozeniePionkow[F8] == PUSTE)
                                    && (ulozeniePionkow[G8] == PUSTE))
                    	dodajNaStos(ret, E8, G8, 2);
                    if (((wieza & 8) != 0) && (ulozeniePionkow[D8] == PUSTE)
                                    && (ulozeniePionkow[C8] == PUSTE) && (ulozeniePionkow[B8] == PUSTE))
                    	dodajNaStos(ret, E8, C8, 2);
            }

            /* generujemy en ruchów*/
            if (ep != -1) {
                    if (bialy == BIALE) {
                            if (COL(ep) != 0 && color[ep + 7] == BIALE
                                            && ulozeniePionkow[ep + 7] == PIONEK)
                            	dodajNaStos(ret, ep + 7, ep, 21);
                            if (COL(ep) != 7 && color[ep + 9] == BIALE
                                            && ulozeniePionkow[ep + 9] == PIONEK)
                            	dodajNaStos(ret, ep + 9, ep, 21);
                    } else {
                            if (COL(ep) != 0 && color[ep - 9] == CZARNE
                                            && ulozeniePionkow[ep - 9] == PIONEK)
                            	dodajNaStos(ret, ep - 9, ep, 21);
                            if (COL(ep) != 7 && color[ep - 7] == CZARNE
                                            && ulozeniePionkow[ep - 7] == PIONEK)
                            	dodajNaStos(ret, ep - 7, ep, 21);
                    }
            }
            return ret;
    }

/* RejestrGenRuchu() jest kopiom gen tylko ¿e 
 * nie generuje tylko rejestruje i porównuje ruchy
 * jest generowany przez quiescence search. */

@SuppressWarnings("rawtypes")
public List RejestrGenRuchu() {
            List ret = new ArrayList();

            if (bialy == BIALE) {
                    long moves = ((bitPionka[BIALE] & 0x00fefefefefefefeL) >> 9)
                                    & bitulozeniePionkow[CZARNE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 9, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[BIALE] & 0x007f7f7f7f7f7f7fL) >> 7)
                                    & bitulozeniePionkow[CZARNE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 7, theMove, 17);
                            moves &= (moves - 1);
                    }
            } else {
                    long moves = ((bitPionka[CZARNE] & 0x00fefefefefefefeL) << 7)
                                    & bitulozeniePionkow[BIALE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 7, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[CZARNE] & 0x007f7f7f7f7f7f7fL) << 9)
                                    & bitulozeniePionkow[BIALE];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 9, theMove, 17);
                            moves &= (moves - 1);
                    }
            }
            long pieces = bitulozeniePionkow[bialy] ^ bitPionka[bialy];
            while (pieces != 0) {
                    int p = getLBit(pieces);
                    for (int j = 0; j < przesuniecie[ulozeniePionkow[p]]; ++j)
                            for (int n = p;;) {
                                    n = pudelko[pudelko64[n] + przesuniecieTab[ulozeniePionkow[p]][j]];
                                    if (n == -1)
                                            break;
                                    if (color[n] != PUSTE) {
                                            if (color[n] == czarny)
                                            	dodajNaStos(ret, p, n, 1);
                                            break;
                                    }
                                    if (!przesun[ulozeniePionkow[p]])
                                            break;
                            }
                    pieces &= (pieces - 1);
            }
            if (ep != -1) {
                    if (bialy == BIALE) {
                            if (COL(ep) != 0 && color[ep + 7] == BIALE
                                            && ulozeniePionkow[ep + 7] == PIONEK)
                            	dodajNaStos(ret, ep + 7, ep, 21);
                            if (COL(ep) != 7 && color[ep + 9] == BIALE
                                            && ulozeniePionkow[ep + 9] == PIONEK)
                            	dodajNaStos(ret, ep + 9, ep, 21);
                    } else {
                            if (COL(ep) != 0 && color[ep - 9] == CZARNE
                                            && ulozeniePionkow[ep - 9] == PIONEK)
                            	dodajNaStos(ret, ep - 9, ep, 21);
                            if (COL(ep) != 7 && color[ep - 7] == CZARNE
                                            && ulozeniePionkow[ep - 7] == PIONEK)
                            	dodajNaStos(ret, ep - 7, ep, 21);
                    }
            }
            return ret;
    }

 /* dodajNaStos() wstawia na stos ruchów chyba ze na 
  * stosie jest pionek promowany u¿ywamy wtedy funkcji 
  * dodaj4Ruchy().
  * Przypisanie ruchów alfanumeryczni  
  * Nale¿y pamiêtaæ, ¿e 1000000 wprowadza siê do przechwytywania 
  * w wyniku przemieszczania siê, a wiêc zawsze pobiera uporz¹dkowane 
  * powy¿ej "normalnego" ruchu. */
 
 @SuppressWarnings({ "unchecked", "rawtypes" })
void dodajNaStos(Collection ret, int from, int to, int bits) {
            if ((bits & 16) != 0) {
                    if (bialy == BIALE) {
                            if (to <= H8) {
                            	dodaj4Ruchy(ret, from, to, bits);
                                    return;
                            }
                    } else {
                            if (to >= A1) {
                            	dodaj4Ruchy(ret, from, to, bits);
                                    return;
                            }
                    }
            }

            Ruchy g = new Ruchy(from, to, 0, bits, znakPionka[ulozeniePionkow[from]]);          
           
            if (color[to] != PUSTE)
                    g.setWynik(1000000 + (ulozeniePionkow[to] * 10) - ulozeniePionkow[from]);
            else
                    g.setWynik(history[from][to]);
            ret.add(g);
    }    
 
 /* genPromote() jest taki jak dodajNaStos (), tylko stawia 4 ruchy
  na stosie ruchu, po jednym dla ka¿dego mo¿liwego elementu promocji */
 
 @SuppressWarnings({ "rawtypes", "unchecked" })
void dodaj4Ruchy(Collection ret, int from, int to, int bits) {
            for (char i = GONIEC; i <= KROLOWA; ++i) {
                    Ruchy g = new Ruchy(from, to, i, (bits | 32), 'P');
                    g.setWynik(1000000 + (i * 10));
                    ret.add(g);
            }
 }
 
 /* makemove() wykonaj ruch. Jesli ruch jest z³y to 
  * zwraca false i cofa ruch w przeciwnym racie true*/      
 @SuppressWarnings("rawtypes")
public boolean wykonajRuch(Ruchy m) {
            long oldBits[] = { bitulozeniePionkow[BIALE], bitulozeniePionkow[CZARNE] };

            int from, to;
            if ((m.bits & 2) != 0) {

                    if (sprawdzAtak(bialy))
                            return false;
                    switch (m.getDo()) {
                    case 62:
                            if (color[F1] != PUSTE || color[G1] != PUSTE
                                            || attack(F1, czarny) || attack(G1, czarny))
                                    return false;
                            from = H1;
                            to = F1;
                            break;
                    case 58:
                            if (color[B1] != PUSTE || color[C1] != PUSTE
                                            || color[D1] != PUSTE || attack(C1, czarny)
                                            || attack(D1, czarny))
                                    return false;
                            from = A1;
                            to = D1;
                            break;
                    case 6:
                            if (color[F8] != PUSTE || color[G8] != PUSTE
                                            || attack(F8, czarny) || attack(G8, czarny))
                                    return false;
                            from = H8;
                            to = F8;
                            break;
                    case 2:
                            if (color[B8] != PUSTE || color[C8] != PUSTE
                                            || color[D8] != PUSTE || attack(C8, czarny)
                                            || attack(D8, czarny))
                                    return false;
                            from = A8;
                            to = D8;
                            break;
                    default: 
                            from = -1;
                            to = -1;
                            break;
                    }
                    color[to] = color[from];
                    ulozeniePionkow[to] = ulozeniePionkow[from];
                    color[from] = PUSTE;
                    ulozeniePionkow[from] = PUSTE;
                    bitulozeniePionkow[bialy] ^= (1L << from) | (1L << to);
            }
            /* kopii zapasowych informacji, abyœmy mogli cofn¹æ ruch. */
           
            HistoriaRuchow h = new HistoriaRuchow();              
            h.m = m;
            to = m.getDo();
            from = m.getOd();
            h.zwyciestwo = ulozeniePionkow[to];
            h.wieza = wieza;
            h.ep = ep;
            h.fifty = fifty;
            h.bitPionka = new long[] { bitPionka[BIALE], bitPionka[CZARNE] };
            h.bitulozeniePionkow = oldBits;
            histDat[hply++] = h;
                                                       
            /*
             * zaktualizowaæ pozycji wiezy             */
            wieza &= ruchywiezy[from] & ruchywiezy[to];
            if ((m.bits & 8) != 0) {
                    if (bialy == BIALE)
                            ep = to + 8;
                    else
                            ep = to - 8;
            } else
                    ep = -1;
            if ((m.bits & 17) != 0)
                    fifty = 0;
            else
                    ++fifty;

            /* ruch pionka */
            int thePiece = ulozeniePionkow[from];
            if (thePiece == KROL)
            	polozenieKrola[bialy] = to;
            color[to] = bialy;
            if ((m.bits & 32) != 0) {
            	ulozeniePionkow[to] = m.wyroznij;
            	matP[bialy] += wartosci[m.wyroznij];
            } else
            	ulozeniePionkow[to] = thePiece;
            color[from] = PUSTE;
            ulozeniePionkow[from] = PUSTE;
            long fromBits = 1L << from;
            long toBits = 1L << to;
            bitulozeniePionkow[bialy] ^= fromBits | toBits;
            if ((m.bits & 16) != 0) {
            	bitPionka[bialy] ^= fromBits;
                    if ((m.bits & 32) == 0)
                    	bitPionka[bialy] |= toBits;
            }
            int zwyciestwo = h.zwyciestwo;                
            if (zwyciestwo != PUSTE) {
            	bitulozeniePionkow[czarny] ^= toBits;
                    if (zwyciestwo == PIONEK)
                    	bitPionka[czarny] ^= toBits;
                    else
                    	matP[czarny] -= wartosci[zwyciestwo];
            }

            /* usun¹æ pionka czy to ruch pionka  */
            if ((m.bits & 4) != 0) {
                    if (bialy == BIALE) {
                            color[to + 8] = PUSTE;
                            ulozeniePionkow[to + 8] = PUSTE;
                            bitulozeniePionkow[CZARNE] ^= (1L << (to + 8));
                            bitPionka[CZARNE] ^= (1L << (to + 8));
                    } else {
                            color[to - 8] = PUSTE;
                            ulozeniePionkow[to - 8] = PUSTE;
                            bitulozeniePionkow[BIALE] ^= (1L << (to - 8));
                            bitPionka[BIALE] ^= (1L << (to - 8));
                    }
            }

            /*
             * zmieniæ strony i sprawdzania poprawnoœæ ruchów (jeœli mo¿emy uchwyciæ inna pozycje
              * Król, to z³a pozycja i musimy podj¹æ ruch do ty³u
             */
            bialy ^= 1;
            czarny ^= 1;
            if (sprawdzAtak(czarny)) {
            	cofnijRuch();
                    return false;
            }
            return true;
    }
 
 /* cofnijRuch() to samo co wykonajRuch tylko cofie do ty³u ruch:)  */
 @SuppressWarnings("rawtypes")
void cofnijRuch() {
	 bialy ^= 1;
	 czarny ^= 1;
            HistoriaRuchow h = histDat[--hply];
            bitPionka = h.bitPionka;
            bitulozeniePionkow = h.bitulozeniePionkow;
            Ruchy m = h.m;
            wieza = h.wieza;
            ep = h.ep;
            fifty = h.fifty;
            int from = m.getOd();
            int to = m.getDo();
            color[from] = bialy;
            if ((m.bits & 32) != 0) {
            	ulozeniePionkow[from] = PIONEK;
            	matP[bialy] -= wartosci[h.m.wyroznij];
            } else {
                    int thePiece = ulozeniePionkow[to];
                    if (thePiece == KROL)
                    	polozenieKrola[bialy] = from;
                    ulozeniePionkow[from] = thePiece;
            }
            if (h.zwyciestwo == PUSTE) {
                    color[to] = PUSTE;
                    ulozeniePionkow[to] = PUSTE;
            } else {
                    color[to] = czarny;
                    ulozeniePionkow[to] = h.zwyciestwo;
                    if (h.zwyciestwo != PIONEK)
                    	matP[czarny] += wartosci[h.zwyciestwo];
            }
            if ((m.bits & 2) != 0) {
                    int cfrom, cto;

                    switch (to) {
                    case 62:
                            cfrom = F1;
                            cto = H1;
                            break;
                    case 58:
                            cfrom = D1;
                            cto = A1;
                            break;
                    case 6:
                            cfrom = F8;
                            cto = H8;
                            break;
                    case 2:
                            cfrom = D8;
                            cto = A8;
                            break;
                    default: /* shouldn't get here */
                            cfrom = -1;
                            cto = -1;
                            break;
                    }
                    color[cto] = bialy;
                    ulozeniePionkow[cto] = WIEZA;
                    color[cfrom] = PUSTE;
                    ulozeniePionkow[cfrom] = PUSTE;
            }
            if ((m.bits & 4) != 0) {
                    if (bialy == BIALE) {
                            color[to + 8] = czarny;
                            ulozeniePionkow[to + 8] = PIONEK;
                    } else {
                            color[to - 8] = czarny;
                            ulozeniePionkow[to - 8] = PIONEK;
                    }
            }
    }
 
 public String toString() {
            int i;

            StringBuffer sb = new StringBuffer("\n8 ");
            for (i = 0; i < 64; ++i) {
                    switch (color[i]) {
                    case PUSTE:
                            sb.append(" .");
                            break;
                    case BIALE:
                            sb.append(" ");
                            sb.append(znakPionka[ulozeniePionkow[i]]);
                            break;
                    case CZARNE:
                            sb.append(" ");
                            sb.append((char) (znakPionka[ulozeniePionkow[i]] + ('a' - 'A')));
                            break;
                    default:
                            throw new IllegalStateException(
                                            "kwadrat pusty: " + i);
                    }
                    if ((i + 1) % 8 == 0 && i != 63) {
                            sb.append("\n");
                            sb.append(Integer.toString(7 - ROW(i)));
                            sb.append(" ");
                    }
            }
            sb.append("\n\n   a b c d e f g h\n\n");
            return sb.toString();
    }
 
 /* liczbaPowtPoz() zwraca iloœæ obecnych
pozycja zosta³a powtórzone. Dziêki Janowi Stanback
do tego sprytnego algorytmu. */    
 int liczbaPowtPoz() {
            int b[] = new int[64];
            int c = 0; /*
                                     *liczy  kwadraty, które s¹ ró¿ne od obecny
                                      * Pozycja
                                     */
            int r = 0; /* liczba powtórzeñ */

            /* powturka z³ego ruchu */
            if (fifty <= 3)
                    return 0;

            /* odwracanie ruchów */
            for (int i = hply - 1; i >= hply - fifty - 1; --i) {
                    if (++b[histDat[i].m.getOd()] == 0)
                            --c;
                    else
                            ++c;
                    if (--b[histDat[i].m.getDo()] == 0)
                            --c;
                    else
                            ++c;
                    if (c == 0)
                            ++r;
            }

            return r;
    }
     
 int pozyPionka() {
            int score[] = new int[2];

            if (oldPawnBits != (bitPionka[BIALE] | bitPionka[CZARNE])) {
                    for (int i = 0; i < 10; ++i) {
                    	MiejscePionka[BIALE][i] = 0;
                    	MiejscePionka[CZARNE][i] = 7;
                    }
                    matPionek[BIALE] = 0;
                    matPionek[CZARNE] = 0;
                    long pieces = bitPionka[BIALE];
                    while (pieces != 0) {
                            int i = getLBit(pieces);
                            matPionek[BIALE] += wartosci[PIONEK];
                            int f = COL(i) + 1; /*
                                                                     * dodaæ 1 ze wzglêdu na dodatkowe pliku w
                                                                      * tablica
                                                                     */
                            if (MiejscePionka[BIALE][f] < ROW(i))
                            	MiejscePionka[BIALE][f] = ROW(i);
                            pieces &= (pieces - 1);
                    }
                    pieces = bitPionka[CZARNE];
                    while (pieces != 0) {
                            int i = getLBit(pieces);
                            matPionek[CZARNE] += wartosci[PIONEK];
                            int f = COL(i) + 1; /*
                                                                                                                                        */
                            if (MiejscePionka[CZARNE][f] > ROW(i))
                            	MiejscePionka[CZARNE][f] = ROW(i);
                            pieces &= (pieces - 1);
                    }
                    oldPawnBits = bitPionka[BIALE] | bitPionka[CZARNE];
            }
            score[BIALE] = matP[BIALE] + matPionek[BIALE];
            score[CZARNE] = matP[CZARNE] + matPionek[CZARNE];
            for (int i = 0; i < 64; ++i) {
                    if (color[i] == PUSTE)
                            continue;
                    if (color[i] == BIALE) {
                            switch (ulozeniePionkow[i]) {
                            case PIONEK:
                                    score[BIALE] += pozPionkaBialego(i);
                                    break;
                            case GONIEC:
                                    score[BIALE] += krolowaPC[i];
                                    break;
                            case SKOCZEK:
                                    score[BIALE] += skoczekPC[i];
                                    break;
                            case WIEZA:
                                    if (MiejscePionka[BIALE][COL(i) + 1] == 0) {
                                            if (MiejscePionka[CZARNE][COL(i) + 1] == 7)
                                                    score[BIALE] += ROOKOPENFILE15;
                                            else
                                                    score[BIALE] += ROOKSEMIOPENFILE10;
                                    }
                                    if (ROW(i) == 1)
                                            score[BIALE] += ROOKONSEVENTH20;
                                    break;
                            case KROL:
                                    if (matP[CZARNE] <= 1200)
                                            score[BIALE] += koncoweRuchyKrolaPC[i];
                                    else
                                            score[BIALE] += pozBialegoKrola(i);
                                    break;
                            }
                    } else {
                            switch (ulozeniePionkow[i]) {
                            case PIONEK:
                                    score[CZARNE] += pozPionkaCzarnego(i);
                                    break;
                            case GONIEC:
                                    score[CZARNE] += krolowaPC[nrSzachownicy[i]];
                                    break;
                            case SKOCZEK:
                                    score[CZARNE] += skoczekPC[nrSzachownicy[i]];
                                    break;
                            case WIEZA:
                                    if (MiejscePionka[CZARNE][COL(i) + 1] == 7) {
                                            if (MiejscePionka[BIALE][COL(i) + 1] == 0)
                                                    score[CZARNE] += ROOKOPENFILE15;
                                            else
                                                    score[CZARNE] += ROOKSEMIOPENFILE10;
                                    }
                                    if (ROW(i) == 6)
                                            score[CZARNE] += ROOKONSEVENTH20;
                                    break;
                            case KROL:
                                    if (matP[BIALE] <= 1200)
                                            score[CZARNE] += koncoweRuchyKrolaPC[nrSzachownicy[i]];
                                    else
                                            score[CZARNE] += pozCzarnegoKrola(i);
                                    break;
                            }
                    }
            }

            /*
             * the score[] jest ustawiona pozycje pionka
              * 
             */
            if (bialy == BIALE)
                    return score[BIALE] - score[CZARNE];
            return score[CZARNE] - score[BIALE];
    }  
 
 public int pozPionkaBialego(int sq) {
            int r = 0; /* zwróc wartosc */
            int f = COL(sq) + 1; /* zwróc dane która kolumna*/

            r += pionkiPC[sq];

            /* jeœli jest pionkiem */
            if (MiejscePionka[BIALE][f] > ROW(sq))
                    r -= PAWNPENALTY10;

            /*
             * jeœli nie ma ¿adnych przyjazne pionki po obu stronach tego jednego
              * 
             */
            if ((MiejscePionka[BIALE][f - 1] == 0) && (MiejscePionka[BIALE][f + 1] == 0))
                    r -= PAWNPENALTY20;

            else if ((MiejscePionka[BIALE][f - 1] < ROW(sq))
                            && (MiejscePionka[BIALE][f + 1] < ROW(sq)))
                    r -= PAWNPENALTY8;

             if ((MiejscePionka[CZARNE][f - 1] >= ROW(sq))
                            && (MiejscePionka[CZARNE][f] >= ROW(sq))
                            && (MiejscePionka[CZARNE][f + 1] >= ROW(sq)))
                    r += (7 - ROW(sq)) * PAWNBONUS20;

            return r;
    }

 public int pozPionkaCzarnego(int sq) {
            int r = 0; /* wartoœæ zwracana */
            int f = COL(sq) + 1; /* pioneki w pliku */

            r += pionkiPC[nrSzachownicy[sq]];

            if (MiejscePionka[CZARNE][f] < ROW(sq))
                    r -= PAWNPENALTY10;

             if ((MiejscePionka[CZARNE][f - 1] == 7) && (MiejscePionka[CZARNE][f + 1] == 7))
                    r -= PAWNPENALTY20;

            else if ((MiejscePionka[CZARNE][f - 1] > ROW(sq))
                            && (MiejscePionka[CZARNE][f + 1] > ROW(sq)))
                    r -= PAWNPENALTY8;

             if ((MiejscePionka[BIALE][f - 1] <= ROW(sq))
                            && (MiejscePionka[BIALE][f] <= ROW(sq))
                            && (MiejscePionka[BIALE][f + 1] <= ROW(sq)))
                    r += ROW(sq) * PAWNBONUS20;

            return r;
    }

    int pozBialegoKrola(int sq) {
            int r = krolPC[sq]; 

            if (COL(sq) < 3) {
                    r += sprawdzPionkaBialego(1);
                    r += sprawdzPionkaBialego(2);
                    r += sprawdzPionkaBialego(3) / 2; /*
                                                              Problemy z pionków na C & f pliki s¹
                                                              * Nie tak powa¿na
                                                             */
            } else if (COL(sq) > 4) {
                    r += sprawdzPionkaBialego(8);
                    r += sprawdzPionkaBialego(7);
                    r += sprawdzPionkaBialego(6) / 2;
            }

            /*
             *inaczej, po prostu oceniæ jakie pola w poblizu sa wolne
              * king
             */
            else {
                    for (int i = COL(sq); i <= COL(sq) + 2; ++i)
                            if ((MiejscePionka[BIALE][i] == 0) && (MiejscePionka[CZARNE][i] == 7))
                                    r -= 10;
            }

            /*
             * sprawdzaæ wartoœæ bezpieczeñstwa króla wed³ug przeciwnika ;
              Za³o¿eniem jest, ¿e * bezpieczeñstwo król mo¿e byæ tylko Ÿle, jeœli przeciwnik ma
              * Ma³o sztuk do ataku
             */
            r *= matP[CZARNE];
            r /= 3100;

            return r;
    }

    /* sprawdzPionkaBialego(f) ocena pionka  */

    int sprawdzPionkaBialego(int f) {
            int r = 0;

            if (MiejscePionka[BIALE][f] == 6)
                    ; /* pionek nie ruchomy */
            else if (MiejscePionka[BIALE][f] == 5)
                    r -= 10; /* pionek przesuno³ sie o jedno pole */
            else if (MiejscePionka[BIALE][f] != 0)
                    r -= 20; /* pionek przesuno³ sie o wiecej pól */
            else
                    r -= 25; /* to nie pionek */

            if (MiejscePionka[CZARNE][f] == 7)
                    r -= 15; /* pionek przeciwnika */
            else if (MiejscePionka[CZARNE][f] == 5)
                    r -= 10; /* pionek przeciwnika na 3 losowaniu */
            else if (MiejscePionka[CZARNE][f] == 4)
                    r -= 5; /* pionek przeciwnika na 3 losowaniu */

            return r;
    }

    int pozCzarnegoKrola(int sq) {
            int r;
            int i;

            r = krolPC[nrSzachownicy[sq]];
            if (COL(sq) < 3) {
                    r += sprawdzPionkaCzarnego(1);
                    r += sprawdzPionkaCzarnego(2);
                    r += sprawdzPionkaCzarnego(3) / 2;
            } else if (COL(sq) > 4) {
                    r += sprawdzPionkaCzarnego(8);
                    r += sprawdzPionkaCzarnego(7);
                    r += sprawdzPionkaCzarnego(6) / 2;
            } else {
                    for (i = COL(sq); i <= COL(sq) + 2; ++i)
                            if ((MiejscePionka[BIALE][i] == 0) && (MiejscePionka[CZARNE][i] == 7))
                                    r -= 10;
            }
            r *= matP[BIALE];
            r /= 3100;
            return r;
    }

    int sprawdzPionkaCzarnego(int f) {
            int r = 0;

            if (MiejscePionka[CZARNE][f] == 1)
                    ;
            else if (MiejscePionka[CZARNE][f] == 2)
                    r -= 10;
            else if (MiejscePionka[CZARNE][f] != 7)
                    r -= 20;
            else
                    r -= 25;

            if (MiejscePionka[BIALE][f] == 0)
                    r -= 15;
            else if (MiejscePionka[BIALE][f] == 2)
                    r -= 10;
            else if (MiejscePionka[BIALE][f] == 3)
                    r -= 5;

            return r;
    }  
   
private static final int m1 = 0x55555555;
private static final int m2 = 0x33333333;

private int getLBit(long y) {
    int x, shift;
    if ((y & 0xffffffffL) == 0) {
        x = (int) (y >> 32);
        shift = 32;
    }
    else {
        x = (int) y;
        shift = 0;
    }
    x = ~(x | -x);
    int a = x - ((x >> 1) & m1);
    int c = (a & m2) + ((a >> 2) & m2);
    c = (c & 0x0f0f0f0f) + ((c >> 4) & 0x0f0f0f0f);
    c = (c & 0xffff) + (c >> 16);
    c = (c & 0xff) + (c >> 8);
    return c + shift;
}

public static int COL(int x) { return (x & 7); }
public static int ROW(int x) { return (x >> 3); }  
}
