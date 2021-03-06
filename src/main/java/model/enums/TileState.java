package model.enums;

public enum TileState {
    /**
     * enum of tile states. Each tile can have exactly one state.
     * if one tile has SWEEPED_MINE as its state, the game is lost
     */
    FREE,
    SWEEPED_FREE,
    FLAGGED_FREE,
    QMARKED_FREE,
    MINE,
    SWEEPED_MINE,
    FLAGGED_MINE,
    QMARKED_MINE
}
