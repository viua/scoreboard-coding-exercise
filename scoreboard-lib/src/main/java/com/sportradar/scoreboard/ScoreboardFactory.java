package com.sportradar.scoreboard;

import com.sportradar.game.Game;
import com.sportradar.game.Score;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardFactory {

    /**
     * Create simple scoreboard
     * @return Scoreboard
     */
    public static Scoreboard createSimpleScoreboard() {
        var games = new ConcurrentHashMap<Game, Score>();
        var scoreComparator = Comparator.comparing(Score::getTotalScore)
                .thenComparing(Score::getStartTime)
                .reversed();
        return new ScoreboardImpl(games, scoreComparator);
    }
}
