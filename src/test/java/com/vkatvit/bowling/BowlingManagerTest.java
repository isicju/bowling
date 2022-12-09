package com.vkatvit.bowling;

import com.vkatvit.DefaultTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BowlingManagerTest extends DefaultTest {

    BowlingManager bowlingManager;

    @BeforeEach
    public void init() {
        bowlingManager = new BowlingManager();
    }

    @Test
    public void checkWorseResults() {
        byte[] allThrows = new byte[]{
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0,
                0, 0
        };

        bowlingManager.playset(allThrows);
        List<Turn> turnList = bowlingManager.generateGameResult();
        int totalScore = turnList.stream().map(Turn::getTotalScore).reduce(0, Integer::sum);
        assertThat(0, equalTo(totalScore));
    }

    @Test
    public void checkBestResults() {
        byte[] allThrows = new byte[]{
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10
        };
        bowlingManager.playset(allThrows);
        List<Turn> turnList = bowlingManager.generateGameResult();
        int lastTurnIndex = turnList.size() - 1;
        assertThat(270, equalTo(turnList.get(lastTurnIndex).getTotalScore()));
    }

    @Test
    public void checkStrikeAndSpareCases() {
        byte[] allThrows = new byte[]{
                1, 8,
                10,
                0, 9,
                10,
                8, 1,
                0, 0,
                8, 1,
                6, 3,
                2, 2
        };

        bowlingManager.playset(allThrows);
        List<Turn> turnList = bowlingManager.generateGameResult();

        assertThat(9, equalTo(turnList.get(0).getTotalScore()));
        assertThat(28, equalTo(turnList.get(1).getTotalScore()));
        assertThat(37, equalTo(turnList.get(2).getTotalScore()));
        assertThat(56, equalTo(turnList.get(3).getTotalScore()));
        assertThat(65, equalTo(turnList.get(4).getTotalScore()));
        assertThat(65, equalTo(turnList.get(5).getTotalScore()));
        assertThat(74, equalTo(turnList.get(6).getTotalScore()));
        assertThat(83, equalTo(turnList.get(7).getTotalScore()));
        assertThat(87, equalTo(turnList.get(8).getTotalScore()));
    }

    @DisplayName("too many hits")
    @Test()
    public void checkInvalidInputTooMany() {
        byte[] allThrows = new byte[]{
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
        };

        bowlingManager.playset(allThrows);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bowlingManager.generateGameResult();
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("data is invalid"));
    }

    @DisplayName("impossible throws")
    @Test()
    public void checkInvalidNegative() {
        byte[] allThrows = new byte[]{
                -1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8,
                1, 8
        };

        bowlingManager.playset(allThrows);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bowlingManager.generateGameResult();
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("data is invalid"));
    }

    @DisplayName("missing throws")
    @Test
    public void checkNotCompletedGame() {
        byte[] allThrows = new byte[]{
                1, 8
        };

        bowlingManager.playset(allThrows);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bowlingManager.generateGameResult();
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("not completed"));
    }

}


