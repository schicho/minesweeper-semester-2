package controller;

import model.Model;
import model.enums.Difficulty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerTests {

    @Test
    @DisplayName("Tests wether handleInput is correct or not")
    public void testHandleInput(){
        //the test coordinates
        int m = 4, n = 8;

        //test for sweeping
        String testCommand = Integer.toString(m) + ":" + Integer.toString(n);

        //================= Test 1 ===========================

        //create controller and model
        Controller c = new Controller();
        c.setModel(new Model(Difficulty.EASY));

        //call handleInput with test parameter
        c.handleInput(testCommand);

        //now check if the function successfully sweeped the tile at 4, 8
        assertTrue(c.getModel().isSweeped(m, n));

        //================== Test 2 ==========================
        //reset test String
        testCommand = "f" + testCommand;

        //reset the model
        c.setModel(new Model(Difficulty.EASY));

        //call handleInput with intend to flag
        c.handleInput(testCommand);

        //assert the tile state
        assertTrue(c.getModel().isFlagged(m, n));
    }
}
