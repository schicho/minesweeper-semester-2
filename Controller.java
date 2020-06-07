package main.java;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

class Model{

    DIFFICULTY difficulty;

    void sweepTile(int row, int col){

    }
}

enum DIFFICULTY{
    EASY,
    MEDIUM,
    HARD
}

public class Controller /*implements MouseListener*/ {

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

        //read the next command from user
        command = scanner.nextLine();

        //read out step values
        String[] parts = command.split(":");
        m = Integer.parseInt(parts[0]);
        n = Integer.parseInt(parts[1]);
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
