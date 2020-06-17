public class Tile {

    private tileState state;
    private int surroundingMines;

    /**
     * returns the tiles state
     * @return state
     */
    public tileState getState() {return state;}

    /**
     * sets the tiles state
     */
    public void setState(tileState state) {this.state = state;}


    /**
     * returns the number of mines surrounding the tile
     * @return surroundingMines
     */
    public int getSurroundingMines() {
        return surroundingMines;
    }

    /**
     * sets the information how many mines surrounding the tile,
     * not the mines themselves!
     * @param surroundingMines
     */
    public  void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }


    /**
     * constructs a new tile
     */
    public Tile() {
        this.state = tileState.FREE;
        this.surroundingMines = 0;
    }
}
