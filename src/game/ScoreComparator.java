package game;
import java.util.Comparator;

public final class ScoreComparator implements Comparator<PlayerScore> {

    @Override
    public int compare(final PlayerScore p1, final PlayerScore p2) {
        return p2.getFinalScore() - p1.getFinalScore();
    }
}
