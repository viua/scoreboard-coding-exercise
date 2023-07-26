package com.sportradar.game;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public final class Game {

    private final String homeTeamName;
    private final String awayTeamName;
    private LocalDateTime startTime;

    private Game(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    private Game(String homeTeamName, String awayTeamName, LocalDateTime startTime) {

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.startTime = startTime;
    }

    public static Game of(String homeTeamName, String awayTeamName) {
        return new Game(homeTeamName, awayTeamName);
    }

    public static Game of(String homeTeamName, String awayTeamName, LocalDateTime startTime) {
        return new Game(homeTeamName, awayTeamName, startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game team = (Game) o;
        return Objects.equals(homeTeamName, team.homeTeamName) && Objects.equals(awayTeamName, team.awayTeamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeamName, awayTeamName);
    }
}
