package view;

import controller.Controller;
import model.Model;
import model.enums.Difficulty;
import model.enums.GameState;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame window;
    private JPanel mainMenu;
    private JPanel pauseMenu;
    private JPanel game;
    private JPanel endGameMessage;

    private int minefieldWidth;
    private int minefieldHeight;


    /**
     * creates a new Gui instance
     */
    public Gui() {
        createWindow(1280, 720, "Minesweeper");
    }

    /**
     * creates a new window
     *
     * @param width  width of the window
     * @param height height of the window
     * @param title  whatever strange name you wanna call your window
     */
    public void createWindow(int width, int height, String title) {
        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, width, height);
        window.setVisible(true);
    }

    public void calculateSize(Model minefield) {
        Difficulty difficulty = minefield.getDifficulty();
        switch (difficulty) {
            case EASY:
                this.minefieldWidth = 9;
                this.minefieldHeight = 9;
                break;
            case NORMAL:
                this.minefieldWidth = 16;
                this.minefieldHeight = 16;
                break;
            case HARD:
                this.minefieldWidth = 30;
                this.minefieldHeight = 16;
                break;
        }
    }

    /**
     * builds the gamefield grid
     * @param rows number of rows
     * @param cols number of cols
     */

    public void fieldBuilder(int rows, int cols) {
        game.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TileButton button = new TileButton();
                button.setCoordinates(i, j);
                button.addMouseListener(Controller.getMouseHandler());
                game.add(button);
            }
        }
    }

    /**
     * loads a specific scene, which is determined by the given state
     *
     * @param state the state to tell the function what to load
     */
    public void loadScene(GameState state) {
        switch (state) {
            case MAIN_MENU: {
                //main menu panel
                mainMenu = new JPanel();

                //play button
                JButton play = new JButton("Play");
                play.addMouseListener(Controller.getMouseHandler());
                mainMenu.add(play);

                //exit button
                JButton exit = new JButton("Exit");
                exit.addMouseListener(Controller.getMouseHandler());
                mainMenu.add(exit);

                TileButton thisIsATestTile = new TileButton();
                thisIsATestTile.setCoordinates(0, 0);
                thisIsATestTile.addMouseListener(Controller.getMouseHandler());
                mainMenu.add(thisIsATestTile);

                window.getContentPane().add(mainMenu);

                window.setVisible(true);
            }
            break;
            case PAUSE: {
                //pause menu panel
                pauseMenu = new JPanel();

                //continue button
            }
            case RUNNING: {
                //game panel
                game = new JPanel();
                fieldBuilder(minefieldHeight, minefieldWidth);

                window.getContentPane().add(game);
                window.setVisible(true);
            }
        }
    }


}
