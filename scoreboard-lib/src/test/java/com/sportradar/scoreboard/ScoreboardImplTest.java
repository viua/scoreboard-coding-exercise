package com.sportradar.scoreboard;

import com.sportradar.exception.ScoreboardException;
import com.sportradar.game.GameSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

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
        List<GameSummary> summaries = scoreboard.getGamesSummary();

        // then
        assertThat(summaries, hasSize(1));

        var summary = scoreboard.getGamesSummary().get(0);

        assertAll(() -> {
            assertEquals(0, summary.homeTeamScore());
            assertEquals("Mexico", summary.homeTeamName());
            assertEquals(0, summary.awayTeamScore());
            assertEquals("Canada", summary.awayTeamName());
        });
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
        var summary = scoreboard.getGamesSummary().get(0);

        assertAll(() -> {
            assertEquals(1, summary.homeTeamScore());
            assertEquals("Mexico", summary.homeTeamName());
            assertEquals(2, summary.awayTeamScore());
            assertEquals("Canada", summary.awayTeamName());
        });
    }

    @Test
    void shouldThrowExceptionIfGameAlreadyStarted() {
        // given
        scoreboard.startNewGame("Mexico", "Canada");

        // when & then
        assertThrows(ScoreboardException.GameAlreadyStartedException.class,
                () -> scoreboard.startNewGame("Mexico", "Canada"));
    }

    @Test
    void shouldGetStatisticOrderedByTotalScore() {
        // given
        var testData = testData();

        // when
        testData.forEach(data -> {
            scoreboard.startNewGame(data.homeTeamName(), data.awayTeamName());
            scoreboard.updateGameScore(data.homeTeamName(), data.homeTeamScore(), data.awayTeamName(), data.awayTeamScore());
        });

        // then
        assertIterableEquals(expectedSummaryOrder(), scoreboard.getGamesSummary());
    }

    private List<GameSummary> expectedSummaryOrder() {
        return List.of(
                new GameSummary("Uruguay", 6, "Italy", 6),
                new GameSummary("Spain", 10, "Brazil", 2),
                new GameSummary("Mexico", 0, "Canada", 5),
                new GameSummary("Argentina", 3, "Australia", 1),
                new GameSummary("Germany", 2, "France", 2));
    }

    private List<GameSummary> testData() {
        return List.of(
                new GameSummary("Mexico", 0, "Canada", 5),
                new GameSummary("Spain", 10, "Brazil", 2),
                new GameSummary("Germany", 2, "France", 2),
                new GameSummary("Uruguay", 6, "Italy", 6),
                new GameSummary("Argentina", 3, "Australia", 1));
    }

}