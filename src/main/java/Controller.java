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
        controller.difficulty=controller.readDifficulty();
        //only easy for now.
        model = new Model(controller.difficulty);

        cli.initializeView(model);

        //gameloop
        do {
            controller.updateModel(model);
            cli.drawModel(model);

            if(model.getGameState() == GameState.WON) {
                cli.displayWin();
                System.exit(0);
            }
            else if(model.getGameState() == GameState.LOST) {
                cli.displayFailure();
                System.exit(0);
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
     * scans the next line (command)
     */
    private Scanner scanner;

    /**
     * saves the difficulty given by the user
     */
    private Difficulty difficulty;

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

        //read the next command from user
        command = scanner.nextLine();

        //read out step values
        String[] parts = command.split(":");
        m = Integer.parseInt(parts[0]);
        n = Integer.parseInt(parts[1]);
    }

    private Difficulty readDifficulty(){
        Difficulty difficulty = null;
        while (difficulty==null) {
            String difficultyString = scanner.nextLine();
            try {
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
                    default:
                        throw new Exception("1. implementiere das in den ExceptionHandler 2. ja nur easy normal hard eingeben.");
                }
            }catch (Exception e){
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
