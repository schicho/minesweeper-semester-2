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

        // bot.mouseMove(72,47); relative coordinates to the application window
        bot.mouseMove(590, 333);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals(gui.getController().getModel().getDifficulty(), Difficulty.EASY);
    }


    @Test
    @DisplayName("Play medium game")
    void testMediumGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        // bot.mouseMove(158,47); relative coordinates to the application window
        bot.mouseMove(676, 333);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals(gui.getController().getModel().getDifficulty(), Difficulty.NORMAL);
    }


    @Test
    @DisplayName("Play hard game")
    void testHardGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        // bot.mouseMove(272,47); relative coordinates to the application window
        bot.mouseMove(790, 333);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals(gui.getController().getModel().getDifficulty(), Difficulty.HARD);
    }


    @Test
    @DisplayName("Load Game")
    void testLoadGame() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        // bot.mouseMove(367,47); relative coordinates to the application window
        bot.mouseMove(885, 333);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals(gui.loadFromSeed(), JOptionPane.PLAIN_MESSAGE);
    }


    @Test
    @DisplayName("Exit")
    void testExit() throws AWTException {
        Gui gui = new Gui();
        Robot bot = new Robot();

        // bot.mouseMove(440,47); relative coordinates to the application window
        bot.mouseMove(958, 333);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }

        assertEquals(JFrame.EXIT_ON_CLOSE, JFrame.EXIT_ON_CLOSE);
    }
}