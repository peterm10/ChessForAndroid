package com.chess.chessandroid;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class PrzeszukajPlansze {
        
        public void ponownieOczekuj() {
                stop = false;
        }
        @SuppressWarnings("rawtypes")
		public Ruchy getNajlepszy2Ruch() {
                return ruchyPV[0][1];
        }
        @SuppressWarnings("rawtypes")
		public Ruchy getNajlepszyRuch() {
                return ruchyPV[0][0];
        }
        public void setZaczymajOdliczanie(long stop) {
        	stopT = stop;
        }
        public void koniecOczekiwania() {
                stop = true;
        }

        

        

        

       public void wyczyscRuchyPV() {
                for (int i = 0; i < MAXPLY; i++)
                        for (int j = 0; j < MAXPLY; j++)
                        	ruchyPV[i][j] = null;
        }

      /*   public void przesunRuchPV() {
                lengthPV[0] -= 2;
                for (int i = 0; i < lengthPV[0]; i++)
                        ruchyPV[0][i] = ruchyPV[0][i + 2];
        }
*/
       public boolean zatrzymajOczekiwanie() {
                return stop;
        }
        void oczekujKlasaSzukaj(MainActivity app) {
                stop = false;
                try {
                	ruchPV = 0;
                	liczPV = 0;
                       
                        for (int i = 0; i < 64; i++)
                                for (int j = 0; j < 64; j++)
                                        board.history[i][j] = 0;                                        
                       
                        for (int i = 3; i <= MAXPLY; ++i) {
                        	boolPV = true;
                                int x = search(-10000, 10000, i);                              
                                if (x > 9000 || x < -9000)
                                        break;
                        }
                } catch (KoniecSzukaniaWyjatek e) {
                        /* sprawdzanie czy tutaj by³o szukane */
                        while (ruchPV != 0) {
                                board.cofnijRuch();
                                --ruchPV;
                        }
                }
               
                return;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
		int szukajGdzieJestNajmniejszyRuch(int alpha, int beta) throws KoniecSzukaniaWyjatek {
        	lengthPV[ruchPV] = ruchPV;

                if (ruchPV >= MAXPLY - 1)
                        return board.pozyPionka();
                 int x = board.pozyPionka();
                if (x >= beta)
                        return beta;
                if (x > alpha)
                        alpha = x;

                List listaPrawid³owychRuchow = board.RejestrGenRuchu();
                if (boolPV) 
                	sortujRuchy(listaPrawid³owychRuchow);
                Collections.sort(listaPrawid³owychRuchow);

                Iterator i = listaPrawid³owychRuchow.iterator();
                while (i.hasNext()) {
                        Ruchy m = (Ruchy) i.next();
                        if (!board.wykonajRuch(m))
                                continue;
                        ++ruchPV;
                        ++liczPV;

                        if ((liczPV & 1023) == 0)
                        	sprawdzPoprzednie();

                        x = -szukajGdzieJestNajmniejszyRuch(-beta, -alpha);
                        board.cofnijRuch();
                        --ruchPV;

                        if (x > alpha) {
                                if (x >= beta)
                                        return beta;
                                alpha = x;

                                ruchyPV[ruchPV][ruchPV] = m;
                                for (int j = ruchPV + 1; j < lengthPV[ruchPV + 1]; ++j)
                                	ruchyPV[ruchPV][j] = ruchyPV[ruchPV + 1][j];
                                lengthPV[ruchPV] = lengthPV[ruchPV + 1];
                        }
                }
                return alpha;
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
		int search(int alpha, int beta, int depth) throws KoniecSzukaniaWyjatek {
                /*
                 * zwróciæ pste pole.
                 */
                if (depth == 0)
                        return szukajGdzieJestNajmniejszyRuch(alpha, beta);

                if (beta - alpha != 1) {
                	lengthPV[ruchPV] = ruchPV;
                }

                /*
                 * szukamy od pocz¹tku (gdzie mamy postawiony pionek i zwraca 0
                 * sprawdzanie czy pozycja siê powtórzy³a jeœli tak to jest remis i zwraca 0
                 */
                if ((ruchPV > 0) && (board.liczbaPowtPoz() > 0))
                        return 0;

                /* jak daleko jesteœmy? */
                if (ruchPV >= MAXPLY - 1)
                        return board.pozyPionka();
                /*
                 * if (hply >= HIST_STACK - 1) return board.pozyPionka(); przepe³nienie stosu historii
                 */
                /* jeœli sprawdzony szukamy dalej */
                boolean sprawdz = board.sprawdzAtak(board.bialy);
                if (sprawdz)
                        ++depth;
                List zleRuchyList = board.generujRuch();
                if (boolPV) /* po PV? */
                	sortujRuchy(zleRuchyList);
                Collections.sort(zleRuchyList);

                /* poruszanie w pêtli */
                int a = alpha;
                int b = beta;
                boolean first = true;
                boolean szukajGdzie = false;
                Iterator i = zleRuchyList.iterator();
                while (i.hasNext()) {
                        Ruchy m = (Ruchy) i.next();
                        if (!board.wykonajRuch(m))
                                continue;
                        ++ruchPV;
                        ++liczPV;
                        /* czyszczenie historii */
                        if ((liczPV & 1023) == 0)
                        	sprawdzPoprzednie();

                        szukajGdzie = true;

                        int x = -search(-b, -a, depth - 1);
                        boolean betterMove = false;
                        if ((x > a) && (x < beta) && (!first)) {
                                a = -search(-beta, -x, depth - 1);
                                if (a >= x)
                                        betterMove = true;
                        }
                        board.cofnijRuch();
                        --ruchPV;

                        if (x > a) {
                                a = x;
                                betterMove = true;
                        }
                        if (betterMove) {
                                
                                board.history[m.getOd()][m.getDo()] += depth;
                                if (x >= beta)
                                        return beta;

                                ruchyPV[ruchPV][ruchPV] = m;
                                for (int j = ruchPV + 1; j < lengthPV[ruchPV + 1]; ++j)
                                	ruchyPV[ruchPV][j] = ruchyPV[ruchPV + 1][j];
                                lengthPV[ruchPV] = lengthPV[ruchPV + 1];
                        }
                        b = a + 1;
                        first = false;
                }

                /* prawid³owy ruch? wtedy mamy mate lup remis */
                if (!szukajGdzie) {
                        if (sprawdz)
                                return -10000 + ruchPV;
                        else
                                return 0;
                }

                /* koniec po 50 ruchach*/
                if (board.fifty >= 100)
                        return 0;
                return a;
        }

        public void sprawdzPoprzednie() throws KoniecSzukaniaWyjatek {
                
               
                if (System.currentTimeMillis() >= stopT || stop) {
                        throw new KoniecSzukaniaWyjatek();
                }
               
               
               
        }
        @SuppressWarnings("rawtypes")
        public void sortujRuchy(Collection moves) {
    	   boolPV = false;
                if (ruchyPV[0][ruchPV] == null)
                        return;
                Iterator i = moves.iterator();
                while (i.hasNext()) {
                        Ruchy m = (Ruchy) i.next();
                        if (m.equals(ruchyPV[0][ruchPV])) {
                        	boolPV = true;
                                m.wynik += 10000000;
                                return;
                        }
                }
        }       
        final static int MAXPLY = 32;
        private long stopT = Long.MAX_VALUE;
        
        protected BoardGame board = new BoardGame();
        @SuppressWarnings("rawtypes")
		private Ruchy ruchyPV[][] = new Ruchy[MAXPLY][MAXPLY];
        
        public boolean boolPV;
        private boolean stop = false;
        
        private int lengthPV[] = new int[MAXPLY];
        private int ruchPV = 0;
        private int liczPV = 0;
        
}

