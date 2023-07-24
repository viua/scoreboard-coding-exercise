package com.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void shouldThrowExceptionIfGameAlreadyStarted() {
        // given
        scoreboard.startNewGame("Mexico", "Canada");

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> scoreboard.startNewGame("Mexico", "Canada"));
    }

    @Test
    void shouldGetStatisticOrderedByTotalScore() {
        // given
        var testData = getTestData();
        var expectedSummary = getExpectedSummaryOrdered();

        // when
        testData.forEach(data -> {
            scoreboard.startNewGame(data.homeTeamName, data.awayTeamName);
            scoreboard.updateGameScore(data.homeTeamName, data.homeTeamScore, data.awayTeamName, data.awayTeamScore);
        });

        // then
        assertIterableEquals(expectedSummary, scoreboard.getGamesSummary());
    }

    private List<String> getExpectedSummaryOrdered() {
        return List.of("Uruguay 6 - Italy 6",
                "Spain 10 - Brazil 2",
                "Mexico 0 - Canada 5",
                "Argentina 3 - Australia 1",
                "Germany 2 - France 2");
    }

    private List<TestData> getTestData() {
        return List.of(new TestData("Mexico", 0, "Canada", 5),
                new TestData("Spain", 10, "Brazil", 2),
                new TestData("Germany", 2, "France", 2),
                new TestData("Uruguay", 6, "Italy", 6),
                new TestData("Argentina", 3, "Australia", 1));
    }

    private record TestData(String homeTeamName, int homeTeamScore, String awayTeamName, int awayTeamScore) { }

}