public class Model {

    /**
     * enum of Difficulty settings. This indicates the size of the minefield
     * and the count of bombs placed.
     */
    public enum Difficulty{
        EASY,
        NORMAL,
        HARD
    }

    private Field minesweeperField;

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
            break;
            case NORMAL:
                minesweeperField = new Field(16, 16);
                minesweeperField.placeMinesRNG(40);
            break;
            case HARD:
                minesweeperField = new Field(16, 30);
                minesweeperField.placeMinesRNG(99);
            break;
        }
        minesweeperField.calcSurroundingMines();
    }

    /**
     * Sets the isSweeped value of the tile at [rowIndex][colIndex]
     * to true.
     * @param rowIndex index of row
     * @param colIndex index of column
     */
    public void sweepTile(int rowIndex, int colIndex){
        minesweeperField.sweepTile(rowIndex, colIndex);
    }

    /**
     * @return the core 2D Tile Array
     */
    public Tile[][] getTileField(){
        return minesweeperField.getMinefield();
    }

    /**
     * @return the Field object
     */
    public Field getMinesweeperField(){
        return minesweeperField;
    }

}
