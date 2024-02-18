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
        this.shuffleTiles();
        for (Player player : players) {
            int i = 0;
            player.addTile(tiles[i]);
            i ++;
        }
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
        tiles[tileCount-1] = null;
        players[currentPlayerIndex].addTile(t);
        return t.toString();
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
     */
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners = new Player[1];
        int temp = 0;
        for (Player p : players) {
            if (p.findLongestChain() > temp) {
                winners[0] = p;
                temp = p.findLongestChain();
            } else if (p.findLongestChain() == temp) {
                winners = new Player[2]; // Sırayla chainler 12 12 13 olunca yanlış sonuç veemiyor mu????
            }
        }
        return winners;
    }

    
     /**
      * Finds the players who have the longest chain
      * @return winners
      */
    /*public Player[] getPlayerWithHighestLongestChainTRY() 
    {
        int longestEver = this.totalLongestChain(players);
        Player[] winners = new Player[4];
        int currentWinner = 0;
        for(int i = 0; i < players.length; i++)
        {
            if(players[i].findLongestChain() == longestEver);
            {
                winners[currentWinner] = players[i];
                currentWinner++;
            }
        }
        return winners;
    }

    /**
     * Compares all players' longest chain and return longest one
     * @param players all players in the game
     * @return longest chainn ever
     */
    /*public int totalLongestChain(Player[] players)
    {
        int longest = players[0].findLongestChain();
        for(int i = 1; i < players.length; i++)
        {
            if(players[i].findLongestChain() > longest)
            {
                longest = players[i].findLongestChain();
            }
        }
        return longest;
    }*/
    


    /**
     * Checks if there are more tiles on the stack to continue the game
     * @return true if there is tiles on the stack
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
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
        for (int i = 0; i < players[currentPlayerIndex].playerTiles.length; i++) {
            for (Tile t : players[currentPlayerIndex].usefulTiles()) {
                if (players[currentPlayerIndex].playerTiles[i] == t) {
                break;
                } 
            }
            players[currentPlayerIndex].getAndRemoveTile(i);
            break;
        }
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
