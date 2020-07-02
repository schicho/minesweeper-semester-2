package view;

import controller.Controller;
import model.Model;
import model.Tile;
import model.enums.GameState;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame window;
    private JPanel mainMenu;
    private JPanel pauseMenu;
    private JPanel game;
    private JPanel endGameMessage;

    //important vars for the design
    int width, height;
    int unifiedMenuButtonWidth, unifiedMenuButtonHeight;
    int buffer;


    /**
     * creates a new Gui instance
     */
    public Gui(){
        createWindow(1280, 720, "Minesweeper");
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

        //set privates
        this.width = width;
        this.height = height;

        unifiedMenuButtonHeight = 1/10*height;
        unifiedMenuButtonWidth = 1/10*width;

        buffer = 20;
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
                mainMenu.setLayout(new GridLayout(0, 1));

                //play button
                JButton play = new JButton("Play");
                play.setPreferredSize(new Dimension(unifiedMenuButtonWidth, unifiedMenuButtonHeight));
                play.addMouseListener(Controller.getMouseHandler());
                mainMenu.add(play);

                //exit button
                JButton exit = new JButton("Exit");
                exit.setPreferredSize(new Dimension(unifiedMenuButtonWidth, unifiedMenuButtonHeight));
                exit.addMouseListener(Controller.getMouseHandler());
                mainMenu.add(exit);

                /*TileButton thisIsATestTile = new TileButton();
                thisIsATestTile.setCoordinates(0,0);
                thisIsATestTile.addMouseListener(Controller.getMouseHandler());
                mainMenu.add(thisIsATestTile);*/

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

    public JFrame getWindow(){
        return window;
    }
}
