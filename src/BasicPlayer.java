import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.text.AbstractDocument.LeafElement;

public class BasicPlayer extends Player{
	
	public void playAsMerchant() {
    	super.createBag();
    }
    // va inspecta toti jucatorii
	public void inspect(Player playerStrategy, Queue<Asset> cards) {
		
		int bribe = playerStrategy.bag.getBribe();
		if (bribe != 0) {
			// playerStrategy.coins += bribe;
			playerStrategy.incrementCoins(bribe);
		}
		
		super.inspect(playerStrategy, cards);
	}
}
