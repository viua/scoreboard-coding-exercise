package com.sportradar.exception;

public class ScoreboardException extends RuntimeException {

    public ScoreboardException(String message) {
        super(message);
    }

    public static class GameNotFoundException extends ScoreboardException {
        private static final String GAME_NOT_FOUND = "No game '%s' - '%s' found.";

        public GameNotFoundException(String homeTeamName, String awayTeamName) {
            super(format(GAME_NOT_FOUND, homeTeamName, awayTeamName));
        }
    }

    public static class GameAlreadyStartedException extends ScoreboardException {
        private static final String GAME_ALREADY_STARTED = "Game '%s' - '%s' already started.";

        public GameAlreadyStartedException(String homeTeamName, String awayTeamName) {
            super(format(GAME_ALREADY_STARTED, homeTeamName, awayTeamName));
        }
    }

    private static String format(String messageTemplate, String homeTeamName, String awayTeamName) {
        return String.format(messageTemplate,  homeTeamName, awayTeamName);
    }
}