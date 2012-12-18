package com.chess.chessandroid;

public final class Move<T> extends MoveBase implements Constants, Comparable<T> {

    public int compareTo(T arg0) {
            Move m = (Move) arg0;
    int mScore = m.getScore();
    return mScore - score; // Can't overflow so this should work.
    }
   
    final int promote, bits;
    final char pieceLetter;
    int score = 0;

    public Move(int from, int to, int promote, int bits, char pieceLetter) {
            super(from, to);
            this.promote = promote;
            this.bits = bits;
            this.pieceLetter = pieceLetter;
    }

    int getScore() {
            return score;
    }

    void setScore(int i) {
            score = i;
    }

    public int hashCode() {
            return from + (to << 8) + (promote << 16);
    }

    public boolean equals(Object o) {
            Move m = (Move) o;
            return (m.from == from && m.to == to && m.promote == promote);
    }
   
public String toString() {
            char c;
            StringBuffer sb = new StringBuffer();

            if ((bits & 32) != 0) {
                    switch (promote) {
                    case KNIGHT:
                            c = 'n';
                            break;
                    case BISHOP:
                            c = 'b';
                            break;
                    case ROOK:
                            c = 'r';
                            break;
                    default:
                            c = 'q';
                            break;
                    }
                    sb.append((char) (getFromCol() + 'a'));
                    sb.append(8 - getFromRow());
                    sb.append("-");
                    sb.append((char) (getToCol() + 'a'));
                    sb.append(8 - getToRow());
                    sb.append(c);
            } else {
                    /*
                    if (pieceLetter != 'P')
                            sb.append((char) pieceLetter);
                            */
                    sb.append((char) (getFromCol() + 'a'));
                    sb.append(8 - getFromRow());
                    sb.append("-");
                    sb.append((char) (getToCol() + 'a'));
                    sb.append(8 - getToRow());
            }
            return sb.toString();
    }
}
