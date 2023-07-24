package com.sportradar.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
@AllArgsConstructor(staticName = "of")
public final class Game {

    @NonNull
    private final String homeTeamName;
    @NonNull
    private final String awayTeamName;

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
