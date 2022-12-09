package com.vkatvit.bowling;

public class Turn {

    public static final int STRIKE_SCORE = 10;

    private int totalScore;
    Hit firstHit;
    Hit secondHit;

    public void addHit(Hit hit) {
        if (firstHit == null) firstHit = hit;
        else {
            secondHit = hit;
            firstHit.nextHit = secondHit;
        }
    }

    public boolean isCompleted() {
        return firstHit != null && firstHit.score == STRIKE_SCORE || secondHit != null;
    }

    public boolean isStrike() {
        return firstHit.score == STRIKE_SCORE;
    }

    public boolean isSpare() {
        return secondHit != null && (firstHit.score + secondHit.score) == STRIKE_SCORE;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

}