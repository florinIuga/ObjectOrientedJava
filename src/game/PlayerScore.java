package game;

public final class PlayerScore {
    private String playerName;
    private int finalScore;

    public PlayerScore(final String name, final int score) {
        this.playerName = name;
        this.finalScore = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getFinalScore() {
        return this.finalScore;
    }
}
