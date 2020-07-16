package model;

import model.enums.*;
import observer_subject.*;

import java.util.ArrayList;
import java.util.List;

public class Model implements Subject {

    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void attach(Observer o) {
        observerList.add(o);
    }

    @Override
    public void detach(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for( Observer o : observerList){
            o.update(this);
        }
    }

    /**
     * the Field itself provides the core functionality on the 2D Tile array.
     * Model manipulations are forwarded to this object.
     */
    private boolean untouched; //true until first tile is sweeped
    private Field minesweeperField;
    private final Difficulty difficulty;
    private int numberOfMines;
    //Initialize GameState variables with default values
    private int numberOfFlags = 0;
    private int countSweepedTiles = 0;
    private GameState gameState = GameState.RUNNING;


    /**
     * Constructs the model which creates a minesweeper field
     * based on difficulty.
     * Values are derived from http://minesweeperonline.com/#
     * @param difficulty either EASY, NORMAL OR HARD
     */
    public Model(Difficulty difficulty){
        this.untouched=true;
        this.difficulty = difficulty; //remember difficulty
        switch (difficulty){
            case EASY:
                numberOfMines = 10;
                minesweeperField = new Field(9,9, numberOfMines);
                break;
            case NORMAL:
                numberOfMines = 40;
                minesweeperField = new Field(16, 16, numberOfMines);
                break;
            case HARD:
                numberOfMines = 99;
                minesweeperField = new Field(16, 30, numberOfMines);
                break;
        }
    }

    /**
     * Sets the isSweeped value of the tile at [rowIndex][colIndex] to true.
     * Also recursively sweeps neighboring tiles, if the tile has a value of
     * zero neighboring mines.
     * Updates the gameState on each sweep.
     * @param rowIndex row index of the clicked on tile
     * @param colIndex column index of the clicked on tile
     */
    public void sweepTile(int rowIndex, int colIndex){
        //make sure the first field has zero surrounding mines and is not a mine itself
        if (untouched){
            sweepClearOnUntouched(rowIndex, colIndex);
        }
        //do not allow sweeping of flagged tiles
        if (isFlagged(rowIndex, colIndex) || isQmarked(rowIndex, colIndex)) {
            return;
        }else if (isMine(rowIndex, colIndex)){
            minesweeperField.sweepTile(rowIndex, colIndex);
            gameState = GameState.LOST;
            notifyObservers();
            return; //no need to further swipe any tiles
        }
        //recursion
        if(!isSweeped(rowIndex, colIndex)) {
            countSweepedTiles++;
            //sweep Tile which was called to do be sweeped.
            minesweeperField.sweepTile(rowIndex, colIndex);
            //sweep adjacent tiles
            sweepRecursively(rowIndex, colIndex);
            notifyObservers();
        }
    }

    /**
     * If the current tile has zero surrounding mines, sweep all the adjacent tiles.
     * @param rowIndex row index of the current tile
     * @param colIndex column index of the current tile
     */
    private void sweepRecursively(int rowIndex, int colIndex){
        if (getSurroundingMines(rowIndex, colIndex) == 0) {
            // top 3
            try {
                sweepTile(rowIndex - 1, colIndex - 1);
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                sweepTile(rowIndex - 1, colIndex);
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                sweepTile(rowIndex - 1, colIndex + 1);
            } catch (IndexOutOfBoundsException ignored) {
            }
            //left and right
            try {
                sweepTile(rowIndex, colIndex - 1);
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                sweepTile(rowIndex, colIndex + 1);
            } catch (IndexOutOfBoundsException ignored) {
            }
            //bottom 3
            try {
                sweepTile(rowIndex + 1, colIndex - 1);
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                sweepTile(rowIndex + 1, colIndex);
            } catch (IndexOutOfBoundsException ignored) {
            }
            try {
                sweepTile(rowIndex + 1, colIndex + 1);
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }

    /**
     * Called only on the first swipe. Make sure the first clicked on Tile is not a Mine.
     * @param rowIndex row index of the clicked tile
     * @param colIndex column index of the clicked tile
     */
    private void sweepClearOnUntouched(int rowIndex, int colIndex){
        List<Integer> surroundingMines = minesweeperField.checkAround(rowIndex,colIndex);
        while (surroundingMines.size()!=0){
            int encodedOffsets = surroundingMines.get(0);
            surroundingMines.remove(0);
            int rowOffset = minesweeperField.decodeSurroundingMineRowOffset(encodedOffsets);
            int colOffest = minesweeperField.decodeSurroundingMineColOffset(encodedOffsets);
            minesweeperField.clearTile(rowIndex+rowOffset,colIndex+colOffest);
            surroundingMines= minesweeperField.checkAround(rowIndex,colIndex);
        }
        this.untouched=false;
    }

    /**
     * Sets the isFlagged value of the tile at [rowIndex][colIndex] to true if previously unflagged,
     * false if previously flagged.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void flagTile(int rowIndex, int colIndex) {
        if (isSweeped(rowIndex, colIndex)){
            return;
        }
        if (!isFlagged(rowIndex, colIndex) && !isQmarked(rowIndex, colIndex)) {
            minesweeperField.flagTile(rowIndex, colIndex);
            numberOfFlags++;
        } else if (isFlagged(rowIndex, colIndex)) {
            minesweeperField.qmarkTile(rowIndex, colIndex);
            numberOfFlags--;
        } else {
            minesweeperField.unQmarkTile(rowIndex, colIndex);
        }
    }

    /**
     * Sweeps all Tiles. Used when the game was lost to show mistakes to player
     * No Observer notification needed as it would end up in a loop and we know when this
     * method is called.
     */
    public void sweepAllOnLost(){
        for(int i=0; i<minesweeperField.getRows(); i++){
            for(int j=0; j<minesweeperField.getCols(); j++){
                minesweeperField.sweepTileForce(i,j);
            }
        }
    }

    /**
     * Check if tile at a certain index is flagged or not.
     * @param rowIndex index of row
     * @param colIndex index of column
     * @return true if tile is flagged, false if not.
     */
    public boolean isFlagged(int rowIndex, int colIndex){
        return minesweeperField.isFlagged(rowIndex, colIndex);
    }

    /**
     * Check if tile at a certain index is question marked or not.
     * @param rowIndex index of row
     * @param colIndex index of column
     * @return true if tile is question marked, false if not.
     */
    public boolean isQmarked(int rowIndex, int colIndex){
        return minesweeperField.isQmarked(rowIndex, colIndex);
    }

    /**
     * Check if tile at a certain index is sweeped or not.
     * @param rowIndex index of row
     * @param colIndex index of column
     * @return true if tile is sweeped, false if not.
     */
    public boolean isSweeped(int rowIndex, int colIndex){
        return minesweeperField.isSweeped(rowIndex, colIndex);
    }

    /**
     * Check if tile at a certain index is a mine-tile or not.
     * @param rowIndex index of row
     * @param colIndex index of column
     * @return true if tile is contains mine, false if not.
     */
    public boolean isMine(int rowIndex, int colIndex){
        return minesweeperField.isMine(rowIndex, colIndex);
    }

    /**
     * Returns the number of bombs surrounding one tile. Maximum of 8.
     * @param rowIndex index of row
     * @param colIndex index of column
     * @return number of bombs surrounding that tile.
     */
    public int getSurroundingMines(int rowIndex, int colIndex){
        return minesweeperField.getSurroundingMines(rowIndex, colIndex);
    }

    /**
     * Get difficulty of the minefield. This indirectly indicates the number
     * of mines placed on the minefield and the size of the minefield.
     * @return the difficulty setting chosen at the construction of the model
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Get number of flags left which are needed to cover all mines.
     * Subtracts the number of Flags from the number of Mines.
     * @return the count of all Mines - the count of set Flags.
     */
    public int getFlagsToSetLeft() {
        return this.numberOfMines - this.numberOfFlags;
    }

    /**
     * Try to avoid using. Use high level functions above.
     * @return the core 2D Tile Array
     */
    public Tile[][] getTileArray(){
        return minesweeperField.getTileArray();
    }

    /**
     * returns the value of the GameState needed for the gameloop
     * @return GameState
     */
    public GameState getGameState(){
        final int numberOfNotMineTiles = (minesweeperField.getRows() * minesweeperField.getCols()) - numberOfMines;
        if(numberOfNotMineTiles == countSweepedTiles && gameState != GameState.LOST){
            gameState = GameState.WON;
        }
        return gameState;
    }

    /**
     * used to set the gameState without, having to lose or win the game
     * @param gs new gameState
     */
    public void setGameState(GameState gs){
        gameState = gs;
    }

    /**
     * @return the number of mines the player has not found yet
     */
    public int getRemainingMines(){
        return minesweeperField.getRemainingMines();
    }
}
