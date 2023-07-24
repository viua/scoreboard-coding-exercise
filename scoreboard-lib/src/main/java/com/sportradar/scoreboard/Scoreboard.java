package com.sportradar.scoreboard;

import java.util.List;

public interface Scoreboard {
    /**
     * Starts new game
     * @param homeTeamName home team name
     * @param awayTeamName way team name
     */
    void startNewGame(String homeTeamName, String awayTeamName);

    /**
     * Returns games summary
     * @return list of games summary
     */
    List<String> getGamesSummary();
}
