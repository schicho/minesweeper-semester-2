package view;

import model.enums.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.event.InputEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTests {

    @Test
    @DisplayName("Test flag display after setting and removing a flag")
    void testFlagDisplay() throws AWTException{

        Gui gui = new Gui();
        Robot bot = new Robot();

        //start easy game
        bot.mouseMove(70, 20);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        //set flag
        bot.mouseMove(50, 110);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

        assertEquals("9", gui.getFlagDisplayText());

        //set question mark
        bot.mouseMove(50, 110);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

        assertEquals("10", gui.getFlagDisplayText());
    }

    @Test
    @DisplayName("Test Pause Function")
    void testPauseFunction() throws AWTException{

        Gui gui = new Gui();
        Robot bot = new Robot();

        //start easy game
        bot.mouseMove(70,20);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        //click pause
        bot.mouseMove(110,-50);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        assertEquals(gui.getController().getModel().getGameState(), GameState.PAUSE);

    }

    @Test
    @DisplayName("Test Pause Menu")
    void testPauseMenu() throws AWTException{

        Gui gui = new Gui();
        Robot bot = new Robot();

            //start easy game
            bot.mouseMove(70, 20);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            //add time between press and release or the input event system may
            //not think it is a click
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            //click pause
            bot.mouseMove(110, -50);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            //add time between press and release or the input event system may
            //not think it is a click
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            //click continue
            bot.mouseMove(110, -45);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            //add time between press and release or the input event system may
            //not think it is a click
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            assertEquals(gui.getController().getModel().getGameState(), GameState.RUNNING);

            //click pause
            bot.mouseMove(110, -50);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            //add time between press and release or the input event system may
            //not think it is a click
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            //click exit to main menu
            bot.mouseMove(250, 0);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            //add time between press and release or the input event system may
            //not think it is a click
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            assertEquals(gui.getController().getModel().getGameState(), GameState.MAIN_MENU);
    }

}
