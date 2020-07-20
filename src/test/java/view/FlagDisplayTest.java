package view;

import model.enums.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlagDisplayTest {

    @Test
    @DisplayName("Test flag display after setting and removing a flag")
    void testFlagDisplay() throws AWTException{

        Gui gui = new Gui();
        Robot bot = new Robot();

        //start easy game
        bot.mouseMove(450, 200);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        //set flag
        bot.mouseMove(450, 280);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals("9", gui.getFlagDisplayText());

        //set question mark
        bot.mouseMove(450, 280);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals("10", gui.getFlagDisplayText());
    }

}
