import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import exceptions.*;

public class Controller /*implements MouseListener*/ {

    /**
     * Main game loop which runs the game and stops it at win or failure
     * @param args *no arguments*
     */
    public static void main(String[] args) {
        Model model;
        Cli cli = new Cli();
        Controller controller = new Controller();

        //only easy for now.
        model = new Model(Difficulty.EASY);

        cli.initializeView(model);

        //gameloop
        do {
            controller.updateModel(model);
            cli.drawModel(model);

            if(model.checkCurrentGameState() == GameState.WON) {
                cli.displayWin();
                System.exit(0);
            }
            else if(model.checkCurrentGameState() == GameState.LOST) {
                cli.displayFailure();
                System.exit(0);
            }
        } while(model.checkCurrentGameState() == GameState.RUNNING);
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
     * @param model
     */
    public void updateModel(Model model){

        handleInput();
        if(m>-1 && n>-1) {
            model.sweepTile(m, n);
        }
    }

    /**
     * interprets the given input
     */
    private void handleInput(){
        inputExceptionHandler tester= new inputExceptionHandler();
        //read the next command from user
        command = scanner.nextLine();

        //read out step values
        try {
            //throws wrongFormatException, if command doesn't contain a ':'
            tester.testSplittable(command);
            String[] parts = command.split(":");
            //throws wrongFormatException, if first part can't be parsed as Int
            tester.testInt(parts[0]);
            m = Integer.parseInt(parts[0]);
            //throws wrongFormatException, if second part can't be parsed as Int
            tester.testInt(parts[1]);
            n = Integer.parseInt(parts[1]);
            //throws notATileException, if m or n is to small or big for the difficulty.
            /**
             * MISSING: Exception for flagging!
             * MISSING: for testInRange, variable for saving the Difficulty instead of Easy
             */
            tester.testInRange(Difficulty.EASY,m,n);
        }
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
