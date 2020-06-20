import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import exceptions.*;


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
     * Main game loop which runs the game and stops it at win or failure
     * @param args *no arguments*
     */
    public static void main(String[] args) {
        cli = new Cli();
        Controller controller = new Controller();

        //only easy for now.
        model = new Model(Difficulty.EASY);

        cli.initializeView(model);

        //gameloop
        do {
            controller.updateModel(model);
            cli.drawModel(model);


            if(model.getGameState() == GameState.WON) {
                cli.displayWin();
                cli.displayMessage("Type \"ng\" to start a new game, \"exit\" to leave.");
                controller.handleInput();
            }
            else if(model.getGameState() == GameState.LOST) {
                cli.displayFailure(model.getRemainingMines());
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
        if(m>-1 && n>-1) {
            if(placeFlag){
                model.flagTile(m,n);
            }
            else{model.sweepTile(m, n);}
        }

    }

    /**
     * interprets the given input
     */
    private void handleInput(){
        inputExceptionHandler tester= new inputExceptionHandler();

        //read the next command from user
        command = scanner.nextLine();
        try {
            tester.testRealCommand(command);
            if (command.contains(":")) {
                //read out step values
                String[] parts = command.split(":");
                //throws wrongFormatException, if first part can't be parsed as Int
                tester.testInt(parts[0]);
                m = Integer.parseInt(parts[0]);
                //throws wrongFormatException, if second part can't be parsed as Int
                tester.testInt(parts[1]);
                n = Integer.parseInt(parts[1]);
                //throws notATileException, if m or n is to small or big for the difficulty.
                tester.testInRange(difficulty, m, n);
                if (parts.length == 3) {
                    placeFlag = true;
                }
            } else {
                //its not a mine command
                switch (command) {
                    case "ng": {
                        //start a new game
                        model = new Model(Difficulty.EASY);

                        //draw the new model once, until the game loop does it again
                        cli.drawModel(model);

                        //set the gameState to running in case the game was lost or won
                        model.setGameState(GameState.RUNNING);
                    }
                    break;
                    case "exit": {
                        //exit
                        model.setGameState(GameState.EXIT);
                    }
                    break;
                }
            }
        }catch (wrongFormatException e){
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
