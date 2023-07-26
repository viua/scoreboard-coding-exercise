package com.sportradar.game;

/**
 * DTO to represent Scoreboard games summaru
 * @param homeTeamName home team name
 * @param homeTeamScore home team score
 * @param awayTeamName away team name
 * @param awayTeamScore away team score
 */
public record GameSummary(String homeTeamName, Integer homeTeamScore, String awayTeamName, Integer awayTeamScore) {
}
