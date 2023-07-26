package com.sportradar.scoreboard;

import com.sportradar.game.Game;
import com.sportradar.game.GameSummary;
import com.sportradar.game.Score;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ScoreboardImpl implements Scoreboard {

    private static final String GAME_NOT_FOUND = "No game '%s' - '%s' found.";
    private static final String GAME_ALREADY_STARTED = "Game '%s' - '%s' already started.";

    private final Map<Game, Score> games;
    private final Comparator<Map.Entry<Game, Score>> scoreComparator;

    protected ScoreboardImpl() {
        this.games = new ConcurrentHashMap<>();
        this.scoreComparator = Comparator.comparing((Map.Entry<Game, Score> e) -> e.getValue().getTotalScore())
                .thenComparing((Map.Entry<Game, Score> e) -> e.getKey().getStartTime())
                .reversed();
    }

    @Override
    public void startNewGame(String homeTeamName, String awayTeamName) {
        var gameAlreadyStarted = games.putIfAbsent(Game.of(homeTeamName, awayTeamName, LocalDateTime.now()),
                Score.defaultScore());
        if (Objects.nonNull(gameAlreadyStarted)) {
            throw new IllegalArgumentException(String.format(GAME_ALREADY_STARTED, homeTeamName, awayTeamName));
        }
    }

    @Override
    public void finishGame(String homeTeamName, String awayTeamName) {
        var removedGame = games.remove(Game.of(homeTeamName, awayTeamName));
        if (Objects.isNull(removedGame)) {
            throw new IllegalArgumentException(String.format(GAME_NOT_FOUND, homeTeamName, awayTeamName));
        }
    }

    @Override
    public void updateGameScore(String homeTeamName, Integer homeTeamScore,
                                             String awayTeamName, Integer awayTeamScore) {
        var computedScore = games.computeIfPresent(Game.of(homeTeamName, awayTeamName),
                (gameKey, score) -> Score.builder()
                        .homeTeamScore(homeTeamScore)
                        .awayTeamScore(awayTeamScore)
                        .build());
        if (Objects.isNull(computedScore)) {
            throw new IllegalArgumentException(String.format(GAME_NOT_FOUND,
                    homeTeamName, awayTeamName));
        }
    }


    @Override
    public List<GameSummary> getGamesSummary() {
        return games.entrySet().stream()
                .sorted(scoreComparator)
                .map(this::summary)
                .collect(Collectors.toList());
    }

    private GameSummary summary(Map.Entry<Game, Score> entry) {
        return new GameSummary(
                entry.getKey().getHomeTeamName(),
                entry.getValue().getHomeTeamScore(),
                entry.getKey().getAwayTeamName(),
                entry.getValue().getAwayTeamScore()
        );
    }
}
