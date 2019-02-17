package main;
import game.Asset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import game.Game;

public final class Main {
    private static final int MAX = 3;
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
        String[] players = new String[MAX];
        int playerContor = 0;
        for (Integer i : gameInput.getAssetIds()) {
            cardsQueue.add(new Asset(i));
        }
        for (String name : gameInput.getPlayerNames()) {

            if (name.equals("bribed")) {
        		players[playerContor] = "BRIBED";
        	} else if (name.equals("basic")) {
        		players[playerContor] = "BASIC";
        	} else if (name.equals("greedy")) {
        		players[playerContor] = "GREEDY";
        	}

        	playerContor++;
        }

        Game g = new Game(cardsQueue, players, playerContor);
        g.play();
   }
}
