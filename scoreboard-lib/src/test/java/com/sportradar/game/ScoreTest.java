package com.sportradar.game;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void testDefaultScore() {
        // given & when
        var defaultScore = Score.defaultScore();

        // then
        assertAll(() -> {
            assertEquals(Score.DEFAULT_SCORE, defaultScore.getAwayTeamScore());
            assertEquals(Score.DEFAULT_SCORE, defaultScore.getHomeTeamScore());
            assertEquals(Score.DEFAULT_SCORE, defaultScore.getTotalScore());
        });
    }

    @Test
    void testScore() {
        // given & when
        var startTime = LocalDateTime.of(2023, 7, 24, 19, 23);
        var score = Score.builder()
                .homeTeamScore(5)
                .awayTeamScore(4)
                .startTime(startTime)
                .build();

        // then
        assertAll(() -> {
            assertEquals(4, score.getAwayTeamScore());
            assertEquals(5, score.getHomeTeamScore());
            assertEquals(9, score.getTotalScore());
            assertEquals(startTime, score.getStartTime());
        });
    }
}