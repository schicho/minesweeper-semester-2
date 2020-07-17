package controller;

import java.awt.event.*;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

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
public class Controller implements KeyListener, MouseListener, Observer {


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
    private static TimerTask secondsTimer = null;

    /**
     * call to initialize the controller class
     */

    public void initializeController(Gui gui){
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
                SecondsTimer.counter = 0;
                secondsTimer = new SecondsTimer();

                //run model.timer every 1000ms = 1s
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
                SecondsTimer.counter = 0;
                secondsTimer = new SecondsTimer();

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
                SecondsTimer.counter = 0;
                secondsTimer = new SecondsTimer();

                //run model.timer ever 1000ms = 1s
                timer.schedule(secondsTimer, 0, 1000);
            } else if (whatItDoes.equals("Load game")) {
                String encodedSting = gui.loadFromSeed();
                if(!(encodedSting==null)&&(!(encodedSting.equals("")))) {
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] byteSeed = decoder.decode(encodedSting.getBytes());
                    String seed = new String(byteSeed);
                    model = new Model(seed);
                    model.setGameState(GameState.RUNNING);
                    model.attach(this);
                    gui.calculateSize(model);
                    gui.loadScene(model.getGameState());
                    SecondsTimer.counter = 0;
                    secondsTimer = new SecondsTimer();
                    //run model.timer ever 1000ms = 1s
                    timer.schedule(secondsTimer, 0, 1000);
                    model.setGameState(GameState.RUNNING);
                    gui.loadScene(model.getGameState());
                }
                else{
                    gui.loadScene(GameState.MAIN_MENU);
                    return;
                }
            }
            else if (whatItDoes.equals("Continue")) {

                //set the game state and load the scene accordingly
                model.setGameState(GameState.RUNNING);

                gui.continueAfterPause();

                //resume the timer
                SecondsTimer.unpauseTimer();
                timer.schedule(secondsTimer, 0, 1000);
            }
            else if(whatItDoes.equals("Pause")){

                //pause the timer
                SecondsTimer.pauseTimer();
                timer.cancel();
                timer.purge();

                //set new game state
                model.setGameState(GameState.PAUSE);

                //load pause menu
                gui.loadScene(model.getGameState());
            }
            else if(whatItDoes.equals("Exit to main menu")){
                //set game state
                model.setGameState(GameState.MAIN_MENU);

                //load main menu
                gui.loadScene(model.getGameState());
            }
            else if (SwingUtilities.isRightMouseButton(e)) {
                //add a f in front of the command to flag
                whatItDoes = "f" + whatItDoes;

                //send the command to handleInput
                handleInput(whatItDoes);
            } else if (SwingUtilities.isLeftMouseButton(e)) {

                //send the command to handleInput
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
            model.touch();
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

        //get game state
        GameState current = model.getGameState();

        //switch game state
        switch (current){
            case WON:
                //update timer display
                gui.updateTimerDisplay();

                gui.updateTileText(model);
                gui.displayWin();

                //stop timerTask and reset
                timer.cancel();
                timer.purge();
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
                timer.cancel();
                timer.purge();
                SecondsTimer.counter = 0;

                gui.loadScene(GameState.MAIN_MENU);
                break;
        }
        gui.focusOnKeyListner();
    }


}
