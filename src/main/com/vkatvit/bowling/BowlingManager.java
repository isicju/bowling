package com.vkatvit.bowling;

import java.util.ArrayList;
import java.util.List;

import static com.vkatvit.bowling.Turn.STRIKE_SCORE;

public class BowlingManager {

    Turn currentTurn;
    Hit lastHit;
    List<Turn> allTurns = new ArrayList<>();

    public void makeThrow(byte score) {
        initializeNewTurn();
        Hit newHit = new Hit(score, currentTurn);
        if (lastHit != null) {
            lastHit.setNextHit(newHit);
        }
        currentTurn.addHit(newHit);
        lastHit = newHit;
    }

    private void initializeNewTurn() {
        if (currentTurn == null || currentTurn.isCompleted()) {
            currentTurn = new Turn();
            allTurns.add(currentTurn);
        }
    }

    public List<Turn> generateGameResult(){
        calculateScores();
        return allTurns;
    }

    private int calculateScores() {
        validateGame();
        int accumulativeSum = 0;
        for (Turn turn : allTurns) {
            int scorePerTurn = turn.isStrike() ? getStrikeScore(turn.firstHit, turn.secondHit) :
                    (turn.isSpare() ? getSpareScore(turn.firstHit, turn.secondHit) : getRegularScore(turn.firstHit, turn.secondHit));
            accumulativeSum += scorePerTurn;
            turn.setTotalScore(accumulativeSum);
        }
        return accumulativeSum;
    }

    private void validateGame() {
        if (gameIsInvalid()) throw new RuntimeException("data is invalid");
        if (!gameIsCompleted()) throw new RuntimeException("game is not completed");
    }

    private boolean gameIsCompleted() {
        return allTurns.size() == 9;
    }

    private boolean gameIsInvalid() {
        Hit hit = allTurns.get(0).firstHit;
        while (hit.nextHit != null){
            if (hit.score > 10 || hit.score < 0) return true;
            hit = hit.nextHit;
        }
        return allTurns.size() > 9;
    }

    private int getStrikeScore(Hit hit, Hit secondHit) {
        return getSpareScore(hit, secondHit) + getNextNonNullScore(2, hit);
    }

    private int getSpareScore(Hit hit, Hit secondHit) {
        return getRegularScore(hit, secondHit) + getNextNonNullScore(1, hit);
    }

    private int getRegularScore(Hit hit, Hit secondHit) {
        return hit.score == STRIKE_SCORE ? hit.score : hit.score + secondHit.score;
    }

    private int getNextNonNullScore(int iterations, Hit hit) {
        for (int i = 0; i < iterations; i++) {
            if (hit.nextHit != null) hit = hit.nextHit;

        }
        return hit.score;
    }


    public void playset(byte[] allThrows) {
        for(byte thrown: allThrows){
            makeThrow(thrown);
        }
    }
}
