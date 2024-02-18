/**
 * This is a Tile class.
 * Each tile has its own value.
 * This program can compare tiles for whether they are same or not, detect whether they can form a chain or not.
 * 
 * @author ATAKAN KAYA, BURKAY TUNCTURK, ECE SESEN, MELIKE KARA, MERT SUCI
 * @version 18.02.2024
 * 
 */
public class Tile {
    
    protected int value;

    //Creates a tile using the given value. False jokers are not included in this game.
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Check if the given tile t and this tile have the same value
     * @param t given tile
     * @return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        return t.value == this.value;
    }

    /**
     * Compare the order of these two tiles
     * @param t given tile
     * @return 1 if given tile has smaller in value, 0 if they have the same value, -1 if the given tile has higher value.
     */
    public int compareTo(Tile t) {
       return matchingTiles(t) ? 0 : t.value < this.value ? 1 : -1;
    }

    /**
     * Determine if this tile and given tile can form a chain together. 
     * This method should check the difference in values of the two tiles
     * @param t given tile
     * @return true if the absoulute value of the difference is 1; otherwise, it should return false.
     */
    public boolean canFormChainWith(Tile t) {
        return (this.value == t.value + 1 || this.value == t.value - 1);
    }

    /**
     * Returns the value as stirng
     */
    public String toString() {
        return "" + value;
    }

    /**
     * Give the number of tile
     * @return value of tile
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of tile to given number
     * @param num given number
     */
    public void setValue(int num){
        this.value = num;
    }
}
