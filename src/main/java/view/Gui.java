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
    private JPanel minefield;
    private JPanel endGameMessage;

    private JLabel remainingFlagsDisplay;

    private JSeparator separator;
    private JLabel remainingTimerDisplay;



    private int minefieldCols;
    private int minefieldRows;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::new);
    }


    /**
     * stores all TileField Buttons for index-based access.
     */
    private TileButton[][] tileButtons;

    private Controller controller;

    /**
     * creates a new Gui instance
     */
    public Gui() {
        controller = new Controller(this);
        controller.setController(controller);
        createWindow(1280, 720,"Minesweeper");

        controller.gameloop();
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

    /**
     * creates a new flag display
     */

    public void createFlagsDisplay(){
        remainingFlagsDisplay = new JLabel();
        remainingFlagsDisplay.setForeground(Color.RED);
        remainingFlagsDisplay.setVerticalAlignment(SwingConstants.CENTER);
        remainingFlagsDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        remainingFlagsDisplay.setPreferredSize(new Dimension(50,50));

        //updateFlagDisplay();
    }

    /**
     * Updates the flag display with the current number of flags that are left to set.
     */
    public void updateFlagDisplay(){
        String text = String.valueOf(controller.getModel().getFlagsToSetLeft());
        remainingFlagsDisplay.setText(text);
    }



    /**
     * creates a separator between flags display and new timer display
     */
    public void createSeparatorDisplay(){
        separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
    }


    /**
     * creates a new timer display
     */
    public void createTimerDisplay(){
        remainingTimerDisplay = new JLabel();
        remainingTimerDisplay.setForeground(Color.RED);
        remainingTimerDisplay.setVerticalAlignment(SwingConstants.CENTER);
        remainingTimerDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        remainingTimerDisplay.setPreferredSize(new Dimension(50,50));
        // updateTimerDisplay();
    }

    /**
     * Updates the timer display with the current number of seconds
     */
    public void updateTimerDisplay(){
        String sec = String.valueOf(model.timer.SecondsTimer.counter);
        remainingTimerDisplay.setText(sec);
    }



    /**
     * Writes Vlaues for the field sizes
     * @param minefield Model of a minefield
     */
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
     *
     * @param rows number of rows
     * @param cols number of cols
     */
    public void fieldBuilder(int rows, int cols) {
        minefield.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tileButtons[i][j] = new TileButton();
                tileButtons[i][j].setCoordinates(i, j);
                tileButtons[i][j].setText("");
                tileButtons[i][j].addMouseListener(controller.getMouseHandler());
                tileButtons[i][j].setPreferredSize(new Dimension(50,50));
                minefield.add(tileButtons[i][j]);
            }
        }
    }

    /**
     * loads a specific scene, which is determined by the given state
     *
     * @param state the state to tell the function what to load
     */
    public void loadScene(GameState state) {
        //clear from previous buttons
        window.getContentPane().removeAll();
        window.setLocationRelativeTo(null);
        switch (state) {
            case MAIN_MENU: {
                //main menu panel
                mainMenu = new JPanel();

                //play button
                JButton play = new JButton("Play");
                play.addMouseListener(controller.getMouseHandler());
                mainMenu.add(play);

                //exit button
                JButton exit = new JButton("Exit");
                exit.addMouseListener(controller.getMouseHandler());
                mainMenu.add(exit);

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
                //minefield panel
                minefield = new JPanel();

                JPanel gamePane = new JPanel();
                gamePane.setLayout(new BoxLayout(gamePane, BoxLayout.PAGE_AXIS));

                createFlagsDisplay();
                createSeparatorDisplay();
                createTimerDisplay();



                fieldBuilder(minefieldRows, minefieldCols);
                createFlagDisplay();

                gamePane.add(remainingFlagsDisplay);

                gamePane.add(separator);
                gamePane.add(remainingTimerDisplay);

                gamePane.add(minefield);
                //pack is important to make each button actually use it's preferred Dimension.
                window.add(gamePane);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        }
    }

    /**
     * Changes the Text on a button in TileButtons[][]
     * @param i row index
     * @param j col index
     * @param newText new text to display
     */
    public void changeButtonText(int i, int j, String newText) {
        tileButtons[i][j].setText(newText);
    }

    /**
     * greys out a button of TileButtons[][] and makes it no more clickable
     * @param i row index
     * @param j col index
     */
    public void greyOutButton(int i, int j) {
        tileButtons[i][j].setContentAreaFilled(true);
        tileButtons[i][j].setFocusable( false );
        tileButtons[i][j].setFocusPainted( false );
        tileButtons[i][j].setRequestFocusEnabled( false );
        tileButtons[i][j].setBackground( Color.lightGray );
    }

    /**
     * iterates over the minefield and updates the text after every new click
     * @param minefield a model instance
     */
    public void updateTileText(Model minefield) {
        for (int i = 0; i < minefieldRows; i++) {
            for (int j = 0; j < minefieldCols; j++) {
                //text needs to be reset after unflagging
                if(!minefield.isSweeped(i,j) && !minefield.isFlagged(i,j)){
                    changeButtonText(i,j, "");
                }
                if (minefield.isSweeped(i, j)) {
                    if (minefield.isMine(i, j)) {
                        changeButtonText(i, j, "B");
                        greyOutButton(i, j);
                    } else {
                        switch(minefield.getSurroundingMines(i, j)){
                            case 0:
                                changeButtonText(i, j, "");
                                greyOutButton(i, j);
                                break;
                            case 1:
                                tileButtons[i][j].setForeground(new java.awt.Color(51, 51, 255));
                                changeButtonText(i, j, "" + minefield.getSurroundingMines(i, j));
                                greyOutButton(i, j);
                                break;
                            case 2:
                                tileButtons[i][j].setForeground(new java.awt.Color(0, 153, 0));
                                changeButtonText(i, j, "" + minefield.getSurroundingMines(i, j));
                                greyOutButton(i, j);
                                break;
                            case 3:
                                tileButtons[i][j].setForeground(new java.awt.Color(255, 0, 0));
                                changeButtonText(i, j, "" + minefield.getSurroundingMines(i, j));
                                greyOutButton(i, j);
                                break;
                            case 4:
                                tileButtons[i][j].setForeground(new java.awt.Color(0, 0, 102));
                                changeButtonText(i, j, "" + minefield.getSurroundingMines(i, j));
                                greyOutButton(i, j);
                                break;
                            case 5:
                                tileButtons[i][j].setForeground(new java.awt.Color(102, 0, 0));
                                changeButtonText(i, j, "" + minefield.getSurroundingMines(i, j));
                                greyOutButton(i, j);
                                break;
                        }
                    }
                } else if (minefield.isFlagged(i, j)) {
                    changeButtonText(i, j, "F");
                } else  if(minefield.isQmarked(i,j)) {
                    changeButtonText(i, j, "?");
                }
            }
        }
    }

    public void displayWin(){
        JOptionPane.showMessageDialog(
                minefield,

                " Congratulations! Du hast " + model.timer.SecondsTimer.counter + " Sekunden fuer das Spiel gebraucht.",

                "YOU WON!",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void displayFailure(int remainingMines){
        JOptionPane.showMessageDialog(
                minefield,
                "Too bad! " + remainingMines + " remaining mines not found.",
                "YOU LOST!",
                JOptionPane.PLAIN_MESSAGE);
    }


}
