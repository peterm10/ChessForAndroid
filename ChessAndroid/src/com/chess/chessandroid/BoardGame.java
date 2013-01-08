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

    public int side = LIGHT;
    public int xside = DARK;
    private int hply = 0;
    private int wieza = 15;
    private int ep = -1;
    private int matP[] = { 3100, 3100 };
    private int pawnMat[] = new int[2];
    private HistoriaRuchow histDat[] = new HistoriaRuchow[HISSTACK];
    
    final static int HISSTACK = 800;// zzn 400;
    final static int PAWNPENALTY10 = 10;
    final static int PAWNPENALTY20 = 20;
    final static int PAWNPENALTY8 = 8;
    final static int PAWNBONUS20 = 20;
    final static int ROOKSEMIOPENFILE10 = 10;
    final static int ROOKOPENFILE15 = 15;
    final static int ROOKONSEVENTH20 = 20;


    int fifty = 0;
    int history[][] = new int[64][64];

    public int MiejscePionka[][] = new int [2][10];
       
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
       
	long bitPionka[] = { 0x00ff000000000000L, 0xff00L };
    long bitulozeniePionkow[] = { 0xffff000000000000L, 0xffffL };
    
    long oldPawnBits = 0;
    long oldPieceBits = 0;
    int polozenieKrola[] = { 60, 4 };

    final private static char znakPionka[] = { 'P', 'N', 'B', 'R', 'Q', 'K' };

    final private static boolean przesun[] = { false, false, true, true, true, false };

    final private static int przesuniecie[] = { 0, 8, 4, 4, 8, 8 };
       
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

    private final static int wartosci[] = {
            100, 300, 300, 500, 900, 0
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
    return (side == LIGHT);
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
     	if (s == LIGHT) {
     		long moves = ((bitPionka[LIGHT] & 0x00fefefefefefefeL) >> 9) & attackSq;
	        if (moves != 0)
	            return true;
	        moves = ((bitPionka[LIGHT] & 0x007f7f7f7f7f7f7fL) >> 7) & attackSq;
	        if (moves != 0)
	             return true;
        } else {
             long moves = ((bitPionka[DARK] & 0x00fefefefefefefeL) << 7)& attackSq;
                    if (moves != 0)
                            return true;
                    moves = ((bitPionka[DARK] & 0x007f7f7f7f7f7f7fL) << 9) & attackSq;
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
                                    if (color[n] != EMPTY)
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
   
            long emptySlots = ~(bitulozeniePionkow[LIGHT] | bitulozeniePionkow[DARK]);
            if (side == LIGHT) {
                    long moves = (bitPionka[LIGHT] >> 8) & emptySlots;
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
                    moves = ((bitPionka[LIGHT] & 0x00fefefefefefefeL) >> 9)
                                    & bitulozeniePionkow[DARK];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 9, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[LIGHT] & 0x007f7f7f7f7f7f7fL) >> 7)
                                    & bitulozeniePionkow[DARK];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 7, theMove, 17);
                            moves &= (moves - 1);
                    }
            } else {
                    long moves = (bitPionka[DARK] << 8) & emptySlots;
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
                    moves = ((bitPionka[DARK] & 0x00fefefefefefefeL) << 7)
                                    & bitulozeniePionkow[LIGHT];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 7, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[DARK] & 0x007f7f7f7f7f7f7fL) << 9)
                                    & bitulozeniePionkow[LIGHT];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 9, theMove, 17);
                            moves &= (moves - 1);
                    }
            }
            long pieces = bitulozeniePionkow[side] ^ bitPionka[side];
            while (pieces != 0) {
                    int i = getLBit(pieces);
                    int p = ulozeniePionkow[i];
                    for (int j = 0; j < przesuniecie[p]; ++j)
                            for (int n = i;;) {
                                    n = pudelko[pudelko64[n] + przesuniecieTab[p][j]];
                                    if (n == -1)
                                            break;
                                    if (color[n] != EMPTY) {
                                            if (color[n] == xside)
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
            if (side == LIGHT) {
                    if (((wieza & 1) != 0) && (ulozeniePionkow[F1] == EMPTY)
                                    && (ulozeniePionkow[G1] == EMPTY))
                    	dodajNaStos(ret, E1, G1, 2);
                    if (((wieza & 2) != 0) && (ulozeniePionkow[D1] == EMPTY)
                                    && (ulozeniePionkow[C1] == EMPTY) && (ulozeniePionkow[B1] == EMPTY))
                    	dodajNaStos(ret, E1, C1, 2);
            } else {
                    if (((wieza & 4) != 0) && (ulozeniePionkow[F8] == EMPTY)
                                    && (ulozeniePionkow[G8] == EMPTY))
                    	dodajNaStos(ret, E8, G8, 2);
                    if (((wieza & 8) != 0) && (ulozeniePionkow[D8] == EMPTY)
                                    && (ulozeniePionkow[C8] == EMPTY) && (ulozeniePionkow[B8] == EMPTY))
                    	dodajNaStos(ret, E8, C8, 2);
            }

            /* generujemy en ruchów*/
            if (ep != -1) {
                    if (side == LIGHT) {
                            if (COL(ep) != 0 && color[ep + 7] == LIGHT
                                            && ulozeniePionkow[ep + 7] == PAWN)
                            	dodajNaStos(ret, ep + 7, ep, 21);
                            if (COL(ep) != 7 && color[ep + 9] == LIGHT
                                            && ulozeniePionkow[ep + 9] == PAWN)
                            	dodajNaStos(ret, ep + 9, ep, 21);
                    } else {
                            if (COL(ep) != 0 && color[ep - 9] == DARK
                                            && ulozeniePionkow[ep - 9] == PAWN)
                            	dodajNaStos(ret, ep - 9, ep, 21);
                            if (COL(ep) != 7 && color[ep - 7] == DARK
                                            && ulozeniePionkow[ep - 7] == PAWN)
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

            if (side == LIGHT) {
                    long moves = ((bitPionka[LIGHT] & 0x00fefefefefefefeL) >> 9)
                                    & bitulozeniePionkow[DARK];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 9, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[LIGHT] & 0x007f7f7f7f7f7f7fL) >> 7)
                                    & bitulozeniePionkow[DARK];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove + 7, theMove, 17);
                            moves &= (moves - 1);
                    }
            } else {
                    long moves = ((bitPionka[DARK] & 0x00fefefefefefefeL) << 7)
                                    & bitulozeniePionkow[LIGHT];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 7, theMove, 17);
                            moves &= (moves - 1);
                    }
                    moves = ((bitPionka[DARK] & 0x007f7f7f7f7f7f7fL) << 9)
                                    & bitulozeniePionkow[LIGHT];
                    while (moves != 0) {
                            int theMove = getLBit(moves);
                            dodajNaStos(ret, theMove - 9, theMove, 17);
                            moves &= (moves - 1);
                    }
            }
            long pieces = bitulozeniePionkow[side] ^ bitPionka[side];
            while (pieces != 0) {
                    int p = getLBit(pieces);
                    for (int j = 0; j < przesuniecie[ulozeniePionkow[p]]; ++j)
                            for (int n = p;;) {
                                    n = pudelko[pudelko64[n] + przesuniecieTab[ulozeniePionkow[p]][j]];
                                    if (n == -1)
                                            break;
                                    if (color[n] != EMPTY) {
                                            if (color[n] == xside)
                                            	dodajNaStos(ret, p, n, 1);
                                            break;
                                    }
                                    if (!przesun[ulozeniePionkow[p]])
                                            break;
                            }
                    pieces &= (pieces - 1);
            }
            if (ep != -1) {
                    if (side == LIGHT) {
                            if (COL(ep) != 0 && color[ep + 7] == LIGHT
                                            && ulozeniePionkow[ep + 7] == PAWN)
                            	dodajNaStos(ret, ep + 7, ep, 21);
                            if (COL(ep) != 7 && color[ep + 9] == LIGHT
                                            && ulozeniePionkow[ep + 9] == PAWN)
                            	dodajNaStos(ret, ep + 9, ep, 21);
                    } else {
                            if (COL(ep) != 0 && color[ep - 9] == DARK
                                            && ulozeniePionkow[ep - 9] == PAWN)
                            	dodajNaStos(ret, ep - 9, ep, 21);
                            if (COL(ep) != 7 && color[ep - 7] == DARK
                                            && ulozeniePionkow[ep - 7] == PAWN)
                            	dodajNaStos(ret, ep - 7, ep, 21);
                    }
            }
            return ret;
    }

 /* dodajNaStos() wstawia na stos ruchów chyba ze na 
  * stosie jest pionek promowany u¿ywamy wtedy funkcji 
  * dodaj4Ruchy().
  * Przypisanie ruchów alfanumeryczni  
  * Jeœli ruch jest przechwytywanie, u¿ywa MVV / Lot
  * (Most Valuable Victim / najmniej cenne Atakuj¹cy). 
  * W przeciwnym razie, u¿ywa on the Move to wartoœæ heurystyczn¹ historii.
  * Nale¿y pamiêtaæ, ¿e 1000000 wprowadza siê do przechwytywania 
  * w wyniku przemieszczania siê, a wiêc zawsze pobiera uporz¹dkowane 
  * powy¿ej "normalnego" ruchu. */
 
 @SuppressWarnings({ "unchecked", "rawtypes" })
void dodajNaStos(Collection ret, int from, int to, int bits) {
            if ((bits & 16) != 0) {
                    if (side == LIGHT) {
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
           
            if (color[to] != EMPTY)
                    g.setScore(1000000 + (ulozeniePionkow[to] * 10) - ulozeniePionkow[from]);
            else
                    g.setScore(history[from][to]);
            ret.add(g);
    }    
 
 /* genPromote() jest taki jak dodajNaStos (), tylko stawia 4 ruchy
  na stosie ruchu, po jednym dla ka¿dego mo¿liwego elementu promocji */
 
 @SuppressWarnings({ "rawtypes", "unchecked" })
void dodaj4Ruchy(Collection ret, int from, int to, int bits) {
            for (char i = KNIGHT; i <= QUEEN; ++i) {
                    Ruchy g = new Ruchy(from, to, i, (bits | 32), 'P');
                    g.setScore(1000000 + (i * 10));
                    ret.add(g);
            }
 }
 
 /* makemove() wykonaj ruch. Jesli ruch jest z³y to 
  * zwraca false i cofa ruch w przeciwnym racie true*/      
 @SuppressWarnings("rawtypes")
public boolean wykonajRuch(Ruchy m) {
            long oldBits[] = { bitulozeniePionkow[LIGHT], bitulozeniePionkow[DARK] };

            int from, to;
            if ((m.bits & 2) != 0) {

                    if (sprawdzAtak(side))
                            return false;
                    switch (m.getTo()) {
                    case 62:
                            if (color[F1] != EMPTY || color[G1] != EMPTY
                                            || attack(F1, xside) || attack(G1, xside))
                                    return false;
                            from = H1;
                            to = F1;
                            break;
                    case 58:
                            if (color[B1] != EMPTY || color[C1] != EMPTY
                                            || color[D1] != EMPTY || attack(C1, xside)
                                            || attack(D1, xside))
                                    return false;
                            from = A1;
                            to = D1;
                            break;
                    case 6:
                            if (color[F8] != EMPTY || color[G8] != EMPTY
                                            || attack(F8, xside) || attack(G8, xside))
                                    return false;
                            from = H8;
                            to = F8;
                            break;
                    case 2:
                            if (color[B8] != EMPTY || color[C8] != EMPTY
                                            || color[D8] != EMPTY || attack(C8, xside)
                                            || attack(D8, xside))
                                    return false;
                            from = A8;
                            to = D8;
                            break;
                    default: /* shouldn't get here */
                            from = -1;
                            to = -1;
                            break;
                    }
                    color[to] = color[from];
                    ulozeniePionkow[to] = ulozeniePionkow[from];
                    color[from] = EMPTY;
                    ulozeniePionkow[from] = EMPTY;
                    bitulozeniePionkow[side] ^= (1L << from) | (1L << to);
            }
            /* kopii zapasowych informacji, abyœmy mogli cofn¹æ ruch. */
           
            HistoriaRuchow h = new HistoriaRuchow();              
            h.m = m;
            to = m.getTo();
            from = m.getFrom();
            h.capture = ulozeniePionkow[to];
            h.wieza = wieza;
            h.ep = ep;
            h.fifty = fifty;
            h.bitPionka = new long[] { bitPionka[LIGHT], bitPionka[DARK] };
            h.bitulozeniePionkow = oldBits;
            histDat[hply++] = h;
                                                       
            /*
             * zaktualizowaæ pozycji wiezy             */
            wieza &= ruchywiezy[from] & ruchywiezy[to];
            if ((m.bits & 8) != 0) {
                    if (side == LIGHT)
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
            if (thePiece == KING)
            	polozenieKrola[side] = to;
            color[to] = side;
            if ((m.bits & 32) != 0) {
            	ulozeniePionkow[to] = m.promote;
            	matP[side] += wartosci[m.promote];
            } else
            	ulozeniePionkow[to] = thePiece;
            color[from] = EMPTY;
            ulozeniePionkow[from] = EMPTY;
            long fromBits = 1L << from;
            long toBits = 1L << to;
            bitulozeniePionkow[side] ^= fromBits | toBits;
            if ((m.bits & 16) != 0) {
            	bitPionka[side] ^= fromBits;
                    if ((m.bits & 32) == 0)
                    	bitPionka[side] |= toBits;
            }
            int capture = h.capture;                
            if (capture != EMPTY) {
            	bitulozeniePionkow[xside] ^= toBits;
                    if (capture == PAWN)
                    	bitPionka[xside] ^= toBits;
                    else
                    	matP[xside] -= wartosci[capture];
            }

            /* usun¹æ pionka czy to ruch pionka  */
            if ((m.bits & 4) != 0) {
                    if (side == LIGHT) {
                            color[to + 8] = EMPTY;
                            ulozeniePionkow[to + 8] = EMPTY;
                            bitulozeniePionkow[DARK] ^= (1L << (to + 8));
                            bitPionka[DARK] ^= (1L << (to + 8));
                    } else {
                            color[to - 8] = EMPTY;
                            ulozeniePionkow[to - 8] = EMPTY;
                            bitulozeniePionkow[LIGHT] ^= (1L << (to - 8));
                            bitPionka[LIGHT] ^= (1L << (to - 8));
                    }
            }

            /*
             * zmieniæ strony i sprawdzania poprawnoœæ ruchów (jeœli mo¿emy uchwyciæ inna pozycje
              * Król, to z³a pozycja i musimy podj¹æ ruch do ty³u
             */
            side ^= 1;
            xside ^= 1;
            if (sprawdzAtak(xside)) {
            	cofnijRuch();
                    return false;
            }
            return true;
    }
 
 /* cofnijRuch() to samo co Makemowe tylko cofie do ty³u ruch:)  */
 @SuppressWarnings("rawtypes")
void cofnijRuch() {
            side ^= 1;
            xside ^= 1;
            HistoriaRuchow h = histDat[--hply];
            bitPionka = h.bitPionka;
            bitulozeniePionkow = h.bitulozeniePionkow;
            Ruchy m = h.m;
            wieza = h.wieza;
            ep = h.ep;
            fifty = h.fifty;
            int from = m.getFrom();
            int to = m.getTo();
            color[from] = side;
            if ((m.bits & 32) != 0) {
            	ulozeniePionkow[from] = PAWN;
            	matP[side] -= wartosci[h.m.promote];
            } else {
                    int thePiece = ulozeniePionkow[to];
                    if (thePiece == KING)
                    	polozenieKrola[side] = from;
                    ulozeniePionkow[from] = thePiece;
            }
            if (h.capture == EMPTY) {
                    color[to] = EMPTY;
                    ulozeniePionkow[to] = EMPTY;
            } else {
                    color[to] = xside;
                    ulozeniePionkow[to] = h.capture;
                    if (h.capture != PAWN)
                    	matP[xside] += wartosci[h.capture];
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
                    color[cto] = side;
                    ulozeniePionkow[cto] = ROOK;
                    color[cfrom] = EMPTY;
                    ulozeniePionkow[cfrom] = EMPTY;
            }
            if ((m.bits & 4) != 0) {
                    if (side == LIGHT) {
                            color[to + 8] = xside;
                            ulozeniePionkow[to + 8] = PAWN;
                    } else {
                            color[to - 8] = xside;
                            ulozeniePionkow[to - 8] = PAWN;
                    }
            }
    }
 
 public String toString() {
            int i;

            StringBuffer sb = new StringBuffer("\n8 ");
            for (i = 0; i < 64; ++i) {
                    switch (color[i]) {
                    case EMPTY:
                            sb.append(" .");
                            break;
                    case LIGHT:
                            sb.append(" ");
                            sb.append(znakPionka[ulozeniePionkow[i]]);
                            break;
                    case DARK:
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
                    if (++b[histDat[i].m.getFrom()] == 0)
                            --c;
                    else
                            ++c;
                    if (--b[histDat[i].m.getTo()] == 0)
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

            if (oldPawnBits != (bitPionka[LIGHT] | bitPionka[DARK])) {
                    for (int i = 0; i < 10; ++i) {
                    	MiejscePionka[LIGHT][i] = 0;
                    	MiejscePionka[DARK][i] = 7;
                    }
                    pawnMat[LIGHT] = 0;
                    pawnMat[DARK] = 0;
                    long pieces = bitPionka[LIGHT];
                    while (pieces != 0) {
                            int i = getLBit(pieces);
                            pawnMat[LIGHT] += wartosci[PAWN];
                            int f = COL(i) + 1; /*
                                                                     * dodaæ 1 ze wzglêdu na dodatkowe pliku w
                                                                      * tablica
                                                                     */
                            if (MiejscePionka[LIGHT][f] < ROW(i))
                            	MiejscePionka[LIGHT][f] = ROW(i);
                            pieces &= (pieces - 1);
                    }
                    pieces = bitPionka[DARK];
                    while (pieces != 0) {
                            int i = getLBit(pieces);
                            pawnMat[DARK] += wartosci[PAWN];
                            int f = COL(i) + 1; /*
                                                                                                                                        */
                            if (MiejscePionka[DARK][f] > ROW(i))
                            	MiejscePionka[DARK][f] = ROW(i);
                            pieces &= (pieces - 1);
                    }
                    oldPawnBits = bitPionka[LIGHT] | bitPionka[DARK];
            }
            score[LIGHT] = matP[LIGHT] + pawnMat[LIGHT];
            score[DARK] = matP[DARK] + pawnMat[DARK];
            for (int i = 0; i < 64; ++i) {
                    if (color[i] == EMPTY)
                            continue;
                    if (color[i] == LIGHT) {
                            switch (ulozeniePionkow[i]) {
                            case PAWN:
                                    score[LIGHT] += pozPionkaBialego(i);
                                    break;
                            case KNIGHT:
                                    score[LIGHT] += krolowaPC[i];
                                    break;
                            case BISHOP:
                                    score[LIGHT] += skoczekPC[i];
                                    break;
                            case ROOK:
                                    if (MiejscePionka[LIGHT][COL(i) + 1] == 0) {
                                            if (MiejscePionka[DARK][COL(i) + 1] == 7)
                                                    score[LIGHT] += ROOKOPENFILE15;
                                            else
                                                    score[LIGHT] += ROOKSEMIOPENFILE10;
                                    }
                                    if (ROW(i) == 1)
                                            score[LIGHT] += ROOKONSEVENTH20;
                                    break;
                            case KING:
                                    if (matP[DARK] <= 1200)
                                            score[LIGHT] += koncoweRuchyKrolaPC[i];
                                    else
                                            score[LIGHT] += pozBialegoKrola(i);
                                    break;
                            }
                    } else {
                            switch (ulozeniePionkow[i]) {
                            case PAWN:
                                    score[DARK] += pozPionkaCzarnego(i);
                                    break;
                            case KNIGHT:
                                    score[DARK] += krolowaPC[nrSzachownicy[i]];
                                    break;
                            case BISHOP:
                                    score[DARK] += skoczekPC[nrSzachownicy[i]];
                                    break;
                            case ROOK:
                                    if (MiejscePionka[DARK][COL(i) + 1] == 7) {
                                            if (MiejscePionka[LIGHT][COL(i) + 1] == 0)
                                                    score[DARK] += ROOKOPENFILE15;
                                            else
                                                    score[DARK] += ROOKSEMIOPENFILE10;
                                    }
                                    if (ROW(i) == 6)
                                            score[DARK] += ROOKONSEVENTH20;
                                    break;
                            case KING:
                                    if (matP[LIGHT] <= 1200)
                                            score[DARK] += koncoweRuchyKrolaPC[nrSzachownicy[i]];
                                    else
                                            score[DARK] += pozCzarnegoKrola(i);
                                    break;
                            }
                    }
            }

            /*
             * the score[] jest ustawiona pozycje pionka
              * 
             */
            if (side == LIGHT)
                    return score[LIGHT] - score[DARK];
            return score[DARK] - score[LIGHT];
    }  
 
 public int pozPionkaBialego(int sq) {
            int r = 0; /* zwróc wartosc */
            int f = COL(sq) + 1; /* zwróc dane która kolumna*/

            r += pionkiPC[sq];

            /* jeœli jest pionkiem */
            if (MiejscePionka[LIGHT][f] > ROW(sq))
                    r -= PAWNPENALTY10;

            /*
             * jeœli nie ma ¿adnych przyjazne pionki po obu stronach tego jednego
              * 
             */
            if ((MiejscePionka[LIGHT][f - 1] == 0) && (MiejscePionka[LIGHT][f + 1] == 0))
                    r -= PAWNPENALTY20;

            else if ((MiejscePionka[LIGHT][f - 1] < ROW(sq))
                            && (MiejscePionka[LIGHT][f + 1] < ROW(sq)))
                    r -= PAWNPENALTY8;

             if ((MiejscePionka[DARK][f - 1] >= ROW(sq))
                            && (MiejscePionka[DARK][f] >= ROW(sq))
                            && (MiejscePionka[DARK][f + 1] >= ROW(sq)))
                    r += (7 - ROW(sq)) * PAWNBONUS20;

            return r;
    }

 public int pozPionkaCzarnego(int sq) {
            int r = 0; /* wartoœæ zwracana */
            int f = COL(sq) + 1; /* pioneki w pliku */

            r += pionkiPC[nrSzachownicy[sq]];

            if (MiejscePionka[DARK][f] < ROW(sq))
                    r -= PAWNPENALTY10;

             if ((MiejscePionka[DARK][f - 1] == 7) && (MiejscePionka[DARK][f + 1] == 7))
                    r -= PAWNPENALTY20;

            else if ((MiejscePionka[DARK][f - 1] > ROW(sq))
                            && (MiejscePionka[DARK][f + 1] > ROW(sq)))
                    r -= PAWNPENALTY8;

             if ((MiejscePionka[LIGHT][f - 1] <= ROW(sq))
                            && (MiejscePionka[LIGHT][f] <= ROW(sq))
                            && (MiejscePionka[LIGHT][f + 1] <= ROW(sq)))
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
                            if ((MiejscePionka[LIGHT][i] == 0) && (MiejscePionka[DARK][i] == 7))
                                    r -= 10;
            }

            /*
             * sprawdzaæ wartoœæ bezpieczeñstwa króla wed³ug przeciwnika ;
              Za³o¿eniem jest, ¿e * bezpieczeñstwo król mo¿e byæ tylko Ÿle, jeœli przeciwnik ma
              * Ma³o sztuk do ataku
             */
            r *= matP[DARK];
            r /= 3100;

            return r;
    }

    /* sprawdzPionkaBialego(f) ocena pionka  */

    int sprawdzPionkaBialego(int f) {
            int r = 0;

            if (MiejscePionka[LIGHT][f] == 6)
                    ; /* pionek nie ruchomy */
            else if (MiejscePionka[LIGHT][f] == 5)
                    r -= 10; /* pionek przesuno³ sie o jedno pole */
            else if (MiejscePionka[LIGHT][f] != 0)
                    r -= 20; /* pionek przesuno³ sie o wiecej pól */
            else
                    r -= 25; /* to nie pionek */

            if (MiejscePionka[DARK][f] == 7)
                    r -= 15; /* pionek przeciwnika */
            else if (MiejscePionka[DARK][f] == 5)
                    r -= 10; /* pionek przeciwnika na 3 losowaniu */
            else if (MiejscePionka[DARK][f] == 4)
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
                            if ((MiejscePionka[LIGHT][i] == 0) && (MiejscePionka[DARK][i] == 7))
                                    r -= 10;
            }
            r *= matP[LIGHT];
            r /= 3100;
            return r;
    }

    int sprawdzPionkaCzarnego(int f) {
            int r = 0;

            if (MiejscePionka[DARK][f] == 1)
                    ;
            else if (MiejscePionka[DARK][f] == 2)
                    r -= 10;
            else if (MiejscePionka[DARK][f] != 7)
                    r -= 20;
            else
                    r -= 25;

            if (MiejscePionka[LIGHT][f] == 0)
                    r -= 15;
            else if (MiejscePionka[LIGHT][f] == 2)
                    r -= 10;
            else if (MiejscePionka[LIGHT][f] == 3)
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
