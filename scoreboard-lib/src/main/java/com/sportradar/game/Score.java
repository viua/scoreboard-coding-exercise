package com.sportradar.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Score {

    static final Integer DEFAULT_SCORE = 0;

    public static Score defaultScore() {
        return Score.builder()
                .homeTeamScore(DEFAULT_SCORE)
                .awayTeamScore(DEFAULT_SCORE)
                .build();
    }

    private Integer homeTeamScore;
    private Integer awayTeamScore;

    public synchronized Integer getTotalScore() {
        return this.homeTeamScore + this.awayTeamScore;
    }
}
