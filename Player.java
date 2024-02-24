/**
 * This is a Player class.
 * Each player has their own list of tiles, name and amount of tile.
 * This class can determine whether a player has won or not. 
 * It can also finds the longest chain in list of tiles and determines whether the getting tile is useful or not.
 * 
 * @author ATAKAN KAYA, BURKAY TUNCTURK, ECE SESEN, MELIKE KARA, MERT SUCI
 * @version 18.02.2024
 */

import java.util.*;
public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /**
     * Checks this player's hand to determine if this player is winning. 
     * The player with a complete chain of 14 consecutive numbers wins the game
     * Note that the player whose turn is now draws one extra tile to have 15 tiles in hand, 
     * and the extra tile does not disturb the longest chain and therefore the winning condition.
     * @return whether player won or not
     */
    public boolean checkWinning() {
        /* boolean won = true;
        // 14 değerini değiştridim ki kontrol edebileyim
        for (int i = 1; i <= 14; i++) {
            if (won == false) {
                return false;
            }
            for (int j = 0; j < playerTiles.length; j++) {
                if (playerTiles[j].getValue() == i) {
                    won = true;
                    break;
                } else {
                    won = false;
                }
            }
        }
        return won; */

        return 14 == this.findLongestChain();
    }
    
    /**
     * Finds the longest chain of consecutive numbers in this player hand by iterating over playerTiles 
     * @return length of longest chain
     */
    public int findLongestChain() {
        int longestChain = 0;
        int currentlylongestChain = 0;
        this.bubbleSort(playerTiles);

        for (int i = 0; i < playerTiles.length - 1; i++) {
            if (playerTiles[i].getValue() + 1 == playerTiles[i+1].getValue()){
                currentlylongestChain += 1;
            }
            else{
                if(currentlylongestChain > longestChain){
                    longestChain = currentlylongestChain;
                }
                currentlylongestChain = 0;
            }
        }

        return longestChain + 1;
    }

    
    /**
     * Finds the tiles that creates the longest chain
     * @return tile array
     */
    public Tile[] usefulTiles() {

        int longestChain = findLongestChain();
        Tile[] usefulTiles = new Tile[longestChain];
        int counter = 1;
        for (int i = 0; i < playerTiles.length - 1; i++){
            if (playerTiles[i].getValue() + 1 == playerTiles[i+1].getValue()) {
                counter++;
            } else { counter = 1;}
            if (counter == longestChain) {
                for (int j = 0; j < longestChain; j++) {
                    usefulTiles[j] = playerTiles[i + 2 - counter + j];
                }
            }
        }
        return usefulTiles;
    }

    /**
     * Checks if the tile makes the longest chain longer or not. Decides its usefulness.
     * @param a given tile
     * @return true if given tile creates longer chain compared the recent one
     */
    public boolean isUseful(Tile a) {

        Tile[] tempArray = new Tile[playerTiles.length + 1];
        System.arraycopy(playerTiles, 0, tempArray, 0, playerTiles.length);
        tempArray[tempArray.length-1] = a;
        this.bubbleSort(tempArray);
        int longestChain = 0;
        int currentlylongestChain = 0;

        for (int i = 0; i < tempArray.length - 1; i++) {
            if (tempArray[i].getValue() + 1 == tempArray[i+1].getValue()){
                currentlylongestChain += 1;
            }
            else{
                if(currentlylongestChain > longestChain){
                    longestChain = currentlylongestChain;
                }
                currentlylongestChain = 0;
            }
        }
        return longestChain > findLongestChain();
    }

    /**
     * Removes and returns the tile in given index position
     * @param index of removed tile
     * @return removed tile
     */
    public Tile getAndRemoveTile(int index) {
        Tile[] newList = new Tile[14];
        for (int i = 0; i < newList.length; i++) {
            newList[i] = new Tile(-1);
        }
        newList[13].setValue(100);
        int plusWhat = 0;
        Tile inPosition = newList[0];

        for (int i = 0; i < 14; i++) {
            if(i == index){
                plusWhat = 1;
                inPosition = playerTiles[i];
            }
            newList[i] = playerTiles[i+plusWhat]; //Bence bu kodda plusWhat'a gerek yok. Zaten i arttıkça index de atlanmış oluyor.
            
        }
        playerTiles = newList;

        numberOfTiles = 14;

        return inPosition;
    }

    /**
     * Adds the given tile to this player's tiles list keeping the ascending order
     * @param addedTile given tile
     */
    public void addTile(Tile addedTile) {
        
        Tile[] modification = new Tile[playerTiles.length + 1];
        for (int i = 0; i < playerTiles.length; i++) {
            modification[i] = playerTiles[i];
        }
        modification[modification.length - 1] = addedTile;
        numberOfTiles ++;
        this.bubbleSort(modification);
        playerTiles = modification;
    }

    public void initializeDeck(Tile[] tiles){
        playerTiles = tiles;
        numberOfTiles = 14;
        this.bubbleSort(tiles);
    }

    /**
     * Put tiles in ascending order.
     * @param arr tiles list
     */
    private void bubbleSort(Tile[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].getValue() > arr[j + 1].getValue()) {
                    // Swap arr[j] and arr[j+1] (swap the Tile objects)
                    Tile temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

   /**
    * Finds the index for a given tile in this player's hand
    * @param t given tile
    * @return index
    */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(this.playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /**
     * Displays the tiles of this player
     */
    public void displayTiles() {
        //System.out.println(playerTilesplayerTiles.length-1] + "!!");
        bubbleSort(playerTiles);
        //System.out.println(playerTiles[playerTiles.length-1] + "!!!!");

        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < playerTiles.length; i++) {
            System.out.print(playerTiles[i].toString() + ", ");
        }
        System.out.println();
        System.out.println("Length: " + playerTiles.length);
    }

    /**
     * Give the all the tiles that player has
     * @return tiles list
     */
    public Tile[] getTiles() {
        return this.playerTiles;
    }

    /**
     * Set the player's name
     * @param name player name
     */
    public void setName(String name) {
        this.playerName = name;
    }

    /**
     * Get the player's name
     * @return name
     */
    public String getName() {
        return this.playerName;
    }
}
