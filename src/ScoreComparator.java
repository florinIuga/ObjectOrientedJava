import java.util.Comparator;

public class ScoreComparator implements Comparator<PlayerScore>{

	@Override
	public int compare(PlayerScore p1, PlayerScore p2) {
		// TODO Auto-generated method stub
		return p2.getFinalScore() - p1.getFinalScore();
	}

}
