package com.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class ScoreboardImplTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = ScoreboardFactory.createSimpleScoreboard();
    }

    @Test
    void shouldStartNewGame() {
        // given
        scoreboard.startNewGame("Mexico", "Canada");

        // when
        List<String> summary = scoreboard.getGamesSummary();

        // then
        assertThat(summary, hasSize(1));
        assertThat(summary.get(0), equalTo("Mexico 0 - Canada 0"));
    }
}