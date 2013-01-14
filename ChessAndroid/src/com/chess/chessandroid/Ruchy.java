package com.chess.chessandroid;

public final class Ruchy<T> extends RuchyPodstawowe implements Sta³eConst, Comparable<T> {

    
   
    final char znak;
    public int wynik = 0;
    final int wyroznij, bits;
    
    public Ruchy(int from, int to, int wyroznij, int bits, char znak) {
            super(from, to);
            this.wyroznij = wyroznij;
            this.bits = bits;
            this.znak = znak;
    }

      @SuppressWarnings("rawtypes")
	public int compareTo(T arg0) {
            Ruchy m = (Ruchy) arg0;
            int mScore = m.getWynik();
            return mScore - wynik; 
    } 
public String toString() {
            char c;
            StringBuffer sb = new StringBuffer();

            if ((bits & 32) != 0) {
                    switch (wyroznij) {
                    case GONIEC:
                            c = 'n';
                            break;
                    case SKOCZEK:
                            c = 'b';
                            break;
                    case WIEZA:
                            c = 'r';
                            break;
                    default:
                            c = 'q';
                            break;
                    }
                    sb.append((char) (getOdKolumn() + 'a'));
                    sb.append(8 - getOdWier());
                    sb.append("-");
                    sb.append((char) (getDoKolumn() + 'a'));
                    sb.append(8 - getDoWierszy());
                    sb.append(c);
            } else {
                    /*
                    if (znak != 'P')
                            sb.append((char) znak);
                            */
                    sb.append((char) (getOdKolumn() + 'a'));
                    sb.append(8 - getOdWier());
                    sb.append("-");
                    sb.append((char) (getDoKolumn() + 'a'));
                    sb.append(8 - getDoWierszy());
            }
            return sb.toString();
    }
	/*@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
		Ruchy m = (Ruchy) o;
    	return (m.from == from && m.to == to && m.wyroznij == wyroznij);
	}
	public int hashCode() {
		return from + (to << 8) + (wyroznij << 16);
	}*/
	public int getWynik() {
		return this.wynik;
	}
	 
	void setWynik(int i) {
		this.wynik = i;
	}




}
