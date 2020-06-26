package entities;

import java.util.Random;
import entities.enums.*;

public class Field {

    private final int rows;
    private final int cols;

    private int remainingMines;

    /**
     * This is the core 2D tile array.
     */
    private Tile[][] minefield;

    /**
     * Initializes the minefield.
     * @param rows  rows of the minefield grid
     * @param cols  columns of the minefield grid
     */
    public Field(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.minefield = new Tile[rows][cols];

        populate();
    }

    /**
     * Fills initially empty minefield with tiles.
     * Does not have to be run manually. Is done in constructor.
     */
    public void populate(){
        for(int i=0; i < rows; i++){
            for(int j=0; j < cols; j++){
                minefield[i][j] = new Tile();
            }
        }
    }

    /**
     * Randomly spreads mines of the minefield.
     * @param numberOfMines number of mines to be placed on the minefield.
     */
    public void placeMinesRNG(int numberOfMines){
        Random rng = new Random();
        int count = numberOfMines;
        while(count > 0){
            int rowIndex = rng.nextInt(rows);  //value between zero (inclusive) and
            int colIndex = rng.nextInt(cols);  // rows/cols (exclusive)
            if(minefield[rowIndex][colIndex].getState() == TileState.MINE){
                continue;               //if Tile is a mine already skip to next loop cycle
            }else{
                minefield[rowIndex][colIndex].setState(TileState.MINE);
                count--;
            }
        }

        remainingMines = numberOfMines;
    }

    /**
     * Calculate for each tile of the minefield how many of the 8 surrounding
     * tiles are mines. Then save the value to each individual tile.
     * tiles which are mines also get this value assigned, even if not needed
     * for gameplay.
     * This method must only be run once.
     */
    public void calcSurroundingMines(){
        for(int i=0; i < rows; i++){
            for(int j=0; j < cols; j++){
                int count = 0;
                //check all 8 surrounding tiles for mines. This is very ugly.
                //top 3
                try {
                    if(minefield[i-1][j-1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i-1][j].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i-1][j+1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                //left and right
                try {
                    if(minefield[i][j-1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i][j+1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                //bottom 3
                try {
                    if(minefield[i+1][j-1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i+1][j].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i+1][j+1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException e){}

                //write to the current tile which's surrounding mines we counted
                minefield[i][j].setSurroundingMines(count);
            }
        }
    }

    /**
     * Sweep/Open up a covered tile
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void sweepTile(int rowIndex, int colIndex){
        if(minefield[rowIndex][colIndex].getState() == TileState.MINE){
            minefield[rowIndex][colIndex].setState(TileState.SWEEPED_MINE);
            return;
        }
        minefield[rowIndex][colIndex].setState(TileState.SWEEPED_FREE);
    }

    /**
     * mark a tile with a flag, the state is set respectively
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void flagTile(int rowIndex, int colIndex){
        if(minefield[rowIndex][colIndex].getState() == TileState.MINE){
            minefield[rowIndex][colIndex].setState(TileState.FLAGGED_MINE);
            //reduce remainingMines, because a mine was found
            remainingMines--;
            return;
        }
        minefield[rowIndex][colIndex].setState(TileState.FLAGGED_FREE);
    }

    /**
     * takes the flag off the tile
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void unflagTile(int rowIndex, int colIndex){
        if(minefield[rowIndex][colIndex].getState() == TileState.FLAGGED_MINE){
            minefield[rowIndex][colIndex].setState(TileState.MINE);
            //a found mine was unflagged
            remainingMines++;
            return;
        }
        minefield[rowIndex][colIndex].setState(TileState.FREE);
    }

    /**
     * returns true if tile at given index is sweeped.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public boolean isSweeped(int rowIndex, int colIndex){
        return (minefield[rowIndex][colIndex].getState() == TileState.SWEEPED_FREE) ||
                (minefield[rowIndex][colIndex].getState() == TileState.SWEEPED_MINE);
    }

    /**
     * returns true if tile at given index is flagged.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public boolean isFlagged(int rowIndex, int colIndex) {
        return minefield[rowIndex][colIndex].getState() == TileState.FLAGGED_FREE ||
                minefield[rowIndex][colIndex].getState() == TileState.FLAGGED_MINE;
    }

    /**
     * returns true if tile at given index contains a mine.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public boolean isMine(int rowIndex, int colIndex){
        return (minefield[rowIndex][colIndex].getState() == TileState.MINE) ||
                (minefield[rowIndex][colIndex].getState() == TileState.SWEEPED_MINE) ||
                (minefield[rowIndex][colIndex].getState() == TileState.FLAGGED_MINE);
    }

    /**
     * returns number of mines surrounding the tile at given index. maximum of 8.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public int getSurroundingMines(int rowIndex, int colIndex){
        return  minefield[rowIndex][colIndex].getSurroundingMines();
    }

    /**
     * @return 2D Array of Tiles
     */
    public Tile[][] getTileArray(){
        return minefield;
    }

    /**
     * @return number of rows of the tile array
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return number of columns of the tile array
     */
    public int getCols() {
        return cols;
    }

    /**
     * @return number of remaining mines the player has yet to find
     */
    public int getRemainingMines(){
        return remainingMines;
    }
}
