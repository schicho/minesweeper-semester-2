public class Tile {

    private boolean isSweeped;
    private boolean isMine;
    private int surroundingMines;
    private boolean isFlagged;


    // Getter & Setter isSweeped
    public boolean getIsSweeped() {
        return isSweeped;
        }

    public  void setIsSweeped(boolean isSweeped) {
        this.isSweeped = isSweeped;
    }


    // Getter & Setter isMine
    public boolean getIsMine() {
        return isMine;
    }

    public  void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }


    // Getter & Setter surroundingMines
    public int getSurroundingMines() {
        return surroundingMines;
    }

    public  void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }


    // Getter & Setter isFlagged
    public boolean getIsFlagged() {
        return isFlagged;
    }

    public  void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }


    // Constructor
    public Tile() {
        this.isSweeped = false;
        this.isMine = false;
        this.surroundingMines = 0;
        this.isFlagged = false;
    }
}
