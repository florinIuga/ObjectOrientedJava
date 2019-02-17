import java.util.Comparator;

public class AssetComparator implements Comparator<Asset>{

	@Override
	public int compare(Asset a1, Asset a2) {
		return a2.getProfit() - a1.getProfit();
	}

}
