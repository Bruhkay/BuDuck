/**
 * This is a Okey Game class.
 * It contains crucial mthods for playing game such as distributing tiles to players, getting last discarded tile or from pile.
 * 
 * @author ATAKAN KAYA, BURKAY TUNCTURK, ECE SESEN, MELIKE KARA, MERT SUCI
 * @version 25.02.2024
 */

import java.util.*;
public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    /**
     * Creates tile with four copies of each value, no jokers
     */
    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile] = new Tile(i);
                currentTile++;
            }
        }
        tileCount = 104;
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int j = 0;
        Tile[] a;
        for (Player player : players) {
            a = new Tile[14];
            for(int i = 0; i < 14; i++){
                a[i] = tiles[j];
                j++;
            }
            player.initializeDeck(a);
        }
        players[0].addTile(tiles[j++]);
        tileCount -= j;
        // !! arrayden dagıtılanları düş
    }

    /**
     * This simulates picking up the tile discarded by the previous player (get the last discarded tile for the current player)
     * @return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        Tile t = lastDiscardedTile;
        players[currentPlayerIndex].addTile(t);
        return t.toString();
    }

    /**
     * Get the top tile from tiles array for the current player (that tile is no longer in the tiles array)
     * @return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Tile t = tiles[tileCount -1];
        tileCount--;
        String a = t.toString();
        players[currentPlayerIndex].addTile(t);
        return a;
    }

    /**
     * Randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random random = new Random();
        for (int i = 0; i < tileCount; i++) {
            int r = random.nextInt(tiles.length);
            int temp = tiles[i].getValue();
            tiles[i].setValue(tiles[r].getValue()); 
            tiles[r].setValue(temp);
        }
    }

    /**
     * Check if game still continues
     * @return true if current player finished the game.
     */
    public boolean didGameFinish() {
        return players[currentPlayerIndex].checkWinning();
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     * 
     * !!! Kod için Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 1 out of boun bounds for length 1
 bounds for length 1
        at SimplifiedOkeyGame.getPlayerWithHighestLongestChain(SimplifiedOkeyGame.java:123)
        at ApplicationMain.getWinnerDeck(ApplicationMain.java:129)
        at ApplicationMain.main(ApplicationMain.java:117)

     * Hatası veriyor, yazarın elinden öper
<<<<<<< HEAD
=======
     * 
     * --Düzelmiş olması lazım ama bubbleSort'taki hatadan dolayı oyun galibi 
     * ilan etme noktasına gelmiyor şuan.
>>>>>>> bcb2144f6d2104ed837defc3790500768f0862ce
     */
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners = new Player[1];
        Player[] plHolder;
        int temp = 0;
        int counter = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].findLongestChain() > temp) {
                temp = players[i].findLongestChain();       
            }
        }
        for (int j = 0; j < players.length; j++) {
            if (players[j].findLongestChain() == temp) {
                counter++;
                plHolder = winners.clone();
                if (counter > 1) {
                    winners = new Player[counter];
                }
                for( int i = 0; i < plHolder.length; i++){
                    winners[i] = plHolder[i];
                }
                winners[counter - 1] = players[j];
            }
        }
        return winners;
    }

    /**
     * Checks if there are more tiles on the stack to continue the game
     * @return true if there is tiles on the stack
     */
    public boolean hasMoreTileInStack() {
        return tileCount > 2;
    }

   
    /**
     * Pick a tile for the current computer player using useful one:
     * Whİch can be picking new tile or getting last discarded tile. 
     */
    public void pickTileForComputer() {
        if (players[currentPlayerIndex].isUseful(lastDiscardedTile)) {
            getLastDiscardedTile();
        } else {
            getTopTile();
        }
    }

    /**
     * Current computer player will discard the least useful tile. You may choose based on how useful each tile is
     */
    @SuppressWarnings("unused")   // nothing important, about the incrementation.
    public void discardTileForComputer() {
        ArrayList<Integer> possibilities = new ArrayList<>();
        
        for (int i = 0; i < players[currentPlayerIndex].playerTiles.length; i++) {
            for (Tile t : players[currentPlayerIndex].usefulTiles()) {
                if (players[currentPlayerIndex].playerTiles[i] == t) {
                break;
                } 
            }
            players[currentPlayerIndex].getAndRemoveTile(i);
            break;
        }

        Random rand = new Random();
        lastDiscardedTile = tiles[rand.nextInt(14)];
    }

    /**
     * Discards the current player's tile at given index
     * This should set lastDiscardedTile variable and remove that tile from
     * @param tileIndex discarded tile index
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].playerTiles[tileIndex];
        players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    /**
     * Shows the information about last discarded tile
     */
    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    /**
     * Displays the current player's tile list
     */
    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    /**
     * Give the current player's index
     * @return index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Give the current player's name
     * @return name
     */
      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    /**
     * Turn is passed to following player
     */
    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public int getTileCount(){
        return this.tileCount;
    }

    /**
     * Create a new player with given name and index location
     * @param index of the player in the players' list
     * @param name player's name
     */
    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }
}
