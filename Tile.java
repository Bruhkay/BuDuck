public class Tile {
    
    protected int value;

    /*
     * creates a tile using the given value. False jokers are not included in this game.
     */
    public Tile(int value) {
        this.value = value;
    }

    /*
     * TODO: should check if the given tile t and this tile have the same value 
     * return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        return t.value == this.value;
    }

    /*
     * TODO: should compare the order of these two tiles
     * return 1 if given tile has smaller in value
     * return 0 if they have the same value
     * return -1 if the given tile has higher value
     */
    public int compareTo(Tile t) {
       return matchingTiles(t) ? 0 : t.value < this.value ? 1 : -1;
    }

    /*
     * TODO: should determine if this tile and given tile can form a chain together
     * this method should check the difference in values of the two tiles
     * should return true if the absoulute value of the difference is 1 (they can form a chain)
     * otherwise, it should return false (they cannot form a chain)
     */
    public boolean canFormChainWith(Tile t) {
        return (this.value == t.value + 1 || this.value == t.value - 1);
    }

    public String toString() {
        return "" + value;
    }

    public int getValue() {
        return value;
    }

}
