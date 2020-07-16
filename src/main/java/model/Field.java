package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.enums.*;

/**
 * field class
 * represents the minefield
 * basically consists out of a tile array
 */
public class Field {

    /**
     * how many rows the field has
     */
    private final int rows;

    /**
     * how many columns the field has
     */
    private final int cols;

    /**
     * how many mines are yet to be found on this field
     * this is the real number of mines, not the number displayed for the flags
     */
    private int remainingMines;

    /**
     * This is the core 2D tile array.
     */
    private final Tile[][] minefield;

    /**
     * Initializes the minefield.
     * @param rows  rows of the minefield grid
     * @param cols  columns of the minefield grid
     */
    public Field(int rows, int cols, int numberOfMines){
        //initialize members
        this.rows = rows;
        this.cols = cols;
        this.minefield = new Tile[rows][cols];

        //filling in the mines
        populate();
        placeMinesRNG(numberOfMines);

        //check the surrounding mines
        calcSurroundingMines();
    }

    /**
     * Fills initially empty minefield with tiles.
     * Does not have to be run manually. Is done in constructor.
     */
    private void populate(){
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
    private void placeMinesRNG(int numberOfMines){
        Random rng = new Random();
        int count = numberOfMines;
        while(count > 0){
            int rowIndex = rng.nextInt(rows);  //value between zero (inclusive) and
            int colIndex = rng.nextInt(cols);  // rows/cols (exclusive)
            if(minefield[rowIndex][colIndex].getState() != TileState.MINE) {
                minefield[rowIndex][colIndex].setState(TileState.MINE);
                count--;
                //if Tile is a mine already skip to next loop cycle
            }
        }
        if(numberOfMines>1){remainingMines = numberOfMines;}//set new only if there is a new field. Not wenn recalculating mines
    }

    /**
     * Calculate for each tile of the minefield how many of the 8 surrounding
     * tiles are mines. Then save the value to each individual tile.
     * tiles which are mines also get this value assigned, even if not needed
     * for gameplay.
     * This method must only be run once.
     */
    private void calcSurroundingMines(){
        for(int i=0; i < rows; i++){
            for(int j=0; j < cols; j++){
                int count = 0;
                //check all 8 surrounding tiles for mines. This is very ugly.
                //top 3
                try {
                    if(minefield[i-1][j-1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                try {
                    if(minefield[i-1][j].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                try {
                    if(minefield[i-1][j+1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                //left and right
                try {
                    if(minefield[i][j-1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                try {
                    if(minefield[i][j+1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                //bottom 3
                try {
                    if(minefield[i+1][j-1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                try {
                    if(minefield[i+1][j].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}
                try {
                    if(minefield[i+1][j+1].getState() == TileState.MINE){count++;}
                }catch (IndexOutOfBoundsException ignored){}

                //write to the current tile which's surrounding mines we counted
                minefield[i][j].setSurroundingMines(count);
            }
        }
    }

    /**
     * values in the encoded list can be interpreted as follows:
     * -1 1 3
     * -2 2 6
     * -4 4 12
     *
     * while 2 is the Tile of which the surroundings ar being checked
     * @param m row
     * @param n collumn
     * @return 's an encoded List of the mines arround and in a tile
     */
    public List<Integer> checkAround(int m, int n){
        List<Integer> returnList = new ArrayList<>();
        int mMax = Math.min(this.getRows()-1,m+1);
        int nMax = Math.min(this.getCols()-1,n+1);
        for (int mMin = Math.max(0,m-1); mMin<=mMax; mMin++){
            for (int nMin = Math.max(0,n-1); nMin <= nMax; nMin++){
                if(isMine(mMin,nMin)){
                    returnList.add(((int) Math.pow(2,(1+mMin-m)))*(2*(nMin-n)+1));
                }

            }
        }
        return returnList;
    }

    /**
     *
     * @param encodedValue
     * @return offset to determine the relativ M position of a mine
     */
    public int decodeSurroundingMineRowOffset(int encodedValue){
        if (encodedValue%4==0){
            return 1;
        }
        else if (encodedValue%2==0){
            return 0;
        }
        else return -1;
    }

    /**
     *
     * @param encodedValue
     * @return offset to determine the relativ N position of a mine
     */
    public int decodeSurroundingMineColOffset(int encodedValue){
        if(encodedValue<0){
            return -1;
        }
        else if(encodedValue%3==0){
            return 1;
        }
        else return 0;
    }

    /**
     * Check which surrounding tiles of a specific tile actually exist and return it in a boolean array.
     * Format of the array is:
     * 0 1 2    | 4 is the tile we passed.
     * 3 4 5    | save boolean value if the surrounding tile exists or not
     * 6 7 8
     * @param rowIndex rowIndex of the tile
     * @param colIndex colIndex of the tile
     * @return boolean[] true if the surrounding tile exists
     */
    public boolean[] whichSurroundingTilesExist(int rowIndex, int colIndex) {
        boolean[] surroundingTileExists = new boolean[9];
        int arrayIndex = 0;
        for (int offsetRow = -1; offsetRow <= 1; offsetRow++) {
            for (int offsetCol = -1; offsetCol <= 1; offsetCol++) {
                try {
                    surroundingTileExists[arrayIndex] = (minefield[rowIndex + offsetRow][colIndex + offsetCol] != null);
                } catch (ArrayIndexOutOfBoundsException e) {
                    surroundingTileExists[arrayIndex] = false;
                }
                arrayIndex++;
            }
        }
        return surroundingTileExists;
    }

    /**
     * changes the tile to the mine free equivalent of it's current status
     * @param m row
     * @param n collumn
     */
    private void deMine(int m, int n){
        if (minefield[m][n].getState()==TileState.FLAGGED_MINE){
            minefield[m][n].setState(TileState.FLAGGED_FREE);
        }
        else if (minefield[m][n].getState()==TileState.QMARKED_MINE){
            minefield[m][n].setState(TileState.QMARKED_FREE);
        }
        else minefield[m][n].setState(TileState.FREE);
    }

    /**
     * Defuses given mine, places new random mine, recalculates the surrounding mines(sadly every time)
     * @param rowIndex Row
     * @param colIndex Collumn
     */
    public void clearTile(int rowIndex, int colIndex){
        deMine(rowIndex,colIndex);
        placeMinesRNG(1);
        calcSurroundingMines();
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
     * Sweeps a Tile accordingly no matter what the previous state was.
     * Used at the end of a lost game.
     * @param rowIndex row index in the 2D Tile Array
     * @param colIndex column index in the 2D Tile Array
     */
    public void sweepTileForce(int rowIndex, int colIndex){
        switch (minefield[rowIndex][colIndex].getState()){
            case FREE:
            case SWEEPED_FREE:
            case FLAGGED_FREE:
            case QMARKED_FREE:
                minefield[rowIndex][colIndex].setState(TileState.SWEEPED_FREE);
            break;
            case MINE:
            case FLAGGED_MINE:
            case QMARKED_MINE:
            case SWEEPED_MINE:
                minefield[rowIndex][colIndex].setState(TileState.SWEEPED_MINE);
            break;
        }
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
     * replaces a flag with a question mark
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void qmarkTile(int rowIndex, int colIndex) {
        if (minefield[rowIndex][colIndex].getState() == TileState.FLAGGED_MINE) {
            minefield[rowIndex][colIndex].setState(TileState.QMARKED_MINE);
            //a found mine was questionmarked
            remainingMines++;
            return;
        }
        minefield[rowIndex][colIndex].setState(TileState.QMARKED_FREE);
    }

    /**
     * takes the flag off the tile
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void unQmarkTile(int rowIndex, int colIndex){
        if(minefield[rowIndex][colIndex].getState() == TileState.QMARKED_MINE){
            minefield[rowIndex][colIndex].setState(TileState.MINE);
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
     * returns true if tile at given index is question marked.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public boolean isQmarked(int rowIndex, int colIndex) {
        return minefield[rowIndex][colIndex].getState() == TileState.QMARKED_FREE ||
                minefield[rowIndex][colIndex].getState() == TileState.QMARKED_MINE;
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
