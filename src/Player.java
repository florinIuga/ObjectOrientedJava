
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

abstract class Player {
	
	private String playerName;
	private int playerID;
	private int coins;
	private int round;
	private int finalScore;
	protected Bag bag;
	private boolean isSherif;
	private int kingBonus;
	private int queenBonus;
	private static final int chickenID = 3;
	private static final int breadID = 2;
	private static final int initialCoins = 50;
	protected List<Asset> assetsInHand = new LinkedList<>();
	protected List<Asset> assetsOnStand = new LinkedList<>();
	
	public Player() {
		kingBonus = 0;
		queenBonus = 0;
		coins = initialCoins;
		finalScore = 0;
		bag = new Bag();
		finalScore = 0;
	}
	
	// draw cards as long as we don't have 6 cards
  public void drawAssets(Queue<Asset> cards) {
	if (!cards.isEmpty()) 
	  while (assetsInHand.size() < 6) {
		  assetsInHand.add(cards.poll());
	  }
  }
  
  public void setName(String name) {
	  this.playerName = name;
  }
  
  public String getPlayerName() {
	  return this.playerName;
  }
  
  public int getPlayerID() {
	  return this.playerID;
  }
  
  public void makeSherif(boolean isSherif) {
	  this.isSherif = isSherif;
  }
  
  public boolean isSherif() {
	  return this.isSherif;
  }
  /* based on getWantedAsset(), it creates the bag */
    public void createBag() {
		
	   Asset legalAsset = null;
	   Asset illegalAsset = null;
	   List<Asset> listAux = new LinkedList<>();
	   // if there are legal cards as well
	   if (!onlyIllegal()) {
		  legalAsset = getWantedAsset();
		  for (Asset a : assetsInHand) {
			if (legalAsset.getID() == a.getID() && !bag.isFull()) {
				bag.addInBag(legalAsset);
				listAux.add(legalAsset);
			}
		   }
		  bag.setDeclaredType(legalAsset.getAssetName());
		  this.assetsInHand.removeAll(listAux);
	   } else {
		  illegalAsset = getWantedAsset();
		  /* if there are only illegal assets, we add only one,
		   * with the best profit */
		  bag.addInBag(illegalAsset);
		  assetsInHand.remove(illegalAsset);
		  bag.setDeclaredType("Apple");
		  
	   } 
	 
    }
	// check if there are only illegal cards in hand
    public boolean onlyIllegal() {
		int x = 0;
		for (Asset a : assetsInHand) {
			if (a.isLegal() == false)
				x++;
		}
		if (x == assetsInHand.size())
			return true;
		return false;
    }
	
    public Asset getWantedAsset() {
    	
      int[] freq = new int[4];
      int max = 0;
      int id_max = 0;
      if (!onlyIllegal()) {
    	  for (Asset a : assetsInHand) {
    		if (a.isLegal() == true) {
      		   freq[a.getID()]++;
      		 }
      	  }
    	  for (int i = 0; i < freq.length; ++i) {
    		  if (i == chickenID && freq[breadID] == freq[chickenID]) {
    			  if (freq[i] > max) {
      				  max = freq[i];
        		      id_max = breadID;
        		  }
    		  } else
    		  if (freq[i] >= max) {
  				  max = freq[i];
    		      id_max = i;
    		  }
          }
    	return new Asset(id_max);  
      } else {
    	  // daca sunt numai ilegale
    	  int max_profit = 0;
    	  int idIllegal = 0;
    	  for (Asset a : assetsInHand) {
    		  if (a.getProfit() > max_profit) {
    			  max_profit = a.getProfit();
    			  idIllegal = a.getID();
    		  }
    	  }
    	  return new Asset(idIllegal);
      }
      
	}
    
    public void setRound(int round) {
    	this.round = round;
    }
   
    // in cazul in care jucatorul a trecut de control 
    public void putAssetsOnStand() {
	   for (Asset a : bag.bagList) {
		   assetsOnStand.add(a);
	   }
	   bag.bagList.clear();
    }
    public void addIllegalBonusOnStand() {
	   List<Asset> listOfBonuses = new LinkedList<>();
	   for (Asset a : assetsOnStand) {
		   // daca are Silk, mai adauga 3 produse Cheese pe stand
		   if (a.getAssetName().equals("Silk")) {
			   for (int i = 0; i < 3; ++i)
			     listOfBonuses.add(new Asset(1));
		   } else if (a.getAssetName().equals("Pepper")) {
			   for (int i = 0; i < 2; ++i) {
				 listOfBonuses.add(new Asset(3));
			   }
		   } else if (a.getAssetName().equals("Barrel")) {
			   for (int i = 0; i < 2; ++i) {
				 listOfBonuses.add(new Asset(2));
			   }
		   }
	   }
	   // adaug lista completa de bonusuri pe stand
	   for (Asset a : listOfBonuses) {
		   assetsOnStand.add(a);
	   }
	   listOfBonuses.clear();
    }
    
    abstract void playAsMerchant(); 
    	
    public void inspect(Player playerStrategy, Queue<Asset> cards) {
		int checkAssets = 0;
		int assetPenalty = 0;
		// int idOfDeclaredType = 
		List<Asset> listToBeRemoved = new LinkedList<>();
    	for (Asset a : playerStrategy.bag.bagList) {
    		if (!a.getAssetName().equals(playerStrategy.bag.getDeclaredType()) || a.isLegal() == false) {
    		  // if (a.id != bag.declaredTypeID || a.legal == false) {
    			this.coins += a.getPenalty();
    			playerStrategy.coins -= a.getPenalty();
    			// il adaug in gramada de carti
    			cards.add(a);
    			// add it to the list of items which have to delete
    			listToBeRemoved.add(a);
    			// playerStrategy.bag.bagList.remove(a);
    			checkAssets++;
    		}
    	}
    	   playerStrategy.bag.bagList.removeAll(listToBeRemoved);
    	// daca checkAssets ramane 0 inseamna ca playerStrategy a fost onest
    	// astfel, seriful va plati penalty
    	if (checkAssets == 0) {
    		// pentru a afla cat este penalty-ul bunului declarat, fac doar un pas si ma opresc
    		for (Asset a : playerStrategy.bag.bagList) {
    			assetPenalty = a.getPenalty();
    			break;
    		}
    		playerStrategy.coins += playerStrategy.bag.bagList.size() * assetPenalty;
    		this.coins -= playerStrategy.bag.bagList.size() * assetPenalty;
    	}	
    }
    
    /*public int getScore() {
    	
    }*/
    // intoarce numarul de elemente cu id ul assetID din stand ul jucatorului
    public int getAssetsCount(int assetID) {
    	int counter = 0;
    	for (Asset a : assetsOnStand) {
    		if (a.getID() == assetID) {
    			counter++;
    		}
    	}
    	return counter;
    }
    
    public void addKingBonus(int kingBonus) {
    	this.kingBonus += kingBonus;
    }
    
    public int getKingBonus() {
    	return this.kingBonus;
    }
    
    public int getQueenBonus() {
    	return this.queenBonus;
    }
    
    public void addQueenBonus(int queenBonus) {
    	this.queenBonus += queenBonus;
    }
    
    public void setPlayerID(int id) {
    	this.playerID = id;
    }
    
    public int getCoins() {
    	return this.coins;
    }
    public void calculateFinalScore() {
    	finalScore += coins;
    	// the score from assetsOnStand
    	for (Asset a : assetsOnStand) {
    		finalScore += a.getProfit();
    	}
    	finalScore += kingBonus;
    	finalScore += queenBonus;
    }
    
    public int getFinalScore() {
    	return this.finalScore;
    }
    
	public void incrementCoins(int x) {
		this.coins += x;
	}
	
	public void decrementCoins(int x) {
		this.coins -= x;
	}
	
    public void incrementFinalScore(int score) {
    	this.finalScore += score;
    }
}

