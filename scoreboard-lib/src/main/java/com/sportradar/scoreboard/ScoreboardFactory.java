package com.sportradar.scoreboard;

public class ScoreboardFactory {

    /**
     * Create simple scoreboard
     * @return Scoreboard
     */
    public static Scoreboard createSimpleScoreboard() {
        return new ScoreboardImpl();
    }
}
