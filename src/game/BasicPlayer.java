package game;
import java.util.Queue;

public final class BasicPlayer extends Player {

    public void playAsMerchant() {
        super.createBag();
    }
    // inspect all the players
    public void inspect(final Player playerStrategy, final Queue<Asset> cards) {
       // if the playerStrategy has bribe, give it back to him
      int bribe = playerStrategy.bag.getBribe();
        if (bribe != 0) {
           playerStrategy.incrementCoins(bribe);
        }
       super.inspect(playerStrategy, cards);
     }
}
