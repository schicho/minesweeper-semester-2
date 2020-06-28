package model;

import model.enums.*;

public class Model {

    /**
     * the Field itself provides the core functionality on the 2D Tile array.
     * Model manipulations are forwarded to this object.
     */
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
     * Also updates the gameState on each sweep.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void sweepTile(int rowIndex, int colIndex){
        //do not allow sweeping of flagged tiles
        if(isFlagged(rowIndex, colIndex)){
            return;
        }
        //also check if tile has not been sweeped before, to stop recursion.
        boolean isAlreadySweeped = isSweeped(rowIndex, colIndex);
        //Update GameState
        if(isMine(rowIndex, colIndex)){
            minesweeperField.sweepTile(rowIndex, colIndex);
            gameState = GameState.LOST;
            return; //no need to further swipe any tiles
        }else if(!isAlreadySweeped){
            countSweepedTiles++;
        }
        //recursion
        if(!isAlreadySweeped){
            //sweep Tile which was called to do be sweeped.
            minesweeperField.sweepTile(rowIndex, colIndex);
            //recursively sweep surrounding tiles.
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
        if (!isFlagged(rowIndex, colIndex)) {
            minesweeperField.flagTile(rowIndex, colIndex);
            numberOfFlags++;
        } else {
            minesweeperField.unflagTile(rowIndex, colIndex);
            numberOfFlags--;
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
