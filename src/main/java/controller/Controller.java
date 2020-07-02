package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import model.*;
import model.enums.*;
import view.*;
import model.exceptions.*;
import model.timer.*;

import javax.swing.*;

public class Controller implements MouseListener {

    /**
     * holds the controller instance
     */
    private static Controller controller;

    /**
     * holds the model instance
     */
    private static Model model;

    /**
     * holds the gui instance
     */
    private static Gui gui;

    /**
     * variables used for the timer.
     */
    private static final Timer timer = new Timer();
    private static TimerTask timerTask = null;

    /**
     * exit variable, once set to true the game terminates.
     */
    private static boolean exit = false;

    /**
     * Main game loop which runs the game and stops it at win or failure.
     *
     * @param args *no arguments*
     */
    public static void main(String[] args) {
        gui = new Gui();

        controller = new Controller();

        timerTask = new SecondsTimer();
        //run model.timer ever 1000ms = 1s
        timer.schedule(timerTask, 0, 1000);

        // TODO: this needs to be set by the main menu when starting a game.
        model = new Model(Difficulty.EASY);
        gui.calculateSize(model);
        gui.loadScene(GameState.RUNNING);

        do {
            //check first time in case a new game was started and old
            //thread needs to be stopped
            if (exit) {
                timerTask.cancel();
                timerTask = null;
                timer.cancel();
                return;
            }

            if (model.getGameState() == GameState.WON) {
                gui.displayWin();

                //stop timerTask and reset
                timerTask.cancel();
                SecondsTimer.counter = 0;

            } else if (model.getGameState() == GameState.LOST) {
                gui.displayFailure(model.getRemainingMines());

                //stop timerTask and reset
                timerTask.cancel();
                SecondsTimer.counter = 0;

            }
        } while (model.getGameState() == GameState.RUNNING);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() instanceof JButton) {
            String whatsItDo = buttonInfo((JButton) e.getSource());
            if (whatsItDo.equals("Exit")) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    System.out.println("tixE");
                } else {
                    System.out.println("Exit");
                }
            } else if (whatsItDo.equals("Play")) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("yalP");
                } else System.out.println("Play");

            // Click on Tile Button. Sweep/Flag/Unflag
            } else if (e.getSource() instanceof TileButton) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    whatsItDo = "f" + whatsItDo;
                    handleInput(whatsItDo);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    handleInput(whatsItDo);
                }
                //update Tile Text
                gui.updateTileText(model);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * @param button JButton oder Tilebutton
     * @return a string, identifing the button
     */
    private String buttonInfo(JButton button) {
        if (button instanceof TileButton) {
            return ((TileButton) button).getM() + ":" + ((TileButton) button).getN();
        }else {
            return button.getText();
        }
    }

    /**
     * creates a new controller instance
     */
    public Controller() {
    }

    /**
     * @return the current Controller which is also our inputHandler
     */
    public static Controller getMouseHandler() {
        return controller;
    }

    /**
     * @return the current model
     */
    public static Model getModel() {
        return model;
    }


    /**
     * interprets the given input
     */
    private void handleInput(String input) {

        //index of row and column
        int m, n;

        //flag a tile
        if (input.contains(":") && input.startsWith("f")) {
            input = input.replace("f", "");
            //read out step values
            String[] parts = input.split(":");
            m = Integer.parseInt(parts[0]);
            n = Integer.parseInt(parts[1]);

            model.flagTile(m, n);
        }
        //sweep a tile
        else if (input.contains(":")) {
            //read out step values
            String[] parts = input.split(":");
            m = Integer.parseInt(parts[0]);
            n = Integer.parseInt(parts[1]);

            model.sweepTile(m, n);
        }
    }
}
