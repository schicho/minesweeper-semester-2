public class Model {

    /**
     * the Field itself provides the core functionality on the 2D Tile array.
     * Model manipulations are forwarded to this object.
     */
    private Field minesweeperField;
    private Difficulty difficulty;
    private int numberOfMines;

    /**
     * Constructs the model which creates a minesweeper field
     * based on difficulty.
     * Values are derived from http://minesweeperonline.com/#
     * @param difficulty either EASY, NORMAL OR HARD
     */
    public Model(Difficulty difficulty){
        switch (difficulty){
            case EASY:
                minesweeperField = new Field(9,9);
                minesweeperField.placeMinesRNG(10);
                numberOfMines = 10;
                this.difficulty = difficulty; //this.difficulty = Difficulty.EASY
            break;
            case NORMAL:
                minesweeperField = new Field(16, 16);
                minesweeperField.placeMinesRNG(40);
                numberOfMines = 40;
                this.difficulty = difficulty;
            break;
            case HARD:
                minesweeperField = new Field(16, 30);
                minesweeperField.placeMinesRNG(99);
                numberOfMines = 99;
                this.difficulty = difficulty;
            break;
        }
        minesweeperField.calcSurroundingMines();
    }

    /**
     * Sets the isSweeped value of the tile at [rowIndex][colIndex] to true.
     * Also recursively sweeps neighboring tiles, if the tile has a value of
     * zero neighboring mines.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void sweepTile(int rowIndex, int colIndex){
        //also check if tile has not been sweeped before, to stop recursion.
        boolean isAlreadySweeped = isSweeped(rowIndex, colIndex);
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
     * Try to avoid using. Use high level functions above.
     * @return the core 2D Tile Array
     */
    public Tile[][] getTileArray(){
        return minesweeperField.getTileArray();
    }

    /**
     * Returns the GameState of the model
     * @return a value of GameState Enum
     */
    public GameState checkCurrentGameState(){
        int countSweepedTiles = 0;
        int numberOfNotMineTiles = (minesweeperField.getRows() * minesweeperField.getCols()) - numberOfMines;
        Tile[][] tilearray = getTileArray();

        for(int i=0; i<minesweeperField.getRows(); i++){
            for(int j=0; j<minesweeperField.getCols(); j++){
                if(tilearray[i][j].getIsMine() && tilearray[i][j].getIsSweeped()){
                    return GameState.LOST;
                }else if(!tilearray[i][j].getIsMine() && tilearray[i][j].getIsSweeped()){
                    countSweepedTiles++;
                }
            }
        }
        if(countSweepedTiles == numberOfNotMineTiles){
            return GameState.WON;
        }
        return GameState.RUNNING;
    }

}
