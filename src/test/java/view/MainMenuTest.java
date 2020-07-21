package view;

import model.enums.Difficulty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainMenuTest {

    @Test
    @DisplayName("Play easy game")
    void testEasyGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        bot.mouseMove(0,0);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try{Thread.sleep(250);
        }catch(InterruptedException e){}

        assertEquals(gui.getController().getModel().getDifficulty(), Difficulty.EASY);

    }


    @Test
    @DisplayName("Play medium game")
    void testMediumGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        bot.mouseMove(0,0);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try{Thread.sleep(250);
        }catch(InterruptedException e){}

        assertEquals(gui.getController().getModel().getDifficulty(), Difficulty.NORMAL);
    }

    @Test
    @DisplayName("Play hard game")
    void testHardGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        bot.mouseMove(0,0);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try{Thread.sleep(250);
        }catch(InterruptedException e){}

        assertEquals(gui.getController().getModel().getDifficulty(), Difficulty.HARD);
    }

    /*@Test
    @DisplayName("Load Game")
    void testLoadGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        bot.mouseMove(0,0);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try{Thread.sleep(250);
        }catch(InterruptedException e){}


        assertEquals(gui.loadFromSeed());
    }


    @Test
    @DisplayName("Exit")
    void testLoadGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        bot.mouseMove(0,0);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try{Thread.sleep(250);
        }catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try{Thread.sleep(250);
        }catch(InterruptedException e){}


        assertEquals(gui.createWindow(), JFrame.EXIT_ON_CLOSE);
    }

     */

}

