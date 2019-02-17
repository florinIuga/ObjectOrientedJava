import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Game {
	// fiecare lista va contine id-urile jucatorilor care au kingBonus sau
	// queen bonus pe un anumit tip de produs legal
    private List<Integer> listOfAppleKingBonus = new LinkedList<>();
    private List<Integer> listOfAppleQueenBonus = new LinkedList<>();
    
	private List<Integer> listOfCheeseKingBonus = new LinkedList<>();
	private List<Integer> listOfCheeseQueenBonus = new LinkedList<>();
	
	private List<Integer> listOfBreadKingBonus = new LinkedList<>();
	private List<Integer> listOfBreadQueenBonus = new LinkedList<>();
	
	private List<Integer> listOfChickenKingBonus = new LinkedList<>();
	private List<Integer> listOfChickenQueenBonus = new LinkedList<>();
	
	private String[] players;
	private Player[] player = new Player[3];
	private Queue<Asset> cardsQueue = new LinkedList<>();
	private Map<String, Integer> mapPlayers = new HashMap<String, Integer>();
	private List<PlayerScore> leaderBoard = new LinkedList<PlayerScore>();
	
	private int playerContor;
	private int round;
	private int sherifCounter;
	private int greedyRound;
	private static final int appleKingBonus = 20;
	private static final int appleQueenBonus = 10;
	private static final int cheeseKingBonus = 15;
	private static final int cheeseQueenBonus = 10;
	private static final int breadKingBonus = 15;
	private static final int breadQueenBonus = 10;
	private static final int chickenKingBonus = 10;
	private static final int chickenQueenBonus = 5;
	
	 public Game(Queue<Asset> cards, String[] players, int playerContor) {
		 this.cardsQueue = cards;
		 this.players = players;
		 this.sherifCounter = 0;
		 this.round = 1;
		 this.greedyRound = 1;
		 this.playerContor = playerContor;
	 }
	 /* add the ID of the players on kingList and/or queenList if they deserve bonus for
	  * a certain asset, based on the maximum of assets of a certain type (maxKing and maxQueen*/
	 public void makeListOfBonus(List<Integer> kingList, List<Integer> queenList, int assetID) {
		 
		 Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		 List<Integer> kingKeysToDelete  = new LinkedList<>();
		 List<Integer> queenKeysToDelete  = new LinkedList<>();
		 
		 for (int i = 0; i < playerContor; ++i) {
			 map.put(i, player[i].getAssetsCount(assetID));
		 }
		 
		 int maxKing = Collections.max(map.values());
		 for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			 if (entry.getValue() == maxKing) {
				 kingList.add(entry.getKey());
				 kingKeysToDelete.add(entry.getKey());
			 }
		 }
		 /* delete the ID of the players who received kingBonus
		  * in order to find out the players who deserve queenBonus */
		 for (Integer i : kingKeysToDelete) {
			 map.remove(i);
		 }
		 kingKeysToDelete.clear();
		 
		 if (!map.isEmpty()) {
		    int maxQueen = Collections.max(map.values());
		        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			       if (entry.getValue() == maxQueen) {
				      queenList.add(entry.getKey());
				      queenKeysToDelete.add(entry.getKey());
			        }
		         }
		      for (Integer i : queenKeysToDelete) {
			  map.remove(i);
		      }
		      queenKeysToDelete.clear();
		 }    
		 
	 }
	 // decide who is the sherif in the current round
	 public void decideSherif(int sherifCount) {
		
		  for (int i = 0; i < playerContor; ++i) {
			  if (player[i].getPlayerID() == sherifCount) {
				  player[i].makeSherif(true);
			  } else {
				  player[i].makeSherif(false);
			  }
		  }
	 }
	 // create instances of a certain strategy based on player's name
	 public void initializePlayers() {
		  for (int i = 0; i < playerContor; ++i) {
			  if (players[i].equals("Basic")) {
				  player[i] = new BasicPlayer();
				  player[i].setPlayerID(i);
				  player[i].setName("Basic");
				  mapPlayers.put("Basic", i);
			  } else if (players[i].equals("Bribe")) {
				  player[i] = new BribePlayer();
				  player[i].setPlayerID(i);
				  player[i].setName("Bribe");
				  mapPlayers.put("Bribe", i);
			  } else if (players[i].equals("Greedy")) {
				  player[i] = new GreedyPlayer();
				  player[i].setPlayerID(i);
				  player[i].setName("Greedy");
				  mapPlayers.put("Greedy", i);
			  }
		  }
		  
	 }
	 
	 public void takeCards() {
		 for (int i = 0; i < playerContor; ++i) {
			 player[i].drawAssets(cardsQueue);
		 }
	 }
	 // returns the current sherif at each round
	 public Player getCurrentSherif() {
		 Player p = null;
		 for (int i = 0; i < playerContor; i++) {
			 if (player[i].isSherif() == true) {
				 p = player[i];
				 break;
			 }
		 }
		 return p;
	 }
	 // each player will play as merchant in his way (if not sherif)
	 public void playAsMerchants(int greedyRound) {
		 for (int i = 0; i < playerContor; ++i) {
			  if (player[i].getPlayerName().equals("Greedy"))
				 player[i].setRound(greedyRound);
			 
			 if (player[i].isSherif() == false) {
				 player[i].playAsMerchant();
			 }
		 }
	 }
	 // at each round, the players will put their assets on the market stand
	 public void putAssetsOnStand() {
		 for (int i = 0; i < playerContor; ++i) {
			 if (player[i].isSherif() == false) {
				 player[i].putAssetsOnStand();
			 }
		 }
	 }
	 /* if the ID of a player does exist in the following lists of kings
	  * the player with that certain ID will add to his kingBonus the
	  * king bonus for that particular asset. */
	 public void addKingBonuses(List<Integer> appleKing, List<Integer> cheeseKing,
			                    List<Integer> breadKing, List<Integer> chickenKing) {
		 
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : appleKing) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addKingBonus(appleKingBonus);
				  }
			   }
		 }
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : cheeseKing) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addKingBonus(cheeseKingBonus);
				  }
			   }
		 }
		 
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : breadKing) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addKingBonus(breadKingBonus);
				  }
			   }
		 }
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : chickenKing) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addKingBonus(chickenKingBonus);
				  }
			   }
		 }
		 
	 }
	 /* if the ID of a player does exist in the following lists of queens
	  * the player with that certain ID will add to his queenBonus the
	  * queen bonus associated with that particular asset. */
	 public void addQueenBonuses(List<Integer> appleQueen, List<Integer> cheeseQueen,
			                     List<Integer> breadQueen, List<Integer> chickenQueen) {
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : appleQueen) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addQueenBonus(appleQueenBonus);
				  }
			   }
		 }
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : cheeseQueen) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addQueenBonus(cheeseQueenBonus);
				  }
			   }
		 }
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : breadQueen) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addQueenBonus(breadQueenBonus);
				  }
			   }
		 }
		 for (int k = 0; k < playerContor; ++k) {
			   for (Integer i : chickenQueen) {
				  if (player[k].getPlayerID() == i) {
					  player[k].addQueenBonus(chickenQueenBonus);
				  }
			   }
		 }
	 }
	 // sort the players by final score
	 public void createLeaderboard() {
		 ScoreComparator sC = new ScoreComparator();
		 
		 for (int i = 0; i < playerContor; ++i) {
			 leaderBoard.add(new PlayerScore(player[i].getPlayerName(), player[i].getFinalScore()));
		 }
		 Collections.sort(leaderBoard, sC);
		 
	 }
	 
	 public void printLeaderboard() {
		 for (PlayerScore pS : leaderBoard) {
			 System.out.println(pS.getPlayerName() + ": " + pS.getFinalScore());
		 }
	 }
	 
	 public void resetBribe() {
		 player[mapPlayers.get("Bribe")].bag.setBribe(0);
	 }
	
	 // the actual game logic goes here
	 public void play() {
		 initializePlayers();
		 takeCards();
		 while (round < 2 * playerContor + 1) {
			 decideSherif(sherifCounter);
			 Player p = getCurrentSherif();
			 // each playerStrategy will play as merchant in different way
			 playAsMerchants(greedyRound);
			 // obviously, inspect only the non sherif players
			 for (int i = 0; i < playerContor; ++i) {
				 if (player[i].isSherif() == false) {
					 p.inspect(player[i], cardsQueue);
				 }
			 }
			 putAssetsOnStand();
			 sherifCounter++;
			 if (sherifCounter == playerContor) {
				 sherifCounter = 0;
			 }
			 // draw cards from heap cards
			 takeCards();
			 round++;
			 if (player[mapPlayers.get("Greedy")].isSherif() == false) {
				 greedyRound++;
			 }
			 resetBribe();
		 }
		 
		 // add illegal bonus
		 for (int i = 0; i < playerContor; ++i) {
			 player[i].addIllegalBonusOnStand();
		 }
		// for apples
		 makeListOfBonus(listOfAppleKingBonus, listOfAppleQueenBonus, 0);
		 // for cheese
		 makeListOfBonus(listOfCheeseKingBonus, listOfCheeseQueenBonus, 1);
		 // for bread
		 makeListOfBonus(listOfBreadKingBonus, listOfBreadQueenBonus, 2);
		 // for chicken
		 makeListOfBonus(listOfChickenKingBonus, listOfChickenQueenBonus, 2 + 1);
		 // add bonuses for kings after making the lists of ID-s who deserve bonus
		 addKingBonuses(listOfAppleKingBonus,listOfCheeseKingBonus,
				        listOfBreadKingBonus,listOfChickenKingBonus);
		 // and bonuses for queens
		 addQueenBonuses(listOfAppleQueenBonus, listOfCheeseQueenBonus,
				         listOfBreadQueenBonus, listOfChickenQueenBonus);
		// calculate for each player the final score
		 for (int i = 0; i < playerContor; ++i) {
			 player[i].calculateFinalScore();
		 }
		 // create the final leader board
		 createLeaderboard();
		 // print the final leader board
		 printLeaderboard();
		 
	 }
	 
}
