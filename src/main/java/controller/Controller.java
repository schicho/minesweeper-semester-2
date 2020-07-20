package controller;

import java.awt.event.*;
import java.util.Base64;
import java.util.Timer;


import model.*;
import model.enums.*;
import observer_subject.*;
import view.*;
import model.timer.SecondsTimer;

import javax.swing.*;

/**
 * controller class
 * is the mouse listener and also observes the model
 */
public class Controller implements MouseListener, Observer {


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
    private Timer timer = null;
    private SecondsTimer secondsTimer = null;

    /**
     * call to initialize the controller class
     */

    public void initializeController(Gui gui) {
        this.gui = gui;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JButton) {

            //get the button text...
            String whatItDoes = buttonInfo((JButton) e.getSource());

            //...to switch between the different buttons actions
            if (whatItDoes.equals("Exit")) {
                //exit the program
                gui.getWindow().dispatchEvent(new WindowEvent(gui.getWindow(), WindowEvent.WINDOW_CLOSING));
            }
            else if (whatItDoes.equals("Play easy")) {

                //change the gameState, load the new scene
                model = new Model(Difficulty.EASY);
                model.setGameState(GameState.RUNNING);

                //bind the new model to its observer
                model.attach(this);

                //tell gui what to do
                gui.calculateSize(model);
                gui.loadScene(model.getGameState());

                //initialize timer
                timer = new Timer();
                secondsTimer = new SecondsTimer();
                secondsTimer.attach(this);

                //run model.timer ever 1000ms = 1s
                timer.schedule(secondsTimer, 0, 1000);
            }
            else if (whatItDoes.equals("Play medium")) {

                //change the gameState, load the new scene
                model = new Model(Difficulty.NORMAL);
                model.setGameState(GameState.RUNNING);

                //bind the new model to its observer
                model.attach(this);

                //tell gui what to do
                gui.calculateSize(model);
                gui.loadScene(model.getGameState());

                //initialize timer
                timer = new Timer();
                secondsTimer = new SecondsTimer();
                secondsTimer.attach(this);

                //run model.timer ever 1000ms = 1s
                timer.schedule(secondsTimer, 0, 1000);
            }
            else if (whatItDoes.equals("Play hard")) {

                //change the gameState, load the new scene
                model = new Model(Difficulty.HARD);
                model.setGameState(GameState.RUNNING);

                //bind new model to its observer
                model.attach(this);

                //tell gui what to do
                gui.calculateSize(model);
                gui.loadScene(model.getGameState());

                //initialize timer
                timer = new Timer();
                secondsTimer = new SecondsTimer();
                secondsTimer.attach(this);

                //run model.timer ever 1000ms = 1s
                timer.schedule(secondsTimer, 0, 1000);
            }
            else if (whatItDoes.equals("Load game")) {
                //load the seed string
                String encodedSting = gui.loadFromSeed();

                //check on empty
                if (!(encodedSting == null) && (!(encodedSting.equals("")))) {
                    //if a timer was previously running, cancel first
                    if (timer != null) {
                        timer.cancel();
                    }
                    try {
                        //decode seed string
                        Base64.Decoder decoder = Base64.getDecoder();
                        byte[] byteSeed = decoder.decode(encodedSting.getBytes());
                        String seed = new String(byteSeed);

                        //create new model by seed
                        model = new Model(seed);
                        model.setGameState(GameState.RUNNING);
                        model.attach(this);

                        //initialize timer
                        timer = new Timer();
                        secondsTimer = new SecondsTimer();
                        secondsTimer.attach(this);
                        //run model.timer ever 1000ms = 1s
                        timer.schedule(secondsTimer, 0, 1000);

                        //load the scene and update the window
                        gui.calculateSize(model);
                        gui.loadScene(model.getGameState());
                        gui.getWindow().repaint();
                        gui.getWindow().revalidate();
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        gui.invalidSeed();
                    }

                }
                if (model == null) {
                    //if string is empty, return to main menu
                    gui.loadScene(GameState.MAIN_MENU);
                    return;
                }
                gui.loadScene(model.getGameState());
            }
            else if (whatItDoes.equals("Save game")) {
                String seed = model.getSeed();
                model.touch();
                gui.returnSeed(seed);
            }
            else if (whatItDoes.equals("Continue")) {

                //set the game state and load the scene accordingly
                model.setGameState(GameState.RUNNING);

                //resume the timer
                secondsTimer.unpauseTimer();
                gui.continueAfterPause(secondsTimer.counter);
            }
            else if (whatItDoes.equals("Pause")) {

                //pause the timer
                secondsTimer.pauseTimer();

                //set new game state
                model.setGameState(GameState.PAUSE);

                //load pause menu
                gui.loadScene(model.getGameState());
            }
            else if (whatItDoes.equals("Exit to main menu")) {
                //set game state
                model.setGameState(GameState.MAIN_MENU);

                //load main menu
                gui.loadScene(model.getGameState());

                //stop timer
                timer.cancel();
            }
            else if (SwingUtilities.isRightMouseButton(e)) {
                //add a f in front of the command to flag
                whatItDoes = "f" + whatItDoes;

                //send the command to handleInput
                handleInput(whatItDoes);
            }
            else if (SwingUtilities.isLeftMouseButton(e)) {

                //send the command to handleInput
                handleInput(whatItDoes);
            }

            //update Tile Text
            gui.updateTileText(model);

            //update flag display
            gui.updateFlagDisplay();
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
        }
        else {
            return button.getText();
        }
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
     * sets the model
     * ONLY FOR TESTING, DO NOT USE FOR OTHER THAN TESTING
     * @param m the new model to overwrite the existing one
     */
    public void setModel(Model m){
        model = m;
    }

    /**
     * interprets the given input
     */
    public void handleInput(String input) {

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

            model.sweepTile(m, n, false);
        }
    }

    @Override
    public void update(Subject s) {
        //update timer display
        gui.updateTimerDisplay(secondsTimer.counter);
        //get game state
        GameState current = model.getGameState();

        //switch game state
        switch (current) {
            case WON:

                //stop timer
                timer.cancel();

                gui.updateTileText(model);
                gui.displayWin(secondsTimer.counter);

                model.setGameState(GameState.MAIN_MENU);
                gui.loadScene(model.getGameState());
                break;
            case LOST:

                //stop timer
                timer.cancel();

                model.sweepAllOnLost();

                gui.updateTileText(model);
                gui.displayFailure(model.getRemainingMines());

                model.setGameState(GameState.MAIN_MENU);
                gui.loadScene(model.getGameState());
                break;
        }

    }


}
