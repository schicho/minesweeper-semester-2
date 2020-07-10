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
    @DisplayName("ExampleTest")
    void testCreateFacultyWithChairs() {
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

}
