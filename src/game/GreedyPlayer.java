package game;
import java.util.Queue;

public final class GreedyPlayer extends Player {

    private int round;

    public void setRound(final int round) {
       this.round = round;
    }
    // returns the most profitable illegal
    public Asset getMostProfitableIllegal() {
        int mostProfitable = 0;
        int idOfMaxProfit = 0;
        for (Asset a : assetsInHand) {
           if (!a.isLegal() && a.getProfit() > mostProfitable) {
                mostProfitable = a.getProfit();
                idOfMaxProfit = a.getID();
            }
         }
       return new Asset(idOfMaxProfit);
    }
    // check whether there are illegal assets or not
    public boolean existIllegals() {
      int contor = 0;
       for (Asset a : assetsInHand) {
            if (!a.isLegal()) {
               contor++;
             }
        }
        if (contor > 0) {
            return true;
        }
       return false;
     }

    public void playAsMerchant() {
         // check the parity of the round
         if (round % 2 == 0 && existIllegals()) {
            // at first, he does the same as basic player
             super.createBag();
             if (!bag.isFull()) {
                 Asset a = getMostProfitableIllegal();
                 bag.addInBag(a);
                 assetsInHand.remove(a);
              }
          } else {
              super.createBag();
           }
     }

     public boolean hasBribe(final Player playerStrategy) {
        if (playerStrategy.bag.getBribe() > 0) {
            return true;
         }
        return false;
      }

     public void inspect(final Player playerStrategy, final Queue<Asset> cards) {
        int bribe = playerStrategy.bag.getBribe();
         if (bribe != 0) {
            incrementCoins(bribe);
         } else {
            super.inspect(playerStrategy, cards);
         }
     }
}
