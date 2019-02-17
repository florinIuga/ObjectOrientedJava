package game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

abstract class Player {

    private String playerName;
    private int playerID;
    private int coins;
    private int round;
    private int finalScore;
    protected  Bag bag;
    private boolean isSherif;
    private int kingBonus;
    private int queenBonus;
    private static final int MAX = 6;
    private static final int INITIAL_COINS = 50;
    private static final int MAXLEGALS = 4;
    private static final int MAX_PROFIT = 4;
    private static final int CHICKEN_ID = 3;
    private static final int MAX_CHEESE_BONUS = 3;
    protected List<Asset> assetsInHand = new LinkedList<>();
    protected List<Asset> assetsOnStand = new LinkedList<>();

    Player() {
        kingBonus = 0;
        queenBonus = 0;
        coins = INITIAL_COINS;
        finalScore = 0;
        bag = new Bag();
        finalScore = 0;
    }
    // draw cards as long as we don't have 6 cards
    public void drawAssets(final Queue<Asset> cards) {
     if (!cards.isEmpty()) {
    	 while (assetsInHand.size() < MAX) {
               assetsInHand.add(cards.poll());
         }
      }
    }

    public void setName(final String name) {
    	this.playerName = name;
    }

    public String getPlayerName() {
    	return this.playerName;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void makeSherif(final boolean isSheriff) {
        this.isSherif = isSheriff;
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
           if (!a.isLegal()) {
              x++;
            }
        }
        if (x == assetsInHand.size()) {
             return true;
         }
       return false;
    }
    // returns the number of breads from hand
    public int getNrOfBread() {
      int k = 0;
        for (Asset a : assetsInHand) {
            if (a.getAssetName().equals("Bread")) {
                k++;
             }
         }
       return k;
    }
    // returns the number of chickens from hand
    public int getNrOfChicken() {
       int k = 0;
        for (Asset a : assetsInHand) {
            if (a.getAssetName().equals("Chicken")) {
               k++;
             }
         }
       return k;
    }
    // based on assets from hand situations
    public Asset getWantedAsset() {

      int[] freq = new int[MAXLEGALS];
      int max = 0;
      int idMax = 0;
      if (!onlyIllegal()) {
         for (Asset a : assetsInHand) {
            if (a.isLegal()) {
               freq[a.getID()]++;
             }
          }
          for (int i = 0; i < freq.length; ++i) {
              if (freq[i] >= max) {
                  max = freq[i];
                  idMax = i;
               }
           }
          /* In case the number of bread and chicken is the same and
           * it's equal with the max frequency return the first one of
           * them from hand */
          int nrOfBread = getNrOfBread();
          int nrOfChicken = getNrOfChicken();
          if (nrOfBread == nrOfChicken && nrOfBread == max) {
        	  for (Asset a : assetsInHand) {
                  if (a.getProfit() == MAX_PROFIT) {
                      idMax = a.getID();
                      break;
                   }
               }
           }
          return new Asset(idMax);
        } else {
          // else, if there are onlyIllegal, get the most profitable one
          int maxProfit = 0;
          int idIllegal = 0;
          for (Asset a : assetsInHand) {
              if (a.getProfit() > maxProfit) {
                  maxProfit = a.getProfit();
                  idIllegal = a.getID();
               }
           }
         return new Asset(idIllegal);
        }
    }

    public void setRound(final int round) {
        this.round = round;
    }

    // add the assets on stand after inspection
    public void putAssetsOnStand() {
      for (Asset a : bag.bagList) {
           assetsOnStand.add(a);
       }
      bag.bagList.clear();
     }
    // for each illegal card from the stand, add the bonus
    public void addIllegalBonusOnStand() {
       List<Asset> listOfBonuses = new LinkedList<>();
       for (Asset a : assetsOnStand) {
          if (a.getAssetName().equals("Silk")) {
              for (int i = 0; i < MAX_CHEESE_BONUS; ++i) {
                 listOfBonuses.add(new Asset(1));
               }
           } else if (a.getAssetName().equals("Pepper")) {
               for (int i = 0; i < 2; ++i) {
                  listOfBonuses.add(new Asset(CHICKEN_ID));
               }
           } else if (a.getAssetName().equals("Barrel")) {
               for (int i = 0; i < 2; ++i) {
                  listOfBonuses.add(new Asset(2));
               }
           }
       }
       // add the bonuses on stand
       for (Asset a : listOfBonuses) {
           assetsOnStand.add(a);
       }
       listOfBonuses.clear();
    }
    // each player will implement this method in his own way
    abstract void playAsMerchant();

    public void inspect(final Player playerStrategy, final Queue<Asset> cards) {
        int checkAssets = 0;
        int assetPenalty = 0;

        List<Asset> listToBeRemoved = new LinkedList<>();
        for (Asset a : playerStrategy.bag.bagList) {
            if (!a.getAssetName().equals(playerStrategy.bag.getDeclaredType())
            		                                       || !a.isLegal()) {
                this.coins += a.getPenalty();
                playerStrategy.coins -= a.getPenalty();
                // add the asset to the heap cards
                cards.add(a);
                // add it to the list of items which have to delete
                listToBeRemoved.add(a);
                checkAssets++;
              }
          }
         playerStrategy.bag.bagList.removeAll(listToBeRemoved);
        // if checkAssets == 0 this means the player was honest
        // thus, the sherifPlayer will pay the penalty
        if (checkAssets == 0) {
            // in order to find the penalty
            for (Asset a : playerStrategy.bag.bagList) {
                assetPenalty = a.getPenalty();
                break;
            }
            playerStrategy.coins += playerStrategy.bag.bagList.size() * assetPenalty;
            this.coins -= playerStrategy.bag.bagList.size() * assetPenalty;
         }
    }

    // returns the number of assets with assetID from his stand
   public int getAssetsCount(final int assetID) {
      int counter = 0;
        for (Asset a : assetsOnStand) {
            if (a.getID() == assetID) {
                counter++;
             }
         }
       return counter;
    }

    public void addKingBonus(final int bonusOfKing) {
        this.kingBonus += bonusOfKing;
    }

    public int getKingBonus() {
        return this.kingBonus;
    }

    public int getQueenBonus() {
        return this.queenBonus;
    }

    public void addQueenBonus(final int bonusOfQueen) {
        this.queenBonus += bonusOfQueen;
    }

    public void setPlayerID(final int id) {
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

    public void incrementCoins(final int x) {
         this.coins += x;
    }

    public void decrementCoins(final int x) {
         this.coins -= x;
    }

    public void incrementFinalScore(final int score) {
         this.finalScore += score;
    }
}
