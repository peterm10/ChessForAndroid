package com.chess.chessandroid;

public class RuchyPodstawowe {
    protected final int from;
    protected final int to;

    public RuchyPodstawowe(int from, int to) {
            this.from = from;
            this.to = to;
    }
    public final int getOdWier() {
            return from >> 3;
    }
    public final int getDoWierszy() {
            return to >> 3;
    }
    public final int getDo() {
            return to;
    }
    public final int getOd() {
            return from;
    }
    public final int getOdKolumn() {
            return from % 8;
    }

    public final int getDoKolumn() {
            return to % 8;
    }
}

