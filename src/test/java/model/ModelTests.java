package model;

import model.enums.Difficulty;

import model.enums.TileState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTests {

    @Test
    @DisplayName("10 mines on an easy-sized minefield:")
    void testCorrectNumberOfMinesEasy() {
        Model easyModel = new Model(Difficulty.EASY);

        int bombCounter = 0;
        Tile[][] twoDarray = easyModel.getTileArray();
        for (Tile[] oneDarray: twoDarray) {
            for (Tile t: oneDarray){
                if (t.getState() == TileState.MINE){
                    bombCounter++;
                }
            }

        }
        assertEquals(10, bombCounter);
    }

    @Test
    @DisplayName("40 mines on an normal-sized minefield:")
    void testCorrectNumberOfMinesNormal() {
        Model easyModel = new Model(Difficulty.NORMAL);

        int bombCounter = 0;
        Tile[][] twoDarray = easyModel.getTileArray();
        for (Tile[] oneDarray: twoDarray) {
            for (Tile t: oneDarray){
                if (t.getState() == TileState.MINE){
                    bombCounter++;
                }
            }

        }
        assertEquals(40, bombCounter);
    }

    @Test
    @DisplayName("99 mines on an hard-sized minefield:")
    void testCorrectNumberOfMinesHard() {
        Model easyModel = new Model(Difficulty.HARD);

        int bombCounter = 0;
        Tile[][] twoDarray = easyModel.getTileArray();
        for (Tile[] oneDarray: twoDarray) {
            for (Tile t: oneDarray){
                if (t.getState() == TileState.MINE){
                    bombCounter++;
                }
            }

        }
        assertEquals(99, bombCounter);
    }
}
