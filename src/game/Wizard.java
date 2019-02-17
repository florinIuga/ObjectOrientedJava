package game;

import java.util.Queue;

public class Wizard extends Player{

	@Override
	public void playAsMerchant() {
		super.createBag();
	}
	
	public void inspect(final Player playerStrategy, final Queue<Asset> cards) {
		if (playerStrategy.bag.getBribe() != 0) {
			super.inspect(playerStrategy, cards);
		}
	}
	
}
