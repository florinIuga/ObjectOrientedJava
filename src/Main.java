import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public final class Main {

    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();
        Queue<Asset> cardsQueue = new LinkedList<>();
        String[] players = new String[3];
        int playerContor = 0;
        for (Integer i : gameInput.getAssetIds()) {
        	cardsQueue.add(new Asset(i));
        }
        for (String name : gameInput.getPlayerNames()) {
        	players[playerContor] = name;
        	playerContor++;
        }
        Game g = new Game(cardsQueue, players, playerContor);
        g.play();
        
    }
}
// import java.util.HashMap;
/*import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
// import java.util.Map;
import java.util.Queue;

//import javax.swing.SingleSelectionModel; 
public class Main {
	List<String> mPlayersOrder = new ArrayList<>();
	public static void main(String[] args) {
		int[] cards = {3, 1, 12, 1, 0, 2, 12, 3, 11, 0, 2, 0, 1, 12, 0, 10, 2, 0, 2, 11, 1, 0, 12, 0, 0, 1, 0, 3, 2, 1, 3, 1, 2, 0, 2, 1, 11, 0, 3, 12, 0, 0, 0, 1, 1, 2, 11, 3, 0, 0, 1, 10, 12, 0, 10, 0, 0, 1, 12, 1, 2, 11, 1, 2, 3, 12, 3, 3, 10, 0, 11, 2, 0, 1, 2, 1, 3, 0, 1, 1, 3, 10, 2, 0, 2, 0, 1, 2, 0, 1, 2, 11, 0, 11, 3, 0, 2, 3, 10, 11, 2, 2, 11, 1, 11, 3, 10, 10, 1, 12, 0, 11, 12, 1, 11, 1, 10, 2, 3, 12, 2, 1, 10, 0, 3, 3, 11, 10, 12, 0, 1, 11, 2, 11, 10, 1, 3, 10, 2, 3, 10, 2, 1, 1, 2, 1, 1, 3, 12, 1, 0, 12, 12, 10, 2, 2, 0, 2, 2, 12, 1, 0, 0, 10, 2, 0, 2, 3, 10, 3, 0, 2, 2, 3, 0, 1, 12, 1, 10, 0, 10, 11, 2, 0, 1, 0, 11, 2, 12, 0, 10, 11, 0, 1, 3, 3, 0, 12, 0, 2, 0, 2, 2, 0, 0, 0, 0, 12, 1, 1, 0, 3, 11, 1, 2, 2};
		// String[] players = {"Bribe","Basic", "Greedy"};
		String[] players = new String[3];
		List<String> mPlayersOrder = new ArrayList<>();
		mPlayersOrder.add("Bribe");
		mPlayersOrder.add("Basic");
		mPlayersOrder.add("Greedy");
		int x = mPlayersOrder.size();
		// mPlayersOrder.add("Greedy");
		int k = 0;
		for (String s : mPlayersOrder) {
			players[k] = s;
			k++;
		}
		for (int i = 0; i < k; ++i) {
			System.out.println(players[i]);
		}
		//System.out.println(mPlayersOrder);
		Queue<Asset> cardsQueue = new LinkedList<>();
		for (int i = 0; i < cards.length; ++i) {
			 cardsQueue.add(new Asset(cards[i]));
		 } 
		Game g = new Game(cardsQueue, players, k);
		
		g.play();
		
		
	}
}*/
