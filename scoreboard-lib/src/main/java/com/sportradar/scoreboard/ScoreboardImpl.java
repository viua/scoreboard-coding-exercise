package com.sportradar.scoreboard;

import com.sportradar.game.Game;
import com.sportradar.game.GameSummary;
import com.sportradar.game.Score;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    public synchronized void startNewGame(String homeTeamName, String awayTeamName) {
        final Game game = Game.of(homeTeamName, awayTeamName, LocalDateTime.now());
        if (this.games.containsKey(game)) {
            throw new IllegalArgumentException(String.format(GAME_ALREADY_STARTED,
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
    public synchronized void updateGameScore(String homeTeamName, Integer homeTeamScore,
                                             String awayTeamName, Integer awayTeamScore) {
        final Game game = Game.of(homeTeamName, awayTeamName);
        if (!games.containsKey(game)) {
            throw new IllegalArgumentException(String.format(GAME_NOT_FOUND,
                    homeTeamName, awayTeamName));
        }
        this.games.computeIfPresent(game, (gameKey, score) -> Score.builder()
                .homeTeamScore(homeTeamScore)
                .awayTeamScore(awayTeamScore)
                .build());
    }


    @Override
    public List<GameSummary> getGamesSummary() {
        return this.games.entrySet().stream()
                .sorted(scoreComparator)
                .map(this::summary)
                .collect(Collectors.toList());
    }

    private GameSummary summary(Map.Entry<Game, Score> entry) {
        return new GameSummary(entry.getKey().getHomeTeamName(), entry.getValue().getHomeTeamScore(),
                entry.getKey().getAwayTeamName(), entry.getValue().getAwayTeamScore());
    }
}
