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

    @Test
    void shouldFinishGame() {
        // given
        scoreboard.startNewGame("Mexico", "Canada");

        assertThat("The game is not started", scoreboard.getGamesSummary(), hasSize(1));

        // when
        scoreboard.finishGame("Mexico", "Canada");

        // then
        assertThat(scoreboard.getGamesSummary(), hasSize(0));
    }

    @Test
    void shouldUpdateGameScore() {
        // given
        scoreboard.startNewGame("Mexico", "Canada");

        // when
        scoreboard.updateGameScore("Mexico", 1, "Canada", 2);

        // then
        assertThat(scoreboard.getGamesSummary().get(0), equalTo("Mexico 1 - Canada 2"));
    }


}