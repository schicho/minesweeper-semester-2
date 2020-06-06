import java.util.Random;

public class Field {

    private final int rows;
    private final int cols;
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
            if(minefield[rowIndex][colIndex].getIsMine()){
                continue;               //if Tile is a mine already skip to next loop cycle
            }else{
                minefield[rowIndex][colIndex].setIsMine(true);
                count--;
            }
        }
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
                    if(minefield[i-1][j-1].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i-1][j].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i-1][j+1].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                //left and right
                try {
                    if(minefield[i][j-1].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i][j+1].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                //bottom 3
                try {
                    if(minefield[i+1][j-1].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i+1][j].getIsMine()){count++;}
                }catch (IndexOutOfBoundsException e){}
                try {
                    if(minefield[i+1][j+1].getIsMine()){count++;}
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
        minefield[rowIndex][colIndex].setIsSweeped(true);
    }

    /**
     * @return 2D Array of Tiles
     */
    public Tile[][] getTileArray(){
        return minefield;
    }
}
