package view;

import controller.Controller;
import model.Model;
import model.enums.GameState;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame window;
    private JPanel mainMenu;
    private JPanel pauseMenu;
    private JPanel game;
    private JPanel endGameMessage;

    private static JButton lastInput[];

    /**
     * creates a new Gui instance
     */
    public Gui(){
        createWindow(1280, 720, "Minesweeper");
        lastInput = new JButton[2];
    }

    /**
     * creates a new window
     * @param width width of the window
     * @param height height of the window
     * @param title whatever strange name you wanna call your window
     */
    public void createWindow(int width, int height, String title){
        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, width, height);
        window.setVisible(true);
    }

    /**
     * loads a specific scene, which is determined by the given state
     * @param state the state to tell the function what to load
     */
    public void loadScene(GameState state){

        switch(state){

            case MAIN_MENU:
            {
                //main menu panel
                mainMenu = new JPanel();

                //play button
                JButton play = new JButton("Play");
                play.addActionListener(Controller.getListener());
                mainMenu.add(play);

                //exit button
                JButton exit = new JButton("Exit");
                exit.addActionListener(Controller.getListener());
                mainMenu.add(exit);

                //set the last input array
                lastInput[0] = play;
                lastInput[1] = exit;

                window.getContentPane().add(mainMenu);

                window.setVisible(true);
            }break;
            case PAUSE:
            {
                //pause menu panel
                pauseMenu = new JPanel();

                //continue button
            }
            case RUNNING:
            {
                //game panel
                game = new JPanel();

                //labels for the menu bar

                //buttons for the minefield
            }
        }
    }

    public static JButton[] getLastInput(){
        return lastInput;
    }
}
