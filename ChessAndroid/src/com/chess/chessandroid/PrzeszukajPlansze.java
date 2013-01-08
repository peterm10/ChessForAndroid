package com.chess.chessandroid;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class PrzeszukajPlansze {
        public Ruchy getBest() {
                return pv[0][0];
        }

        public Ruchy getBestNext() {
                return pv[0][1];
        }

        public void stopThinking() {
                stop = true;
        }

        public void restartThinking() {
                stop = false;
        }

        public boolean isStopped() {
                return stop;
        }

        public void setStopTime(long stop) {
                stopTime = stop;
        }

        public void clearPV() {
                for (int i = 0; i < MAX_PLY; i++)
                        for (int j = 0; j < MAX_PLY; j++)
                                pv[i][j] = null;
        }

        public void shiftPV() {
                pvLength[0] -= 2;
                for (int i = 0; i < pvLength[0]; i++)
                        pv[0][i] = pv[0][i + 2];
        }

        void think(MainActivity app) {
                stop = false;
                try {
                        ply = 0;
                        nodes = 0;
                       
                        for (int i = 0; i < 64; i++)
                                for (int j = 0; j < 64; j++)
                                        board.history[i][j] = 0;                                        
                       
                        for (int i = 3; i <= MAX_PLY; ++i) {
                                followPV = true;
                                int x = search(-10000, 10000, i);                              
                                if (x > 9000 || x < -9000)
                                        break;
                        }
                } catch (KoniecSzukaniaWyjatek e) {
                        /* sprawdzanie czy tutaj by³o szukane */
                        while (ply != 0) {
                                board.cofnijRuch();
                                --ply;
                        }
                }
               
                return;
        }

        /** search() */

        int search(int alpha, int beta, int depth) throws KoniecSzukaniaWyjatek {
                /*
                 * zwróciæ pste pole.
                 */
                if (depth == 0)
                        return quiesce(alpha, beta);

                if (beta - alpha != 1) {
                        pvLength[ply] = ply;
                }

                /*
                 * szukamy od pocz¹tku (gdzie mamy postawiony pionek i zwraca 0
                 * sprawdzanie czy pozycja siê powtórzy³a jeœli tak to jest remis i zwraca 0
                 */
                if ((ply > 0) && (board.liczbaPowtPoz() > 0))
                        return 0;

                /* jak daleko jesteœmy? */
                if (ply >= MAX_PLY - 1)
                        return board.pozyPionka();
                /*
                 * if (hply >= HIST_STACK - 1) return board.pozyPionka(); przepe³nienie stosu historii
                 */
                /* jeœli sprawdzony szukamy dalej */
                boolean check = board.sprawdzAtak(board.side);
                if (check)
                        ++depth;
                List validMoves = board.generujRuch();
                if (followPV) /* po PV? */
                        sortPV(validMoves);
                Collections.sort(validMoves);

                /* poruszanie w pêtli */
                boolean foundMove = false;
                Iterator i = validMoves.iterator();
                int a = alpha;
                int b = beta;
                boolean first = true;
                while (i.hasNext()) {
                        Ruchy m = (Ruchy) i.next();
                        if (!board.wykonajRuch(m))
                                continue;
                        ++ply;
                        ++nodes;
                        /* czyszczenie historii */
                        if ((nodes & 1023) == 0)
                                checkup();

                        foundMove = true;

                        int x = -search(-b, -a, depth - 1);
                        boolean betterMove = false;
                        if ((x > a) && (x < beta) && (!first)) {
                                a = -search(-beta, -x, depth - 1);
                                if (a >= x)
                                        betterMove = true;
                        }
                        board.cofnijRuch();
                        --ply;

                        if (x > a) {
                                a = x;
                                betterMove = true;
                        }
                        if (betterMove) {
                                
                                board.history[m.getFrom()][m.getTo()] += depth;
                                if (x >= beta)
                                        return beta;

                                pv[ply][ply] = m;
                                for (int j = ply + 1; j < pvLength[ply + 1]; ++j)
                                        pv[ply][j] = pv[ply + 1][j];
                                pvLength[ply] = pvLength[ply + 1];
                        }
                        b = a + 1;
                        first = false;
                }

                /* prawid³owy ruch? wtedy mamy mate lup remis */
                if (!foundMove) {
                        if (check)
                                return -10000 + ply;
                        else
                                return 0;
                }

                /* koniec po 50 ruchach*/
                if (board.fifty >= 100)
                        return 0;
                return a;
        }

        /*
         * quiesce() rekurencyjna funkcja wyszukiwania miejscza gdzie nie ma ruchu
         */

        int quiesce(int alpha, int beta) throws KoniecSzukaniaWyjatek {
                pvLength[ply] = ply;

                if (ply >= MAX_PLY - 1)
                        return board.pozyPionka();
                 int x = board.pozyPionka();
                if (x >= beta)
                        return beta;
                if (x > alpha)
                        alpha = x;

                List validCaptures = board.RejestrGenRuchu();
                if (followPV) 
                        sortPV(validCaptures);
                Collections.sort(validCaptures);

                Iterator i = validCaptures.iterator();
                while (i.hasNext()) {
                        Ruchy m = (Ruchy) i.next();
                        if (!board.wykonajRuch(m))
                                continue;
                        ++ply;
                        ++nodes;

                        if ((nodes & 1023) == 0)
                                checkup();

                        x = -quiesce(-beta, -alpha);
                        board.cofnijRuch();
                        --ply;

                        if (x > alpha) {
                                if (x >= beta)
                                        return beta;
                                alpha = x;

                                pv[ply][ply] = m;
                                for (int j = ply + 1; j < pvLength[ply + 1]; ++j)
                                        pv[ply][j] = pv[ply + 1][j];
                                pvLength[ply] = pvLength[ply + 1];
                        }
                }
                return alpha;
        }

        /*
         * sortPV() g³ówne zmiany na planszy
         */

        public void sortPV(Collection moves) {
                followPV = false;
                if (pv[0][ply] == null)
                        return;
                Iterator i = moves.iterator();
                while (i.hasNext()) {
                        Ruchy m = (Ruchy) i.next();
                        if (m.equals(pv[0][ply])) {
                                followPV = true;
                                m.score += 10000000;
                                return;
                        }
                }
        }

        /* checkup()  */

        public void checkup() throws KoniecSzukaniaWyjatek {
                
               
                if (System.currentTimeMillis() >= stopTime || stop) {
                        throw new KoniecSzukaniaWyjatek();
                }
               
               
               
        }
               
        final static int MAX_PLY = 32;

        protected BoardGame board = new BoardGame();
        private Ruchy pv[][] = new Ruchy[MAX_PLY][MAX_PLY];
        private int pvLength[] = new int[MAX_PLY];
        public boolean followPV;
        private int ply = 0;
        private int nodes = 0;
        private long stopTime = Long.MAX_VALUE;
        private boolean stop = false;
}

