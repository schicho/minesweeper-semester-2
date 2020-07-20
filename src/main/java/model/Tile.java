package model;

import model.enums.*;

/**
 * tile class
 * represents a single tile of the minefield
 */
public class Tile {

    /**
     * tells what type of tile this tile is at the moment
     */
    private TileState state;

    /**
     * how many mines this tile is surrounded by
     */
    private int surroundingMines;

    /**
     * constructs a new tile
     */
    public Tile() {
        this.state = TileState.FREE;
        this.surroundingMines = 0;
    }

    /**
     * returns the tiles state
     *
     * @return state
     */
    public TileState getState() {
        return state;
    }

    /**
     * sets the tiles state
     */
    public void setState(TileState state) {
        this.state = state;
    }

    /**
     * returns the number of mines surrounding the tile
     *
     * @return surroundingMines
     */
    public int getSurroundingMines() {
        return surroundingMines;
    }

    /**
     * sets the information how many mines surrounding the tile,
     * not the mines themselves!
     *
     * @param surroundingMines
     */
    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }
}
