import java.util.Queue;

public class GreedyPlayer extends Player {
	private int round; // 
	
	public void setRound(int round) {
		this.round = round;
	}
	
	public Asset getMostProfitableIllegal() {
		int most_profitable = 0;
		int idOfMaxProfit = 0;
		for (Asset a : assetsInHand) {
			if (a.isLegal() == false && a.getProfit() > most_profitable) {
				most_profitable = a.getProfit();
				idOfMaxProfit = a.getID();
			}
		}
		return new Asset(idOfMaxProfit);
	}
	public boolean existIllegals() {
		int contor = 0;
		for (Asset a : assetsInHand) {
			if (a.isLegal() == false)
				contor++;
		}
		if (contor > 0)
			return true;
		return false;
	}
     public void playAsMerchant() {
    	 // daca runda e para, pe langa strategia de baza mai adauga un element ilegal, cel mai profitabil
    	 if (round % 2 == 0 && existIllegals() == true) {
    		 super.createBag(); // se comporta exact ca jucatorul de baza in prima faza
    		 if (bag.isFull() == false) {
    			 // verifica daca exista carti ilegale
    			 //if (existIllegals() == true) {
    				 Asset a = getMostProfitableIllegal();
    			 	 bag.addInBag(a);
    			 	 assetsInHand.remove(a);
    			 //}	 
    		 }
    		 // daca runda e impara se comporta exact ca un jucator basic
    	 } else {
    		 super.createBag();
    	 }
     }
     // o folosesc in Game, inainte de a apela GreedyPlayer.inspect()
     public boolean hasBribe(Player playerStrategy) {
    	   
    	 if (playerStrategy.bag.getBribe() > 0)
    		 return true;
    	 return false;
    	 
     }
     
     public void inspect(Player playerStrategy, Queue<Asset> cards) {
    	 int bribe = playerStrategy.bag.getBribe();
    	 // int currentCoins = getCoins();
    	    if (bribe != 0) {
    	    	// coins += bribe;
    	    	incrementCoins(bribe);
    	    } else {
    	    	super.inspect(playerStrategy, cards);
    	    }
     }
}
