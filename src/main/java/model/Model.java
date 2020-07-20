package model;

import model.enums.*;
import observer_subject.*;

import java.util.ArrayList;
import java.util.List;

/**
 * model class
 * holds all the information and provides methods to alter these
 */
public class Model implements Subject {

    private final Difficulty difficulty; //the current difficulty the game is running on
    /**
     * the observers bound to the model
     */
    private List<Observer> observerList = new ArrayList<>();
    /**
     * the Field itself provides the core functionality on the 2D Tile array.
     * Model manipulations are forwarded to this object.
     */
    private boolean untouched; //true until first tile is sweeped
    private Field minesweeperField; //the basic field, the majority of the game is about
    private int numberOfMines;
    //Initialize GameState variables with default values
    private int numberOfFlags = 0;
    private SaveGame gameSaver= new SaveGame();
    private int sweepedTilesCount = 0;
    private GameState gameState = GameState.MAIN_MENU;

    /**
     * Constructs the model which creates a minesweeper field
     * based on difficulty.
     * Values are derived from http://minesweeperonline.com/#
     * @param difficulty either EASY, NORMAL OR HARD
     */
    public Model(Difficulty difficulty){
        //the field is untouched in the beginning
        this.untouched=true;

        //remember difficulty
        this.difficulty = difficulty;

        //pick the right field size and mine count according to the difficulty
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
     * Second constructor, to decode/load seed
     * @param seed given by load case in Controller.handleInput
     */
    public Model(String seed){
        if(seed.charAt(0)=='0'){
            this.difficulty=Difficulty.EASY;
            numberOfMines = 10;
        }
        else if(seed.charAt(0)=='1'){
            this.difficulty=Difficulty.NORMAL;
            numberOfMines = 40;
        }
        else{
            this.difficulty=Difficulty.HARD;
            numberOfMines = 99;
        }
        int partitionIndex=1;
        for(int i=1;i<seed.length()-4;i+=4){
            if(seed.substring(i,i+4).equals("9999")){
                partitionIndex=i;
            }
        }
        minesweeperField = new Field(seed.substring(0,partitionIndex));
        StringBuilder seedBuilder = new StringBuilder(seed);
        int m;
        int n;
        //cant be done in Field, since field doesnt support recursion
        int loschDas=0;
        for(int i = 0;i<minesweeperField.getRows();i++){
            for(int j = 0; j<minesweeperField.getCols();j++){
                if (minesweeperField.isMine(i,j)){loschDas++;}
            }
        }



        for(int i = partitionIndex+4; i<seedBuilder.length(); i+=4){
            m=Integer.parseInt(seedBuilder.substring(i,i+2));
            n=Integer.parseInt(seedBuilder.substring(i+2,i+4));
            sweepTile(m,n,false);
        }

        untouched=false;
    }

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
     * Sets the isSweeped value of the tile at [rowIndex][colIndex] to true.
     * Also recursively sweeps neighboring tiles, if the tile has a value of
     * zero neighboring mines.
     * Updates the gameState on each sweep.
     * @param rowIndex row index of the clicked on tile
     * @param colIndex column index of the clicked on tile
     */
    public void sweepTile(int rowIndex, int colIndex, boolean inRecursion){
        //make sure the first field has zero surrounding mines and is not a mine itself
        if (untouched){
            sweepClearOnUntouched(rowIndex, colIndex);
        }

        //do not allow sweeping of flagged tiles
        if (isFlagged(rowIndex, colIndex) || isQmarked(rowIndex, colIndex)) {
            return;
        }
        else if (isMine(rowIndex, colIndex)){
            minesweeperField.sweepTile(rowIndex, colIndex);
            gameState = GameState.LOST;
            notifyObservers();
            return; //no need to further swipe any tiles
        }

        //recursion
        if(!isSweeped(rowIndex, colIndex)) {
            sweepedTilesCount++;
            //sweep Tile which was called to do be sweeped.
            minesweeperField.sweepTile(rowIndex, colIndex);
            //sweep adjacent tiles
            if(!inRecursion){
                gameSaver.addSweepCoords(rowIndex,colIndex);

            }
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
            boolean[] existingSurroundingMines = minesweeperField.whichSurroundingTilesExist(rowIndex, colIndex);
            int arrayIndex = 0;
            for (int offsetRow = -1; offsetRow <= 1; offsetRow++) {
                for (int offsetCol = -1; offsetCol <= 1; offsetCol++) {
                    if(existingSurroundingMines[arrayIndex]){
                        sweepTile(rowIndex + offsetRow, colIndex + offsetCol,true);
                    }
                    arrayIndex++;
                }
            }
        }
    }

    /**
     * Called only on the first swipe. Make sure the first clicked on Tile is not a Mine.
     * @param rowIndex row index of the clicked tile
     * @param colIndex column index of the clicked tile
     */
    private void sweepClearOnUntouched(int rowIndex, int colIndex){
        //get all mined tiles near this one
        List<Integer> surroundingMines = minesweeperField.checkAround(rowIndex,colIndex);

        while (surroundingMines.size()!=0){
            int encodedOffsets = surroundingMines.get(0);
            surroundingMines.remove(0);
            int rowOffset = minesweeperField.decodeSurroundingMineRowOffset(encodedOffsets);
            int colOffest = minesweeperField.decodeSurroundingMineColOffset(encodedOffsets);
            minesweeperField.clearTile(rowIndex+rowOffset,colIndex+colOffest);
            // this is needed in case we move a mine/bomb
            surroundingMines = minesweeperField.checkAround(rowIndex,colIndex);
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
        if(numberOfNotMineTiles == sweepedTilesCount && gameState != GameState.LOST){
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

    public String getSeed(){
        return gameSaver.genSeed(minesweeperField);
    }

    public void touch(){
        //don't disrespect my NoNoSquare!
        untouched=false;
    }
}
