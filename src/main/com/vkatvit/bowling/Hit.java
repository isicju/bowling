package com.vkatvit.bowling;

public class Hit {
    int score;
    Turn turn;
    Hit nextHit;

    public Hit(int score, Turn turn) {
        this.score = score;
        this.turn = turn;
    }

    public void setNextHit(Hit nextHit) {
        this.nextHit = nextHit;
    }

}