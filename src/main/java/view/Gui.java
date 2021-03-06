package view;

import controller.Controller;
import model.Model;
import model.enums.Difficulty;
import model.enums.GameState;

import javax.swing.*;
import java.awt.*;

/**
 * gui class
 * represents the user interface
 */
public class Gui {

    //panels for the different game states
    private JFrame window;
    private JPanel mainMenu;
    private JPanel pauseMenu;
    private JPanel minefield;
    private JPanel endGameMessage;
    private JPanel gamePanel;

    //important vars for the design
    public int width, height;
    public final int menuWidth = 500, menuHeight = 500;

    //flag counter
    private JLabel remainingFlagsDisplay;

    //timer display
    private JSeparator separator;
    private JLabel remainingTimerDisplay;

    //describes the minefields dimensions
    private int minefieldCols;
    private int minefieldRows;

    /**
     * main method
     *
     * @param args startup commands
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::new);
    }


    /**
     * stores all TileField Buttons for index-based access.
     */
    private TileButton[][] tileButtons;

    /**
     * holds the controller instance
     */
    private final Controller controller;

    /**
     * creates a new Gui instance
     */
    public Gui() {
        //initialize controller
        controller = new Controller();
        controller.initializeController(this);

        //create window
        createWindow(menuWidth, menuHeight, "Minesweeper");

        //load main menu
        loadScene(GameState.MAIN_MENU);
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

        //set privates
        this.width = width;
        this.height = height;
    }

    /**
     * creates a new flag display
     */
    public void createFlagsDisplay() {
        remainingFlagsDisplay = new JLabel();
        remainingFlagsDisplay.setForeground(Color.RED);
        remainingFlagsDisplay.setVerticalAlignment(SwingConstants.CENTER);
        remainingFlagsDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        remainingFlagsDisplay.setPreferredSize(new Dimension(50, 50));

        //updateFlagDisplay();
    }

    /**
     * Updates the flag display with the current number of flags that are left to set.
     */
    public void updateFlagDisplay() {
        String text = String.valueOf(controller.getModel().getFlagsToSetLeft());
        remainingFlagsDisplay.setText(text);
    }

    /**
     * Method for getting the displaytext for junit test
     * @return Text that is shown in the display
     */
    public String getFlagDisplayText() {
        return remainingFlagsDisplay.getText();
    }


    /**
     * creates a separator between flags display and new timer display
     */
    public void createSeparatorDisplay() {
        separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
    }


    /**
     * creates a new timer display
     */
    public void createTimerDisplay() {
        remainingTimerDisplay = new JLabel();
        remainingTimerDisplay.setForeground(Color.RED);
        remainingTimerDisplay.setVerticalAlignment(SwingConstants.CENTER);
        remainingTimerDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        remainingTimerDisplay.setPreferredSize(new Dimension(50, 50));
        // updateTimerDisplay();
    }

    /**
     * Updates the timer display with the current number of seconds
     */
    public void updateTimerDisplay(int timeInSeconds) {
        String sec = String.valueOf(timeInSeconds);
        remainingTimerDisplay.setText(sec);
    }

    /**
     * Returns text from TimerDisplay for junit tests.
     * @return String that is displayed in TimerDisplay
     */
    public String getTimerDisplayText() {
        return remainingTimerDisplay.getText();
    }

    /**
     * Returns controller for junit tests
     * @return controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Writes Vlaues for the field sizes
     *
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
                tileButtons[i][j].setPreferredSize(new Dimension(50, 50));
                minefield.add(tileButtons[i][j]);
            }
        }
    }

    /**
     * continues the game after pause menu by
     * repainting the window to its state before pause
     */
    public void continueAfterPause(int timeAfterPause) {
        //clear
        window.getContentPane().removeAll();

        //update the window to display the previous game panel
        window.getContentPane().add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.repaint();
        window.revalidate();
        window.setVisible(true);

        //update the information panels
        updateFlagDisplay();
        //we need to update the timer display here manually after the pause
        //to hide the fact that the timer was actually still running and we just
        //write the old value back
        updateTimerDisplay(timeAfterPause);
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
                JButton playEasy = new JButton("Play easy");
                playEasy.addMouseListener(controller.getMouseHandler());
                mainMenu.add(playEasy);

                JButton playMedium = new JButton("Play medium");
                playMedium.addMouseListener(controller.getMouseHandler());
                mainMenu.add(playMedium);

                JButton playHard = new JButton("Play hard");
                playHard.addMouseListener(controller.getMouseHandler());
                mainMenu.add(playHard);

                //load game button
                JButton load = new JButton("Load game");
                load.addMouseListener(controller.getMouseHandler());
                mainMenu.add(load);

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
                JButton resume = new JButton("Continue");
                resume.addMouseListener(controller.getMouseHandler());
                pauseMenu.add(resume);

                //save game button
                JButton save = new JButton("Save game");
                save.addMouseListener(controller.getMouseHandler());
                pauseMenu.add(save);

                //load game button
                JButton load = new JButton("Load game");
                load.addMouseListener(controller.getMouseHandler());
                pauseMenu.add(load);

                //exit to main menu
                JButton exitTMM = new JButton("Exit to main menu");
                exitTMM.addMouseListener(controller.getMouseHandler());
                pauseMenu.add(exitTMM);

                window.getContentPane().add(pauseMenu);

                window.setVisible(true);
            }
            break;
            case RUNNING: {

                //minefield panel
                minefield = new JPanel();

                gamePanel = new JPanel();
                gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));

                createFlagsDisplay();
                createSeparatorDisplay();
                createTimerDisplay();

                fieldBuilder(minefieldRows, minefieldCols);

                //pause button
                JButton pause = new JButton("Pause");
                pause.addMouseListener(controller.getMouseHandler());
                gamePanel.add(pause);

                gamePanel.add(remainingFlagsDisplay);

                gamePanel.add(separator);
                gamePanel.add(remainingTimerDisplay);

                gamePanel.add(minefield);
                //pack is important to make each button actually use it's preferred Dimension.
                window.add(gamePanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                updateFlagDisplay();
            }
        }
    }

    public JFrame getWindow() {
        return window;
    }


    /**
     * Changes the Text on a button in TileButtons[][]
     *
     * @param i       row index
     * @param j       col index
     * @param newText new text to display
     */
    public void changeButtonText(int i, int j, String newText) {
        tileButtons[i][j].setText(newText);
    }

    /**
     * greys out a button of TileButtons[][] and makes it no more clickable
     *
     * @param i row index
     * @param j col index
     */
    public void greyOutButton(int i, int j) {
        tileButtons[i][j].setContentAreaFilled(true);
        tileButtons[i][j].setFocusable(false);
        tileButtons[i][j].setFocusPainted(false);
        tileButtons[i][j].setRequestFocusEnabled(false);
        tileButtons[i][j].setBackground(Color.lightGray);
    }

    /**
     * iterates over the minefield and updates the text after every new click
     *
     * @param minefield a model instance
     */
    public void updateTileText(Model minefield) {
        for (int i = 0; i < minefieldRows; i++) {
            for (int j = 0; j < minefieldCols; j++) {
                //text needs to be reset after unflagging
                if (!minefield.isSweeped(i, j) && !minefield.isFlagged(i, j)) {
                    changeButtonText(i, j, "");
                }
                if (minefield.isSweeped(i, j)) {
                    if (minefield.isMine(i, j)) {
                        changeButtonText(i, j, "B");
                        greyOutButton(i, j);
                    }
                    else {
                        switch (minefield.getSurroundingMines(i, j)) {
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
                }
                else if (minefield.isFlagged(i, j)) {
                    changeButtonText(i, j, "F");
                }
                else if (minefield.isQmarked(i, j)) {
                    changeButtonText(i, j, "?");
                }
            }
        }
    }

    public void displayWin(int timeNeeded) {
        JOptionPane.showMessageDialog(
                minefield,
                " Congratulations! It took you " + timeNeeded + " seconds to finish the game.",
                "YOU WON!",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void displayFailure(int remainingMines) {
        JOptionPane.showMessageDialog(
                minefield,
                "Too bad! " + remainingMines + " remaining mines not found.",
                "YOU LOST!",
                JOptionPane.PLAIN_MESSAGE);
    }


    public void returnSeed(String seed) {
        JPanel saveSeedPanel = new JPanel();
        saveSeedPanel.setLayout(new BoxLayout(saveSeedPanel, BoxLayout.PAGE_AXIS));

        JLabel instructionField = new JLabel("Highlight and Ctrl-C to save game to clipboard.");
        instructionField.setPreferredSize(new Dimension(50, 50));

        JTextArea seedField = new JTextArea(20, 60);
        seedField.setText(seed);
        seedField.setWrapStyleWord(true);
        seedField.setLineWrap(true);
        seedField.setCaretPosition(0);
        seedField.setEditable(false);

        saveSeedPanel.add(instructionField);
        saveSeedPanel.add(seedField);

        JOptionPane.showMessageDialog(null, new JScrollPane(saveSeedPanel), "Save Game", JOptionPane.PLAIN_MESSAGE);
    }

    public String loadFromSeed() {
        return JOptionPane.showInputDialog(minefield, "Please enter a seed:", "Load Game", JOptionPane.PLAIN_MESSAGE);
    }

    public void invalidSeed() {
        JOptionPane.showMessageDialog(
                minefield,
                "This seed contains an error!",
                "Invalid Seed!",
                JOptionPane.PLAIN_MESSAGE);
    }
}
