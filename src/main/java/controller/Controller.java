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
        cli = new Cli();
        Controller controller = new Controller();
        if( (args.length>0)&&(args[0].length()>0)) {
            model =new Model(args[0]);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!GEEEEEEEHT!!!!!!!!!");
        }
        else {
            controller.difficulty = controller.readDifficulty();
            model = new Model(controller.difficulty);
        }

        timerTask = new SecondsTimer();
        //run model.timer ever 1000ms = 1s
        timer.schedule(timerTask, 0, 1000);

        cli.initializeView(model);

        //gameloop
        do {
            //check first time in case a new game was started and old
            //thread needs to be stopped
            if (exit) {
                return;
            }

            controller.updateModel();

            //check second time to avoid redraw
            if (exit) {
                return;
            }

            cli.drawModel(model);

            if (model.getGameState() == GameState.WON) {
                cli.displayWin();

                //stop timerTask and reset
                timerTask.cancel();
                SecondsTimer.counter = 0;

                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                cli.displayInputPrompt();
                controller.handleInput();
            } else if (model.getGameState() == GameState.LOST) {
                cli.displayFailure(model.getRemainingMines());

                //stop timerTask and reset
                timerTask.cancel();
                SecondsTimer.counter = 0;

                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                cli.displayInputPrompt();
                controller.handleInput();
            }
        } while (model.getGameState() == GameState.RUNNING);
    }

    /**
     * saves the difficulty given by the user
     */
    private Difficulty difficulty;

    /**
     * tests userinput for all kinds of mistakes
     */
    private final InputExceptionHandler tester = new InputExceptionHandler();

    /**
     * creates a new controller instance
     */
    public Controller() {
    }

    /**
     * updates the given model instance dependent of the input
     * the class received earlier
     */
    public void updateModel() {
        cli.askForNextTile();

        handleInput();
    }

    private String getSeed(){
        Scanner scanner = new Scanner(System.in);
        String seed = scanner.nextLine();
        return seed;
    }

    /**
     * interprets the given input
     */
    private void handleInput() {

        Scanner scanner = new Scanner(System.in);
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
                        //stop timerTask, set null and reset
                        timerTask.cancel();
                        timerTask = null;
                        SecondsTimer.counter = 0;

                        String[] noargs = {""};
                        main(noargs);
                    }
                    break;
                    case "exit": {
                        //exit
                        //timer needs to be stopped here, otherwise program wont terminate
                        timer.cancel();
                        exit = true;
                    }
                    break;
                    case "save":{
                        String seed= model.getSeed();
                        cli.printSeed(seed);
                    }
                    break;
                    case "load":{
                        cli.askForSeed();
                        cli.displayInputPrompt();
                        String Seed=getSeed();
                        timerTask.cancel();
                        timerTask = null;
                        SecondsTimer.counter = 0;

                        String[] noargs = {Seed};
                        main(noargs);
                    }
                    break;
                }
            }
        } catch (WrongFormatException | NotATileException e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Reads the difficulty the player wants to play the game in.
     * Is entered and read at game start.
     *
     * @return Difficulty (value of enum)
     */
    private Difficulty readDifficulty() {
        Scanner scanner = new Scanner(System.in);
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
