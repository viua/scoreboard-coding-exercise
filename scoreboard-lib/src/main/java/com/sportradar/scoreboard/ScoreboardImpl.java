package com.sportradar.scoreboard;

import com.sportradar.game.Game;
import com.sportradar.game.Score;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreboardImpl implements Scoreboard {

    private static final String GAME_NOT_FOUND = "No game '%s' - '%s' found.";
    private static final String GAME_ALREADY_STARTED = "Game '%s' - '%s' already started.";
    private static final String GAME_SUMMARY_FORMAT = "%s %s - %s %s";

    private final Map<Game, Score> games;
    private final Comparator<Score> scoreComparator;

    ScoreboardImpl(Map<Game, Score> games, Comparator<Score> scoreComparator) {
        this.games = games;
        this.scoreComparator = scoreComparator;
    }

    @Override
    public synchronized void startNewGame(String homeTeamName, String awayTeamName) {
        final Game game = Game.of(homeTeamName, awayTeamName);
        if (this.games.containsKey(game)) {
            throw new RuntimeException(String.format(GAME_ALREADY_STARTED,
                    homeTeamName, awayTeamName));
        }
        this.games.put(game, Score.defaultScore());
    }

    @Override
    public synchronized void finishGame(String homeTeamName, String awayTeamName) {
        final Game game = Game.of(homeTeamName, awayTeamName);
        if (!this.games.containsKey(game)) {
            throw new IllegalArgumentException(String.format(GAME_NOT_FOUND, homeTeamName, awayTeamName));
        }
        this.games.remove(game);
    }

    @Override
    public void updateGameScore(String homeTeamName, Integer homeTeamScore,
                                String awayTeamName, Integer awayTeamScore) {
        final Game game = Game.of(homeTeamName, awayTeamName);
        if (!games.containsKey(game)) {
            throw new IllegalArgumentException(String.format(GAME_NOT_FOUND,
                    homeTeamName, awayTeamName));
        }
        this.games.computeIfPresent(game, (gameKey, score) -> Score.builder()
                .startTime(score.getStartTime())
                .homeTeamScore(homeTeamScore)
                .awayTeamScore(awayTeamScore)
                .build());
    }

    @Override
    public List<String> getGamesSummary() {
        return this.games.entrySet().stream()
                .map(this::summary)
                .collect(Collectors.toList());
    }

    private String summary(Map.Entry<Game, Score> entry) {
        return String.format(GAME_SUMMARY_FORMAT, entry.getKey().getHomeTeamName(),
                entry.getValue().getHomeTeamScore(), entry.getKey().getAwayTeamName(),
                entry.getValue().getAwayTeamScore());
    }
}
