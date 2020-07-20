package view;

import model.enums.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PauseFunctionTest {

    @Test
    @DisplayName("Test Pause Function")
    void testPauseFunction() throws AWTException{

        Gui gui = new Gui();
        Robot bot = new Robot();

        //start easy game
        bot.mouseMove(450,200);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}

        //click pause
        bot.mouseMove(500,140);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //add time between press and release or the input event system may
        //not think it is a click
        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}

        assertEquals(gui.getController().getModel().getGameState(), GameState.PAUSE);
    }

}
