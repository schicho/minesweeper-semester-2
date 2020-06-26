package controller;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import model.*;
import model.enums.*;
import view.*;
import model.exceptions.*;
import model.timer.*;


public class Controller /*implements MouseListener*/ {

    /**
     * holds the model instance
     */
    private static Model model;

    /**
     * holds the cli instance
     */
    private static Cli cli;

    //Initialize Timer variables
    private static final Timer timer = new Timer();
    private static TimerTask timerTask = null;

    private static boolean exit = false;


    /**
     * Main game loop which runs the game and stops it at win or failure
     *
     * @param args *no arguments*
     */
    public static void main(String[] args) {
        cli = new Cli();
        Controller controller = new Controller();

        controller.difficulty = controller.readDifficulty();
        model = new Model(controller.difficulty);

        timerTask = new SecondsTimer();
        //run model.timer ever 1000ms = 1s
        timer.schedule(timerTask, 0, 1000);

        cli.initializeView(model);

        //gameloop
        do {
            controller.updateModel();

            if (exit) {
                return;
            }

            cli.drawModel(model);

            if (model.getGameState() == GameState.WON) {
                cli.displayWin();

                //stop model.timer and reset
                timer.cancel();
                SecondsTimer.counter = 0;

                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                controller.handleInput();
            } else if (model.getGameState() == GameState.LOST) {
                cli.displayFailure(model.getRemainingMines());

                //stop model.timer and reset
                timer.cancel();
                SecondsTimer.counter = 0;

                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                controller.handleInput();
            }
        } while (model.getGameState() == GameState.RUNNING);
    }

    /**
     * scans the next line (command)
     */
    private Scanner scanner;

    /**
     * saves the difficulty given by the user
     */
    private Difficulty difficulty;

    /**
     * tests userinput for all kinds of mistakes
     */
    private InputExceptionHandler tester = new InputExceptionHandler();

    /**
     * creates a new controller instance, which is used to handle input
     * and update the model it is given respectively
     */
    public Controller() {

        scanner = new Scanner(System.in);
    }

    /**
     * updates the given model instance dependent of the input
     * the class received earlier
     */
    public void updateModel() {
        cli.askForNextTile();

        handleInput();
    }

    /**
     * interprets the given input
     */
    private void handleInput() {

        //read the next command from user
        String command = scanner.nextLine();

        //index of row and column
        int m, n;

        try {
            tester.testRealCommand(command);

            //flag a tile
            if (command.contains(":") && command.startsWith("f")) {
                command = command.replace("f", "");
                //read out step values
                String[] parts = command.split(":");
                tester.testInt(parts[0]);
                m = Integer.parseInt(parts[0]);
                tester.testInt(parts[1]);
                n = Integer.parseInt(parts[1]);
                tester.testInRange(difficulty, m, n);

                model.flagTile(m, n);
            }
            //sweep a tile
            else if (command.contains(":")) {
                //read out step values
                String[] parts = command.split(":");
                tester.testInt(parts[0]);
                m = Integer.parseInt(parts[0]);
                tester.testInt(parts[1]);
                n = Integer.parseInt(parts[1]);
                tester.testInRange(difficulty, m, n);

                model.sweepTile(m, n);
            } else {
                //its not a mine command
                switch (command) {
                    case "ng": {
                        //stop timer, set null and reset
                        timerTask.cancel();
                        timerTask = null;
                        SecondsTimer.counter = 0;

                        String[] noargs = {""};
                        main(noargs);
                    }
                    break;
                    case "exit": {
                        //exit
                        scanner.close();
                        //timer needs to be stopped, otherwise program wont terminate
                        timer.cancel();
                        exit = true;
                    }
                    break;
                }
            }
        } catch (WrongFormatException | NotATileException e) {
            System.out.println(e.toString());
        }

    }

    private Difficulty readDifficulty() {
        Difficulty difficulty = null;
        while (difficulty == null) {
            String difficultyString = scanner.nextLine();
            try {
                tester.testForDifficulty(difficultyString);
                switch (difficultyString.toLowerCase().trim()) {
                    case "easy":
                        difficulty = Difficulty.EASY;
                        break;
                    case "normal":
                        difficulty = Difficulty.NORMAL;
                        break;
                    case "hard":
                        difficulty = Difficulty.HARD;
                        break;
                }
            } catch (NotADifficultyException e) {
                System.out.println(e.toString());
            }
        }
        return difficulty;
    }

    /*
    /**
     * overrides keyTyped method from KeyListener
     * @param e
     *
    @Override
    public void keyTyped(KeyEvent e) {
        lastInput = e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    */

}
