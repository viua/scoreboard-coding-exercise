package com.sportradar.scoreboard;

import java.util.List;

/**
 * Represents the Scoreboard
 */
public interface Scoreboard {
    /**
     * Starts new game
     * @param homeTeamName home team name
     * @param awayTeamName away team name
     */
    void startNewGame(String homeTeamName, String awayTeamName);

    /**
     * Finish game
     * @param homeTeamName home team name
     * @param awayTeamName away team name
     */
    void finishGame(String homeTeamName, String awayTeamName);

    /**
     * Update game score
     * @param homeTeamName home team name
     * @param homeTeamScore home team score
     * @param awayTeamName away team name
     * @param awayTeamScore away team score
     */
    void updateGameScore(String homeTeamName, Integer homeTeamScore, String awayTeamName, Integer awayTeamScore);

    /**
     * Returns games summary
     * @return list of games summary
     */
    List<String> getGamesSummary();
}
