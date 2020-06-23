package controller;

import java.util.Scanner;
import model.*;
// import entities.*;
import entities.enums.*;
import view.*;
import exceptions.*;
import java.util.Timer;
import java.util.TimerTask;


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


    /**
     * Main game loop which runs the game and stops it at win or failure
     * @param args *no arguments*
     */
    public static void main(String[] args) {
        cli = new Cli();
        Controller controller = new Controller();
        controller.difficulty=controller.readDifficulty();
        //only easy for now.
        model = new Model(controller.difficulty);

        cli.initializeView(model);

        //Timer erstellen
        timerTask = new SecTimerTask();
        //Timer starten, fängt nach 1 Sekunde an zu zählen
        timer.schedule(timerTask, 0, 1000);

        //gameloop
        do {
            controller.updateModel(model);
            cli.drawModel(model);


            if(model.getGameState() == GameState.WON) {
                cli.displayWin();
                //Timer beenden und Counter auf 0 setzen
                timerTask.cancel();
                Controller.SecTimerTask.counter = 0;
                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                controller.handleInput();
            }
            else if(model.getGameState() == GameState.LOST) {
                cli.displayFailure(model.getRemainingMines());
                //Timer beenden und auf Null setzen
                timerTask.cancel();
                Controller.SecTimerTask.counter = 0;
                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                controller.handleInput();
            }
        } while(model.getGameState() == GameState.RUNNING);

    }

    /**
     * the string that will be interpreted in updateModel
     * is filled with data by handleInput
     */
    private String command;


    /**
     * these are containing the evaluated step values
     * used to update the model
     */
    private int m, n;
    /**
     *true if the last command was one concerning flagplacement
     */
    private boolean placeFlag=false;

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
    private inputExceptionHandler tester= new inputExceptionHandler();

    /**
     * creates a new controller instance, which is used to handle input
     * and update the model it is given respectively
     */
    public Controller(){

        scanner = new Scanner(System.in);
    }

    /**
     * updates the given model instance dependent of the input
     * the class received earlier
     *
     * @param model the model to be updated
     */
    public void updateModel(Model model){
        cli.askForNextTile();

        handleInput();

    }

    /**
     * interprets the given input
     */
    private void handleInput(){

        //read the next command from user
        command = scanner.nextLine();

        try {
            //flag a tile
            tester.testRealCommand(command);
            if(command.contains(":") && command.startsWith("f")){
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
            else if(command.contains(":")){
                //read out step values
                String[] parts = command.split(":");
                tester.testInt(parts[0]);
                m = Integer.parseInt(parts[0]);
                tester.testInt(parts[1]);
                n = Integer.parseInt(parts[1]);
                tester.testInRange(difficulty, m, n);

                model.sweepTile(m, n);
            }
            else{
                //its not a mine command
                switch(command){
                    case "ng":
                    {
                        //Timer beenden und auf Null setzen
                        timerTask.cancel();
                        timerTask = null;
                        Controller.SecTimerTask.counter = 0;
                        String[] noargs = {""};
                        main(noargs);

                    }break;
                    case "exit":
                    {
                        //exit
                        model.setGameState(GameState.EXIT);
                        //Don't wait on gameloop to quit indirectly. Avoids redraw
                        System.exit(0);
                    }break;

                }
            }}
        catch (wrongFormatException e){
            System.out.println(e.toString());
            //set m,n to -1, which can be tested for, to avoid bugs
            m = -1;
            n = -1;
        }
        catch (notATileException e){
            System.out.println(e.toString());
            //set m,n to -1, which can be tested for, to avoid bugs. Probably not neccesary here.
            m = -1;
            n = -1;
        }

    }

    private Difficulty readDifficulty(){
        Difficulty difficulty = null;
        while (difficulty==null) {
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
            }catch (notADifficultyException e){
                System.out.println(e.toString());
            }
        }
        return difficulty;
    }

    // Counter zu Timer
    public static class SecTimerTask extends TimerTask {

        public static int counter = 0;

        @Override
        public void run() {
            counter++;
        }
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
