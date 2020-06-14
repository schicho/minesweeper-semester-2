import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

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

        model.sweepTile(m, n);
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
            tester.testSplittable(command);
            String[] parts = command.split(":");
            m = Integer.parseInt(parts[0]);
            n = Integer.parseInt(parts[1]);
        }
        catch (Exception e){
            System.out.println(e.toString());
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
