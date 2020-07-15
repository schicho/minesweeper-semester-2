package controller;

import java.awt.event.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import model.*;
import model.enums.*;
import observer_subject.*;
import view.*;
import model.timer.*;

import javax.swing.*;

public class Controller implements KeyListener,MouseListener,  Observer {

    /**
     * holds the controller instance
     */
    private Controller controller;

    /**
     * holds the model instance
     */
    private Model model;

    /**
     * holds the gui instance
     */
    private Gui gui;

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
     */
    public void gameloop() {

        timerTask = new SecondsTimer();
        //run model.timer ever 1000ms = 1s
        timer.schedule(timerTask, 0, 1000);

        gui.loadScene(GameState.MAIN_MENU);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() instanceof JButton) {
            String whatItDoes = buttonInfo((JButton) e.getSource());
            if (whatItDoes.equals("Exit")) {
                gui.getWindow().dispatchEvent(new WindowEvent(gui.getWindow(), WindowEvent.WINDOW_CLOSING));
            } else if (whatItDoes.equals("Play easy")) {
                //change the gameState, load the new scene
                model = new Model(Difficulty.EASY);
                model.setGameState(GameState.RUNNING);
                model.attach(this);
                gui.calculateSize(model);
                gui.loadScene(model.getGameState());
                SecondsTimer.counter = 0;
                timerTask = new SecondsTimer();
                //run model.timer ever 1000ms = 1s
                timer.schedule(timerTask, 0, 1000);
            } else if (whatItDoes.equals("Play medium")) {
                //change the gameState, load the new scene
                model = new Model(Difficulty.NORMAL);
                model.setGameState(GameState.RUNNING);
                model.attach(this);
                gui.calculateSize(model);
                gui.loadScene(model.getGameState());
                SecondsTimer.counter = 0;
                timerTask = new SecondsTimer();
                //run model.timer ever 1000ms = 1s
                timer.schedule(timerTask, 0, 1000);
            } else if (whatItDoes.equals("Play hard")) {
                //change the gameState, load the new scene
                model = new Model(Difficulty.HARD);
                model.setGameState(GameState.RUNNING);
                model.attach(this);
                gui.calculateSize(model);
                gui.loadScene(model.getGameState());
                SecondsTimer.counter = 0;
                timerTask = new SecondsTimer();
                //run model.timer ever 1000ms = 1s
                timer.schedule(timerTask, 0, 1000);
            } else if (whatItDoes.equals("Continue")) {
                model.setGameState(GameState.RUNNING);
                gui.loadScene(model.getGameState());
            }
            else if (SwingUtilities.isRightMouseButton(e)) {
                whatItDoes = "f" + whatItDoes;
                handleInput(whatItDoes);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                handleInput(whatItDoes);
            }
            //update Tile Text
            gui.updateTileText(model);
            //update flag display
            gui.updateFlagDisplay();
            //update timer display
            gui.updateTimerDisplay();
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='s'){
            String seed = model.getSeed();
            gui.returnSeed(seed);



        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
    public Controller(Gui gui) {
        this.gui = gui;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * @return the current Controller which is also our inputHandler
     */
    public Controller getMouseHandler() {
        return this;
    }

    /**
     * @return the current model
     */
    public Model getModel() {
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

            model.sweepTile(m, n,false);
        }
    }

    @Override
    public void update(Subject s) {
        GameState current = model.getGameState();
        switch (current){
            case WON:
                //update timer display
                gui.updateTimerDisplay();

                gui.updateTileText(model);
                gui.displayWin();

                //stop timerTask and reset
                timerTask.cancel();
                SecondsTimer.counter = 0;

                gui.loadScene(GameState.MAIN_MENU);
                break;
            case LOST:
                //update timer display
                gui.updateTimerDisplay();

                model.sweepAllOnLost();
                
                gui.updateTileText(model);
                gui.displayFailure(model.getRemainingMines());

                //stop timerTask and reset
                timerTask.cancel();
                SecondsTimer.counter = 0;

                gui.loadScene(GameState.MAIN_MENU);
                break;
        }
        gui.focusOnKeyListner();
    }


}
