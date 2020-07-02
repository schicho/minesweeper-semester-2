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

    private int minefieldCols;
    private int minefieldRows;

    /**
     * stores all TileField Buttons for index-based access.
     */
    private TileButton[][] tileButtons;

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
                this.minefieldRows = 9;
                this.minefieldCols = 9;
                break;
            case NORMAL:
                this.minefieldRows = 16;
                this.minefieldCols = 16;
                break;
            case HARD:
                this.minefieldRows = 16;
                this.minefieldCols = 30;
                break;
        }
        tileButtons = new TileButton[minefieldRows][minefieldCols];
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
                tileButtons[i][j] = new TileButton();
                tileButtons[i][j].setCoordinates(i, j);
                tileButtons[i][j].setText("hini");
                tileButtons[i][j].addMouseListener(Controller.getMouseHandler());
                game.add(tileButtons[i][j]);
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
                fieldBuilder(minefieldRows, minefieldCols);

                window.getContentPane().add(game);
                window.setVisible(true);
            }
        }
    }

    public void changeButtonText(int i, int j, String newText){
        tileButtons[i][j].setText(newText);
    }

    public void greyOutButton(int i, int j){
        tileButtons[i][j].setEnabled(false);
    }


}
