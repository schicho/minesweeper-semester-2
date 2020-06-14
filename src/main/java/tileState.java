public enum tileState {
    /**
     * enum of tile states. Each tile can have exactly one state.
     * if one tile has SWEEPED_MINE as its state, the game is lost
     */
    FREE,
    SWEEPED,
    FLAGGED,
    MINE,
    SWEEPED_MINE,
    FLAGGED_MINE,
    NOT_SET
}
